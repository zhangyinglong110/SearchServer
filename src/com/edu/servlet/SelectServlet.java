package com.edu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.bean.Investigation;
import com.edu.bean.SelectBean;
import com.edu.util.DataBaseOperaUtil;
import com.edu.util.JsonUtil;
import com.edu.util.Log4j;

import net.sf.json.JSONArray;

/**
 * 查询数据结果的页面
 * 
 * @author Poppy
 *
 */
@WebServlet(name = "selectServlet", urlPatterns = "/selectServlet")
public class SelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		req.setCharacterEncoding("UTF-8");
		String jsonString = req.getParameter("name");
		Log4j.info(SelectServlet.class, jsonString);
		// System.out.println(jsonString);
		try {
			SelectBean selectBean = JsonUtil.getJsonSelectJson(jsonString);
			List<Investigation> selectInfo = DataBaseOperaUtil.getSelectInfo1(selectBean);
			PrintWriter pw = resp.getWriter();
			JSONArray jsonArray = JSONArray.fromObject(selectInfo);
			pw.println(jsonArray);
		} catch (Exception e) {
			Log4j.error(SelectServlet.class, e.getMessage());
			e.printStackTrace();
		}
	}

}
