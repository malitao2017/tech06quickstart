/*
 * ExcelReader4Xls.java
 * Copyright: Tsingsoft (c) 2015
 * Company: 北京清软创新科技有限公司
 */
package com.tsingsoft.common.tools.excel.bigdata.reader.xls;

import java.util.List;

import com.tsingsoft.common.tools.excel.bigdata.reader.ExcelReadService;

/**
 * excel 大数据 读取器（针对2003以上版本）
 * 
 * <pre>
 * excel reader for xls(excel 2003)
 * <pre>
 * @author L.Yang
 * @version 1.0, 2015年4月14日
 */
public class ExcelReader4Xls extends UserModelEventListener {

	private ExcelReadService service;
	
	public ExcelReader4Xls(ExcelReadService service) {
		this.service = service;
	}
	
	@Override
	protected void optSheet(int curSheet, List<List<String>> sheetDatas){
		service.optSheet(curSheet, sheetDatas);
	}
}
