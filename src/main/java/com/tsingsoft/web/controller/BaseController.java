/*
 * BaseController.java
 * Copyright: Copyright Dw (c) 2014
 * Org: JavaEE Object-Oriented
 */
package com.tsingsoft.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 基础控制器,提供系统通用行为
 * <pre>
 * 1. 获取session
 * 2. 转换Json
 * </pre>
 * @author L.Y 
 * @time 2014年11月26日
 */
public class BaseController {
	
	protected Logger log = LoggerFactory.getLogger(this.getClass());
	
	/***
	 * 将 Object 对象转换成Json
	 * @param obj 对象
	 * @return json
	 * @throws Exception
	 */
	protected String toJson(Object obj) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}
	
}
