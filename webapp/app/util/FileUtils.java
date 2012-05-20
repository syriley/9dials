package util;

import java.io.File;

public class FileUtils {
	private static final String EXTENSION_SEPARATOR = ".";
	
	public static String extension(File input) {
			int dot = input.getPath().lastIndexOf(EXTENSION_SEPARATOR);
			return input.getPath().substring(dot + 1);
		}
}

