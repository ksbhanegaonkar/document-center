package com.vamanos.controller;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vamanos.util.DesktopUpdateUtil;
import com.vamanos.util.JsonUtil;

@RestController
@Validated
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
        return node;
    }
    
    @PostMapping("/updateapppayload")
    public ObjectNode updatePayload(@RequestBody ObjectNode app) {
    	int appId = app.get("appId").asInt();
    	String payload = app.get("payload").asText();
    	util.updatePayload(appId, payload);
    	return app;
    }
    
    @PostMapping("/oncontextmenuaction")
    public ObjectNode onContextMenuOption(@RequestBody ObjectNode app) {
    	System.out.println(app);
    	String item = app.get("item").asText();
    	String option = app.get("option").asText();

    	int appId = 0;
		if (item == null || "".equals(item) || "desktop-wallpaper".equals(item) || "desktop-item-view".equals(item)) {
			app.put("fileName", "New Folder");
		} else {
			appId = Integer.parseInt(item.split("/")[2]);
			app.put("fileName", item.split("/")[3]);
		}



    	String payload = util.onContextMenuOptionClick(appId, option);
    	app.put("payload", payload);


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
    public ResponseEntity uploadFile(@RequestParam MultipartFile file) {
    	System.out.println("filename is ::::: "+file.getOriginalFilename());
    	util.uploadFile(file.getOriginalFilename(), file);
        return ResponseEntity.ok().build();
    }
    
	
	@PostMapping("/downloadapp")
	public ResponseEntity downloadFile1(@RequestBody ObjectNode app) throws IOException {
		String item = app.get("item").asText();
		String appName = item.split("/")[3];
		int appId = Integer.parseInt(item.split("/")[2]);
		/*
		 * File file = new File(appName); try (FileOutputStream fileOuputStream = new
		 * FileOutputStream(file)) { fileOuputStream.write(); }
		 */
		byte[] filePayload = util.getAppPayloadAsFile(appId);
		InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(filePayload));
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + appName)
				.contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(filePayload.length).body(resource);
	}
    
	/*
	 * @PostMapping("/downloadapp") public ResponseEntity<byte[]>
	 * downloadFile1(@RequestBody ObjectNode app) throws IOException { String item =
	 * app.get("item").asText(); String appName = item.split("/")[3]; int appId =
	 * Integer.parseInt(item.split("/")[2]); File file = new File(appName); try
	 * (FileOutputStream fileOuputStream = new FileOutputStream(file)){
	 * fileOuputStream.write(util.getAppPayloadAsFile(appId)); } byte [] appData =
	 * util.getAppPayloadAsFile(appId);
	 * System.out.println("Length is ::::"+appData.length); InputStreamResource
	 * resource = new InputStreamResource(new FileInputStream(file));
	 * 
	 * HttpHeaders header = new HttpHeaders();
	 * header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	 * header.setContentLength(appData.length); header.set("Content-Disposition",
	 * "attachment; filename=" + appName); return new ResponseEntity<>(appData,
	 * header, HttpStatus.OK); }
	 */

	/*
	 * // Save
	 * 
	 * @PostMapping("/books")
	 * 
	 * @ResponseStatus(HttpStatus.CREATED) Book newBook(@Valid @RequestBody Book
	 * newBook) { return repository.save(newBook); }
	 * 
	 * // Find
	 * 
	 * @GetMapping("/books/{id}") Book findOne(@PathVariable @Min(1) Long id) {
	 * return repository.findById(id) .orElseThrow(() -> new
	 * BookNotFoundException(id)); }
	 * 
	 * // Save or update
	 * 
	 * @PutMapping("/books/{id}") Book saveOrUpdate(@RequestBody Book
	 * newBook, @PathVariable Long id) {
	 * 
	 * return repository.findById(id) .map(x -> { x.setName(newBook.getName());
	 * x.setAuthor(newBook.getAuthor()); x.setPrice(newBook.getPrice()); return
	 * repository.save(x); }) .orElseGet(() -> { newBook.setId(id); return
	 * repository.save(newBook); }); }
	 * 
	 * // update author only
	 * 
	 * @PatchMapping("/books/{id}") Book patch(@RequestBody Map<String, String>
	 * update, @PathVariable Long id) {
	 * 
	 * return repository.findById(id) .map(x -> {
	 * 
	 * String author = update.get("author"); if (!StringUtils.isEmpty(author)) {
	 * x.setAuthor(author);
	 * 
	 * // better create a custom method to update a value = :newValue where id = :id
	 * return repository.save(x); } else { throw new
	 * BookUnSupportedFieldPatchException(update.keySet()); }
	 * 
	 * }) .orElseGet(() -> { throw new BookNotFoundException(id); });
	 * 
	 * }
	 * 
	 * @DeleteMapping("/books/{id}") void deleteBook(@PathVariable Long id) {
	 * repository.deleteById(id); }
	 */
}
