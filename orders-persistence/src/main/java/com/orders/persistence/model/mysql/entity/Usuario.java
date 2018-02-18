package com.orders.persistence.model.mysql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.orders.persistence.model.GenericVersion;

@Entity
@Table(name = "users")
public class Usuario extends GenericVersion {

	@Id
	@Column
	private String username;

	@Column
	private String name;

	@Column
	private String pass;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
}