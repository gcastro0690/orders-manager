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
@Table(name = "addresses")
public class Address{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "address_generator")
	@SequenceGenerator(sequenceName = "address_sequence", name = "address_generator", allocationSize = 50)
	@Column
	private Long id = 0L;

	@Column
	private String street;

	@Column
	private String number;
	
	@Column
	private Integer plz;

	@Column
	private String ort;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getPlz() {
		return plz;
	}

	public void setPlz(Integer plz) {
		this.plz = plz;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}
	
}