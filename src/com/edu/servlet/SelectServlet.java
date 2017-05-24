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
import com.edu.bean.SubjectBean;
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
		PrintWriter pw = resp.getWriter();
		try {
			if (jsonString != null) {
				SelectBean selectBean = JsonUtil.getJsonSelectJson(jsonString);
				List<Investigation> selectInfo = DataBaseOperaUtil.getSelectInfo1(selectBean);
//				for (int i = 0; i < selectInfo.size(); i++) {
//					SubjectBean sBean = new SubjectBean();
//					sBean.setTeacherName(selectInfo.get(i).getTea_Name());
//					sBean.setRoleLevel(selectInfo.get(i).getRole_Level());
//					int majorId = 1;
//					System.out.println(selectInfo.get(i).getCus_Name());
//					if (selectInfo.get(i).getCus_Name().equals("UI设计")) {
//						majorId = 1;
//					} else if (selectInfo.get(i).getCus_Name().equals("web前端")) {
//						majorId = 2;
//					} else if (selectInfo.get(i).getCus_Name().equals("Android")) {
//						majorId = 3;
//					} else if (selectInfo.get(i).getCus_Name().equals("网络营销")) {
//						majorId = 4;
//					} else if (selectInfo.get(i).getCus_Name().equals("java")) {
//						majorId = 5;
//					} else if (selectInfo.get(i).getCus_Name().equals("php")) {
//						majorId = 6;
//					} else if (selectInfo.get(i).getCus_Name().equals("游戏美术")) {
//						majorId = 7;
//					} else if (selectInfo.get(i).getCus_Name().equals("游戏程序")) {
//						majorId = 8;
//					}
//					sBean.setMajorId(majorId);
//					int schoolId = DataBaseOperaUtil.schoolId(selectInfo.get(i).getTea_Name(),
//							selectInfo.get(i).getSch_Name());
//					sBean.setSchoolId(schoolId);
//					sBean.setClassName(selectInfo.get(i).getStu_Class());
					//DataBaseOperaUtil.InsertTeacherTab(sBean);
//				}

				if (selectInfo.size() > 0) {
					JSONArray jsonArray = JSONArray.fromObject(selectInfo);
					pw.println(jsonArray);
				} else {
					pw.print("查询没有结果!");
				}
			} else {
				pw.print("加载出错，F5刷新重试！");
			}
		} catch (Exception e) {
			Log4j.error(SelectServlet.class, e.getMessage());
			e.printStackTrace();
		} finally {
			pw.close();
		}

	}

}
