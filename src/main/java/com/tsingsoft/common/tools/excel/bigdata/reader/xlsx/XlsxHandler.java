/*
 * AbstractHandler4Xlsx.java
 * Copyright: Tsingsoft (c) 2015
 * Company: 北京清软创新科技有限公司
 */
package com.tsingsoft.common.tools.excel.bigdata.reader.xlsx;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.tsingsoft.common.tools.excel.bigdata.reader.ExcelReader;
import com.tsingsoft.common.utils.string.StringUtil;

/**
 * Abstract Handler for Xlsx（excel 2007+）
 * 
 * <pre> XSSF and sax excel handler
 * 
 * <pre>
 * @author L.Yang
 * @version 1.0, 2015年4月14日
 */
public abstract class XlsxHandler extends DefaultHandler implements ExcelReader{

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private SharedStringsTable sst;
	private String lastContents;
	private boolean nextIsString;

	private int curSheet;
	private int curCol;

	private List<String> rowDatas;
	private List<List<String>> sheetDatas;

	/**
	 * 获取所有 sheet 数据
	 * 
	 * @param filename
	 *            excel 文件名
	 * @throws Exception
	 */
	public void process(String filename) throws Exception {
		initVariable();
		logger.debug("开始解析excel 文档.....");
		OPCPackage pkg = OPCPackage.open(filename);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();

		XMLReader parser = fetchSheetParser(sst);

		Iterator<InputStream> sheets = r.getSheetsData();
		long before = new Date().getTime();
		while (sheets.hasNext()) {
			curSheet++;
			sheetDatas.clear();
			logger.debug("开始解析sheet {} 页面内容...", curSheet);
			InputStream sheet = sheets.next();
			InputSource sheetSource = new InputSource(sheet);
			parser.parse(sheetSource);
			sheet.close();
			optSheet(curSheet, sheetDatas);
			long after = new Date().getTime();
			logger.debug("解析sheet {}完成，耗时{} ms", new Object[]{curSheet,(after - before)});
		}
	}

	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {
		if (name.equals("c")) { // c => 单元格
			String cellType = attributes.getValue("t");
			if (cellType != null && cellType.equals("s")) { 
				// 如果下一个元素是 SST 的索引，则將nextIsString標記为true
				nextIsString = true;
			} else {
				nextIsString = false;
			}
		}
		lastContents = ""; // 置空
	}

	public void endElement(String uri, String localName, String name)
			throws SAXException {
		if (nextIsString) { 
			// 根据SST的索引值的到单元格的真正要存儲的字符串 ,这时characters()方法可能会被调用多次
			int idx = Integer.parseInt(lastContents);
			lastContents = new XSSFRichTextString(sst.getEntryAt(idx))
					.toString();
			nextIsString = false;
		}
		// v => 单元格的值，如果单元格是字符串则 v标签的值为該字符串在SST中的索引
		// 將单元格內容加入rowlist中，在这之前先去掉字符串前后的空白符
		if (name.equals("v")) {
			String value = StringUtil.trimNull(lastContents);
			rowDatas.add(curCol, value);
			curCol++;
		} else if (name.equals("row")) {
			// 如果标签名为 row ，说明已经到行尾部，调用optRows() 方法
			sheetDatas.add(new ArrayList<String>(rowDatas));
			rowDatas.clear();
			curCol = 0;
		}
	}

	public void characters(char[] ch, int start, int length)
			throws SAXException { // 得到单元格內容的值
		lastContents += new String(ch, start, length);
	}
	
	
	private XMLReader fetchSheetParser(SharedStringsTable sst)
			throws SAXException {
		XMLReader parser = XMLReaderFactory
				.createXMLReader("org.apache.xerces.parsers.SAXParser");
		this.sst = sst;
		parser.setContentHandler(this);
		return parser;
	}
	
	/** 初始化状态变量，在同一段程序内多次调用的话需要重置sheet 索引 */
	private void initVariable(){
		this.curSheet = 0;
		this.sheetDatas = new ArrayList<List<String>>();
		this.rowDatas = new ArrayList<String>();
	}
	
	/**
	 * 设置整个sheet页的数据
	 * @param curSheet
	 *            当前sheet索引
	 * @param sheetDatas
	 *            列表
	 */
	protected abstract void optSheet(int curSheet, List<List<String>> sheetDatas);
}
