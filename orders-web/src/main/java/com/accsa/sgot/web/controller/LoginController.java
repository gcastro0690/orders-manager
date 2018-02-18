package com.accsa.sgot.web.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;

import com.orders.persistence.model.mysql.repository.UsuarioRepository;

@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController extends BasicController implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private UsuarioRepository usuarioRepository;

	private String username;

	@PostConstruct
	public void init() {
		
	}

	public void login() throws ServletException, IOException {
		
	}


	public void logoutAndRedirect() throws IOException {
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
