package com.vamanos.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.vamanos.util.JsonUtil;

public class StartMenuList {
	List<String> startMenuList = new ArrayList<String>();
	
	public StartMenuList() {
		
		startMenuList.add("My Folder");
		startMenuList.add("My Bookmarks");
		startMenuList.add("My Notes a");
		startMenuList.add("Logout");
		
	}

	public ArrayNode getStartMenuList() {

		return JsonUtil.createJsonArray(startMenuList);
		
	}

	public void setStartMenuList(List<String> startMenuList) {
		this.startMenuList = startMenuList;
	}
	
	
	
}
