package com.accsa.sgot.web.controller;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@ManagedBean(name = "version")
@ApplicationScoped
public class Version {

	private String mensaje;

	@PostConstruct
	public void init() {
		String version;
		String fecha;
		InputStream in = getClass().getClassLoader().getResourceAsStream("config/config.properties");
		if (in == null)
			return;
		Properties props = new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		version = props.getProperty("version");
		fecha = props.getProperty("timestamp");
//		fecha = fecha.substring(0, 10);
//		fecha = fecha.replaceAll("-", "/");
		mensaje = "SGOT - Versión " + version + " " + fecha;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}