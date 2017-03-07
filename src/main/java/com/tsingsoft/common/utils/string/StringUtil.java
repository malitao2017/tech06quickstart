/*
 * StringUtil.java
 * Copyright: Tsingsoft (c) 2015
 * Company: 北京清软创新科技有限公司
 */
package com.tsingsoft.common.utils.string;

import java.util.UUID;

/**
 * 字符串工具类
 * 
 * @author L.Yang
 * @version 1.0, 2014-7-9
 */
public class StringUtil {
	
	private StringUtil(){}

	/***
	 * 判断 是否是小写<br>
	 * 如果有一个大写则不算小写
	 * @param chart 字符串
	 * @return true or false
	 */
	public static final boolean isLowerCase(String chart) {
		for (int i = 0; i < chart.length(); i++) {
			char c = chart.charAt(i);
			if (!Character.isLowerCase(c)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * <pre>
	 * 判断对象是否为空，为空返回true
	 * 为空的条件:
	 * 1、null
	 * 2、去除前后空格并且不区分大小写(""、"null"、'null')
	 * </pre>
	 * 
	 * @param obj 对象
	 * @return 是否为空
	 */
	public static final boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		String str = obj.toString().trim();
		if ("".equals(str) || "null".equalsIgnoreCase(str) || "'null'".equalsIgnoreCase(str)) {
			return true;
		}
		return false;
	}

	/**
	 * <pre>
	 * 处理空值：若对象为空（参见isEmpty），则返回""；不为空，则返回o.toString().trim()
	 * </pre>
	 * 
	 * @param o 对象
	 * @return 如果对象为空这返回""不为空则去空后返回
	 */
	public static final String trimNull(Object o) {
		if (isEmpty(o)) {
			return "";
		} else {
			return o.toString().trim();
		}
	}

	/**
	 * 随机获取UUID
	 * @return 随机UUID
	 */
	public static final String randomUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
