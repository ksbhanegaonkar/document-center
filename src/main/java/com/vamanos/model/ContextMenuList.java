package com.vamanos.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vamanos.util.JsonUtil;

@XmlRootElement
public class ContextMenuList {
	//Map<String,List<String>> contextMenuList = new HashMap<String, List<String>>();
	Map<String,List<String>> contextMenuList = new HashMap<String, List<String>>();
	
	/*
	 *     "desktop-wallpaper":['','','','','Cut',''],
      "start-menu-button":['Option 1','Option 2','Option 3','Option 4'],
      "task-bar":['','Option 6','Option 7','Option 8'],
      "desktop-item-folder":['','w','','','',''],
      "desktop-item-file":['','',','','','']
      ['','','','']
	 * */
	
	public ContextMenuList() {
		// TODO Auto-generated constructor stub
		List<String> desktopWallpaperMenuList = new ArrayList<>();
		desktopWallpaperMenuList.add("New Sprint 1");
		desktopWallpaperMenuList.add("New User Story");
		desktopWallpaperMenuList.add("Refresh");
		desktopWallpaperMenuList.add("Copy");
		desktopWallpaperMenuList.add("Paste");
		
		List<String> taskBarMenuList = new ArrayList<>();
		taskBarMenuList.add("Task bar option 12");
		taskBarMenuList.add("Task bar option 2");
		taskBarMenuList.add("Task bar option 3");
		taskBarMenuList.add("Task bar option 4");
		taskBarMenuList.add("Task bar option 5");
		
		List<String> desktopItemFolderMenuList = new ArrayList<>();
		desktopItemFolderMenuList.add("Open folder1");
		desktopItemFolderMenuList.add("Open folder in new window");
		desktopItemFolderMenuList.add("Bookmark folder");
		desktopItemFolderMenuList.add("Copy Folder");
		desktopItemFolderMenuList.add("Rename Folder");
		desktopItemFolderMenuList.add("Delete Folder");
		

		
		List<String> desktopItemFileMenuList = new ArrayList<>();
		desktopItemFileMenuList.add("Open file1");
		desktopItemFileMenuList.add("Open file in new window");
		desktopItemFileMenuList.add("Bookmark file");
		desktopItemFileMenuList.add("Copy file");
		desktopItemFileMenuList.add("Rename File");
		desktopItemFileMenuList.add("Delete file");
		
		List<String> startButtonContextMenuList = new ArrayList<>();
		startButtonContextMenuList.add("My Folder1");
		startButtonContextMenuList.add("My Bookmarks");
		startButtonContextMenuList.add("My Notes");
		startButtonContextMenuList.add("Logout");

		


		contextMenuList.put("desktop-wallpaper", desktopWallpaperMenuList);
		contextMenuList.put("task-bar", taskBarMenuList);
		contextMenuList.put("desktop-item-folder", desktopItemFolderMenuList);
		contextMenuList.put("desktop-item-file", desktopItemFileMenuList);
		contextMenuList.put("start-menu-button", startButtonContextMenuList);
		
		
	}

	public  ObjectNode getcontextMenuList() {
		
		return JsonUtil.getJsonObjectFromListMap(contextMenuList);
	}

	public void setcontextMenuList(Map<String, List<String>> contextMenuList) {
		this.contextMenuList = contextMenuList;
	}
	
	
	
}
