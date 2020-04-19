package com.vamanos.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vamanos.entity.AppInstanceData;
import com.vamanos.util.DesktopUpdateUtil;
import com.vamanos.util.JsonUtil;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/services")
public class ActionController {
	/*
	 * @Autowired private BookRepository repository;
	 */
	@Autowired
	DesktopUpdateUtil util;

    // Find
    @GetMapping("/addUser/{id}")
    String findAll(@PathVariable int id) {
    	//System.out.println("Logged in user is :::::"+SecurityContextHolder.getContext().getAuthentication().getName());
        return "Test"+id+SecurityContextHolder.getContext().getAuthentication().getName();
    }
    
    @PostMapping("/onaction")
    public ObjectNode onAction(@RequestBody String action) {
    	//System.out.println("user is ::"+SecurityContextHolder.getContext().getAuthentication().getName());
    	//DesktopUpdateUtil util = new DesktopUpdateUtil();
        return util.updateDesktop(action);
    }
    
    @GetMapping("/getdesktopitems")
    public ArrayNode getDesktopItems() {
    	//System.out.println("user is ::"+SecurityContextHolder.getContext().getAuthentication().getName());
    	//DesktopUpdateUtil util = new DesktopUpdateUtil();
        return util.getDesktopApps();
    }
    
    @GetMapping("/getapppayload/{appId}")
    public ObjectNode getAppPayload(@PathVariable int appId) {
    	ObjectNode node = JsonUtil.getEmptyJsonObject();
    	node.put("payload", util.getAppPayload(appId));
    	//[{"appId":41,"appName":"New Folder","appType":"folder"}]
		//node.put("payload", "[{\"appId\":41,\"appName\":\"New Folder\",\"appType\":\"folder\"}]");
        return node;
    }

    @GetMapping("/getapphistory/{appId}")
    public ArrayNode getAppHistory(@PathVariable int appId) {
        ObjectNode node = JsonUtil.getEmptyJsonObject();
        node.put("payload", util.getAppPayload(appId));
        //[{"appId":41,"appName":"New Folder","appType":"folder"}]
        //node.put("payload", "[{\"appId\":41,\"appName\":\"New Folder\",\"appType\":\"folder\"}]");
        return JsonUtil.getAppHistoryAsJsonArray(util.getAppPayloadHistory(appId));
    }

    @PostMapping("/updateapppayload")
    public ObjectNode updatePayload(@RequestBody ObjectNode app) {
        int appId = app.get("appId").asInt();
        String payload = app.get("payload").asText();
        util.updatePayload(appId, payload);
        return app;
    }

    @PostMapping("/addteamold")
    public ObjectNode addTeam(@RequestBody ObjectNode app) {

        String teamName = app.get("teamname").asText();
        String teamDL = app.get("teamdl").asText();
        util.addTeam(teamName,teamDL);
        return app;
    }

	@PostMapping("/addteam")
	public ObjectNode addTeamNew(@RequestBody ObjectNode app) {

  		String teamName = app.get("teamname").asText();
		String teamDL = app.get("teamdl").asText();
		ArrayNode array = (ArrayNode) app.get("managers");
		List<String> managers = new ArrayList<>();
		array.forEach(n->managers.add(n.textValue()));
		util.addTeam(teamName,teamDL,managers);
		return app;
	}

	@PostMapping("/addteammembers")
	public ObjectNode addTeamMembers(@RequestBody ObjectNode app) {
		ArrayNode array = (ArrayNode) app.get("teammembers");
		String teamName = app.get("teamname").asText();
		List<String> teamMembers = new ArrayList<>();
		array.forEach(n->teamMembers.add(n.textValue()));
		util.addTeamMembers(teamMembers,teamName);
		return app;
	}

	@GetMapping("/getteamsofuser/{appId}")
	public String getTeamsOfUser(@PathVariable int appId) {
		return util.getAllTeamsOfUser(appId);
	}

	@PostMapping("/adduser")
	public ObjectNode addUser(@RequestBody ObjectNode app) {

		String userName = app.get("username").asText();
		String email = app.get("email").asText();
		util.addUser(userName,email);
		return app;
	}

	@GetMapping("/getallusers")
	public List<String> addUser() {
		return util.getAllUsers();
	}

    @PostMapping("/updateappversion")
    public ObjectNode updateAppVersion(@RequestParam int appId, @RequestParam MultipartFile file, @RequestParam String comment) {
		ObjectNode node = JsonUtil.getEmptyJsonObject();
		util.updateAppVersion(appId,file,comment);
        return node;
    }
    
