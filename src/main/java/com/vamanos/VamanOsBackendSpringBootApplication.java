package com.vamanos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.vamanos.entity.GlobalApps;
import com.vamanos.filter.CORSResponseFilter;
import com.vamanos.repo.AppInstancePayloadRepository;
import com.vamanos.repo.GlobalAppsRepository;
import com.vamanos.service.AppService;
import com.vamanos.util.DesktopUpdateUtil;

@SpringBootApplication
public class VamanOsBackendSpringBootApplication implements CommandLineRunner 
{
	private static final Logger log = LoggerFactory.getLogger(VamanOsBackendSpringBootApplication.class);

	
	  @Autowired private AppInstancePayloadRepository appRepo;
	 

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
		  
		/*
		 * List<GlobalApps> globalApps = appRepo.findAll();
		 * 
		 * System.out.println(globalApps);
		 */
		  //System.out.println(appRepo.existsByAppId(9));
		/*
		 * int appId = 132; File file = new File("test.docx"); try (FileOutputStream
		 * fileOuputStream = new FileOutputStream(file)){
		 * fileOuputStream.write(appRepo.getAppPayloadByAppId(appId).getPayloadAsBytes()
		 * ); }
		 */
		 
	  }

}
