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

import com.edu.bean.LargeAreaBean;
import com.edu.bean.ListBean;
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
		resp.setCharacterEncoding("UTF-8");// 设置响应的编码格式
		resp.setHeader("Access-Control-Allow-Origin", "*");
		String unionid = null;
		HttpSession session = req.getSession();
		unionid = (String) session.getAttribute("unionid");// 用户ID
		if (unionid == null || unionid == "") {
			unionid = req.getParameter("uid");
		}
		System.out.println("reccently---->" + unionid);
		RecentBean recent = new RecentBean();
		ListBean listBean = new ListBean();
		PrintWriter pw = resp.getWriter();
		try {
			List<String> recentClass = DataBaseOperaUtil.getRecentInputClass(unionid);
			List<String> recentTeacher = DataBaseOperaUtil.getRecentInputTeacher(unionid);
			List<LargeAreaBean> bigarea = DataBaseOperaUtil.getAllLargeAreaBean();
			
			
			
			recent.setClassList(recentClass);
			recent.setTeacherName(recentTeacher);
			
			listBean.setHistory(recent); //历史数据
			listBean.setBigarea(bigarea);//设置大区以及schoolcode
			
			
			
			
			JSONObject jsonObject = new JSONObject().fromObject(recent);
			System.out.println("Recently------->" + jsonObject.toString());
			pw.println(jsonObject.toString());
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

}
