/*
 * StatisticsUtil.java
 * Copyright: Tsingsoft (c) 2015
 * Company: 北京清软创新科技有限公司
 */
package com.tsingsoft.common.utils.math;

/**
 * 统计计算相关类
 * 
 * @author L.Yang
 * @time 2013-12-9
 */
public class StatisticsUtil {

	/**
	 * 绝对值
	 * 
	 * @param o
	 * @return Object
	 * @throws
	 */
	public static Object getAbs(Object o) {
		if (o == null) {
			return null;
		}
		return Math.abs(Double.parseDouble(o.toString()));
	}

	/**
	 * 计算数组中数据的最大值
	 * 
	 * @param datas
	 *            存放数据的数组
	 * @return 最大值
	 */
	public static Object max(Object[] datas) {
		if (datas == null) {
			return null;
		}
		Object maxValue = null;
		double max = Double.MIN_VALUE;
		try {
			for (int i = 0; i < datas.length; i++) {
				if (datas[i] == null) {
					continue;
				} else {
					Double tmp = NumberUtil.convert2Double(datas[i]);
					max = (max < tmp) ? tmp : max;
				}

			}
			maxValue = (max == Double.MIN_VALUE) ? null : max;
			return maxValue;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 计算数组中数据的最小值
	 * 
	 * @param datas
	 *            存放数据的数组
	 * @return 最小值
	 */
	public static Object min(Object[] datas) {
		Object minValue = null;
		double min = Double.MAX_VALUE;
		try {
			for (int i = 0; i < datas.length; i++) {
				if (datas[i] == null) {
					continue;
				}
				Double tmp = NumberUtil.convert2Double(datas[i]);
				min = (min > tmp) ? tmp : min;
			}
			minValue = (min == Double.MAX_VALUE) ? null : min;
			return minValue;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 计算数组中数据的平均值
	 * 
	 * @param datas
	 *            存放数据的数组，null－不参与计算，零－参与计算。
	 * @return 平均值
	 */
	public static Object avg(Object[] datas) {
		if (datas == null) {
			return null;
		}
		Object avgValue = null;
		int count = 0;
		double sum = 0d;
		try {
			for (int i = 0; i < datas.length; i++) {
				if (datas[i] == null) {
					continue;
				}
				sum += Double.parseDouble(datas[i].toString());
				count++;
			}
			if (count > 0) {
				avgValue = new Double(sum / count);
			} else {
				return null;
			}
			return new Double(avgValue.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 计算数组中数据的和
	 * 
	 * @param datas
	 *            存放数据的数组
	 * @return 平均值
	 */
	public static Object sum(Object[] datas) {
		if (datas == null) {
			return null;
		}
		Object sumValue = null;
		double sum = 0d;
		int num = 0;
		try {
			for (int i = 0; i < datas.length; i++) {
				if (datas[i] == null)// 如果是空则跳过
				{
					num++;
					continue;
				}
				sum += Double.parseDouble(datas[i].toString());
			}
			if (num == datas.length) {
				return null;
			} else {
				sumValue = new Double(sum);
				return sumValue;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 计算两个数的和
	 * 
	 * @param datas
	 *            存放数据的数组，null－不参与计算，零－参与计算。
	 * @return 平均值
	 */
	public static Object sum(Object data1, Object data2) {
		if (data1 == null && data2 == null) {
			return null;
		} else if (data1 == null) {
			return data2;
		} else if (data2 == null) {
			return data1;
		}
		double sumValue = Double.parseDouble(data1.toString())
				+ Double.parseDouble(data2.toString());
		return new Double(sumValue);
	}
}
