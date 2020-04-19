package com.vamanos.service;

import com.vamanos.entity.*;
import com.vamanos.repo.*;
import com.vamanos.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	@Autowired
	TeamsRepository teamsRepository;
	@Autowired
	UserTeamRelationRepository userTeamRelationRepository;
	@Autowired
	TeamAppsRepository teamAppsRepository;
	@Autowired
	PasswordEncoder encoder;
	
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

	public List<AppInstanceData> getAllAppsForUser(){
		List<Integer> allAppIds = new ArrayList<>();
		allAppIds.addAll(getGlobalApps());
		allAppIds.addAll(getTeamApps());
		allAppIds.addAll(getPersonalApps());
		List<AppInstanceData> destktopItems= appInstanceDataRepository.findAllById(allAppIds);
		Collections.sort(destktopItems);
		return destktopItems;
	}

	public List<Integer> getGlobalApps(){
		List<GlobalApps> globalApps = globalAppsRepository.findAll();
		List<Integer> globalAppIds = globalApps.stream().map(GlobalApps::getAppId).collect(Collectors.toList());
		return globalAppIds;
	}

	public List<Integer> getPersonalApps(){
		List<PersonalApps> personalApps = personalAppsRepository.getPersonalAppsByUserId(getUserId());
		List<Integer> personalAppIds = personalApps.stream().map(PersonalApps::getAppId).collect(Collectors.toList());
		return personalAppIds;
	}

	public List<Integer> getTeamApps(){
		List<UserTeamRelation> assignedTeams = userTeamRelationRepository.getUserTeamRelatiionByUserId(getUserId());
		List<Integer> teamIds = assignedTeams.stream().map(UserTeamRelation::getTeamId).collect(Collectors.toList());
		List<Teams> teamApps = teamsRepository.findAllById(teamIds);
		List<Integer> allTeamApps = teamApps.stream().map(Teams::getTeamFolderId).collect(Collectors.toList());
		return allTeamApps;
	}

	public int getUserId(){
		Users user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
		return user.getId();
	}

	
	public String getAppPayload(int appId) {
		AppInstancePayload app = appInstancePayloadRepository.getAppPayloadByAppIdAndIsActiveVersion(appId,true).get(0);
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
		AppInstancePayload app = appInstancePayloadRepository.getAppPayloadByAppIdAndIsActiveVersion(appId,true).get(0);
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
		AppInstancePayload appPayloadToCopy = appInstancePayloadRepository.getAppPayloadByAppIdAndIsActiveVersion(appId,true).get(0);
		
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


	public AppInstanceData createNewFolder(String name) {
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
		payload.setAppId(data.getId());
		appInstancePayloadRepository.save(payload);
		return data;
	}

	public AppInstanceData createTrashBean(){


		AppInstanceData data = new AppInstanceData();
		AppInstancePayload payload = new AppInstancePayload();



		data.setName("Trash Bin");
		data.setType("folder-trash-bin");


		payload.setPayload("[]".getBytes());
		payload.setVersionNumber(1);
		payload.setUpdateComment("Initial Create...");
		payload.setActiveVersion(true);
		payload.setUpdatedUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		payload.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));

		appInstanceDataRepository.save(data);
		payload.setAppId(data.getId());
		appInstancePayloadRepository.save(payload);

		return data;
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



		AppInstancePayload parentAppPayload = appInstancePayloadRepository.getAppPayloadByAppIdAndIsActiveVersion(parentAppId,true).get(0);
		String currentPayload = parentAppPayload.getPayload();
		String newPayload = JsonUtil.getUpdatedFolderPayload(currentPayload,data.getId(),data.getName(),data.getType());

		parentAppPayload.setPayload(newPayload.getBytes());
		appInstancePayloadRepository.save(parentAppPayload);



	}

	public AppInstanceData createPersonalFolder(String name) {
		AppInstanceData data = new AppInstanceData();
		AppInstancePayload payload = new AppInstancePayload();
		data.setName(name);
		data.setType("folder-personal");
		payload.setPayload("[]".getBytes());
		payload.setVersionNumber(1);
		payload.setUpdateComment("Initial Create...");
		payload.setActiveVersion(true);
		payload.setUpdatedUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		payload.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
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

        AppInstancePayload parentAppPayload = appInstancePayloadRepository.getAppPayloadByAppIdAndIsActiveVersion(parentAppId,true).get(0);
        String currentPayload = parentAppPayload.getPayload();
        String newPayload = JsonUtil.getUpdatedFolderPayload(currentPayload,newAppInstanceData);

        parentAppPayload.setPayload(newPayload.getBytes());
        appInstancePayloadRepository.save(parentAppPayload);
    }

	public byte[] getAppPayloadAsFile(int appId) {
		AppInstancePayload app = appInstancePayloadRepository.getAppPayloadByAppIdAndIsActiveVersion(appId,true).get(0);
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
			AppInstancePayload parentPayload = appInstancePayloadRepository.getAppPayloadByAppIdAndIsActiveVersion(parentAppId,true).get(0);
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
			app = personalAppsRepository.getPersonalAppsByUserId(user.getId()).get(0);
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

    public void createPersonalFolderForUser(int userId) {
        PersonalApps app = null;
        AppInstanceData folder;
            folder = createPersonalFolder("Personal Folder");
            app = new PersonalApps();
            app.setUserId(userId);
            app.setAppId(folder.getId());
            personalAppsRepository.save(app);
    }

	public String updateAppVersion(int appId, MultipartFile file, String comment)  {
		AppInstancePayload existingVersion = appInstancePayloadRepository.getAppPayloadByAppIdAndIsActiveVersion(appId,true).get(0);
		existingVersion.setActiveVersion(false);
		AppInstancePayload newVersion = new AppInstancePayload();
		newVersion.setAppId(appId);
		newVersion.setActiveVersion(true);
		newVersion.setUpdateComment(comment);
		newVersion.setUpdatedUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		try {
			newVersion.setPayload(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		newVersion.setVersionNumber(existingVersion.getVersionNumber()+1);
		newVersion.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		appInstancePayloadRepository.save(existingVersion);
		appInstancePayloadRepository.save(newVersion);
		return null;
	}

	public List<AppInstancePayload> getAppPayloadHistory(int appId) {
		List<AppInstancePayload> payloadHistory = appInstancePayloadRepository.getAppPayloadByAppId(appId);
		Collections.sort(payloadHistory);
		return payloadHistory;
	}

	public void addUser(String userName, String email) {
		Users user = new Users();
		user.setCredentialsNonExpired(true);
		user.setAccountNonExpired(false);
		user.setAccountNonLocked(false);
		user.setUsername(userName);
		user.setPassword(encoder.encode("Welcome@01"));
		user.setEmail(email);
		user.setEnabled(true);
		userRepository.save(user);
        createPersonalFolderForUser(user.getId());

	}

	public void addTeam(String teamName, String teamDL) {
		Teams team = new Teams();
		team.setTeamName(teamName);
		team.setTeamDL(teamDL);
		teamsRepository.save(team);
		AppInstanceData trashBin = createTrashBean();

		TeamApps teamApp = new TeamApps();
		teamApp.setTeamId(team.getId());
		teamApp.setAppId(trashBin.getId());
		teamAppsRepository.save(teamApp);
	}

	public List<String> getAllUsers() {
		return userRepository.findAll().stream().map(Users::getUsername).collect(Collectors.toList());
	}

	public void addTeam(String teamName, String teamDL, List<String> managers) {

		Teams team = new Teams();
		team.setTeamName(teamName);
		team.setTeamDL(teamDL);

		AppInstanceData trashBin = createTrashBean();

		AppInstanceData data = new AppInstanceData();
		AppInstancePayload payload = new AppInstancePayload();
		data.setName(team.getTeamName());
		data.setType("folder");
		payload.setPayload(JsonUtil.getUpdatedFolderPayload("[]",trashBin).getBytes());
		payload.setVersionNumber(1);
		payload.setUpdateComment("Initial Create...");
		payload.setActiveVersion(true);
		payload.setUpdatedUserName(SecurityContextHolder.getContext().getAuthentication().getName());
		payload.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
		appInstanceDataRepository.save(data);
		payload.setAppId(data.getId());
		appInstancePayloadRepository.save(payload);
		team.setTeamFolderId(data.getId());
		teamsRepository.save(team);




		managers.stream().forEach(
				m -> {
					Users user = userRepository.findByUsername(m);
					userTeamRelationRepository.save(new UserTeamRelation(user.getId(),team.getId()));

					List<Integer> appIds = personalAppsRepository.getPersonalAppsByUserId(user.getId())
							.stream()
							.map(PersonalApps::getAppId)
							.collect(Collectors.toList());

					List<AppInstanceData> personalTeamManagerAppList = appInstanceDataRepository.findAllById(appIds)
							.stream()
							.filter(a->"team-manager".equals(a.getType()))
							.collect(Collectors.toList());

					if(personalTeamManagerAppList.isEmpty()){
						AppInstanceData teamManagerApp = createTeamManagerApp(team.getTeamName());
						personalAppsRepository.save(new PersonalApps(teamManagerApp.getId(),user.getId()));
					}else{
						AppInstancePayload managerAppPayload = appInstancePayloadRepository.getAppPayloadByAppIdAndIsActiveVersion(personalTeamManagerAppList.get(0).getId(),true).get(0);
						String currentPayload = managerAppPayload.getPayload();
						managerAppPayload.setPayload(JsonUtil.getUpdatedTeamManagerAppPayload(currentPayload,team.getTeamName()).getBytes());
						appInstancePayloadRepository.save(managerAppPayload);
					}

				}
		);




	}

	private AppInstanceData createTeamManagerApp(String teamName) {

			AppInstanceData data = new AppInstanceData();
			AppInstancePayload payload = new AppInstancePayload();



			data.setName("Team Manager");
			data.setType("team-manager");


			payload.setPayload(JsonUtil.getUpdatedTeamManagerAppPayload("[]",teamName).getBytes());
			payload.setVersionNumber(1);
			payload.setUpdateComment("Initial Create...");
			payload.setActiveVersion(true);
			payload.setUpdatedUserName(SecurityContextHolder.getContext().getAuthentication().getName());
			payload.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));

			appInstanceDataRepository.save(data);
			payload.setAppId(data.getId());
			appInstancePayloadRepository.save(payload);

			return data;
		}

	public void addTeamMembers(List<String> teamMembers, String teamName) {
		Teams team = teamsRepository.getTeamsByTeamName(teamName);
		List<Users> users = teamMembers.stream().map(userRepository::findByUsername).collect(Collectors.toList());
		userTeamRelationRepository.saveAll(users.stream().map(u->new UserTeamRelation(u.getId(),team.getId())).collect(Collectors.toList()));
	}

	public String getAllTeamsOfUser(int appId) {
		//Stream<Integer> allTeamIds = userTeamRelationRepository.getUserTeamRelatiionByUserId(getUserId()).stream().map(UserTeamRelation::getTeamId);
		//return allTeamIds.map(i->teamsRepository.findById(i).get().getTeamName()).collect(Collectors.toList());
		return appInstancePayloadRepository.getAppPayloadByAppIdAndIsActiveVersion(appId,true).get(0).getPayload();
	}
}