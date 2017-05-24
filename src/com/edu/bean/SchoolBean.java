package com.edu.bean;

import java.util.List;

/**
 * 存储校区的信息的类
 * 
 * @author Administrator
 *
 */
public class SchoolBean {
	private String sch;
	private List<Integer> subcode;

	public String getSch() {
		return sch;
	}

	public void setSch(String sch) {
		this.sch = sch;
	}

	public List<Integer> getSubcode() {
		return subcode;
	}

	public void setSubcode(List<Integer> subcode) {
		this.subcode = subcode;
	}

}
