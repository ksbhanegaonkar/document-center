package com.vamanos;

import com.vamanos.entity.*;
import com.vamanos.filter.CORSResponseFilter;
import com.vamanos.repo.*;
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
				user.setPassword(encoder.encode("Welcome@01"));
				user.setEmail("admin@organization.com");
				user.setEnabled(true);

				userRepository.save(user);

				AppInstanceData adminApp = new AppInstanceData();
				adminApp.setName("Admin Console");
				adminApp.setType("admin");

				AppInstanceData personalFolder = new AppInstanceData();
				personalFolder.setName("Personal Folder");
				personalFolder.setType("folder-personal");

				appInstanceDataRepository.save(adminApp);
				appInstanceDataRepository.save(personalFolder);

				AppInstancePayload payload = new AppInstancePayload();
				payload.setAppId(adminApp.getId());
				payload.setPayload("[]".getBytes());
				payload.setVersionNumber(1);
				payload.setUpdateComment("Application first time startup create.");
				payload.setActiveVersion(true);
				payload.setUpdatedUserName("Admin");
				payload.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));

				AppInstancePayload personalFolderPayload = new AppInstancePayload();
				personalFolderPayload.setAppId(personalFolder.getId());
				personalFolderPayload.setPayload("[]".getBytes());
				personalFolderPayload.setVersionNumber(1);
				personalFolderPayload.setUpdateComment("Application first time startup create.");
				personalFolderPayload.setActiveVersion(true);
				personalFolderPayload.setUpdatedUserName("Admin");
				personalFolderPayload.setUpdatedTimestamp(new Timestamp(System.currentTimeMillis()));

				appInstancePayloadRepository.save(payload);
				appInstancePayloadRepository.save(personalFolderPayload);

				PersonalApps app = new PersonalApps();
				app.setUserId(user.getId());
				app.setAppId(adminApp.getId());

				PersonalApps personalApp = new PersonalApps();
				personalApp.setUserId(user.getId());
				personalApp.setAppId(personalFolder.getId());

				personalAppsRepository.save(app);
				personalAppsRepository.save(personalApp);


			}
	  }

}
