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
	 * 获得大区信息
	 * 
	 * @param resp
	 * @param req
	 * @throws IOException
	 */
	private void getLargeArea(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setCharacterEncoding("UTF-8");// 设置响应的编码格式
		String result = "";
		CityMap cityMap = new CityMap();// 实例化保存省份信息的CityMap类的实例
		Map<String, String[]> map = cityMap.model; // 获取省份信息，保存到Map中
		Set<String> set = map.keySet(); // 获取map集合中的键，并以set集合返回
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			result = result + it.next() + ",";
		}
		result = result.substring(0, result.length() - 1);// 去除最后一个逗号
		resp.setContentType("text/html");
		PrintWriter pw = resp.getWriter();
		pw.println(result);
		pw.flush();
		pw.close();
	}

	/**
	 * 获得校区信息
	 * 
	 * @param resp
	 * @param req
	 * @throws IOException
	 */
	private void getSchName(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setCharacterEncoding("UTF-8");// 设置响应的编码格式
		String result = "";
		String selLargeArea = req.getParameter("parLargeArea"); // 获取选择的大区
		CityMap cityMap = new CityMap(); // 实例化保存省份信息的CityMap类的实例
		Map<String, String[]> map = cityMap.model; // 获取省份信息保存到Map中
		String[] arrSchName = map.get(selLargeArea); // 获取指定键的值
		for (int i = 0; i < arrSchName.length; i++) { // 将获取的市县连接为一个以逗号分隔的字符串
			result = result + arrSchName[i] + ",";
		}
		result = result.substring(0, result.length() - 1); // 去除最后一个逗号
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.print(result); // 输出获取的市县字符串
		out.flush();
		out.close();

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
