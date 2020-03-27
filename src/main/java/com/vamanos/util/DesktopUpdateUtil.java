package com.vamanos.util;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vamanos.entity.AppInstanceData;
import com.vamanos.model.ContextMenuList;
import com.vamanos.model.DesktopItemList;
import com.vamanos.model.DesktopItemView;
import com.vamanos.model.IconsList;
import com.vamanos.model.StartMenuList;
import com.vamanos.service.AppService;

@Component
public class DesktopUpdateUtil {
	
	@Autowired
	AppService appService;
	
	StartMenuList startMenuList = null;
	ContextMenuList contextMenuList = null;
	DesktopItemList desktopItemList = null;
	DesktopItemView desktopItemView = null;
	IconsList iconsList = null;
	
	public ObjectNode updateDesktop(String state) {
		desktopItemView = new DesktopItemView();


		if("init".equals(JsonUtil.getJsonValue(state, "state"))) {
			ObjectNode node = JsonUtil.getEmptyJsonObject();
			startMenuList = new StartMenuList();
			contextMenuList = new ContextMenuList();
			iconsList = new IconsList();
		
			
			node.set("startMenuOption", startMenuList.getStartMenuList());
			//node.set("contextMenuOption", contextMenuList.getcontextMenuList());
			node.set("contextMenuOption", JsonUtil.getJsonObjectFromListMap(appService.getContextMenuOptions()));
			node.set("iconsList", iconsList.getIconList());
			node.put("loggedInUserName", SecurityContextHolder.getContext().getAuthentication().getName());
			//System.out.println("Context menu list is ::::"+appService.getContextMenuOptions());
			return node;
		}
		
		  else if("update".equals(JsonUtil.getJsonValue(state, "state"))){
		  if("on-double-click".equals(JsonUtil.getJsonValue(state, "action"))) 
		  	{
			  return desktopItemView.getDesktopItemView(JsonUtil.getJsonValue(state, "desktopItem"));  
		  	}
		  else if("on-desktop-icons-load".equals(JsonUtil.getJsonValue(state, "action"))) 
		  	{
			  iconsList = new IconsList();
			  return iconsList.getIconList();
			 
			}
		  else if("on-desktop-item-load".equals(JsonUtil.getJsonValue(state, "action"))) 
		  	{
			  desktopItemList = new DesktopItemList();
			  
			 
			  
			  return null;
			 
			}
		  
		  }
		 

		return JsonUtil.getEmptyJsonObject();
	}
	
	public ArrayNode getDesktopApps() {
		 List<AppInstanceData> globalApps = appService.getGlobalApps();
		 return JsonUtil.getAppListAsJsonArray(globalApps);
	}
	
	public String getAppPayload(int appId) {
		 return appService.getAppPayload(appId);
	}
	
	public String updatePayload(int appId,String payload) {
		 return appService.updateAppPayload(appId, payload);
	}
	
	public String onContextMenuOptionClick(int appId, String action) {
		//System.out.println("app id is :::"+appId+" Action is ::: "+action);
		String payload = "";
		if(action.contains("Copy")) {
			appService.copyApp(appId);
		}
		else if(action.contains("Paste")) {
			appService.pasteApp(appId);
		}
		else if(action.contains("Download")) {
			payload = getAppPayload(appId);
		}
		else if(action.contains("New Folder")) {
			appService.createFolder("New Folder");
		}
		return payload;
	}
	
	public void uploadFile(String fileName, MultipartFile file) {
		String fileType = "file";
		
		  if(fileName.contains("txt")) { fileType = "file"; }else
		  if(fileName.contains("doc")) { fileType = "file-word";}else
		  if(fileName.contains("xls")) { fileType = "file-excel"; }else
		  if(fileName.contains("pdf")) { fileType = "file-pdf"; }else
		  if(fileName.contains("ppt")) { fileType = "file-ppt"; }else
	      if(fileName.contains("zip")) { fileType = "file-compressed"; }
		 
		try {
			appService.createNewApp(fileName,fileType,file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public byte[] getAppPayloadAsFile(int appId) {
		return appService.getAppPayloadAsFile(appId);
	}
}
