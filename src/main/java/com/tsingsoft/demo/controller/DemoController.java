/*
 * DemoController.java
 * Copyright: Copyright Zero (c) 2015
 * org: JavaEE Zero
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.tsingsoft.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tsingsoft.entity.User;
import com.tsingsoft.repository.UserRepository;
import com.tsingsoft.web.controller.BaseController;

/**
 * 
 * <pre>base line chart <pre>
 * @author L.Yang
 * @version 1.0, 2015年4月8日
 */
@Controller
@RequestMapping(value = "/demo")
public class DemoController extends BaseController {

	private final String UI_PATH = "/demo/";
	
	
	@Autowired
	private UserRepository repository;
	
	/**
	 * 进入页面 用户个人信息界面
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(Model model){
		log.info("进入demo首页");
		User user = repository.findByMobile("18513955226");
		model.addAttribute("username", user.getNickname());
		return UI_PATH + "home";
	}
	
	/**
	 * 进入页面 用户个人信息界面
	 */
	@RequestMapping(value="/base", method=RequestMethod.GET)
	public String base(Model model){
		log.info("进入base line chart 界面");
		return UI_PATH + "baseLine";
	}
	
}
