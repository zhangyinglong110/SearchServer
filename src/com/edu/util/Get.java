package com.edu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import net.sf.json.JSONObject;

public class Get {
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static JSONObject json(String url) {
		JSONObject jsonObj = null;
		InputStream is = null;
		try {
			is = new URL(url).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			jsonObj = JSONObject.fromObject(readAll(rd));
		} catch (Exception e) {
			Log4j.error(Get.class, e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				Log4j.error(Get.class, e.getMessage());
				e.printStackTrace();
			}
		}
		return jsonObj;
	}
}
