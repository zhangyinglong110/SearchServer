package com.edu.servlet;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.bean.CityMap;

@WebServlet(name = "zoneServlet", urlPatterns = "/zoneServlet")
public class ZoneServletServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if ("getLargeArea".equals(action)) {
			this.getLargeArea(req, resp);
		} else if ("getSchName".equals(action)) {
			this.getSchName(req, resp);
		}
	}

	/**
	 * ��ô�����Ϣ
	 * 
	 * @param resp
	 * @param req
	 * @throws IOException
	 */
	private void getLargeArea(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setCharacterEncoding("UTF-8");// ������Ӧ�ı����ʽ
		String result = "";
		CityMap cityMap = new CityMap();// ʵ��������ʡ����Ϣ��CityMap���ʵ��
		Map<String, String[]> map = cityMap.model; // ��ȡʡ����Ϣ�����浽Map��
		Set<String> set = map.keySet(); // ��ȡmap�����еļ�������set���Ϸ���
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			result = result + it.next() + ",";
		}
		result = result.substring(0, result.length() - 1);// ȥ�����һ������
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		pw.println(result);
		pw.flush();
		pw.close();
	}

	/**
	 * ���У����Ϣ
	 * 
	 * @param resp
	 * @param req
	 * @throws IOException
	 */
	private void getSchName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setCharacterEncoding("UTF-8");// ������Ӧ�ı����ʽ
		String result = "";
		String selLargeArea = req.getParameter("parLargeArea"); // ��ȡѡ��Ĵ���
		CityMap cityMap = new CityMap(); // ʵ��������ʡ����Ϣ��CityMap���ʵ��
		Map<String, String[]> map = cityMap.model; // ��ȡʡ����Ϣ���浽Map��
		String[] arrSchName = map.get(selLargeArea); // ��ȡָ������ֵ
		for (int i = 0; i < arrSchName.length; i++) { // ����ȡ����������Ϊһ���Զ��ŷָ����ַ���
			result = result + arrSchName[i] + ",";
		}
		result = result.substring(0, result.length() - 1); // ȥ�����һ������
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.print(result); // �����ȡ�������ַ���
		out.flush();
		out.close();

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
