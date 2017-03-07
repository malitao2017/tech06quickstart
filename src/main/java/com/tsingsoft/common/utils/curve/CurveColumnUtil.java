/*
 * CurveColumnUtil.java
 * Copyright: Tsingsoft (c) 2015
 * Company: 北京清软创新科技有限公司
 */
package com.tsingsoft.common.utils.curve;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 
 * <pre>曲线列名、列标题 工具类  <pre>
 * @author L.Yang
 * @version 1.0, 2015年6月2日
 */
public class CurveColumnUtil {
	
	private CurveColumnUtil(){}

	/**
	 * 一天的所有分钟数 1440
	 */
	public static final int MINUTES = 24 * 60;
	
	/**
	 * 根据点数获取 曲线点数列名列表 如0000
	 * @param step 曲线步长
	 * @@param isStartWithZero 是否从00开始
	 * @return 对应点数的时刻列表
	 */
	public static final List<String> getCurveColumnList(int step, boolean isStartWithZero){
		List<String> columnList = new ArrayList<String>();
		int pointNum = MINUTES/step;
		Calendar gc = GregorianCalendar.getInstance();
		gc.set(Calendar.HOUR_OF_DAY, 0);
		
		if(!isStartWithZero){
			gc.set(Calendar.MINUTE, step);
		}else{
			gc.set(Calendar.MINUTE, 0);
		}
		
		DateFormat dateFormat = new SimpleDateFormat("HHmm");
		for(int i = 0; i < pointNum; i++){
			String column = "T" + dateFormat.format(gc.getTime());
			if(!isStartWithZero && column.equalsIgnoreCase("T0000")){
				column="T2400";
			}
			columnList.add(column);
			gc.add(Calendar.MINUTE, step);
		}
		return columnList;
	}
}