    @PostMapping("/oncontextmenuaction")
    public ObjectNode onContextMenuOption(@RequestBody ObjectNode app) {
    	System.out.println(app);
    	String item = app.get("item").asText();
    	String option = app.get("option").asText();

    	int appId = 0;
		if (item == null || "".equals(item) || "desktop-wallpaper".equals(item)) {
			app.put("fileName", "New Folder");
			String payload = util.onContextMenuOptionClick(appId, option);
			app.put("payload", payload);
		} else 	if (item == null || "".equals(item) || "desktop-item-view-folder".equals(item)) {
			int parentAppId = app.get("parentAppId").asInt();
			app.put("fileName", "New Folder");
			String payload = util.onContextMenuOptionClick(appId, option,parentAppId);
			app.put("payload", payload);
		}

		else {
			appId = Integer.parseInt(item.split("/")[2]);
			app.put("fileName", item.split("/")[3]);
			String payload = util.onContextMenuOptionClick(appId, option);
			app.put("payload", payload);
		}






    	return app;
    }

	@PostMapping("/renameapp")
	public ObjectNode renameApp(@RequestBody ObjectNode app) {
		System.out.println(app);
		int appId = app.get("appId").asInt();
		int parentAppId = app.get("parentAppId").asInt();
		String newName = app.get("newName").asText();
		boolean updateSuccess = false;
		if(parentAppId==0){
			updateSuccess = util.renameApp(appId,newName);
		}else{
			updateSuccess = util.renameApp(appId,newName,parentAppId);
		}

		app.put("isSuccess",updateSuccess);




		return app;
	}

	/*
	 * @PostMapping("/uploadfile") public ObjectNode
	 * uploadFileAndCreateApp(@RequestBody ObjectNode app) { String fileName =
	 * app.get("fileName").asText(); String payload = app.get("payload").asText();
	 * //File file = app.get("file").binaryValue();
	 * System.out.println("File is :::: "+app.get("file").asText());
	 * util.uploadFile(fileName, payload); return app; }
	 */
    
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadFile(@RequestParam MultipartFile file, @RequestParam int parentAppId) {
    	if(parentAppId != 0)
    		util.uploadFile(file.getOriginalFilename(), file,parentAppId);
    	else
			util.uploadFile(file.getOriginalFilename(), file);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/uploadmultiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadMultipleFile(@RequestParam("files") MultipartFile [] files, @RequestParam int parentAppId) {

		if(parentAppId != 0){
			for(MultipartFile file : files) {
				util.uploadFile(file.getOriginalFilename(), file, parentAppId);
			}
		}
		else{
			for(MultipartFile file : files) {
				util.uploadFile(file.getOriginalFilename(), file);
			}
		}
        return ResponseEntity.ok().build();
    }

	@PostMapping(value = "/uploadforfolder", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity uploadFileForFolder(@RequestParam MultipartFile file, @RequestParam String data) {
		util.uploadFile(file.getOriginalFilename(), file);
		return ResponseEntity.ok().build();
	}



	@PostMapping("/downloadapp")
	public ResponseEntity downloadFile1(@RequestBody ObjectNode app) throws IOException {
//		String item = app.get("item").asText();
//		String appName = item.split("/")[3];
//		int appId = Integer.parseInt(item.split("/")[2]);

		int appId = app.get("appId").asInt();
		int appVersion = app.get("version").asInt();
		String appName= app.get("appName").asText();
		/*
		 * File file = new File(appName); try (FileOutputStream fileOuputStream = new
		 * FileOutputStream(file)) { fileOuputStream.write(); }
		 */
        byte[] filePayload = null;
		if(appVersion == 0){
            filePayload  = util.getActiveVersionAppPayloadAsFile(appId);
        }else{
            filePayload = util.getSpecificVersionAppPayloadAsFile(appId,appVersion);
        }

		InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(filePayload));
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + appName)
				.contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(filePayload.length).body(resource);
	}


	@GetMapping("/getpersonalapps")
	public ObjectNode getPersonalApps() {
	//{appId:26,appName:"My Folder",appType:"folder"}
        AppInstanceData data = util.getPersonalFolder();
		ObjectNode node = JsonUtil.getEmptyJsonObject();
		node.put("appId",data.getId());
		node.put("appName","My Personal Folder");
		node.put("appType","folder");

		return node;
	}

}
