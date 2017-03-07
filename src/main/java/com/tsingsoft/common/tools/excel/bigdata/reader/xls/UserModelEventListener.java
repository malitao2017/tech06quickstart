/*
 * UserModelEventListener.java
 * Copyright: Tsingsoft (c) 2015
 * Company: 北京清软创新科技有限公司
 */
package com.tsingsoft.common.tools.excel.bigdata.reader.xls;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.eventusermodel.FormatTrackingHSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFEventFactory;
import org.apache.poi.hssf.eventusermodel.HSSFListener;
import org.apache.poi.hssf.eventusermodel.HSSFRequest;
import org.apache.poi.hssf.eventusermodel.MissingRecordAwareHSSFListener;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.record.BOFRecord;
import org.apache.poi.hssf.record.FormulaRecord;
import org.apache.poi.hssf.record.LabelRecord;
import org.apache.poi.hssf.record.LabelSSTRecord;
import org.apache.poi.hssf.record.NumberRecord;
import org.apache.poi.hssf.record.Record;
import org.apache.poi.hssf.record.RowRecord;
import org.apache.poi.hssf.record.SSTRecord;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tsingsoft.common.tools.excel.bigdata.reader.ExcelReader;
import com.tsingsoft.common.utils.string.StringUtil;



/**
 * 基于POI HSSF的eventmodel 模型的时间解析方式 
 *   
 * <pre>
 * 优点：解析数据相当快。 
 * 缺点：1.仅仅支持97~2003版本的excel，不支持2007版本的excel。 
 * <pre>
 * @author L.Yang
 * @version 1.0, 2015年4月15日
 */
public abstract class UserModelEventListener implements HSSFListener, ExcelReader{

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/** Records we pick up as we process */
	private SSTRecord sstRecord;
	
	/** So we known which sheet we're on */
	private int curSheet;
	private int curRow;
	/** sheet 页中含有数据行的总行数 */
	private int rows = 0;
	
	private List<String> rowDatas;
	private List<List<String>> sheetDatas;
	
	/**
	 * 遍历 excel 文件
	 */
	public void process(String filename) throws IOException {
		initVariable();
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filename));
		MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(
				this);
		FormatTrackingHSSFListener formatListener = new FormatTrackingHSSFListener(
				listener);

		HSSFEventFactory factory = new HSSFEventFactory();
		HSSFRequest request = new HSSFRequest();
		request.addListenerForAllRecords(formatListener);
		factory.processWorkbookEvents(request, fs);
	}
	
	private long before = new Date().getTime();
	
	/**
	 * HSSFListener 监听方法，处理 Record
	 * @param record
	 */
	@Override
	public void processRecord(Record record) {
		String value = null;
		switch (record.getSid()) {

		case BOFRecord.sid:
			BOFRecord bof = (BOFRecord) record;
			
			if (bof.getType() == BOFRecord.TYPE_WORKBOOK) {
				logger.debug("开始解析excel 文档.....");
			} else if (bof.getType() == BOFRecord.TYPE_WORKSHEET) {
//				顺序进入新的Worksheet，因为Event API不会把Excel文件里的所有数据结构都关联起来， 
//				所以这儿一定要记录现在进入第几个sheet了。
				curSheet++;
				logger.debug("开始解析sheet {} 页面内容...", curSheet);
			}
			break;
		case SSTRecord.sid:
			sstRecord = (SSTRecord) record;
			break;
		case RowRecord.sid:
            rows++;
            break;
		case FormulaRecord.sid:
			FormulaRecord frec = (FormulaRecord) record;
			
			curRow = frec.getRow();
			value = StringUtil.trimNull(frec.getValue());
			rowDatas.add(frec.getColumn(), value);
			break;
		case LabelRecord.sid:
			LabelRecord lrec = (LabelRecord) record;

			curRow = lrec.getRow();
			value = StringUtil.trimNull(lrec.getValue());
			rowDatas.add(lrec.getColumn(), value);
//			logger.debug("LabelRecord。　行：{} , 列：{}" ,new Object[]{curRow, lrec.getColumn()});
			break;
		case LabelSSTRecord.sid:
			LabelSSTRecord lsrec = (LabelSSTRecord) record;

			curRow = lsrec.getRow();
			value = StringUtil.trimNull(sstRecord.getString(lsrec.getSSTIndex()));
			rowDatas.add(lsrec.getColumn(), value);
//			logger.debug("LabelRecord。　行：{} , 列：{}" ,new Object[]{curRow, lsrec.getColumn()});
			break;
		case NumberRecord.sid:
			NumberRecord numrec = (NumberRecord) record;

			curRow = numrec.getRow();
			value = StringUtil.trimNull(numrec.getValue());//TODO			判断是否是日期
			rowDatas.add(numrec.getColumn(), value);
//			logger.debug("LabelRecord。　行：{} , 列：{}" ,new Object[]{curRow, numrec.getColumn()});
			break;
		default:
			break;
		}

		// 空值的操作
		if (record instanceof MissingCellDummyRecord) {
			MissingCellDummyRecord mc = (MissingCellDummyRecord) record;
			curRow = mc.getRow();
			rowDatas.add(mc.getColumn(), " ");
		}

		if (record instanceof LastCellOfRowDummyRecord && !rowDatas.isEmpty()) {// 行结束时的操作
			sheetDatas.add(new ArrayList<String>(rowDatas));
			rowDatas.clear();
			if(rows - 1 == curRow){//本sheet页中所有行读取完成
				optSheet(curSheet, sheetDatas);
				long after = new Date().getTime();
				logger.debug("解析sheet {}完成，耗时{} ms", new Object[]{curSheet,(after - before)});
				before = after;
				rows = 0 ;
				sheetDatas.clear();
			}
		}
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
