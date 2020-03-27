package com.vamanos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "TEAM_APPS")
public class TeamApps extends BaseIdEntity{
	
	@Column(name = "TEAM_ID")
	int teamId;
	@Column(name = "APP_ID")
	int appId;
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	

}
