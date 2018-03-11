package com.orders.web.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.orders.persistence.model.mysql.entity.Order;
import com.orders.persistence.model.mysql.entity.User;
import com.orders.persistence.model.mysql.entity.UserRole;
import com.orders.persistence.model.mysql.repository.OrderRepository;
import com.orders.persistence.model.mysql.repository.UserRepository;

@ManagedBean(name = "orderController")
@SessionScoped
public class OrderController extends BasicController implements Serializable {

	private static final long serialVersionUID = 1L;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private UserRepository userRepository;
	private Order order;
	private List<Order> orders;

	public List<Order> getOrders() {
		
		return orderRepository.findAll();
	}

	@PostConstruct
	public void init() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		ServletContext servletContext = (ServletContext) externalContext.getContext();
		WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).getAutowireCapableBeanFactory()
				.autowireBean(this);
	}
	
	public SelectItem[] getTechnicians() {
		List<User> users = userRepository.findByRole(UserRole.TECHNICIAN);
		
		SelectItem[] items = new SelectItem[users.size()];
		int i = 0;
		for(User user : users) {
			items[i] = new SelectItem(user.getId(), user.getName() + " " + user.getLastname());
			i++;
		}
		
		return items;
	}
	
	public void saveOrder() {
		try {
			System.out.println("Saving Order....");
			orderRepository.saveAndFlush(order);
			order = new Order();
	        FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Successful",  "Order created!") );
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteOrder(Long id) {
		try {
			orderRepository.delete(id);
	        FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Successful",  "Order deleted!"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public Order getOrder() {
		if(order == null) {
			order = new Order();
		}
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}


}
