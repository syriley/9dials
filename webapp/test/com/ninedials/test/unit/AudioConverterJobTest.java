package com.ninedials.test.unit;

import java.io.File;
import java.io.IOException;

import jobs.AudioConverterJob;

import org.junit.Before;
import org.junit.Test;

import play.Logger;
import play.test.UnitTest;

public class AudioConverterJobTest extends NineDialsTest {
	private static final String OUTPUT_FILENAME = "test/converter-test-output.mp3";

	@Before
	public void setup() {
		File testOuput = new File(OUTPUT_FILENAME);
		if (testOuput != null) {
			testOuput.delete();
		}
	}

	@Test
	public void testAudioConversion() throws IOException {
		File input = new File("test/converter-test-input.ogg");
		AudioConverterJob job = new AudioConverterJob(input,
				"converter-test-output", "mp3");
		File output = null;
		output = job.doJobWithResult();
		assertNotNull("Audio conversion failed", output);
		if (output != null) {
			output.delete();
		}
	}
}
