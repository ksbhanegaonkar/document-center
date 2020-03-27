package com.vamanos.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "CONTEXT_MENU_OPTIONS")
public class ContextMenuOptions  extends BaseIdEntity{
	@Column(name="TYPE")
	String type;
	@Column(name="OPTION_LIST")
	String optionList;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOptionList() {
		return optionList;
	}
	public void setOptionList(String optionList) {
		this.optionList = optionList;
	}
	
	
}
