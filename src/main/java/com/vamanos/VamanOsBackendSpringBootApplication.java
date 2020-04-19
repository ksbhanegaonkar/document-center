package com.vamanos;

import com.vamanos.entity.*;
import com.vamanos.filter.CORSResponseFilter;
import com.vamanos.repo.*;
import com.vamanos.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.Filter;
import java.io.IOException;
import java.sql.Timestamp;

@SpringBootApplication
public class VamanOsBackendSpringBootApplication implements CommandLineRunner 
{
	private static final Logger log = LoggerFactory.getLogger(VamanOsBackendSpringBootApplication.class);

	
	  @Autowired
	  private AppInstancePayloadRepository appRepo;
	  @Autowired
	  private UserRepository userRepository;
	  @Autowired
	  private ContextMenuOptionRepository contextMenuOptionRepository;
	  @Autowired
	  private AppInstanceDataRepository appInstanceDataRepository;
	  @Autowired
	  private AppInstancePayloadRepository appInstancePayloadRepository;
	  @Autowired
	  private PersonalAppsRepository personalAppsRepository;
	  @Autowired
	  private PasswordEncoder encoder;
	  @Autowired
	  private TeamsRepository teamsRepository;
	  @Autowired
	  private UserTeamRelationRepository userTeamRelationRepository;
	  @Autowired
	  private TeamAppsRepository teamAppsRepository;
	 

	public static void main(String[] args) {
		SpringApplication.run(VamanOsBackendSpringBootApplication.class, args);
	}
	
	@Bean
	public Filter compressFilter() {
	    CORSResponseFilter corsFilter = new CORSResponseFilter();
	    return corsFilter;
	}
	

	

	

	
	  @Override 
	  public void run(String... args) throws IOException {

		 	long count = userRepository.count();
		  	if(count == 0){
				ContextMenuOptions desktopWallpaper = new ContextMenuOptions();
					desktopWallpaper.setType("desktop-wallpaper");
					desktopWallpaper.setOptionList("New Folder,Upload,Refresh,Copy,Paste");
				ContextMenuOptions taskBar = new ContextMenuOptions();
					taskBar.setType("task-bar");
					taskBar.setOptionList("Task bar option 1,Task bar option 2,Task bar option 3,Task bar option 4");
				ContextMenuOptions startMenuButton = new ContextMenuOptions();
					startMenuButton.setType("start-menu-button");
					startMenuButton.setOptionList("My Bookmarks,My Notes,Logout");
				ContextMenuOptions file = new ContextMenuOptions();
					file.setType("file");
					file.setOptionList("Download,Update,Bookmark,Copy,Rename,Delete,History");
				ContextMenuOptions folder = new ContextMenuOptions();
					folder.setType("folder");
					folder.setOptionList("Bookmark folder,Rename folder,Delete folder");
				ContextMenuOptions desktopItemView = new ContextMenuOptions();
					desktopItemView.setType("desktop-item-view");
					desktopItemView.setOptionList("New Sprint 1,New User Story,Refresh,Copy,Paste,Upload");

				contextMenuOptionRepository.save(desktopWallpaper);
				contextMenuOptionRepository.save(taskBar);
				contextMenuOptionRepository.save(startMenuButton);
				contextMenuOptionRepository.save(file);
				contextMenuOptionRepository.save(folder);
				contextMenuOptionRepository.save(desktopItemView);

				Users user = new Users();
				user.setCredentialsNonExpired(true);
				user.setAccountNonExpired(false);
				user.setAccountNonLocked(false);
				user.setUsername("Admin");
				user.setPassword(encoder.encode("a"));
				user.setEmail("admin@organization.com");
				user.setEnabled(true);

				userRepository.save(user);



				AppInstanceData personalFolder = new AppInstanceData();
				personalFolder.setName("Personal Folder");
				personalFolder.setType("folder-personal");


				appInstanceDataRepository.save(personalFolder);



				AppInstancePayload personalFolderPayload = new AppInstancePayload();
				personalFolderPayload.setAppId(personalFolder.getId());
				personalFolderPayload.setPayload("[]".getBytes());
				personalFolderPayload.setVersionNumber(1);
				personalFolderPayload.setUpdateComment("Application first time startup create.");
				personalFolderPayload.setActiveVersion(true);
				personalFolderPayload.setUpdatedUserName("Admin");
				personalFolderPayload.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));


				appInstancePayloadRepository.save(personalFolderPayload);


				PersonalApps personalApp = new PersonalApps();
				personalApp.setUserId(user.getId());
				personalApp.setAppId(personalFolder.getId());

				personalAppsRepository.save(personalApp);

				Teams adminTeam = new Teams();
				adminTeam.setTeamDL("Admin Team");
				adminTeam.setTeamName("Doc Center Admin");
				teamsRepository.save(adminTeam);

				UserTeamRelation userTeamRelation = new UserTeamRelation();
				userTeamRelation.setTeamId(adminTeam.getId());
				userTeamRelation.setUserId(user.getId());
				userTeamRelationRepository.save(userTeamRelation);

				AppInstanceData adminTeamFolder = new AppInstanceData();
				adminTeamFolder.setName("Admin Team");
				adminTeamFolder.setType("folder");
				appInstanceDataRepository.save(adminTeamFolder);

