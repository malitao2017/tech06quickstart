/*
 * UserRepository.java
 * Copyright: Copyright Zero (c) 2015
 * org: JavaEE Zero
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.tsingsoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tsingsoft.entity.User;

/**
 * <pre>用户数据操作数据行为接口</pre>
 * @author L.Yang 
 * @time 2015年3月24日
 */
public interface UserRepository  extends JpaRepository<User, Long>{

	/**
	 * 根据手机号获取用户
	 * @param mobile 手机号
	 * @return 用户
	 */
	User findByMobile(String mobile);
}
