package com.orders.web.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.orders.persistence.model.mysql.entity.User;
import com.orders.persistence.model.mysql.repository.UserRepository;
@ManagedBean(name = "loginController")
@SessionScoped
public class LoginController extends BasicController implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private UserRepository userRepository;
	
	private String username;
	private String pass;
	
	
	@PostConstruct
	public void init() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		ServletContext servletContext = (ServletContext) externalContext.getContext();
		WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).getAutowireCapableBeanFactory()
				.autowireBean(this);
	}

	public void login() throws ServletException, IOException {
		User user = userRepository.findByUsernameAndPass(username, pass);
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();

		if(user != null) {			
			setUserSession(user);
			response.sendRedirect("view/home/adminHome.xhtml");
		}else {
			context.addMessage(null, new FacesMessage("Error!",  "User or Pass are incorrect") );
		}
	}

	private void setUserSession(User user) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put("user", user);
	}
	
	
	public void logoutAndRedirect() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
		
		context.getExternalContext().invalidateSession();
		
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

}