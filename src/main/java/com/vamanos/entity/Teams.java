package com.vamanos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "TEAMS")
public class Teams extends BaseIdEntity{
	@Column(name = "TEAM_NAME")
	String teamName;
	@Column(name = "TEAM_DL")
	String teamDL;
	@Column(name = "TEAM_FOLDER_ID")
	int teamFolderId;
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	public String getTeamDL() {
		return teamDL;
	}
	public void setTeamDL(String string) {
		this.teamDL = string;
	}

	public int getTeamFolderId() {
		return teamFolderId;
	}

	public void setTeamFolderId(int teamFolderId) {
		this.teamFolderId = teamFolderId;
	}
}
