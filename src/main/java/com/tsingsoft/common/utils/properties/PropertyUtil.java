/*
 * PropertyUtil.java
 * Copyright: Tsingsoft (c) 2015
 * Company: 北京清软创新科技有限公司
 */
package com.tsingsoft.common.utils.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.tsingsoft.common.utils.string.StringUtil;

/**
 * 
 * <pre> 属性文件操作工具 <pre>
 * @author L.Yang
 * @version 1.0, 2015年7月15日
 */
public class PropertyUtil {

	/** 默认配置文件 */
	private final String def_file = "application.properties";
	
	/** 配置文件后缀名 */
	private final String file_suffix = ".properties";
	
	private Properties props;

	private InputStream is;

	/**
	 * 带参的构造方法
	 * 
	 * <pre>
	 * 默认读取 user.dir 路径下的配置文件，
	 * 如果该文件不存在则去读取 class path 路径下的文件
	 * </pre>
	 * @param fileName 要读取的配置文件名 ，如果文件名为空则默认加载 application.properties
	 */
	public PropertyUtil(String fileName) {
		try {
			fileName = StringUtil.isEmpty(fileName) ? def_file :
				fileName + file_suffix;
			String user_dir = System.getProperty("user.dir");
			
			props = new Properties();
			if (user_dir != null) {
				user_dir = user_dir + File.separator + fileName;
				File file = new File(user_dir);
				if (file.exists()) {
					is = new FileInputStream(file);
				} else {
					is = PropertyUtil.class.getClassLoader()
							.getResourceAsStream(fileName);
				}
				props.load(is);
			}
		} catch (Exception e) {
			// 此处不会发出异常，不做处理
		} finally {
			close();
		}
	}

	/**
	 * 得到存放在plan.properties中的系统定义的字符串
	 * 
	 * @param key
	 *            资源标识
	 * @return 资源值
	 */
	public String getValue(String key) {
		try {
			return props.getProperty(key);
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 关闭刘文建
	 */
	private void close() {
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
				//此处不会发出异常，不做处理
			}
		}
	}
}
