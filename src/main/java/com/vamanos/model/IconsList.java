package com.vamanos.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vamanos.util.Image2Base64StringConverter;
import com.vamanos.util.JsonUtil;

public class IconsList {
	private static final String FOLDER_ICON = "src/main/resources/static/icons/folder.png";
	private static final String FILE_ICON = "src/main/resources/static/icons/file.png";
	private static final String FILE_WORD_ICON = "src/main/resources/static/icons/file_word.png";
	private static final String FILE_PPT_ICON = "src/main/resources/static/icons/file_ppt.png";
	private static final String FILE_EXCEL_ICON = "src/main/resources/static/icons/file_excel.png";
	private static final String FILE_PDF_ICON = "src/main/resources/static/icons/file_pdf.png";
	private static final String FILE_COMPRESSED_ICON = "src/main/resources/static/icons/file_compressed.jpeg";
	public ObjectNode getIconList(){
		Map<String,String> iconMap = new HashMap<>();
		iconMap.put("folder", Image2Base64StringConverter.convert(FOLDER_ICON));
		iconMap.put("file", Image2Base64StringConverter.convert(FILE_ICON));
		iconMap.put("file-word", Image2Base64StringConverter.convert(FILE_WORD_ICON));
		iconMap.put("file-ppt", Image2Base64StringConverter.convert(FILE_PPT_ICON));
		iconMap.put("file-pdf", Image2Base64StringConverter.convert(FILE_PDF_ICON));
		iconMap.put("file-excel", Image2Base64StringConverter.convert(FILE_EXCEL_ICON));
		iconMap.put("file-compressed", Image2Base64StringConverter.convert(FILE_COMPRESSED_ICON));

		return JsonUtil.getJsonObjectFromMap(iconMap);
	}
}
