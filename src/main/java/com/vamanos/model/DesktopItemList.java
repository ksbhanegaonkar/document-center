package com.vamanos.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vamanos.service.AppService;
import com.vamanos.util.JsonUtil;

public class DesktopItemList {
	Map<String,String> desktopItemList = new HashMap<>();

	
	public DesktopItemList() {
		
		/*
		 * desktopItemList.put("MyFile.txt", "file"); desktopItemList.put("MyFile2.txt",
		 * "file"); desktopItemList.put("MyFolder1", "folder");
		 * desktopItemList.put("MyFolder2", "folder"); desktopItemList.put("VamanOS",
		 * "file"); desktopItemList.put("KedarsFile.txt", "file");
		 * 
		 * for(int i=0;i<20;i++) { desktopItemList.put("MyFolder2"+i, "folder"); }
		 */
		 
	}

	public ObjectNode getDesktopItemList(AppService appService) {
		//desktopItemList.putAll(appService.getGlobalApps());
		return JsonUtil.getJsonObjectFromMap(desktopItemList);
	}

	public Map<String,String> getDesktopItemMap() {
		return desktopItemList;
	}
	public void setDesktopItemList(Map<String, String> desktopItemList) {
		this.desktopItemList = desktopItemList;
	}
}
