/*
 * ExcelReaderFactory.java
 * Copyright: Tsingsoft (c) 2015
 * Company: 北京清软创新科技有限公司
 */
package com.tsingsoft.common.tools.excel.bigdata.reader;

import com.tsingsoft.common.tools.excel.bigdata.reader.xls.ExcelReader4Xls;
import com.tsingsoft.common.tools.excel.bigdata.reader.xlsx.ExcelReader4Xlsx;
import com.tsingsoft.common.utils.string.StringUtil;


/**
 * excel 读取器工厂
 * <pre>excel 读取工厂，根据配置文件信息判断是获取03版excel读取器还是07版excel读取器  <pre>
 * @author L.Yang
 * @version 1.0, 2015年4月15日
 */
public class ExcelReaderFactory {
	
	/**
	 * 获取excel reader 实例
	 * @param service 数据处理业务逻辑（ExcelReadService）
	 * @param version excel 版本 03/07 , 为空 则默认为07
	 * @return excel reader实例
	 */
	public static ExcelReader getReader(ExcelReadService service, String version){
		version = StringUtil.isEmpty(version) ? "07" : version;
		ExcelReader reader = null;
		if(version.equals("03")){
			reader = new ExcelReader4Xls(service);
		}else if(version.equals("07")){
			reader = new ExcelReader4Xlsx(service);
		} 
		return reader;
	}
	
}
