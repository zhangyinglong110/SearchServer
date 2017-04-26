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

/**
 * 检查是否可以重复投票
 * 
 * @author Poppy(张应龙)
 *
 */
@WebServlet(name = "CheckServlet", urlPatterns = "/CheckServlet")
public class CheckRepeatServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");// 设置请求参数的编码
		resp.setCharacterEncoding("UTF-8");// 设置响应的编码格式
		resp.setHeader("Access-Control-Allow-Origin", "*");
		String checkJson = req.getParameter("checkJson");
		// System.out.println("CheckServlet传过来的条件---->" + checkJson);
		Log4j.info(CheckRepeatServlet.class, checkJson);
		String unionid = (String) req.getSession().getAttribute("unionid");
		// System.out.println("CheckServlet---unionid---->" + unionid);
		Log4j.info(CheckRepeatServlet.class, unionid);
		Investigation beans = JsonUtil.getCheckRepeatJson(checkJson, unionid);
		PrintWriter pw = resp.getWriter();
		try {
			int result = DataBaseOperaUtil.chekIsRepeat(beans);
			Log4j.info(CheckRepeatServlet.class, result + "");
			//System.out.println("CheckServlet返回的数据库结果---->" + result);
			// 还没评论
			if (result == 0) {
				pw.println(1);
			} else {
				pw.println(0);
			}
		} catch (SQLException e) {
			Log4j.error(CheckRepeatServlet.class, e.getMessage());
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

}
