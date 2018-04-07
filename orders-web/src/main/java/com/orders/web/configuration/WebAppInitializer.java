package com.orders.web.configuration;

import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.sun.faces.config.FacesInitializer;

public class WebAppInitializer extends FacesInitializer implements WebApplicationInitializer {
  @Override
  public void onStartup(Set<Class<?>> classes, ServletContext servletContext)
      throws ServletException {
    super.onStartup(classes, servletContext);
  }

  @Override
  public void onStartup(ServletContext container) throws ServletException {
    AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
    rootContext.register(ApplicationContextCore.class, ApplicationContextPersistence.class);
    container.addListener(new ContextLoaderListener(rootContext));
    AnnotationConfigWebApplicationContext dispatcherServlet =
        new AnnotationConfigWebApplicationContext();

    ServletRegistration.Dynamic dispatcher =
        container.addServlet("dispatcher", new DispatcherServlet(dispatcherServlet));
    dispatcher.setLoadOnStartup(1);
  }

}
