package com.accsa.sgot.web.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "menuController")
@ViewScoped
public class MenuController extends BasicController implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;

	public MenuController() {
	}

	@PostConstruct
	public void init() {
		
	}

	public String getUsername() {
		return username;
	}

}