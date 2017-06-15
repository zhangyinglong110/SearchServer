package com.edu.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.edu.bean.AllData;
import com.edu.bean.ConfigBean;
import com.edu.bean.LargeAreaBean;
import com.edu.bean.LargeAreaSumBean;
import com.edu.bean.ListBean;
import com.edu.bean.RecentBean;
import com.edu.bean.SchoolBean;
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
		resp.setContentType("application/json;charset=utf-8");
		String unionid = null;
		HttpSession session = req.getSession();
		unionid = (String) session.getAttribute("unionid");// 用户ID
		if (unionid == null || unionid == "") {
			unionid = req.getParameter("uid");
		}
		System.out.println("reccently---->" + unionid);
		PrintWriter pw = resp.getWriter();
		try {
			ConfigBean configBean = DataBaseOperaUtil.seletConfigData();
			if (configBean.getSelectData() == null || "".equals(configBean.getSelectData())) {
			String jsonData	= getJsonData(unionid);
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				String createTime = sdf.format(date);
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
				String createDate = sdf1.format(date);

				int result = DataBaseOperaUtil.insertSelectData(jsonData, createDate, createTime, null, null);
				if (result > 0) {
					pw.print(jsonData);
				}
			} else {
				pw.print(configBean.getSelectData());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	
	
	
	
	public String getJsonData(String unionid) throws SQLException{
		RecentBean recent = new RecentBean();
		ListBean listBean = new ListBean();
		AllData allData = new AllData();
		List<String> recentClass = DataBaseOperaUtil.getRecentInputClass(unionid);
		List<String> recentTeacher = DataBaseOperaUtil.getRecentInputTeacher(unionid);
		List<String> subjects = DataBaseOperaUtil.getSubjects();
		HashMap<Integer, LargeAreaSumBean> largeMap = DataBaseOperaUtil.getSchools();
		List<List<SchoolBean>> sumList = new ArrayList<List<SchoolBean>>();
		List<LargeAreaBean> areaList = new ArrayList<LargeAreaBean>();
		Iterator iter = largeMap.entrySet().iterator(); // 获得map的Iterator
		while (iter.hasNext()) {
			Entry entry = (Entry) iter.next();
			LargeAreaSumBean bean = (LargeAreaSumBean) entry.getValue();
			sumList.add(bean.getSchools()); // 字段schools就出来了

			LargeAreaBean labean = new LargeAreaBean();
			labean.setName(bean.getName());
			labean.setSchoolcode(bean.getSchoolcode());
			areaList.add(labean);
		}

		recent.setClassList(recentClass);
		recent.setTeacherName(recentTeacher);

		listBean.setHistory(recent); // 历史数据
		listBean.setBigarea(areaList);// 设置大区以及schoolcode
		listBean.setSchools(sumList); // 设置校区的字段
		listBean.setSubject(subjects);

		allData.setCode(200);
		allData.setMsg("请求成功");
		allData.setResults(listBean);
		JSONObject jsonObject = JSONObject.fromObject(allData);
		String jsonData = jsonObject.toString();
		return  jsonData;
	}
	
	
	
}
