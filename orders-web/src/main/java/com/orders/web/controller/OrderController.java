package com.orders.web.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.orders.persistence.model.mysql.entity.Order;
import com.orders.persistence.model.mysql.entity.OrderStatus;
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
	private UploadedFile file;

	public List<Order> getOrders() {
		return orderRepository.findAll();
	}

	public List<Order> getTechnicianOrders() {
		List<Order> listReturn = null;
		if (getUserSession() != null) {
			listReturn = orderRepository.findAllByTechnicianId(getUserSession().getId());
		}

		if (listReturn == null) {
			listReturn = new ArrayList<Order>();
		}
		return listReturn;
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
		for (User user : users) {
			items[i] = new SelectItem(user.getId(), user.getName() + " " + user.getLastname());
			i++;
		}

		return items;
	}

	public List<User> getTechniciansControlPanel() {
		List<User> users = userRepository.findByRole(UserRole.TECHNICIAN);
		return users;
	}

	public String getOddRowStyle() {
		/* Change Your style as programmatically */
		return "background-color: #F7F7F7!important ;";
	}

	public String getEvenRowStyle() {
		/* Change Your style as programmatically */
		return "background-color: #05855F!important ;";
	}

	public void downloadReport() throws IOException {
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
	    ExternalContext externalContext = facesContext.getExternalContext();
	    externalContext.setResponseHeader("Content-Type", order.getFileMimeType());
//	    externalContext.setResponseHeader("Content-Length", order.getFile().length);
	    externalContext.setResponseHeader("Content-Disposition", "attachment;filename=\"" + order.getFileName() + "\"");
	    externalContext.getResponseOutputStream().write(order.getFile());
	    facesContext.responseComplete();
	}

	private static void writeBytesToFile(byte[] bFile, String fileDest) {

		try (FileOutputStream fileOuputStream = new FileOutputStream(fileDest)) {
			fileOuputStream.write(bFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void saveOrder() {
		try {
			System.out.println("Saving Order....");
			order.setStatus(OrderStatus.PENDING);
			orderRepository.saveAndFlush(order);
			order = new Order();
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Successful", "Order created!"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private User getUserSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		return (User) context.getExternalContext().getSessionMap().get("user");
	}

	public void takeOrder() {
		try {
			System.out.println("Taking Order....");
			order.setStartDate(new Date());
			orderRepository.saveAndFlush(order);
			order = new Order();
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Successful", "Order taked!"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void endOrder() {
		System.out.println("Ending Order....");
		try {

			order.setEndDate(new Date());
			order.setStatus(OrderStatus.COMPLETED);
			orderRepository.saveAndFlush(order);
			order = new Order();
			FacesContext context = FacesContext.getCurrentInstance();

			context.addMessage(null, new FacesMessage("Successful", "Order ended!"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void deleteOrder(Long id) {
		try {
			orderRepository.delete(id);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, new FacesMessage("Successful", "Order deleted!"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Order getOrder() {
		if (order == null) {
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

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void handleFileUpload(FileUploadEvent event) throws IOException {
		setFile(event.getFile());
		byte[] buffer = null;
		String name = null;
		String type = null;
		if (file != null) {
			name = file.getFileName();
			type = file.getContentType();
			long size = file.getSize();
			InputStream stream = file.getInputstream();
			buffer = new byte[(int) size];
			stream.read(buffer, 0, (int) size);
			stream.close();
		}

		order.setFile(buffer);
		order.setFileMimeType(type);
		order.setFileName(name);
	}
}
