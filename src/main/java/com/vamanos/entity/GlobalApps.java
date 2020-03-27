package com.vamanos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "GLOBAL_APPS")
public class GlobalApps extends BaseIdEntity{
	@Column(name = "APP_ID")
	private int appId;

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}
	
	
}
