package com.orders.persistence.model;

import java.lang.reflect.Field;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


@MappedSuperclass
public abstract class GenericVersion {

	@Version
	@Column
	protected Long version = 0L;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ReflectionToStringBuilder(this) {
			@Override
			protected boolean accept(Field field) {
				return !field.getName().equals("password");
			}
		};
		return builder.toString();
	}
}