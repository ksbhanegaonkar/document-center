package com.vamanos.util;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;

public class Image2Base64StringConverter {
	private static final String IMAGE_STRING_PRIFIX = "data:image/png;base64,";
	public static String convert(String path) {
		byte[] fileContent;
		try {
			fileContent = FileUtils.readFileToByteArray(new File(path));
			 return IMAGE_STRING_PRIFIX + Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
