package com.vamanos.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class DesktopItemViewUtil {

	public Map<String, Map<String,String>> getDesktopItemViewInfo(String item) {
		Map<String,String> itemData = new HashMap<>();
		Map<String,String> payload = new HashMap<>();
		if("MyFolder21".equals(item)) {
			payload.put("file1", "file");
			payload.put("file2", "file");
			payload.put("file3", "file");
			payload.put("MyFolder22", "folder");
			payload.put("folder2", "folder");
			payload.put("folder3", "folder");
			
			itemData.put("name", item);
			itemData.put("type", "folder");
			String s = JsonUtil.getJsonObjectFromMap(payload).textValue();
			itemData.put("payload",JsonUtil.getJsonObjectFromMap(payload).asText());
		}
		
		else if("MyFolder22".equals(item)) {
			payload.put("file1", "file");
			payload.put("file2", "file");
			payload.put("file3", "file");
			
			itemData.put("name", item);
			itemData.put("type", "folder");
			itemData.put("payload",JsonUtil.getJsonObjectFromMap(payload).asText());
		}
		
		Map<String,Map<String,String>> map = new HashMap<>();
		map.put("desktop-item-data", itemData);
		return map;
	}

}
