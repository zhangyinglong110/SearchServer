package com.edu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.bean.RecentBean;
import com.edu.util.DataBaseOperaUtil;

import net.sf.json.JSONObject;

@WebServlet(name = "recentServlet", urlPatterns = "/recentServlet")
public class RecentInputServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("RecentInputServlet-->>>");
		resp.setCharacterEncoding("UTF-8");// 设置响应的编码格式
		resp.setHeader("Access-Control-Allow-Origin", "*");
		String unionid = req.getParameter("uid");
		System.out.println("reccently---->" + unionid);
		// HttpSession session = req.getSession();
		// String unionid = (String) session.getAttribute("unionid");// 用户ID
		RecentBean recent = new RecentBean();
		List<String> recentClass = DataBaseOperaUtil.getRecentInputClass(unionid);
		List<String> recentTeacher = DataBaseOperaUtil.getRecentInputTeacher(unionid);
		recent.setClassList(recentClass);
		recent.setTeacherName(recentTeacher);
		JSONObject jsonObject = new JSONObject().fromObject(recent);
		PrintWriter pw = resp.getWriter();
		System.out.println("Recently------->" + jsonObject.toString());
		pw.println(jsonObject.toString());

	}

}
