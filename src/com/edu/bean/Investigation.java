package com.edu.bean;

/**
 * 数据库实体类
 * 
 * @author Administrator
 *
 */
public class Investigation {
	// 序号
	private int user_Num;
	// 用户ID
	private String user_Id;
	// 大区名称
	private String large_Area;
	// 校区名称
	private String sch_Name;
	// 班级编号
	private String stu_Class;
	// 角色(班主任、讲师)
	private String role_Level;
	// 用户的昵称
	private String user_Nick;
	// 教师姓名
	private String tea_Name;
	// 所授课程
	private String cus_Name;
	// 填表日期
	private String fill_Date;
	// 教师出勤
	private double tea_Attendance;
	// 课堂讲解
	private double cls_Explain;
	// 课堂提问
	private double cls_Quesions;
	// 回答学员问题
	private double ques_Answer;
	// 课后辅导
	private double cls_Coach;
	// 课堂纪律
	private double cls_Discipline;
	// 授课技巧
	private double cls_Skill;
	// 授课进度
	private double cls_Progress;
	// 实例讲解
	private double exam_Explain;
	// 课后作业
	private double class_Homework;
	// 总分
	private double total_Score;
	// 学员建议
	private String stu_Advice;
	// 平均分
	private double average;
	// 投票的人数
	private int peopleCount;

	public int getUser_Num() {
		return user_Num;
	}

	public void setUser_Num(int user_Num) {
		this.user_Num = user_Num;
	}

	public String getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(String user_Id) {
		this.user_Id = user_Id;
	}

	public String getLarge_Area() {
		return large_Area;
	}

	// 设置大区的名称
	public void setLarge_Area(String large_Area) {
		this.large_Area = large_Area;
	}

	public String getSch_Name() {
		return sch_Name;
	}

	// 设置校区名称
	public void setSch_Name(String sch_Name) {
		if (sch_Name != null && stu_Advice != "") {
			this.sch_Name = sch_Name;
		}
	}

	public String getTea_Name() {
		return tea_Name;
	}

	// 设置教师姓名
	public void setTea_Name(String tea_Name) {
		if (tea_Name != null && stu_Advice != "") {
			this.tea_Name = tea_Name;
		}
	}

	public String getCus_Name() {
		return cus_Name;
	}

	// 设置授课名称
	public void setCus_Name(String cus_Name) {
		if (cus_Name != null && stu_Advice != "") {
			this.cus_Name = cus_Name;
		}
	}

	public String getFill_Date() {
		return fill_Date;
	}

	// 设置填表日期的时间
	public void setFill_Date(String fill_Date) {
		if (fill_Date != null && stu_Advice != "") {
			this.fill_Date = fill_Date;
		}
	}

	public double getTea_Attendance() {
		return tea_Attendance;
	}

	// 设置出勤值
	public void setTea_Attendance(double tea_Attendance) {
		if (tea_Attendance >= 0 && tea_Attendance <= 5) {
			this.tea_Attendance = tea_Attendance;
		}
	}

	public double getCls_Explain() {
		return cls_Explain;
	}

	// 设置课堂讲解的值
	public void setCls_Explain(double cls_Explain) {
		if (cls_Explain >= 0 && cls_Explain <= 5) {
			this.cls_Explain = cls_Explain;
		}
	}

	public double getCls_Quesions() {
		return cls_Quesions;
	}

	// 设置课堂提问的值
	public void setCls_Quesions(double cls_Quesions) {
		if (cls_Quesions >= 0 && cls_Quesions <= 5) {
			this.cls_Quesions = cls_Quesions;
		}
	}

	public double getQues_Answer() {
		return ques_Answer;
	}

	// 设置回答学员问题值
	public void setQues_Answer(double ques_Answer) {
		if (ques_Answer >= 0 && ques_Answer <= 5) {
			this.ques_Answer = ques_Answer;
		}
	}

	public double getCls_Coach() {
		return cls_Coach;
	}

