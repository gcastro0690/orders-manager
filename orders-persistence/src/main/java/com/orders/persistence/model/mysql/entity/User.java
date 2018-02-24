package com.orders.persistence.model.mysql.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "user_generator")
	@SequenceGenerator(sequenceName = "user_sequence", name = "user_generator", allocationSize = 50)
	@Column
	private Long id = 0L;

	@Column
	private String username;

	@Column
	private String name;

	@Column
	private String lastname;
	
	@Column
	private String pass;

	@Column
	private boolean logged;
	
	@Column
	private String mail;
	
	@Column
	private String status;
	
	@Column
	private String calification;
	
	@Enumerated(EnumType.STRING)
    private UserRole role;
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCalification() {
		return calification;
	}

	public void setCalification(String calification) {
		this.calification = calification;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
}