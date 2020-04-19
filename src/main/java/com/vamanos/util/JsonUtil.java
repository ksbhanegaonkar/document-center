package com.vamanos.util;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vamanos.entity.AppInstanceData;
import com.vamanos.entity.AppInstancePayload;
import com.vamanos.model.ContextMenuList;
import com.vamanos.model.DesktopItemList;
import com.vamanos.model.StartMenuList;

public class JsonUtil {
	
	private static ObjectMapper mapper = new ObjectMapper();
	static StartMenuList startMenuList = null;
	static ContextMenuList contextMenuList = null;
	static DesktopItemList desktopItemList = null;
	static DesktopUpdateUtil util = new DesktopUpdateUtil();

	
	
	public static String getJsonValue(String json,String key) {
		try {
			JsonNode node = mapper.readTree(json);
			return node.get(key).asText();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static ArrayNode createJsonArray(List<String> values) {
		ArrayNode arrayNode = mapper.createArrayNode();
		values.forEach(v->arrayNode.add(v));
		return arrayNode;
	
	}
	
	public static ObjectNode getEmptyJsonObject() {
		return mapper.createObjectNode();
	}
	
	public static ObjectNode getJsonObjectFromMap(Map<String,String> map) {
		ObjectNode obj = getEmptyJsonObject();
		for(String key : map.keySet()) {
			obj.put(key, map.get(key));
		}
		return obj;
	}
	
	public static ObjectNode getJsonObjectFromObjectMap(Map<String,Map<String,String>> map) {
		ObjectNode obj = getEmptyJsonObject();
		for(String key : map.keySet()) {
			obj.set(key, getJsonObjectFromMap(map.get(key)));
		}
		return obj;
	}
	
	
	public static ObjectNode getJsonObjectFromListMap(Map<String,List<String>> map) {
		ObjectNode obj = getEmptyJsonObject();
		for(String key : map.keySet()) {
			obj.set(key, createJsonArray(map.get(key)));
		}
		return obj;
	}
	
	public static void main(String[] args) throws JsonProcessingException {
		startMenuList = new StartMenuList();
		contextMenuList = new ContextMenuList();
		desktopItemList = new DesktopItemList();
		
		
		
		ObjectNode objectNode = mapper.createObjectNode();
		objectNode.set("test", startMenuList.getStartMenuList());
	
		
		
		System.out.println(util.updateDesktop("{\"state\":\"init\"}"));
		
	}

	public static ObjectNode getPayloadHistoryAsJsonObject(AppInstancePayload app){
		ObjectNode node = getEmptyJsonObject();
		node.put("version", app.getVersionNumber());
		node.put("updated_user", app.getUpdatedUserName());
		node.put("timestamp", app.getUpdatedTimestamp().toString());
		node.put("comment", app.getUpdateComment());
		return node;
	}
	
	
	public static ObjectNode getAppInstanceDataAsJsonObject(AppInstanceData app) {
		
		ObjectNode node = getEmptyJsonObject();
		node.put("appId", app.getId());
		node.put("appName", app.getName());
		node.put("appType", app.getType());
		return node;
	}
	
	public static ArrayNode getAppListAsJsonArray(List<AppInstanceData> apps) {
		ArrayNode arrayNode = mapper.createArrayNode();
		apps.forEach(v->arrayNode.add(getAppInstanceDataAsJsonObject(v)));
		return arrayNode;
	}

	public static ArrayNode getAppHistoryAsJsonArray(List<AppInstancePayload> apps) {
		ArrayNode arrayNode = mapper.createArrayNode();
		apps.forEach(v->arrayNode.add(getPayloadHistoryAsJsonObject(v)));
		return arrayNode;
	}

	public static String getUpdatedFolderPayload(String currentPayload, int appId, String appName, String appType){
		ArrayNode payload = null;
		try {
			//[{"appId":41,"appName":"New Folder","appType":"folder"}]
			ObjectNode newAppToAdd = getEmptyJsonObject();
			newAppToAdd.put("appId",appId);
			newAppToAdd.put("appName",appName);
			newAppToAdd.put("appType",appType);
			payload = (ArrayNode) mapper.readTree(currentPayload);
			payload.add(newAppToAdd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return payload!=null?payload.toString():null;
	}


	public static String getUpdatedFolderPayload(String currentPayload, AppInstanceData app){
		ArrayNode payload = null;
		try {
			//[{"appId":41,"appName":"New Folder","appType":"folder"}]
			ObjectNode newAppToAdd = getEmptyJsonObject();
			newAppToAdd.put("appId",app.getId());
			newAppToAdd.put("appName",app.getName());
			newAppToAdd.put("appType",app.getType());
			payload = (ArrayNode) mapper.readTree(currentPayload);
			payload.add(newAppToAdd);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return payload!=null?payload.toString():null;
	}

	public static String getUpdatedTeamManagerAppPayload(String currentPayload,String teamName){
		try {
			ArrayNode node = (ArrayNode) mapper.readTree(currentPayload);
			node.add(teamName);
			return node.toString();
		} catch (IOException e) {
			return "[]";
		}
	}
}


