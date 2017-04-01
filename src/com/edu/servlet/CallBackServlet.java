package com.edu.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.edu.bean.WeiChatInfo;
import com.edu.bean.WeiXinUserInfo;
import com.edu.global.Global;
import com.edu.util.JsonUtil;

@WebServlet(name = "callBackServlet", urlPatterns = "/callBackServlet")
public class CallBackServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		String code = req.getParameter("code");
		String state = req.getParameter("state");
		// 控制台输出code和state
		System.out.println("redirct code-->" + code + ",redirct state-->" + state);
		String randomValue = (String) req.getSession().getAttribute("randomValue");

		if (code != null && (state.equals(randomValue))) {
			req.getSession().removeAttribute("randomValue");
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Global.APP_ID + "&secret="
					+ Global.SECRET + "&code=" + code + "&grant_type=authorization_code";
			String jsonString = doGetByHttpClient(url); // 返回的是json字符串
			System.out.println("access_token-->" + jsonString);
			WeiChatInfo weiChatInfo = new JsonUtil().getWeiChat(jsonString);
			String getUserUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + weiChatInfo.getAccess_token()
					+ "&openid=" + weiChatInfo.getOpenid();
			// String weiUseString = doGetByHttpClient(getUserUrl);
			// System.out.println("---->two token" + weiUseString);
			// WeiXinUserInfo weiXinUserInfo = new
			// JsonUtil().getWeiXinUserInfo(weiUseString);
			HttpSession session = req.getSession();
			session.setAttribute("unionid", weiChatInfo.getUnionid());
			resp.sendRedirect("index.html");
			// req.getRequestDispatcher("index.html").forward(req, resp);//
			// 重定向页面
		} else {
			resp.sendRedirect("tankyou.html");
		}
	}

	public String doGetByHttpClient(String url) {
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			connection.setReadTimeout(5000);
			connection.connect();// 建立连接
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		} // 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

}