	// 设置课后辅导的值
	public void setCls_Coach(double cls_Coach) {
		if (cls_Coach >= 0 && cls_Coach <= 5) {
			this.cls_Coach = cls_Coach;
		}
	}

	public double getCls_Discipline() {
		return cls_Discipline;
	}

	// 课堂纪律
	public void setCls_Discipline(double cls_Discipline) {
		if (cls_Discipline >= 0 && cls_Discipline <= 5) {
			this.cls_Discipline = cls_Discipline;
		}
	}

	public double getCls_Skill() {
		return cls_Skill;
	}

	// 授课技巧
	public void setCls_Skill(double cls_Skill) {
		if (cls_Skill >= 0 && cls_Skill <= 5) {
			this.cls_Skill = cls_Skill;
		}
	}

	public double getCls_Progress() {
		return cls_Progress;
	}

	// 授课进度
	public void setCls_Progress(double cls_Progress) {
		if (cls_Progress >= 0 && cls_Progress <= 5) {
			this.cls_Progress = cls_Progress;
		}
	}

	public double getExam_Explain() {
		return exam_Explain;
	}

	// 实例讲解
	public void setExam_Explain(double exam_Explain) {
		if (exam_Explain >= 0 && exam_Explain <= 5) {
			this.exam_Explain = exam_Explain;
		}
	}

	public double getClass_Homework() {
		return class_Homework;
	}

	// 课后作业
	public void setClass_Homework(double class_Homework) {
		if (class_Homework >= 0 && class_Homework <= 5) {
			this.class_Homework = class_Homework;
		}
	}

	public double getTotal_Score() {
		return total_Score;
	}

	// 总分
	public void setTotal_Score(double total_Score) {
		if (class_Homework >= 0 && class_Homework <= 5) {
			this.total_Score = total_Score;
		}
	}

	public String getStu_Advice() {
		return stu_Advice;
	}

	// 建议
	public void setStu_Advice(String stu_Advice) {
		if (stu_Advice != null && stu_Advice != "") {
			this.stu_Advice = stu_Advice;
		}
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}

	public int getPeopleCount() {
		return peopleCount;
	}

	public void setPeopleCount(int peopleCount) {
		this.peopleCount = peopleCount;
	}

	public String getStu_Class() {
		return stu_Class;
	}

	public void setStu_Class(String stu_Class) {
		this.stu_Class = stu_Class;
	}

	public String getRole_Level() {
		return role_Level;
	}

	public void setRole_Level(String role_Level) {
		this.role_Level = role_Level;
	}

	public String getUser_Nick() {
		return user_Nick;
	}

	public void setUser_Nick(String user_Nick) {
		this.user_Nick = user_Nick;
	}

	@Override
	public String toString() {
		return "Investigation [user_Num=" + user_Num + ", user_Id=" + user_Id + ", large_Area=" + large_Area
				+ ", sch_Name=" + sch_Name + ", stu_Class=" + stu_Class + ", role_Level=" + role_Level + ", user_Nick="
				+ user_Nick + ", tea_Name=" + tea_Name + ", cus_Name=" + cus_Name + ", fill_Date=" + fill_Date
				+ ", tea_Attendance=" + tea_Attendance + ", cls_Explain=" + cls_Explain + ", cls_Quesions="
				+ cls_Quesions + ", ques_Answer=" + ques_Answer + ", cls_Coach=" + cls_Coach + ", cls_Discipline="
				+ cls_Discipline + ", cls_Skill=" + cls_Skill + ", cls_Progress=" + cls_Progress + ", exam_Explain="
				+ exam_Explain + ", class_Homework=" + class_Homework + ", total_Score=" + total_Score + ", stu_Advice="
				+ stu_Advice + ", average=" + average + ", peopleCount=" + peopleCount + "]";
	}

//	@Override
//	public String toString() {
//		return tea_Name + ":" + sch_Name + ":" + role_Level + ":" + total_Score+":"+average;
//	}
	
	

}
