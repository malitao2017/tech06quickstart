/*
 * ExcelReadService.java
 * Copyright: Tsingsoft (c) 2015
 * Company: 北京清软创新科技有限公司
 */
package com.tsingsoft.common.tools.excel.bigdata.reader;

import java.util.List;

/**
 * excel 读取业务逻辑行为接口
 * <pre> 
 * 具体业务实现此接口，来实现对excel读取数据的处理
 * <pre>
 * @author L.Yang
 * @version 1.0, 2015年4月15日
 */
public interface ExcelReadService {
	
	/**
	 * 设置整个sheet页的数据
	 * @param curSheet
	 *            当前sheet索引
	 * @param sheetDatas
	 *            列表
	 */
	void optSheet(int curSheet, List<List<String>> sheetDatas);
}
