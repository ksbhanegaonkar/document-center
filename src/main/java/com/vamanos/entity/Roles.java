package com.vamanos.entity;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

@Entity
public class Roles extends BaseIdEntity {

	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "permission_role", joinColumns = {
			@JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "permission_id", referencedColumnName = "id") })
	private List<Permissions> permissions = new ArrayList<Permissions>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Permissions> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permissions> permissions) {
		this.permissions = permissions;
	}

	@Override
	public String toString() {
		return "Roles [name=" + name + ", permissions=" + permissions + "]";
	}
	
	
}