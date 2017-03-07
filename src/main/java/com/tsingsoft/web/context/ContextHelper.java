/*
 * ContextHelper.java
 * Copyright: Copyright Zero (c) 2015
 * org: JavaEE Zero
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.tsingsoft.web.context;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * <pre>
 * 获取上下文中的类实例
 * </pre>
 * 
 * @author L.Yang
 * @time 2013-9-16
 */
public class ContextHelper {
	/**
	 * 跟具bean名称和contex获取bean
	 * 
	 * @param beanName
	 * @param context
	 * @return
	 */
	public static Object getBean(String beanName, ServletContext context) {
		ApplicationContext springContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		return springContext.getBean(beanName);
	}

	/**
	 * 跟具bean名称和request获取bean
	 * 
	 * @param beanName
	 * @param request
	 * @return
	 */
	public static Object getBean(String beanName, HttpServletRequest request) {
		ApplicationContext springContext = RequestContextUtils.getWebApplicationContext(request);
		return springContext.getBean(beanName);
	}
}
