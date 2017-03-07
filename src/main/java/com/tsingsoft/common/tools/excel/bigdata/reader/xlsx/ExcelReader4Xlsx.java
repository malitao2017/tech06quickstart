/*
 * ExcelReader4Xlsx.java
 * Copyright: Tsingsoft (c) 2015
 * Company: 北京清软创新科技有限公司
 */
package com.tsingsoft.common.tools.excel.bigdata.reader.xlsx;

import java.util.List;

import com.tsingsoft.common.tools.excel.bigdata.reader.ExcelReadService;

/**
 * excel 大数据 读取器（针对2007以上版本）
 * <pre>
 * excel reader for xlsx(excel 2007+)  
 * <pre>
 * @author L.Yang
 * @version 1.0, 2015年4月14日
 */
public class ExcelReader4Xlsx extends XlsxHandler{

	private ExcelReadService service;
	
	public ExcelReader4Xlsx(ExcelReadService service) {
		this.service = service;
	}

	@Override
	protected void optSheet(int curSheet, List<List<String>> sheetDatas){
		service.optSheet(curSheet, sheetDatas);
	}
}
