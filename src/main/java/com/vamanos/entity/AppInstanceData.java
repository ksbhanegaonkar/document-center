package com.vamanos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "APP_INSTANCE_DATA")
public class AppInstanceData extends BaseIdEntity{

	@Column(name="APP_NAME")
	String name;
	@Column(name="APP_TYPE")
	String type;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	
	
	

}
