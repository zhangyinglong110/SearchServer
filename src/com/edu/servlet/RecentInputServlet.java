package com.edu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		resp.setCharacterEncoding("UTF-8");// ������Ӧ�ı����ʽ
		resp.setHeader("Access-Control-Allow-Origin", "*");
		HttpSession session = req.getSession();
		String unionid = (String) session.getAttribute("unionid");// �û�ID
		RecentBean recent = new RecentBean();
		List<String> recentClass = new DataBaseOperaUtil().getRecentInputClass(unionid);
		List<String> recentTeacher = new DataBaseOperaUtil().getRecentInputTeacher(unionid);
		recent.setClassList(recentClass);
		recent.setTeacherName(recentTeacher);
		JSONObject jsonObject = new JSONObject().fromObject(recent);
		PrintWriter pw = resp.getWriter();
		System.out.println("Recently------->" + jsonObject.toString());
		pw.println(jsonObject.toString());

	}

}