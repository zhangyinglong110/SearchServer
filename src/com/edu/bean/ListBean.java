package com.edu.bean;

import java.util.ArrayList;
import java.util.List;

public class ListBean {

	private RecentBean history;

	private List<LargeAreaBean> bigarea = new ArrayList<LargeAreaBean>();

	private List<List<SchoolBean>> schools;

	private List<String> subject;

	public RecentBean getHistory() {
		return history;
	}

	public void setHistory(RecentBean history) {
		this.history = history;
	}

	public List<LargeAreaBean> getBigarea() {
		return bigarea;
	}

	public void setBigarea(List<LargeAreaBean> bigarea) {
		this.bigarea = bigarea;
	}

	public List<List<SchoolBean>> getSchools() {
		return schools;
	}

	public void setSchools(List<List<SchoolBean>> schools) {
		this.schools = schools;
	}

	public List<String> getSubject() {
		return subject;
	}

	public void setSubject(List<String> subject) {
		this.subject = subject;
	}

}
