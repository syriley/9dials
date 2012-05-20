package jobs;

import java.io.File;
import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;

import play.Logger;
import play.jobs.Job;

public class AudioConverterJob extends Job<File> {
	private File input;
	private String outputMediaType;
	private String outputName;
	private static String CMD_BASE = "ffmpeg -y -i %s %s";

	public AudioConverterJob(File input, String outputName,
			String outputMediaType) {
		this.outputName = outputName;
		this.input = input;
		this.outputMediaType = outputMediaType;
	}

	public File doJobWithResult() throws IOException {
		String outputFilename = buildOutputFilename(input, outputName,
				outputMediaType);
		String cmdToRun = buildCommand(input, outputFilename);
		Logger.debug("Converting audio using: " + cmdToRun);
		File output = convertAudioNatively(outputFilename, cmdToRun);
		verifyOutput(cmdToRun, output);
		return output;
	}

	public static String buildOutputFilename(File input, String outputFilename,
			String outputMediaType) {
		String outputFolder = getOutputFolder(input);
		return getOutputFilename(outputFolder, outputFilename, outputMediaType);

	}

	private void verifyOutput(String cmdToRun, File output) throws IOException {
		if (output == null || output.length() == 0) {
			String error = buildErrorMessage(cmdToRun);
			throw new IOException(error);
		}
	}

	private static String buildCommand(File input, String outputFilename) {
		return String.format(CMD_BASE, input.getAbsolutePath(), outputFilename);
	}

	private File convertAudioNatively(String outputFilename, String cmdToRun)
			throws IOException {
		File output = null;
		try {
			CommandLine cmdLine = CommandLine.parse(cmdToRun);
			DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

			ExecuteWatchdog watchdog = new ExecuteWatchdog(60 * 1000);
			Executor executor = new DefaultExecutor();
			executor.setExitValue(1);
			executor.setWatchdog(watchdog);
			executor.setStreamHandler(new PumpStreamHandler(null, null, null));
			executor.execute(cmdLine, resultHandler);
			// some time later the result handler callback was invoked so we
			// can safely request the exit value
			resultHandler.waitFor();
			output = new File(outputFilename);
		} catch (IOException e) {
			String error = buildErrorMessage(cmdToRun);
			throw new IOException(error, e);
		} catch (InterruptedException e) {
			String error = buildErrorMessage(cmdToRun);
			throw new IOException(error, e);
		}
		return output;
	}

	private String buildErrorMessage(String cmdToRun) {
		StringBuilder message = new StringBuilder();
		message.append("Unable to convert audio using command: ").append(
				cmdToRun);
		return message.toString();
	}

	private static String getOutputFolder(File input) {
		return input.getParentFile().getAbsolutePath() + "/";
	}

	private static String getOutputFilename(String outputFolder,
			String outputName, String outputMediaType) {
		return outputFolder + outputName + "." + outputMediaType;
	}
	
}
