package com.vamanos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "USER_TEAM_RELATION")
public class UserTeamRelation extends BaseIdEntity{
	
	@Column(name = "USER_ID")
	int userId;
	@Column(name = "TEAM_ID")
	int teamId;
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getTeamId() {
		return teamId;
	}
	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
	
	
}
