package com.vamanos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "APP_INSTANCE_PAYLOAD")
public class AppInstancePayload extends BaseIdEntity{
	
	@Column(name="APP_ID")
	int appId;
	@Column(name="PAYLOAD")
	byte [] payload;

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
	
	
	
	

}
