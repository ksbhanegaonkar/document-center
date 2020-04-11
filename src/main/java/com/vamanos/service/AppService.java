package com.vamanos.service;

import java.io.IOException;
import java.util.*;

import com.vamanos.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vamanos.entity.AppInstanceData;
import com.vamanos.entity.AppInstancePayload;
import com.vamanos.entity.ContextMenuOptions;
import com.vamanos.entity.GlobalApps;
import com.vamanos.repo.AppInstanceDataRepository;
import com.vamanos.repo.AppInstancePayloadRepository;
import com.vamanos.repo.ContextMenuOptionRepository;
import com.vamanos.repo.GlobalAppsRepository;

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
}
