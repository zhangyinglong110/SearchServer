package com.edu.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

/**
 * LoginServlet�ص�������
 * 
 * @author Poppy(��Ӧ��)
 *
 */
@WebServlet(name = "callBackServlet", urlPatterns = "/callBackServlet")
public class CallBackServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("gbk");
		resp.setCharacterEncoding("gbk");
		String code = req.getParameter("code");
		String state = req.getParameter("state");
		// ����̨���code��state
		System.out.println("redirct code-->" + code + ",redirct state-->" + state);
		String randomValue = (String) req.getSession().getAttribute("randomValue");
		System.out.println("CallBackServlet--randomValue--->" + randomValue);
		if (code != null && state != null) {
			req.getSession().removeAttribute("randomValue");
			// ��һ��URL��ַ
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + Global.APP_ID + "&secret="
					+ Global.SECRET + "&code=" + code + "&grant_type=authorization_code";
			String jsonString = doGetByHttpClient(url); // ���ص���json�ַ���
			System.out.println("access_token-->" + jsonString);
			// �洢΢��������Ϣ��ʵ����
			WeiChatInfo weiChatInfo = new JsonUtil().getWeiChat(jsonString);

			// ��ȡ���û��Ļ�����Ϣ
			String getUserUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + weiChatInfo.getAccess_token()
					+ "&openid=" + weiChatInfo.getOpenid();
			String weiUseString = doGetByHttpClient(getUserUrl);
			System.out.println("---->CallServlet two token" + weiUseString);
			WeiXinUserInfo weiXinUserInfo = new JsonUtil().getWeiXinUserInfo(weiUseString);
			HttpSession session = req.getSession();
			String nick = weiXinUserInfo.getNickname();
			nick = new String(nick.getBytes("utf-8"), "gbk");
			System.out.println("nicl----->" + nick);
			session.setAttribute("unionid", weiChatInfo.getUnionid());
			session.setAttribute("nickname", nick);
			resp.sendRedirect("index.html");
		} else {
			System.out.println("CallBackServlet----->else����");
			resp.sendRedirect("tankyou.html");
		}
	}

	/**
	 * ������������
	 * 
	 * @param url
	 * @return
	 */
	public String doGetByHttpClient(String url) {
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			URLConnection connection = realUrl.openConnection();
			connection.setReadTimeout(5000);
			connection.connect();// ��������
			// ���� BufferedReader����������ȡURL����Ӧ
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("����GET��������쳣��" + e);
			e.printStackTrace();
		} // ʹ��finally�����ر�������
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
