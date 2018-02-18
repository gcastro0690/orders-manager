package com.accsa.sgot.web.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

/**
 * El uso de este {@link LoggablePostProcessor} es para inyectar el logger SLF4J en la clases que
 * utilizen la anotacion {@link Loggable}
 */
public class LoggablePostProcessor implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {
		ReflectionUtils.doWithFields(bean.getClass(), field -> {

			ReflectionUtils.makeAccessible(field);
			if (field.getAnnotation(Loggable.class) != null) {
				Logger log = LoggerFactory.getLogger(bean.getClass());
				field.set(bean, log);
			}
		});

		return bean;
	}
}