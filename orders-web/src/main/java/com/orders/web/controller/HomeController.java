package com.orders.web.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean(name = "homeController")
@ViewScoped
public class HomeController extends BasicController implements Serializable {

	private static final long serialVersionUID = -3698710644462880861L;

	

	public HomeController() {
	}

	@PostConstruct
	public void init() {
		
	}

}