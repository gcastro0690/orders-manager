package com.orders.web.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan("com.orders")
@PropertySource(value = "classpath:/config/config.properties", ignoreResourceNotFound = false)
@EnableAspectJAutoProxy // EstiloAspectJ de Spring AOP
public class ApplicationContextCore {
  /* Inyeccion del Logger SLF4J */
  @Bean
  public LoggablePostProcessor gloggablePostProcessor() {
    return new LoggablePostProcessor();
  }

  // To resolve ${} in @Value
  @Bean
  public static PropertySourcesPlaceholderConfigurer propertyConfig() {
    return new PropertySourcesPlaceholderConfigurer();
  }
}
