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

import com.edu.bean.Investigation;
import com.edu.util.DataBaseOperaUtil;
import com.edu.util.JsonUtil;

import net.sf.json.JSONArray;

@WebServlet(name = "SaveServlet", urlPatterns = "/SaveServlet")
public class InsertSer extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		req.setCharacterEncoding("UTF-8");
		String jsonStirng1 = req.getParameter("name");
		String unionid = (String) req.getSession().getAttribute("unionid");
		System.out.println(jsonStirng1);
		System.out.println("insert unionid----->" + unionid);
		// 获取解析完的数据
		Investigation beanData = new JsonUtil().getJsonBean(jsonStirng1, unionid);
		try {
			// 检查数据库是否存在数据
			int checkResult = new DataBaseOperaUtil().chekResult(beanData);
			if (checkResult == 0) {// 第一次插入数据库
				int inserResult = new DataBaseOperaUtil().insertData(beanData);
				System.out.println("insert------>" + inserResult);
				if (inserResult > 0) {
					// 从数据库中查询排名的数据
					// List<Investigation> list = new
					// DataBaseOperaUtil().queryRanking(beanData.getLarge_Area(),
					// beanData.getCus_Name());
					// 将list转化成json数据
					// JSONArray jsonArray = JSONArray.fromObject(list);
					PrintWriter pw = resp.getWriter();
					pw.println(200);
					// System.out.println(jsonArray);
					req.getSession().removeAttribute("unionid");
				} else {
					PrintWriter pw = resp.getWriter();
					pw.println(100);
				}
			} else {// 已经插入过数据
				PrintWriter pw = resp.getWriter();
				pw.println(101);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
