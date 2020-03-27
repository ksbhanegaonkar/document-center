package com.vamanos.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vamanos.util.DesktopItemViewUtil;
import com.vamanos.util.JsonUtil;

public class DesktopItemView {
	Map<String,Map<String,String>> desktopItemView = new HashMap<>();
	DesktopItemViewUtil desktopItemViewUtil = new DesktopItemViewUtil();
	public DesktopItemView() {
		
	}

	public ObjectNode getDesktopItemView(String item) {

	    desktopItemView = desktopItemViewUtil.getDesktopItemViewInfo(item);
		return JsonUtil.getJsonObjectFromObjectMap(desktopItemView);
	}


	

	
	
}
