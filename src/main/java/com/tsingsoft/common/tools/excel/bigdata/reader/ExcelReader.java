/*
 * ExcelReader.java
 * Copyright: Tsingsoft (c) 2015
 * Company: 北京清软创新科技有限公司
 */
package com.tsingsoft.common.tools.excel.bigdata.reader;


/**
 * 大数据excel处理行为接口
 * <pre>
 * excel data reader for big data(excel) 
 * 定义了excel读取过程中对每行数据处理的方法,
 * <pre>
 * @author L.Yang
 * @version 1.0, 2015年4月15日
 */
public interface ExcelReader {

	/**
	 * 解析 excel数据 （无需实现，已经在UserModelEventListener【03-】、XlsxHandler【07+】 分别实现）
	 * 
	 * 在读入excel时需要调用此方法，才能触发excel读入
	 * @param filename
	 *            excel 文件名
	 * @throws Exception
	 */
	void process(String filename) throws Exception;
}
