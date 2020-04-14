package com.vamanos.service;

import com.vamanos.entity.*;
import com.vamanos.repo.*;
import com.vamanos.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

@Service
public class AppService {
	
	private AppInstanceData copiedAppInstanceData;
	private AppInstancePayload copiedAppInstancePayload;
	
	@Autowired
	GlobalAppsRepository globalAppsRepository;
	@Autowired
	AppInstanceDataRepository appInstanceDataRepository;
	@Autowired
	AppInstancePayloadRepository appInstancePayloadRepository;
	@Autowired
	ContextMenuOptionRepository contextMenuOptionRepository;
	@Autowired
	PersonalAppsRepository personalAppsRepository;
	@Autowired
	UserRepository userRepository;
	
	public Map<String, String> getGlobalAppsOld(){
		Map<String,String> desktopItemList = new HashMap<>();
		List<GlobalApps> globalApps = globalAppsRepository.findAll();
		List<Integer> globalAppIds = new ArrayList<>();
		globalApps.stream().forEach(app ->{
			globalAppIds.add(app.getAppId());
		});
		List<AppInstanceData> appInstanceData = appInstanceDataRepository.findAllById(globalAppIds);
		appInstanceData.forEach(data ->{
			desktopItemList.put(data.getName(), data.getType());
		});
		
		return desktopItemList;
	}
	
	public List<AppInstanceData> getGlobalApps(){

		List<GlobalApps> globalApps = globalAppsRepository.findAll();
		List<Integer> globalAppIds = new ArrayList<>();
		globalApps.stream().forEach(app ->{
			globalAppIds.add(app.getAppId());
		});
		List<AppInstanceData> destktopItems= appInstanceDataRepository.findAllById(globalAppIds);

		Collections.sort(destktopItems);
		return destktopItems;
	}
	
	public String getAppPayload(int appId) {
		AppInstancePayload app = appInstancePayloadRepository.getAppPayloadByAppId(appId);
		return app.getPayload();
	}
	public String getActiveVersionAppPayload(int appId) {
		List<AppInstancePayload> app = appInstancePayloadRepository.getAppPayloadByAppIdAndIsActiveVersion(appId,true);
		if(app != null && !app.isEmpty())
		return app.get(0).getPayload();
		else return "[]";
	}

	public String getSpecificVersionAppPayload(int appId,int version) {
		List<AppInstancePayload> app = appInstancePayloadRepository.getAppPayloadByAppIdAndVersionNumber(appId,version);
		if(app != null && !app.isEmpty())
			return app.get(0).getPayload();
		else return "[]";
	}
	
	public String updateAppPayload(int appId,String payload) {
		AppInstancePayload app = appInstancePayloadRepository.getAppPayloadByAppId(appId);
		app.setPayload(payload.getBytes());
		appInstancePayloadRepository.save(app);
		return app.getPayload();
	}
	
	public Map<String,List<String>> getContextMenuOptions(){
		Map<String,List<String>> contextMenuOptions = new HashMap<>();
		List<ContextMenuOptions> allOptionList = contextMenuOptionRepository.findAll();
		allOptionList.stream().forEach(i->contextMenuOptions.put(i.getType(),Arrays.asList(i.getOptionList().split(","))));
		return contextMenuOptions;
	}
	
	
	public void copyApp(int appId) {
		copiedAppInstanceData = new AppInstanceData();
		copiedAppInstancePayload = new AppInstancePayload();
		
		AppInstanceData appDataToCopy = appInstanceDataRepository.getAppById(appId);
		AppInstancePayload appPayloadToCopy = appInstancePayloadRepository.getAppPayloadByAppId(appId);
		
		copiedAppInstanceData.setName("Copy of - "+appDataToCopy.getName());
		copiedAppInstanceData.setType(appDataToCopy.getType());
		

		copiedAppInstancePayload.setPayload(appPayloadToCopy.getPayload().getBytes());
		
		
		
	}

