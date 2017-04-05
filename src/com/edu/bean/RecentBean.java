package com.edu.bean;

import java.util.List;

/**
 * 
 * @author Poppy(张应龙)
 *
 */
public class RecentBean {

	private List<String> classList;// 评论过的班级

	private List<String> teacherName;// 评论过的老师

	public List<String> getClassList() {
		return classList;
	}

	public void setClassList(List<String> classList) {
		this.classList = classList;
	}

	public List<String> getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(List<String> teacherName) {
		this.teacherName = teacherName;
	}

}
