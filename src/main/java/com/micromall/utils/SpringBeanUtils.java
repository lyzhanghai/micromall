package com.micromall.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanUtils implements ApplicationContextAware {

	private static ApplicationContext	applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringBeanUtils.applicationContext = applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(String name) {
		return (T) applicationContext.getBean(name);
	}

	public static <T> T get(Class<T> clazz) {
		return (T) applicationContext.getBean(clazz);
	}
}
