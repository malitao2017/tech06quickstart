/*
 * ExcelReader.java
 * Copyright: Copyright TsingSoft (c) 2015
 * Company: 北京清软创新科技有限公司
 */
package com.tsingsoft.common.tools.excel.normaldata.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.tsingsoft.common.utils.exception.ExceptionUtil;


/**
 * 
 * excel 读取 工具<br>
 * <pre>支持 2003、 2007  <pre>
 * @author L.Yang
 * @version 1.0, 2014-11-20
 */
@Service("excelReader")
public class ExcelReader{
	
	private final Log log = LogFactory.getLog(this.getClass());

	/**
	 * 读取excel 文件值
	 * @param file excel 文件实例
	 * @return key:sheet名, value:行列数据
	 */
	public Map<String, List<List<Object>>> readForMap(File file) {
		Map<String, List<List<Object>>> datas = null;
		InputStream inputStream = null;
		try {
			Workbook wb = null;
			try {
				try {
					inputStream = new FileInputStream(file);
					wb = new HSSFWorkbook(inputStream);
				} catch (Exception e) {
					inputStream = new FileInputStream(file);
					wb = new XSSFWorkbook(inputStream);
				}
				datas = readForMap(wb);
			} catch (Exception ex) {
				log.error(ExceptionUtil.getStackTraceAsString(ex));
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			log.error(ExceptionUtil.getStackTraceAsString(e));
		} catch (IOException e) {
			log.error(ExceptionUtil.getStackTraceAsString(e));
		} finally {
			close(inputStream);
		}
		return datas;
	}

	/**
	 * 根据工作表进行读取
	 * @param wb 工作表实例
	 * @return key:sheet名, value:行列数据
	 */
	private Map<String, List<List<Object>>> readForMap(Workbook wb) {
		Map<String, List<List<Object>>> datas = new LinkedHashMap<String, List<List<Object>>>();
		int sheetNum = wb.getNumberOfSheets();
		for(int i = 0; i < sheetNum; i++){
			Sheet sheet = wb.getSheetAt(i);
			List<List<Object>> sheetDatas = read(sheet);
			datas.put(sheet.getSheetName(), sheetDatas);
		}
		return datas;
	}
	
	/**
	 * 读取excel 文件值
	 * @param file excel 文件实例
	 * @return key:sheet名, value:行列数据
	 */
	public List<List<List<Object>>> readForList(File file) {
		List<List<List<Object>>> datas = null;
		InputStream inputStream = null;
		try {
			Workbook wb = null;
			try {
				try {
					inputStream = new FileInputStream(file);
					wb = new HSSFWorkbook(inputStream);
				} catch (Exception e) {
					inputStream = new FileInputStream(file);
					wb = new XSSFWorkbook(inputStream);
				}
				datas = readForList(wb);
			} catch (Exception ex) {
				log.error(ExceptionUtil.getStackTraceAsString(ex));
			}
			inputStream.close();
		} catch (FileNotFoundException e) {
			log.error(ExceptionUtil.getStackTraceAsString(e));
		} catch (IOException e) {
			log.error(ExceptionUtil.getStackTraceAsString(e));
		} finally {
			close(inputStream);
		}
		return datas;
	}

	/**
	 * 根据工作表进行读取
	 * @param wb 工作表实例
	 * @return key:sheet名, value:行列数据
	 */
	private List<List<List<Object>>> readForList(Workbook wb) {
		List<List<List<Object>>> datas = new ArrayList<List<List<Object>>>();
		int sheetNum = wb.getNumberOfSheets();
		for(int i = 0; i < sheetNum; i++){
			Sheet sheet = wb.getSheetAt(i);
			List<List<Object>> sheetDatas = read(sheet);
			datas.add(sheetDatas);
		}
		return datas;
	}

	/**
	 * 安sheet页进行excel 读取
	 * @param sheet sheet对象实例
	 * @return 每个sheet也内的数据，安行列存储
	 */
	private List<List<Object>> read(Sheet sheet) {
		List<List<Object>> dataLst = new ArrayList<List<Object>>();
		for (int r = sheet.getFirstRowNum() ; r <= sheet.getLastRowNum(); r++) {//循环Excel的行
			Row row = sheet.getRow(r);
			if (row == null) continue;
			List<Object> rowLst = new ArrayList<Object>();
			for (int c = row.getFirstCellNum(); c <= row.getLastCellNum(); c++) {//循环Excel的列
				Cell cell = row.getCell(c);
				Object cellValue = null;
				if (null != cell) {
					// 以下是判断数据的类型
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC: // 数字
						if (HSSFDateUtil.isCellDateFormatted(cell)
								|| cell.getCellStyle().getDataFormat() == 58
								|| cell.getCellStyle().getDataFormat() == 31
								|| cell.getCellStyle().getDataFormat() == 32) {  
							cellValue = cell.getDateCellValue();
			            } else {  
			            	cellValue = cell.getNumericCellValue();  
			            }  
						 break;  
					case HSSFCell.CELL_TYPE_STRING: // 字符串
						cellValue = cell.getStringCellValue();
						break;

					case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
						cellValue = cell.getBooleanCellValue();
						break;

					case HSSFCell.CELL_TYPE_FORMULA: // 公式
						try {
							cellValue = cell.getNumericCellValue();
						} catch (IllegalStateException e) {
							cell.setCellType(Cell.CELL_TYPE_STRING);
							cellValue = cell.getRichStringCellValue();
						}
						break;

					case HSSFCell.CELL_TYPE_BLANK: // 空值
						cellValue = null;
						break;

					case HSSFCell.CELL_TYPE_ERROR: // 故障
						cellValue = "非法字符";
						break;

					default:
						cellValue = "未知类型";
						break;
					}
				}
				rowLst.add(cellValue);
			}
			/* 保存第r行的第c列 */
			dataLst.add(rowLst);
		}
		return dataLst;
	}

	/** 关闭流 */
	private void close(InputStream inputStream) {
		if (inputStream != null) {
			try {
				inputStream.close();
			} catch (IOException e) {
				inputStream = null;
				log.error(ExceptionUtil.getStackTraceAsString(e));
			}
		}
	}
}
