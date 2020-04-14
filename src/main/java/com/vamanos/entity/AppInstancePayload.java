package com.vamanos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Date;
import java.sql.Timestamp;

@Entity(name = "APP_INSTANCE_PAYLOAD")
public class AppInstancePayload extends BaseIdEntity implements Comparable<AppInstancePayload>{
	
	@Column(name="APP_ID")
	int appId;
	@Column(name="PAYLOAD")
	byte [] payload;
	@Column(name = "VERSION_NUMBER")
	int versionNumber;
	@Column(name = "IS_ACTIVE_VERSION")
	boolean isActiveVersion;
	@Column(name = "UPDATED_TIMESTAMP", columnDefinition = "timestamp with time zone")// not null")
	Timestamp updatedTimestamp;
	@Column(name = "UPDATED_USER_NAME")
	String updatedUserName;
	@Column(name = "UPDATE_COMMENT")
	String updateComment;

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getPayload() {
		return new String(payload);
	}
	
	public byte[] getPayloadAsBytes() {
		return payload;
	}

	public void setPayload(byte[] payload) {
		this.payload = payload;
	}

	public int getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}

	public boolean isActiveVersion() {
		return isActiveVersion;
	}

	public void setActiveVersion(boolean activeVersion) {
		isActiveVersion = activeVersion;
	}

	public Timestamp getUpdatedTimestamp() {
		return updatedTimestamp;
	}

	public void setUpdatedTimestamp(Timestamp updatedTimestamp) {
		this.updatedTimestamp = updatedTimestamp;
	}

	public String getUpdateComment() {
		return updateComment;
	}

	public void setUpdateComment(String updateComment) {
		this.updateComment = updateComment;
	}

	public String getUpdatedUserName() {
		return updatedUserName;
	}

	public void setUpdatedUserName(String updatedUserName) {
		this.updatedUserName = updatedUserName;
	}

	@Override
	public int compareTo(AppInstancePayload o) {
		return o.getVersionNumber() - this.versionNumber;
	}
}
