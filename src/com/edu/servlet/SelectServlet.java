package com.edu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.edu.bean.Investigation;
import com.edu.bean.SelectBean;
import com.edu.util.DataBaseOperaUtil;
import com.edu.util.JsonUtil;

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
		HttpSession session = req.getSession();
		String jsonString = req.getParameter("name");
		System.err.println(jsonString);
		try {
			SelectBean selectBean = new JsonUtil().getJsonSelectJson(jsonString);
			List<Investigation> selectInfo1 = new DataBaseOperaUtil().getSelectInfo1(selectBean);
			List<Investigation> selectInfo = new DataBaseOperaUtil().getSelectInfo(selectBean);
			PrintWriter pw = resp.getWriter();
			session.setAttribute("selectInfo", selectInfo);
			JSONArray jsonArray = JSONArray.fromObject(selectInfo1);
			pw.println(jsonArray);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
