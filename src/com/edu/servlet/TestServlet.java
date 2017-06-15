package com.edu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.edu.bean.ClassBean;
import com.edu.bean.AllClass;
import com.edu.util.DataBaseOperaUtil;
import com.edu.util.DbPoolConnection;
import com.edu.util.Log4j;

import net.sf.json.JSONObject;

@WebServlet(name = "test", urlPatterns = "/Test")
public class TestServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
//			List<AllClass> list = DataBaseOperaUtil.getClassList();
//			ClassBean cb = new ClassBean();
//			cb.setCode(200);
//			cb.setMsg("成功");
//			cb.setResults(list);
//			JSONObject jsonObject = JSONObject.fromObject(cb);
//			System.out.println(jsonObject.toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println("发出的请求：------->" + req.getParameter("no"));
		// Statement stmt = null;
		// ResultSet rSet = null;
		// DruidPooledConnection conn = null;
		// int result;
		// try {
		// conn = DbPoolConnection.getInstance().getConnection();
		// String sql = "select count(1) from tab_researchinfo";
		// stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
		// ResultSet.TYPE_FORWARD_ONLY);
		// rSet = stmt.executeQuery(sql);
		// while (rSet.next()) {
		// result = rSet.getInt(1);
		// System.out.println("查询结果--->" + result);
		// }
		//
		// } catch (SQLException e) {
		// Log4j.error(TestServlet.class, e.getMessage());
		// e.printStackTrace();
		// } finally {
		// DbPoolConnection.getInstance().close(conn, stmt, rSet);
		// }
		//
		// myInsert(resp);

	}

	public void myInsert(HttpServletResponse resp) throws IOException {
		Statement stmt = null;
		DruidPooledConnection conn = null;
		PrintWriter pw = resp.getWriter();// 响应服务器对象
		try {
			conn = DbPoolConnection.getInstance().getConnection();
			String sql1 = "INSERT INTO `tab_researchinfo` VALUES (null, '张三1', 'ofLi_wL_V7vQ4V_P3OofJVBfXU-s', '京津冀', '北京国贸', '曹冠华', '0', 'UI11', 'UI设计', '2017-04-12 15:26:07', '5', '4', '5', '5', '5', '5', '5', '5', '5', '5', '49', '4.9', '这个老师非常好', null, null, null, null);";
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY);
			int result1 = stmt.executeUpdate(sql1);
			System.out.println("插入数据的结果------->" + result1);
			pw.println("成功code:" + result1);
		} catch (Exception exception) {
			Log4j.error(TestServlet.class, exception.getMessage());
			exception.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (pw != null) {
				pw.close();
			}
		}
	}

}