				AppInstancePayload adminTeamFolderPayload = new AppInstancePayload();
				adminTeamFolderPayload.setAppId(adminTeamFolder.getId());
				adminTeamFolderPayload.setPayload("[]".getBytes());
				adminTeamFolderPayload.setVersionNumber(1);
				adminTeamFolderPayload.setUpdateComment("Application first time startup create.");
				adminTeamFolderPayload.setActiveVersion(true);
				adminTeamFolderPayload.setUpdatedUserName("Admin");
				adminTeamFolderPayload.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
				appInstancePayloadRepository.save(adminTeamFolderPayload);

				adminTeam.setTeamFolderId(adminTeamFolder.getId());
				teamsRepository.save(adminTeam);

				AppInstanceData adminApp = new AppInstanceData();
				adminApp.setName("Admin Console");
				adminApp.setType("admin");
				appInstanceDataRepository.save(adminApp);

				AppInstancePayload payload = new AppInstancePayload();
				payload.setAppId(adminApp.getId());
				payload.setPayload("[]".getBytes());
				payload.setVersionNumber(1);
				payload.setUpdateComment("Application first time startup create.");
				payload.setActiveVersion(true);
				payload.setUpdatedUserName("Admin");
				payload.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
				appInstancePayloadRepository.save(payload);

				AppInstanceData userConsole = new AppInstanceData();
				userConsole.setName("User Console");
				userConsole.setType("user-console");
				appInstanceDataRepository.save(userConsole);

				AppInstancePayload userConsolePayload = new AppInstancePayload();
				userConsolePayload.setAppId(userConsole.getId());
				userConsolePayload.setPayload("[]".getBytes());
				userConsolePayload.setVersionNumber(1);
				userConsolePayload.setUpdateComment("Application first time startup create.");
				userConsolePayload.setActiveVersion(true);
				userConsolePayload.setUpdatedUserName("Admin");
				userConsolePayload.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
				appInstancePayloadRepository.save(userConsolePayload);


				AppInstanceData teamConsole = new AppInstanceData();
				teamConsole.setName("Team Console");
				teamConsole.setType("team-console");
				appInstanceDataRepository.save(teamConsole);

				AppInstancePayload teamConsolePayload = new AppInstancePayload();
				teamConsolePayload.setAppId(teamConsole.getId());
				teamConsolePayload.setPayload("[]".getBytes());
				teamConsolePayload.setVersionNumber(1);
				teamConsolePayload.setUpdateComment("Application first time startup create.");
				teamConsolePayload.setActiveVersion(true);
				teamConsolePayload.setUpdatedUserName("Admin");
				teamConsolePayload.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
				appInstancePayloadRepository.save(teamConsolePayload);

				AppInstanceData iconConsole = new AppInstanceData();
				iconConsole.setName("Icon Console");
				iconConsole.setType("icon-console");
				appInstanceDataRepository.save(iconConsole);

				AppInstancePayload iconConsolePayload = new AppInstancePayload();
				iconConsolePayload.setAppId(iconConsole.getId());
				iconConsolePayload.setPayload("[]".getBytes());
				iconConsolePayload.setVersionNumber(1);
				iconConsolePayload.setUpdateComment("Application first time startup create.");
				iconConsolePayload.setActiveVersion(true);
				iconConsolePayload.setUpdatedUserName("Admin");
				iconConsolePayload.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));
				appInstancePayloadRepository.save(iconConsolePayload);

				String updatedAdminTeamFolderPayload = adminTeamFolderPayload.getPayload();
				updatedAdminTeamFolderPayload = JsonUtil.getUpdatedFolderPayload(updatedAdminTeamFolderPayload,adminApp);
				updatedAdminTeamFolderPayload = JsonUtil.getUpdatedFolderPayload(updatedAdminTeamFolderPayload,userConsole);
				updatedAdminTeamFolderPayload = JsonUtil.getUpdatedFolderPayload(updatedAdminTeamFolderPayload,teamConsole);
				updatedAdminTeamFolderPayload = JsonUtil.getUpdatedFolderPayload(updatedAdminTeamFolderPayload,iconConsole);
				adminTeamFolderPayload.setPayload(updatedAdminTeamFolderPayload.getBytes());
				appInstancePayloadRepository.save(adminTeamFolderPayload);

//				TeamApps app1 = new TeamApps();
//				app1.setTeamId(adminTeam.getId());
//				app1.setAppId(adminApp.getId());
//				teamAppsRepository.save(app1);
//
//				TeamApps app2 = new TeamApps();
//				app2.setTeamId(adminTeam.getId());
//				app2.setAppId(userConsole.getId());
//				teamAppsRepository.save(app2);
//
//				TeamApps app3 = new TeamApps();
//				app3.setTeamId(adminTeam.getId());
//				app3.setAppId(teamConsole.getId());
//				teamAppsRepository.save(app3);
//
//				TeamApps app4 = new TeamApps();
//				app4.setTeamId(adminTeam.getId());
//				app4.setAppId(iconConsole.getId());
//				teamAppsRepository.save(app4);


			}
	  }

}
