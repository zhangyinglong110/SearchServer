package com.edu.util;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

public class ExportUtils {
	/**
	 * 设置sheet表头信息
	 * 
	 * @author Administrator
	 *
	 */
	public static void outputHeaders(String[] headerInfo, HSSFSheet sheet, HSSFCellStyle headstyle) {
		HSSFRow row = sheet.createRow(0);
		row.setRowStyle(headstyle);
		for (int i = 0; i < headerInfo.length; i++) {
			sheet.setColumnWidth(i, 3500);
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(headstyle);
			cell.setCellValue(headerInfo[i]);
		}
	}

	public static void outputColumn(String[] headerInfo, List columnsInfo, HSSFSheet sheet, int rowIndex,
			HSSFCellStyle headstyle) {
		HSSFRow row;
		int headerSize = headerInfo.length;
		int columnSize = columnsInfo.size();
		// 循环插入多少行
		for (int i = 0; i < columnsInfo.size(); i++) {
			row = sheet.createRow(rowIndex + i);
			Object obj = columnsInfo.get(i);
			// 循环插入每行多少列
			for (int j = 0; j < headerInfo.length; j++) {
				Object value = getFieldValueByName(headerInfo[j], obj);
				if (null != value) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(headstyle);
					cell.setCellValue(value.toString());

				}
			}
		}
	}

	// 根据对象属性获取值
	private static Object getFieldValueByName(String fieldName, Object obj) {
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		String getter = "get" + firstLetter + fieldName.substring(1);
		try {
			Method method = obj.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(obj, new Object[] {});
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("属性不存在！");
			return null;
		}
	}
}
