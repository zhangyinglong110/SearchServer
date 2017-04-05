package com.edu.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.edu.bean.Investigation;
import com.edu.util.ExportUtils;

public class ExportssServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		req.setCharacterEncoding("UTF-8");
		try {
			exportEXcel(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出表格
	 * 
	 * @param req
	 * @param resp
	 * @throws UnsupportedEncodingException
	 */
	private void exportEXcel(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// String jsonArr = req.getParameter("exportss");
		List<Investigation> exportList = (List<Investigation>) req.getSession().getAttribute("selectInfo");

		String[] headName = { "大区", "校区", "教师姓名", "专业", "班级", "老师出勤", "项目讲解", "培训提问", "回答问题", "老师指导", "培训纪律", "讲解技巧",
				"培训进度", "实例讲解", "培训后作品", "总分", "平均分", "学员建议" };
		// List<Investigation> exportList = new
		// JsonUtil().getJsonExprossBean(jsonArr);
		System.out.println(exportList.toString());
		SimpleDateFormat ss = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = ss.format(new Date());

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheets = wb.createSheet("sheet0");

		HSSFCellStyle cellstyle = (HSSFCellStyle) wb.createCellStyle();// 设置表头样式
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 设置居中

		HSSFCellStyle headerStyle = (HSSFCellStyle) wb.createCellStyle();// 创建标题样式
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 设置垂直居中
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 设置水平居中

		HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
		headerFont.setFontName("Times New Roman"); // 设置字体类型
		headerFont.setFontHeightInPoints((short) 12); // 设置字体大小
		headerStyle.setFont(headerFont); // 为标题样式设置字体样式

		HSSFCellStyle cell_Style = (HSSFCellStyle) wb.createCellStyle();// 设置字体样式
		cell_Style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell_Style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直对齐居中
		cell_Style.setWrapText(true); // 设置为自动换行
		HSSFFont cell_Font = (HSSFFont) wb.createFont();
		cell_Font.setFontName("宋体");
		cell_Font.setFontHeightInPoints((short) 10);
		cell_Style.setFont(cell_Font);
		cell_Style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		cell_Style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		cell_Style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		cell_Style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

		String Column[] = { "large_Area", "sch_Name", "tea_Name", "cus_Name","stu_Class","tea_Attendance", "cls_Explain",
				"cls_Quesions", "ques_Answer", "cls_Coach", "cls_Discipline", "cls_Skill", "cls_Progress",
				"exam_Explain", "class_Homework", "total_Score", "average", "stu_Advice" };// 列id
		ExportUtils.outputHeaders(headName, sheets, headerStyle);// 生成表头
		ExportUtils.outputColumn(Column, exportList, sheets, 1, cell_Style);// 生成列表数据

		String path = req.getRealPath("/xlsx");
		System.out.println("绝对路径path:" + path);
		String filePath = path + "/" + dateString + ".xls";
		System.out.println("文件路径：" + filePath);
		FileOutputStream fos = new FileOutputStream(filePath);
		wb.write(fos);
		fos.flush();
		fos.close();

		ServletOutputStream out = resp.getOutputStream();
		// String fileUrl = path+"/abc.xls";
		out.println(dateString + ".xls");
		out.flush();
		out.close();

	}

}