	public void createFolder(String name) {
		AppInstanceData data = new AppInstanceData();
		AppInstancePayload payload = new AppInstancePayload();



		data.setName(name);
		data.setType("folder");


		payload.setPayload("[]".getBytes());
		payload.setVersionNumber(1);
		payload.setUpdateComment("Initial Create...");
		payload.setActiveVersion(true);
		payload.setUpdatedUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		payload.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));

		appInstanceDataRepository.save(data);
		data.setName(data.getName()+" "+data.getId());
		appInstanceDataRepository.save(data);
		payload.setAppId(data.getId());
		appInstancePayloadRepository.save(payload);

		if(!globalAppsRepository.existsByAppId(data.getId())){
			GlobalApps app = new GlobalApps();
			app.setAppId(data.getId());
			globalAppsRepository.save(app);
		}



	}

	public void createFolder(String name, int parentAppId) {
		AppInstanceData data = new AppInstanceData();
		AppInstancePayload payload = new AppInstancePayload();



		data.setName(name);
		data.setType("folder");


		payload.setPayload("[]".getBytes());
		payload.setVersionNumber(1);
		payload.setUpdateComment("Initial Create...");
		payload.setActiveVersion(true);
		payload.setUpdatedUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		payload.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));

		appInstanceDataRepository.save(data);
		data.setName(data.getName()+" "+data.getId());
		appInstanceDataRepository.save(data);
		payload.setAppId(data.getId());
		appInstancePayloadRepository.save(payload);



		AppInstancePayload parentAppPayload = appInstancePayloadRepository.getAppPayloadByAppId(parentAppId);
		String currentPayload = parentAppPayload.getPayload();
		String newPayload = JsonUtil.getUpdatedFolderPayload(currentPayload,data.getId(),data.getName(),data.getType());

		parentAppPayload.setPayload(newPayload.getBytes());
		appInstancePayloadRepository.save(parentAppPayload);



	}

	public AppInstanceData createPersonalFolder(String name) {
		AppInstanceData data = new AppInstanceData();
		AppInstancePayload payload = new AppInstancePayload();



		data.setName(name);
		data.setType("folder");


		payload.setPayload("[]".getBytes());
		payload.setVersionNumber(1);
		payload.setUpdateComment("Initial Create...");
		payload.setActiveVersion(true);
		payload.setUpdatedUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		payload.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));

		appInstanceDataRepository.save(data);
		data.setName(data.getName()+" "+data.getId());
		appInstanceDataRepository.save(data);
		payload.setAppId(data.getId());
		appInstancePayloadRepository.save(payload);
		return data;
	}
	
	public void pasteApp(int appId) {
		if(copiedAppInstanceData != null && copiedAppInstancePayload != null) {
			appInstanceDataRepository.save(copiedAppInstanceData);
			copiedAppInstancePayload.setAppId(copiedAppInstanceData.getId());
			appInstancePayloadRepository.save(copiedAppInstancePayload);
			//if(globalAppsRepository.existsByAppId(copiedAppInstanceData.getId())){
				GlobalApps app = new GlobalApps();
				app.setAppId(copiedAppInstanceData.getId());
				globalAppsRepository.save(app);
			//}
		}
		copiedAppInstanceData = null;
		copiedAppInstancePayload = null;
	}

	public void createNewApp(String fileName, String fileType,MultipartFile file) throws IOException {
		AppInstanceData newAppInstanceData = new AppInstanceData();
		AppInstancePayload newAppInstancePayload = new AppInstancePayload();
		newAppInstanceData.setName(fileName);
		newAppInstanceData.setType(fileType);
		appInstanceDataRepository.save(newAppInstanceData);
		newAppInstancePayload.setAppId(newAppInstanceData.getId());
		newAppInstancePayload.setPayload(file.getBytes());
		newAppInstancePayload.setVersionNumber(1);
		newAppInstancePayload.setUpdateComment("Initial Create...");
		newAppInstancePayload.setActiveVersion(true);
		newAppInstancePayload.setUpdatedUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		newAppInstancePayload.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		appInstancePayloadRepository.save(newAppInstancePayload);
		GlobalApps app = new GlobalApps();
		app.setAppId(newAppInstanceData.getId());
		globalAppsRepository.save(app);
	}

    public void createNewApp(String fileName, String fileType,MultipartFile file,int parentAppId) throws IOException {
        AppInstanceData newAppInstanceData = new AppInstanceData();
        AppInstancePayload newAppInstancePayload = new AppInstancePayload();
        newAppInstanceData.setName(fileName);
        newAppInstanceData.setType(fileType);
        appInstanceDataRepository.save(newAppInstanceData);
        newAppInstancePayload.setAppId(newAppInstanceData.getId());
        newAppInstancePayload.setPayload(file.getBytes());
		newAppInstancePayload.setVersionNumber(1);
		newAppInstancePayload.setUpdateComment("Initial Create...");
		newAppInstancePayload.setActiveVersion(true);
		newAppInstancePayload.setUpdatedUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		newAppInstancePayload.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
        appInstancePayloadRepository.save(newAppInstancePayload);

        AppInstancePayload parentAppPayload = appInstancePayloadRepository.getAppPayloadByAppId(parentAppId);
        String currentPayload = parentAppPayload.getPayload();
        String newPayload = JsonUtil.getUpdatedFolderPayload(currentPayload,newAppInstanceData);

        parentAppPayload.setPayload(newPayload.getBytes());
        appInstancePayloadRepository.save(parentAppPayload);
    }

	public byte[] getAppPayloadAsFile(int appId) {
		AppInstancePayload app = appInstancePayloadRepository.getAppPayloadByAppId(appId);
		return app.getPayloadAsBytes();
	}

	public byte[] getActiveVersionAppPayloadAsFile(int appId) {
		List<AppInstancePayload> app = appInstancePayloadRepository.getAppPayloadByAppIdAndIsActiveVersion(appId,true);
		if(app != null && !app.isEmpty())
			return app.get(0).getPayloadAsBytes();
		else return null;

	}

	public byte[] getSpecificVersionAppPayloadAsFile(int appId, int version) {
		List<AppInstancePayload> app = appInstancePayloadRepository.getAppPayloadByAppIdAndVersionNumber(appId,version);
		if(app != null && !app.isEmpty())
			return app.get(0).getPayloadAsBytes();
		else return null;
	}


    public boolean renameApp(int appId, String newName) {
	    if(appInstanceDataRepository.existsByName(newName)){
	        return false;
        }else {
            AppInstanceData data = appInstanceDataRepository.getAppById(appId);
            data.setName(newName);
            appInstanceDataRepository.save(data);
            return true;
        }
    }

	public boolean renameApp(int appId, String newName, int parentAppId) {
		if(appInstanceDataRepository.existsByName(newName)){
			return false;
		}else {
			AppInstanceData data = appInstanceDataRepository.getAppById(appId);
			AppInstancePayload parentPayload = appInstancePayloadRepository.getAppPayloadByAppId(parentAppId);
			String newPayload = parentPayload.getPayload().replace(data.getName(),newName);
			parentPayload.setPayload(newPayload.getBytes());
			data.setName(newName);
			appInstanceDataRepository.save(data);
			appInstancePayloadRepository.save(parentPayload);
			return true;
		}
	}

	public AppInstanceData getPersonalFolder() {
		Users user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		PersonalApps app = null;
		AppInstanceData folder;
		if(personalAppsRepository.existsByUserId(user.getId())){
			app = personalAppsRepository.getPersonalAppsByUserId(user.getId());
			folder = appInstanceDataRepository.getAppById(app.getAppId());
		}else{
			folder = createPersonalFolder("Personal Data "+user.getId());
			app = new PersonalApps();
			app.setUserId(user.getId());
			app.setAppId(folder.getId());
			personalAppsRepository.save(app);
		}

		return folder;
	}

	public String updateAppVersion(int appId, MultipartFile file, String payload) {
		return null;
	}
}
