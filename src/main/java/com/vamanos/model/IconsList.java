package com.vamanos.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vamanos.util.Image2Base64StringConverter;
import com.vamanos.util.JsonUtil;

public class IconsList {
	private static final String FOLDER_ICON = "static/icons/folder.png";
	private static final String FILE_ICON = "static/icons/file.png";
	private static final String FILE_WORD_ICON = "static/icons/file_word.png";
	private static final String FILE_PPT_ICON = "static/icons/file_ppt.png";
	private static final String FILE_EXCEL_ICON = "static/icons/file_excel.png";
	private static final String FILE_PDF_ICON = "static/icons/file_pdf.png";
	private static final String FILE_COMPRESSED_ICON = "static/icons/file_compressed.jpeg";
	private static final String ADMIN_ICON = "static/icons/admin.jpg";
	private static final String FOLDER_PERSONAL_ICON = "static/icons/personal_folder.jpg";
	private static final String USER_CONSOLE_ICON = "static/icons/user_console.png";
	private static final String TEAM_CONSOLE_ICON = "static/icons/team_console.png";
	private static final String ICON_CONSOLE_ICON = "static/icons/icon_console.jpg";
	private static final String TRASH_BIN_ICON = "static/icons/folder_trash_bin.png";
	private static final String TEAM_MANAGER_ICON = "static/icons/team_manager.png";
	public ObjectNode getIconList(){
		Map<String,String> iconMap = new HashMap<>();
		iconMap.put("folder", Image2Base64StringConverter.convert(FOLDER_ICON));
		iconMap.put("file", Image2Base64StringConverter.convert(FILE_ICON));
		iconMap.put("file-word", Image2Base64StringConverter.convert(FILE_WORD_ICON));
		iconMap.put("file-ppt", Image2Base64StringConverter.convert(FILE_PPT_ICON));
		iconMap.put("file-pdf", Image2Base64StringConverter.convert(FILE_PDF_ICON));
		iconMap.put("file-excel", Image2Base64StringConverter.convert(FILE_EXCEL_ICON));
		iconMap.put("file-compressed", Image2Base64StringConverter.convert(FILE_COMPRESSED_ICON));
		iconMap.put("admin", Image2Base64StringConverter.convert(ADMIN_ICON));
		iconMap.put("folder-personal", Image2Base64StringConverter.convert(FOLDER_PERSONAL_ICON));
		iconMap.put("user-console", Image2Base64StringConverter.convert(USER_CONSOLE_ICON));
		iconMap.put("team-console", Image2Base64StringConverter.convert(TEAM_CONSOLE_ICON));
		iconMap.put("icon-console", Image2Base64StringConverter.convert(ICON_CONSOLE_ICON));
		iconMap.put("folder-trash-bin", Image2Base64StringConverter.convert(TRASH_BIN_ICON));
		iconMap.put("team-manager", Image2Base64StringConverter.convert(TEAM_MANAGER_ICON));
		return JsonUtil.getJsonObjectFromMap(iconMap);
	}
}
