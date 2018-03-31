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
import com.orders.persistence.model.mysql.entity.UserRole;
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
			user.setStatus("ONLINE");
			userRepository.saveAndFlush(user);
			setUserSession(user);
			if(user.getRole().name().equals(UserRole.ADMIN.name())) {
				response.sendRedirect("view/home/adminHome.xhtml");
			}else if(user.getRole().name().equals(UserRole.DISPATCHER.name())){
				response.sendRedirect("view/home/dispatcherHome.xhtml");
			}else if(user.getRole().name().equals(UserRole.TECHNICIAN.name())){
				response.sendRedirect("view/home/technicianHome.xhtml");
			}
			
			
		}else {
			context.addMessage(null, new FacesMessage("Error!",  "User or Pass are incorrect") );
		}
	}

	private void setUserSession(User user) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put("user", user);
	}
	
	private User getUserSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		return (User) context.getExternalContext().getSessionMap().get("user");
	}
	
	public void logoutAndRedirect() throws IOException {
		User user = getUserSession();
		user.setStatus("OFFLINE");
		userRepository.saveAndFlush(user);
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
