package com.vamanos.util;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

public class Image2Base64StringConverter {
	private static final String IMAGE_STRING_PRIFIX = "data:image/png;base64,";
	public static String convert(String path) {
		byte[] fileContent;
		try {
			ClassPathResource resource = new ClassPathResource(path);
			//fileContent = FileUtils.readFileToByteArray(ResourceUtils.getFile("classpath:"+path));
			fileContent = FileCopyUtils.copyToByteArray(resource.getInputStream());
			 return IMAGE_STRING_PRIFIX + Base64.getEncoder().encodeToString(fileContent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
