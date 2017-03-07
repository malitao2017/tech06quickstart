/*
 * NumberUtil.java
 * Copyright: Tsingsoft (c) 2015
 * Company: 北京清软创新科技有限公司
 */
package com.tsingsoft.common.utils.math;

import java.text.NumberFormat;

import com.tsingsoft.common.utils.string.StringUtil;

/**
 * 数字工具类
 * @author L.Yang 
 * @time 2013-12-9
 */
public class NumberUtil {
	private NumberUtil(){}
	
	
	/** 默认保留小数点位数 */
	private static final int DEF_SCALE = 2;
	
	/**
	 * 将数据转换成 Double
	 * @param val
	 * @return double 值
	 */
	public static final Double convert2Double(Object val){
		String value = StringUtil.trimNull(val);
		if(StringUtil.isEmpty(value)){
			return null;
		}else{
			return Double.parseDouble(value);
		}
	}
	
	
	/**
     * 格式化显示小数
     * 
     * @param val
     *            需要显示的数值
     * 
     * @param scale
     *            小数点后保留几位
     * 
     * @return 格式化的结果
     */
    public static final String formatNumber(Double val, int scale) {
        String rtnVal = "";
        java.text.NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(scale);
        format.setMinimumFractionDigits(scale);
        format.setGroupingUsed(false);
        if(val != null) {
            rtnVal = format.format(val);
        }
        return rtnVal;

    }

    /**
     * 格式化显示小数（两位有效数字的小数）
     * 
     * @param val
     *            需要显示的数值
     * 
     * 
     * @return 格式化的结果
     */
    public static final String formatNumber(Double val) {
        return formatNumber(val, DEF_SCALE);

    }

    /**
     * 格式化显示小数
     * 
     * @param val
     *            需要显示的数值
     * 
     * 
     * @return 格式化的结果
     */
    public static final String formatNumber(String val) {
        Double dobject = null;
        if(StringUtil.isEmpty(val)) {
            return "";
        }
        try {
            dobject = convert2Double(val);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return formatNumber(dobject, DEF_SCALE);

    }

    /**
     * 格式化显示小数
     * 
     * @param val
     *            需要显示的数值
     * 
     * 
     * @return 格式化的结果
     */
    public static final String formatNumber(String val, int scale) {
        Double dobject = null;
        if(StringUtil.isEmpty(val)) {
            return "";
        }
        try {
        	dobject = convert2Double(val);
        } catch (Exception e) {
             e.printStackTrace();
            return "";
        }
        return formatNumber(dobject, scale);
    }
    
    /**
	 * 根据最大值最小值生成随机数
	 * @param min 最小值
	 * @param max 最大值
	 * @return 随即岁 in (最小值, 最大值)
	 */
	public static final int randomNumber(int min, int max) {
		return (int) Math.round((Math.random() * (max - min) + min));
	}
}
