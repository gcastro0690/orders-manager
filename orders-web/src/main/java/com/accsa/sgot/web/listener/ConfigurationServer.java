package com.accsa.sgot.web.listener;

import java.text.MessageFormat;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.slf4j.bridge.SLF4JBridgeHandler;

/**
 * Este WebListener tiene el propósito de inicializar componentes y parámetros en el arranque del contexto
 * 
 * @author ahernandez
 */
public class ConfigurationServer implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		Logger.getGlobal().info("INICIALIZANDO_CONTEXTO_EN_SERVIDOR");

		// Se desinstala los Handlers JUL que viene por defecto en el servidor
		// El Bridge se autoconfigura al encontrarse en el classpath.
		SLF4JBridgeHandler.removeHandlersForRootLogger();
		// Inicialización del MessageResolver con el ResourceBundle de mensaje para usar
		// con la clase Message de OmniFaces
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// NOOP
	}

}