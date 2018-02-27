package com.orders.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.orders.persistence.model.mysql.entity.User;
import com.orders.persistence.model.mysql.repository.UserRepository;

@ManagedBean(name = "userController")
@SessionScoped
public class UserController extends BasicController implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private UserRepository userRepository;
	private User user;
	private List<User> employees;

	public List<User> getEmployees() {
		
		return userRepository.findAll();
	}

	@PostConstruct
	public void init() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		ServletContext servletContext = (ServletContext) externalContext.getContext();
		WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).getAutowireCapableBeanFactory()
				.autowireBean(this);
	}
	
	public void saveUser() {
		try {
			userRepository.saveAndFlush(user);
			user = new User();
	        FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Successful",  "Employee created!") );
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteUser(Long id) {
		try {
			userRepository.delete(id);
	        FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Successful",  "Employee deleted!"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public User getUser() {
		if(user == null) {
			user = new User();
		}
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}
