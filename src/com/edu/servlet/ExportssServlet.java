package com.edu.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.edu.bean.Investigation;
import com.edu.bean.SelectBean;
import com.edu.util.DataBaseOperaUtil;
import com.edu.util.ExportUtils;
import com.edu.util.JsonUtil;

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
		String jsonString = req.getParameter("name");
		SelectBean selectBean = JsonUtil.getJsonSelectJson(jsonString);
		List<Investigation> exportList = DataBaseOperaUtil.getSelectInfo(selectBean);
		if (exportList != null && exportList.size() > 0) {
			if (exportList.get(0).getRole_Level().equals("讲师")) {
				String[] headName1 = { "大区", "校区", "教师姓名", "角色", "专业", "班级", "老师出勤", "项目讲解", "培训提问", "回答问题", "老师指导",
						"培训纪律", "讲解技巧", "培训进度", "实例讲解", "培训后作品", "总分", "平均分", "学员建议", "填表日期" };
				expressTeacher(req, resp, headName1, exportList, selectBean);
			} else {
				String[] headName2 = { "大区", "校区", "教师姓名", "角色", "专业", "班级", "老师出勤", "关心程度", "巡堂", "找学员沟通", "缺勤关注",
						"班级纪律", "受理投诉", "组织活动", "资料的及时收发", "整体工作评分", "总分", "平均分", "学员建议", "填表日期" };
				expressTeacher(req, resp, headName2, exportList, selectBean);
			}
		} else {
			PrintWriter out = resp.getWriter();
			out.print("当前导出无结果！");
		}
	}

	/**
	 * 导出老师的
	 * 
	 * @param resp
	 * @param resp
	 * @param headName
	 * @param exportList
	 * @throws IOException
	 */
	private void expressTeacher(HttpServletRequest req, HttpServletResponse resp, String[] headName,
			List<Investigation> exportList, SelectBean selectBean) throws IOException {
		SimpleDateFormat ss = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = ss.format(new Date());

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheets = wb.createSheet("sheet0"); // 设置excel表格的名称

		HSSFCellStyle headerStyle = (HSSFCellStyle) wb.createCellStyle();// 创建标题样式
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 设置垂直居中
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 设置水平居中
		HSSFFont headerFont = (HSSFFont) wb.createFont(); // 创建字体样式
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 字体加粗
		headerFont.setFontName("Times New Roman"); // 设置字体类型
		headerFont.setFontHeightInPoints((short) 12); // 设置字体大小
		headerStyle.setFont(headerFont); // 为标题样式设置字体样式

		// ---------------第一种样式-----------------
		HSSFCellStyle cell_Style = wb.createCellStyle();// 设置字体样式
		cell_Style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell_Style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直对齐居中
		cell_Style.setWrapText(true); // 设置为自动换行
		HSSFFont cell_Font = wb.createFont();
		cell_Font.setFontName("宋体");
		cell_Font.setFontHeightInPoints((short) 10);
		cell_Style.setFont(cell_Font);
		cell_Style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		cell_Style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		cell_Style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		cell_Style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

		// ------------第二种样式--------------------------
		HSSFCellStyle cell_Style_new = wb.createCellStyle();// 设置字体样式
		cell_Style_new.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell_Style_new.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直对齐居中
		cell_Style_new.setWrapText(true); // 设置为自动换行
		cell_Style_new.setFont(cell_Font);
		cell_Style_new.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		cell_Style_new.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		cell_Style_new.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		cell_Style_new.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

		// ------------第三种样式--------------------------
		HSSFCellStyle cell_Style_red = wb.createCellStyle();// 设置字体样式
		cell_Style_red.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell_Style_red.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直对齐居中
		cell_Style_red.setWrapText(true); // 设置为自动换行
		cell_Style_red.setFont(cell_Font);
		cell_Style_red.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
		cell_Style_red.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
		cell_Style_red.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
		cell_Style_red.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

		String Column[] = { "large_Area", "sch_Name", "tea_Name", "role_Level", "cus_Name", "stu_Class",
				"tea_Attendance", "cls_Explain", "cls_Quesions", "ques_Answer", "cls_Coach", "cls_Discipline",
				"cls_Skill", "cls_Progress", "exam_Explain", "class_Homework", "total_Score", "average", "stu_Advice",
				"fill_Date" };// 列id
		ExportUtils.outputHeaders(headName, sheets, headerStyle);// 生成表头
		ExportUtils.outputColumn(Column, exportList, sheets, 1, cell_Style, cell_Style_new,cell_Style_red);// 生成列表数据

		// ----------------------------导出表格的操作-----------------------------
		String path = req.getRealPath("/xlsx");
		String fileName = null;
		if ("".equals(selectBean.getLargeArea()) || "请选择".equals(selectBean.getLargeArea())) {
			fileName = "全国-" + selectBean.getRole_Level() + "-" + dateString + "-满意度调查结果表";
		} else if ("".equals(selectBean.getSchName()) || "请选择".equals(selectBean.getSchName())) {
			fileName = selectBean.getLargeArea() + "-" + selectBean.getRole_Level() + "-" + dateString + "-满意度调查结果表";
		} else {
			fileName = selectBean.getLargeArea() + "-" + selectBean.getSchName() + "-" + selectBean.getRole_Level()
					+ "-" + dateString + "-满意度调查结果表";
		}

		String filePath = path + "/" + fileName + ".xls";
		FileOutputStream fos = new FileOutputStream(filePath);
		wb.write(fos);
		fos.flush();
		fos.close();

		PrintWriter pw = resp.getWriter();// 响应服务器对象
		pw.println(fileName + ".xls");
		pw.flush();
		pw.close();

	}

}
