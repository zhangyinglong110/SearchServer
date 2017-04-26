package com.edu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.bean.Investigation;
import com.edu.util.DataBaseOperaUtil;
import com.edu.util.JsonUtil;
import com.edu.util.Log4j;

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

		String jsonStirng = req.getParameter("name");// 获取插入数据的json
		String unionid = (String) req.getSession().getAttribute("unionid");
		String nickname = (String) req.getSession().getAttribute("nickname");
		// System.out.println(jsonStirng);
		// System.out.println("insert unionid----->" + unionid);
		// System.out.println("insert nickname----->" + nickname);
		Log4j.info(InsertSer.class, jsonStirng);
		Log4j.info(InsertSer.class, unionid);
		Log4j.info(InsertSer.class, nickname);
		PrintWriter pw = resp.getWriter();// 响应服务器对象

		if ("".equals(jsonStirng) || jsonStirng == null) {
			pw.println("登录信息不全！");
		} else {
			// 获取解析完的数据
			Investigation beanData = JsonUtil.getJsonBean(jsonStirng, unionid, nickname);
			// 检查数据库是否存在数据
			int checkResult;
			try {
				checkResult = DataBaseOperaUtil.chekIsRepeat(beanData);
				if (checkResult == 0) {
					// 执行插入数据库操作
					int inserResult = DataBaseOperaUtil.insertData(beanData);
					System.out.println("插入数据的执行结果是----》" + checkResult);
					if (inserResult > 0) {
						pw.println(200);
					} else {
						pw.println(100);
					}
				}
			} catch (SQLException e) {
				Log4j.error(InsertSer.class, e.getMessage());
				e.printStackTrace();
			} finally {
				if (pw != null) {
					pw.close();
				}
			}
		}

		// try {
		// if (checkResult == 0) {// 第一次插入数据库
		// int inserResult = new DataBaseOperaUtil().insertData(beanData);
		// if (inserResult > 0) {
		// 从数据库中查询排名的数据
		// List<Investigation> list = new
		// DataBaseOperaUtil().queryRanking(beanData.getLarge_Area(),
		// beanData.getCus_Name());
		// 将list转化成json数据
		// JSONArray jsonArray = JSONArray.fromObject(list);
		// PrintWriter pw = resp.getWriter();
		// pw.println(200);
		// System.out.println(jsonArray);
		// req.getSession().removeAttribute("unionid");
		// } else {
		// PrintWriter pw = resp.getWriter();
		// pw.println(100);
		// }
		// } else {// 已经插入过数据
		// PrintWriter pw = resp.getWriter();
		// pw.println(101);
		// }
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
	}
}
