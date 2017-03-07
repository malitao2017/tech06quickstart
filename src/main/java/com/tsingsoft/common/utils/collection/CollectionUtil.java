/*
 * CollectionUtil.java
 * Copyright: Tsingsoft (c) 2015
 * Company: 北京清软创新科技有限公司
 */
package com.tsingsoft.common.utils.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tsingsoft.common.utils.reflect.ReflectionUtil;


/**
 * <pre>
 * 集合工具类 
 * 本类内提供对 collection 不同类型间转换 等
 * </pre>
 * 
 * @author L.Y
 * @version 1.0, 2014/07/08
 */
public class CollectionUtil {

	private CollectionUtil() {
	}

	/**
	 * 将list转换成map
	 * 
	 * @param list
	 *            待转换的列表
	 * @param name
	 *            列表实体中要用来充当map 键值的 属性名称
	 * @return 转换后map
	 * @throws Exception
	 */
	public static <T> Map<String, T> convertList2Map(List<T> list, String name) {
		Map<String, T> rtnMap = new HashMap<String, T>();
		if (list == null)
			return rtnMap;
		for (T bean : list) {
			rtnMap.put(ReflectionUtil.getFieldValue(bean, name).toString(), bean);
		}
		return rtnMap;
	}

	/**
	 * 将list转换成map，根据传如的键值生成器进行键值生成
	 * 
	 * @param list
	 *            待转换的列表
	 * @param generator
	 *            转换后键值生成器（需手动实现，建议直接在参数位置写实现）
	 * @return 转换后map
	 * @throws Exception
	 */
	public static <T> Map<String, T> convertList2Map(List<T> list, IKeyGenerator<T> generator) {
		if (list == null || list.isEmpty())
			return new HashMap<String, T>();
		Map<String, T> rtnMap = new HashMap<String, T>();
		for (T bean : list) {
			rtnMap.put(generator.generateKey(bean), bean);
		}
		return rtnMap;
	}

	/**
	 * 将map转换为List
	 * 
	 * @param map
	 *            待转换的map
	 * @return 转换后的list
	 */
	public static <T> List<T> convertMap2List(Map<String, T> map) {
		if (map == null)
			return null;
		List<T> rtnList = new ArrayList<T>();
		rtnList.addAll(map.values());
		return rtnList;
	}
}
