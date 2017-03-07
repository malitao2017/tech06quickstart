/*
 * Users.java
 * Copyright: Copyright Zero (c) 2015
 * org: JavaEE Zero
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.tsingsoft.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 * <pre>用户实体  <pre>
 * @author L.Yang
 * @version 1.0, 2015年3月24日
 */
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	/** 主键 */
	private Long id;

	/** 昵称 */
	private String nickname;

	/** 密码 */
	private String password;
	
	/** 邮箱 */
	private String email;
	
	/** 电话 */
	private String mobile;
	
	/** 是否有效（1:true, 0: false） */
	private int available;

	/**
	 * 获取主键
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 设置主键
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取昵称
	 * @return the nickname
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * 设置昵称
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * 获取密码
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * 设置密码
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 获取邮箱
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * 设置邮箱
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * 获取手机号
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置手机号
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 是否有效（1:true, 0: false）
	 * @return the available
	 */
	public int getAvailable() {
		return available;
	}

	/**
	 * 是否有效（1:true, 0: false）
	 * @param available the available to set
	 */
	public void setAvailable(int available) {
		this.available = available;
	}
	
}
