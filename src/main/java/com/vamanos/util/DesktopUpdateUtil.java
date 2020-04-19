package com.vamanos.util;

import java.io.IOException;
import java.util.List;

import com.vamanos.entity.AppInstancePayload;
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
		 List<AppInstanceData> globalApps = appService.getAllAppsForUser();
		 return JsonUtil.getAppListAsJsonArray(globalApps);
	}
	
	public String getAppPayload(int appId) {
		 return appService.getAppPayload(appId);
	}
	public String getActiveVersionAppPayload(int appId) {
		return appService.getActiveVersionAppPayload(appId);
	}

	public String getSpecificVersionAppPayload(int appId,int version) {
		return appService.getSpecificVersionAppPayload(appId,version);
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
			//payload = getAppPayload(appId);
			payload = getActiveVersionAppPayload(appId);
		}
		else if(action.contains("New Folder")) {
			appService.createFolder("New Folder");
		}
		return payload;
	}

	public String onContextMenuOptionClick(int appId, String action, int parentAppId) {
		//System.out.println("app id is :::"+appId+" Action is ::: "+action);
		String payload = "";
		if(action.contains("Copy")) {
			appService.copyApp(appId);
		}
		else if(action.contains("Paste")) {
			appService.pasteApp(appId);
		}
		else if(action.contains("Download")) {
			payload = getActiveVersionAppPayload(appId);
		}
		else if(action.contains("New Folder")) {
			appService.createFolder("New Folder",parentAppId);
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

    public void uploadFile(String fileName, MultipartFile file, int parentAppId) {
        String fileType = "file";

        if(fileName.contains("txt")) { fileType = "file"; }else
        if(fileName.contains("doc")) { fileType = "file-word";}else
        if(fileName.contains("xls")) { fileType = "file-excel"; }else
        if(fileName.contains("pdf")) { fileType = "file-pdf"; }else
        if(fileName.contains("ppt")) { fileType = "file-ppt"; }else
        if(fileName.contains("zip")) { fileType = "file-compressed"; }

        try {
            appService.createNewApp(fileName,fileType,file,parentAppId);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

	public void uploadFileInFolder(String fileName, MultipartFile file, String folderName) {
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

	public byte[] getActiveVersionAppPayloadAsFile(int appId) {
		return appService.getActiveVersionAppPayloadAsFile(appId);
	}

	public byte[] getSpecificVersionAppPayloadAsFile(int appId, int version) {
		return appService.getSpecificVersionAppPayloadAsFile(appId, version);
	}

	public boolean renameApp(int appId, String newName){
		return appService.renameApp(appId,newName);
	}

	public boolean renameApp(int appId, String newName, int parentAppId) {
		return appService.renameApp(appId,newName,parentAppId);
	}

	public AppInstanceData getPersonalFolder(){
		return appService.getPersonalFolder();
	}

	public String updateAppVersion(int appId, MultipartFile file, String payload){
		return appService.updateAppVersion(appId, file, payload);
	}

	public List<AppInstancePayload> getAppPayloadHistory(int appId) {
		return appService.getAppPayloadHistory(appId);
	}

	public void addUser(String userName, String email) {
		appService.addUser(userName,email);
	}

	public void addTeam(String teamName, String teamDL) {
		appService.addTeam(teamName,teamDL);
	}

	public List<String> getAllUsers() {
		return appService.getAllUsers();
	}

	public void addTeam(String teamName, String teamDL, List<String> managers) {
		appService.addTeam(teamName,teamDL,managers);
	}

	public void addTeamMembers(List<String> teamMembers, String teamName) {
		appService.addTeamMembers(teamMembers,teamName);
	}

	public String getAllTeamsOfUser(int appId) {
		return appService.getAllTeamsOfUser(appId);
	}
}
