package com.edu.util;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.HSSFColor;

import com.edu.bean.Investigation;

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

	public static void outputColumn(String[] headerInfo, List<Investigation> columnsInfo, HSSFSheet sheet, int rowIndex,
			HSSFCellStyle cell_Style, HSSFCellStyle cell_Style_new, HSSFCellStyle cell_Style_red) {
		HSSFRow row;
		int headerSize = headerInfo.length;
		int columnSize = columnsInfo.size();
		// 循环插入多少行
		for (int i = 0; i < columnSize; i++) {
			row = sheet.createRow(rowIndex + i);
			Object obj = columnsInfo.get(i);
			// 循环插入每行多少列
			for (int j = 0; j < headerSize; j++) {

				Object value = getFieldValueByName(headerInfo[j], obj);
				if (columnsInfo.get(i).getAverage() < 4.0 && columnsInfo.get(i).getAverage() >= 3.0) {
					cell_Style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
					cell_Style.setFillForegroundColor(HSSFColor.YELLOW.index);
					if (null != value) {
						if (j > 5 && j < 18) {
							HSSFCell cell = row.createCell(j);
							cell.setCellStyle(cell_Style);
							cell.setCellValue(Double.parseDouble(value.toString()));
						} else {
							HSSFCell cell = row.createCell(j);
							cell.setCellStyle(cell_Style);
							cell.setCellValue(value.toString());
						}
					}
				} else if (columnsInfo.get(i).getAverage() < 3.0) {
					cell_Style_new.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
					cell_Style_new.setFillForegroundColor(HSSFColor.RED.index);
					if (null != value) {
						if (j > 5 && j < 18) {
							HSSFCell cell = row.createCell(j);
							cell.setCellStyle(cell_Style_new);
							cell.setCellValue(Double.parseDouble(value.toString()));
						} else {
							HSSFCell cell = row.createCell(j);
							cell.setCellStyle(cell_Style_new);
							cell.setCellValue(value.toString());
						}
					}
				} else {
					cell_Style_red.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
					cell_Style_red.setFillForegroundColor(HSSFColor.WHITE.index);
					if (null != value) {
						if (j > 5 && j < 18) {
							HSSFCell cell = row.createCell(j);
							cell.setCellStyle(cell_Style_red);
							cell.setCellValue(Double.parseDouble(value.toString()));
						} else {
							HSSFCell cell = row.createCell(j);
							cell.setCellStyle(cell_Style_red);
							cell.setCellValue(value.toString());
						}
					}
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
