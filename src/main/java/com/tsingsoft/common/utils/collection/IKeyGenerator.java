/*
 * IKeyGenerator<T>.java
 * Copyright: Tsingsoft (c) 2015
 * Company: 北京清软创新科技有限公司
 */
package com.tsingsoft.common.utils.collection;

/**
 * 
 * <pre> 
 * list 转换 map 的键值生成器接口
 * 提供键值生成方法
 * <pre>
 * @author L.Y
 * @version 1.0, 2014-7-22
 */
public interface IKeyGenerator<T> {

	/**
	 * 生成键值
	 * @param bean map中的实体bean
	 * @return 生成后的键值
	 */
	String generateKey(T bean);
}
