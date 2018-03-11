package com.orders.persistence.model.mysql.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "orders")
public class Order{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "order_generator")
	@SequenceGenerator(sequenceName = "order_sequence", name = "order_generator", allocationSize = 50)
	@Column
	private Long id = 0L;

	@Column
	private String type;

	@Column
	private String descripcion;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="address_id")
	private Address address;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="technician_id")
	private User technician;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Address getAddress() {
		if(address == null) {
			address = new Address();
		}
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public User getTechnician() {
		if(technician == null) {
			technician = new User();
		}
		return technician;
	}

	public void setTechnician(User technician) {
		this.technician = technician;
	}

	
	
}