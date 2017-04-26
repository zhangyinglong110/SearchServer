package com.edu.util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.edu.bean.AccessTokenBean;
import com.edu.bean.WXUserBean;

import net.sf.json.JSONObject;

public class OAuth2API {
	private static String ACCESS_TOKEN = "access_token";
	private static String EXPIRES_IN = "expires_in";
	private static String REFRESH_TOKEN = "refresh_token";
	private static String OPENID = "openid";
	private static String SCOPE = "scope";
	private static String UNIONID = "unionid";

	private static String appid = "wxb1ae167f69eb9f62"; // 这里填写你自己的appid
	private static String secret = "36e674c0589ad519d4a71a4b080af370"; // 这里填写你自己的secre

	/**
	 * 获取微信用户信息
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static WXUserBean getUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");

		AccessTokenBean atBean = getAccessToken(request);
		if (atBean == null) {
			// response.sendRedirect("LoginServlet");
			return null;
		}
		if (!verify(atBean.getAccess_token(), atBean.getOpenid())) {
			atBean = refreshAccessToken(atBean.getRefresh_token());
		}
		WXUserBean wxub = getUserInfoApi(atBean.getAccess_token(), atBean.getOpenid());
		return wxub;
	}

	/**
	 * 获取AccessToken
	 * 
	 * @param request
	 * @return
	 */
	private static AccessTokenBean getAccessToken(HttpServletRequest request) {
		String code = request.getParameter("code");
		JSONObject jObj = Get.json("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid + "&secret="
				+ secret + "&code=" + code + "&grant_type=authorization_code");
		AccessTokenBean oBean = new AccessTokenBean();
		Log4j.info(OAuth2API.class, jObj.toString());
		if (jObj != null) {

			if (jObj.has(ACCESS_TOKEN)) {
				String tmp = jObj.getString(ACCESS_TOKEN);
				oBean.setAccess_token(tmp);
			} else {
				return null;
			}

			if (jObj.has(EXPIRES_IN)) {
				int tmp = jObj.getInt(EXPIRES_IN);
				oBean.setExpires_in(tmp);
			} else {
				return null;
			}

			if (jObj.has(REFRESH_TOKEN)) {
				String tmp = jObj.getString(REFRESH_TOKEN);
				oBean.setRefresh_token(tmp);
			} else {
				return null;
			}

			if (jObj.has(OPENID)) {
				String tmp = jObj.getString(OPENID);
				oBean.setOpenid(tmp);
			} else {
				return null;
			}

			if (jObj.has(SCOPE)) {
				String tmp = jObj.getString(SCOPE);
				oBean.setScope(tmp);
			} else {
				return null;
			}

			if (jObj.has(UNIONID)) {
				String tmp = jObj.getString(UNIONID);
				oBean.setUnionid(tmp);
			} else {
				return null;
			}
		}
		return oBean;
	}

	/**
	 * 刷新AccessToken
	 * 
	 * @param refresh_token
	 * @return
	 */
	private static AccessTokenBean refreshAccessToken(String refresh_token) {
		JSONObject jObj = Get.json("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + appid
				+ "&grant_type=refresh_token&refresh_token=" + refresh_token);
		AccessTokenBean oBean = new AccessTokenBean();
		oBean.setAccess_token(jObj.getString("access_token"));
		oBean.setExpires_in(jObj.getInt("expires_in"));
		oBean.setRefresh_token(jObj.getString("refresh_token"));
		oBean.setOpenid(jObj.getString("openid"));
		oBean.setScope(jObj.getString("scope"));
		return oBean;
	}

	/***
	 * 验证AccessToken是否可用*
	 * 
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	private static boolean verify(String accessToken, String openid) {
		JSONObject jObj = Get
				.json("https://api.weixin.qq.com/sns/auth?access_token=" + accessToken + "&openid=" + openid);
		return jObj.getInt("errcode") == 0;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param accessToken
	 * @param openid
	 * @return
	 */
	private static WXUserBean getUserInfoApi(String accessToken, String openid) {
		JSONObject jObj = Get.json("https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid="
				+ openid + "&lang=zh_CN");
		WXUserBean wxub = new WXUserBean();
		if (jObj.has("openid")) {
			wxub.setOpenid(jObj.getString("openid"));
		}
		if (jObj.has("nickname")) {
			wxub.setNickname(jObj.getString("nickname"));
		}
		if (jObj.has("sex")) {
			wxub.setSex(jObj.getInt("sex"));
		}
		if (jObj.has("province")) {
			wxub.setProvince(jObj.getString("province"));
		}
		if (jObj.has("city")) {
			wxub.setCity(jObj.getString("city"));
		}
		if (jObj.has("country")) {
			wxub.setCountry(jObj.getString("country"));
		}
		if (jObj.has("headimgurl")) {
			wxub.setHeadimgurl(jObj.getString("headimgurl"));
		}
		if (jObj.has("unionid")) {
			wxub.setUnionid(jObj.getString("unionid"));
		}
		return wxub;
	}

}
