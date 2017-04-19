package com.edu.servlet;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
	 * �������
	 * 
	 * @param req
	 * @param resp
	 * @throws UnsupportedEncodingException
	 */
	private void exportEXcel(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String jsonString = req.getParameter("name");
		SelectBean selectBean = new JsonUtil().getJsonSelectJson(jsonString);
		List<Investigation> exportList = new DataBaseOperaUtil().getSelectInfo(selectBean);
		if (exportList != null && exportList.size() > 0) {
			if (exportList.get(0).getRole_Level().equals("��ʦ")) {
				String[] headName1 = { "����", "У��", "��ʦ����", "��ɫ", "רҵ", "�༶", "��ʦ����", "��Ŀ����", "��ѵ����", "�ش�����", "��ʦָ��",
						"��ѵ����", "���⼼��", "��ѵ����", "ʵ������", "��ѵ����Ʒ", "�ܷ�", "ƽ����", "ѧԱ����" };
				expressTeacher(req, resp, headName1, exportList);
			} else {
				String[] headName2 = { "����", "У��", "��ʦ����", "��ɫ", "רҵ", "�༶", "��ʦ����", "���ĳ̶�", "Ѳ��", "��ѧԱ��ͨ", "ȱ�ڹ�ע",
						"�༶����", "����Ͷ��", "��֯�", "���ϵļ�ʱ�շ�", "���幤������", "�ܷ�", "ƽ����", "ѧԱ����" };
				expressTeacher(req, resp, headName2, exportList);
			}
		} else {
			PrintWriter out = resp.getWriter();
			out.println("ʧЧ��");
		}
	}

	/**
	 * ������ʦ��
	 * 
	 * @param resp
	 * @param resp
	 * @param headName
	 * @param exportList
	 * @throws IOException
	 */
	private void expressTeacher(HttpServletRequest req, HttpServletResponse resp, String[] headName,
			List<Investigation> exportList) throws IOException {
		SimpleDateFormat ss = new SimpleDateFormat("yyyyMMddHHmmss");
		String dateString = ss.format(new Date());

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheets = wb.createSheet("sheet0");

		HSSFCellStyle cellstyle = (HSSFCellStyle) wb.createCellStyle();// ���ñ�ͷ��ʽ
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// ���þ���

		HSSFCellStyle headerStyle = (HSSFCellStyle) wb.createCellStyle();// ����������ʽ
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // ���ô�ֱ����
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // ����ˮƽ����

		HSSFFont headerFont = (HSSFFont) wb.createFont(); // ����������ʽ
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // ����Ӵ�
		headerFont.setFontName("Times New Roman"); // ������������
		headerFont.setFontHeightInPoints((short) 12); // ���������С
		headerStyle.setFont(headerFont); // Ϊ������ʽ����������ʽ

		HSSFCellStyle cell_Style = (HSSFCellStyle) wb.createCellStyle();// ����������ʽ
		cell_Style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cell_Style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// ��ֱ�������
		cell_Style.setWrapText(true); // ����Ϊ�Զ�����
		HSSFFont cell_Font = (HSSFFont) wb.createFont();
		cell_Font.setFontName("����");
		cell_Font.setFontHeightInPoints((short) 10);
		cell_Style.setFont(cell_Font);
		cell_Style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // �±߿�
		cell_Style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ��߿�
		cell_Style.setBorderTop(HSSFCellStyle.BORDER_THIN);// �ϱ߿�
		cell_Style.setBorderRight(HSSFCellStyle.BORDER_THIN);// �ұ߿�

		String Column[] = { "large_Area", "sch_Name", "tea_Name", "role_Level", "cus_Name", "stu_Class",
				"tea_Attendance", "cls_Explain", "cls_Quesions", "ques_Answer", "cls_Coach", "cls_Discipline",
				"cls_Skill", "cls_Progress", "exam_Explain", "class_Homework", "total_Score", "average", "stu_Advice" };// ��id
		ExportUtils.outputHeaders(headName, sheets, headerStyle);// ���ɱ�ͷ
		ExportUtils.outputColumn(Column, exportList, sheets, 1, cell_Style);// �����б�����

		String path = req.getRealPath("/xlsx");
		String filePath = path + "/" + dateString + ".xls";
		FileOutputStream fos = new FileOutputStream(filePath);
		wb.write(fos);
		fos.flush();
		fos.close();

		ServletOutputStream out = resp.getOutputStream();
		out.println(dateString + ".xls");
		out.flush();
		out.close();
	}

}
