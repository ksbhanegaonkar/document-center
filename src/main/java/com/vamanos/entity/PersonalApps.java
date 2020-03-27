package com.vamanos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "PERSONAL_APPS")
public class PersonalApps extends BaseIdEntity{
	@Column(name = "APP_ID")
	int appId;
	@Column(name = "USER_ID")
	int userId;
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
