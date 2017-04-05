package com.edu.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.edu.bean.Investigation;
import com.edu.bean.SelectBean;
import com.edu.global.Global;

/**
 * У�����ݿɷ񱻲������ݿ�
 * 
 * @author Administrator
 *
 */
public class DataBaseOperaUtil {

	/**
	 * �����ݿ��������
	 * 
	 * @param bean
	 * @return
	 */
	public int insertData(Investigation bean) {
		int result = 0; // �������ݿ�󷵻صĽ��
		Connection conn = C3P0Util.getConnection();// ��ȡ���ݿ������
		PreparedStatement ps = null;
		// sql���
		String sql = "insert into " + Global.TAB_NAME
				+ " (user_Nick,user_Id,large_Area,sch_Name,tea_Name,role_Level,stu_Class,cus_Name,fill_Date,tea_Attendance,cls_Explain,cls_Quesions,ques_Answer,cls_Coach,cls_Discipline,cls_Skill,cls_Progress,exam_Explain,class_Homework,total_Score,stu_Advice,average)"
				+ "value (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			// ���PreparedStatement����
			ps = conn.prepareStatement(sql);
			ps.setString(1, bean.getUser_Nick());// �û��ǳ�
			ps.setString(2, bean.getUser_Id());
			ps.setString(3, bean.getLarge_Area());
			ps.setString(4, bean.getSch_Name());
			ps.setString(5, bean.getTea_Name());
			ps.setInt(6, bean.getRole_Level()); // ���۵Ľ�ɫ
			ps.setString(7, bean.getStu_Class()); // �༶���
			ps.setString(8, bean.getCus_Name());
			ps.setString(9, bean.getFill_Date());
			ps.setDouble(10, bean.getTea_Attendance());
			ps.setDouble(11, bean.getCls_Explain());
			ps.setDouble(12, bean.getCls_Quesions());
			ps.setDouble(13, bean.getQues_Answer());
			ps.setDouble(14, bean.getCls_Coach());
			ps.setDouble(15, bean.getCls_Discipline());
			ps.setDouble(16, bean.getCls_Skill());
			ps.setDouble(17, bean.getCls_Progress());
			ps.setDouble(18, bean.getExam_Explain());
			ps.setDouble(19, bean.getClass_Homework());
			ps.setDouble(20, bean.getTotal_Score());
			ps.setString(21, bean.getStu_Advice());
			ps.setDouble(22, bean.getAverage());
			result = ps.executeUpdate();
			System.out.println("�������ݿ�ɹ�");
		} catch (Exception e) {
			System.out.println("�������ݿ���ִ���");
			e.printStackTrace();
			C3P0Util.close(conn, ps, null);
		} finally {
			C3P0Util.close(conn, ps, null);
		}
		return result;
	}

	/**
	 * ����Ƿ��Ѿ�Ͷ��Ʊ
	 * 
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public int chekIsRepeat(Investigation bean) throws SQLException {
		int result = 0;
		String sqlString = "SELECT * FROM tab_researchinfo WHERE user_Id = '" + bean.getUser_Id()
				+ "' and large_Area = '" + bean.getLarge_Area() + "' AND sch_Name = '" + bean.getSch_Name()
				+ "' and cus_Name = '" + bean.getCus_Name() + "' AND tea_Name = '" + bean.getTea_Name()
				+ "' and date_format(fill_Date,'%Y-%m') = date_format('" + bean.getFill_Date() + "','%Y-%m')";
		System.out.println("----��ѯ�����Ƿ��Ѿ�Ͷ����sql----->" + sqlString);
		Connection conn = C3P0Util.getConnection();
		Statement stmt = null;
		ResultSet rSet = null;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.TYPE_FORWARD_ONLY);
			rSet = stmt.executeQuery(sqlString);
			while (rSet.next()) {
				result = rSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
				rSet.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * ����
	 * 
	 * @param large_Area
	 *            ����
	 * @param cus_Name
	 *            רҵ����
	 * @return
	 * @throws SQLException
	 */
	public List<Investigation> queryRanking(String large_Area, String cus_Name) throws SQLException {
		String sql = "SELECT A.large_Area,A.sch_Name,A.cus_Name,A.tea_Name,SUM(A.average)/COUNT(1) b from "
				+ Global.TAB_NAME + " A where A.large_Area = '" + large_Area
				+ "' GROUP BY A.tea_Name,A.large_Area,A.sch_Name,A.cus_Name ORDER BY b desc;";
		System.out.println("������sql:-----" + sql);
		Connection conn = C3P0Util.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<Investigation> list = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); // ִ�в�ѯ���ݿ�Ĳ���
			list = new ArrayList<Investigation>();
			while (rs.next()) {
				Investigation investigation = new Investigation();
				investigation.setLarge_Area(rs.getString("large_Area"));
				investigation.setSch_Name(rs.getString("sch_Name"));
				investigation.setTea_Name(rs.getString("tea_Name"));
				investigation.setCus_Name(rs.getString("cus_Name"));
				investigation.setAverage(rs.getDouble("b"));
				list.add(investigation);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("��ѯ���ݴ���~");
			C3P0Util.close(conn, null, rs);
			stmt.close();
		} finally {
			C3P0Util.close(conn, null, rs);
			stmt.close();
		}
		return list;
	}

	/**
	 * ��̨ͳ��
	 * 
	 * @param large_Area
	 * @param sch_Name
	 * @param major
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	public List<Investigation> getSelectInfo(SelectBean selectBean) throws SQLException {
		String sql = null;
		if ("��ѡ��".equals(selectBean.getLargeArea())) {
			if ("��ѡ��".equals(selectBean.getMajor())) {
				sql = "select * from " + Global.TAB_NAME + " where  fill_Date >= '" + selectBean.getStartDate()
						+ "' and fill_Date<= '" + selectBean.getEndDate()
						+ "' ORDER BY  large_Area ASC,sch_Name ASC,tea_Name ASC,cus_Name ASC,total_Score DESC";
			} else {
				sql = "select * from " + Global.TAB_NAME + " where cus_Name = '" + selectBean.getMajor()
						+ "' and fill_Date >= '" + selectBean.getStartDate() + "' and fill_Date<= '"
						+ selectBean.getEndDate()
						+ "' ORDER BY  large_Area ASC,sch_Name ASC,tea_Name ASC,cus_Name ASC,total_Score DESC ";
			}
		} else {
			if ("��ѡ��".equals(selectBean.getSchName())) {
				if ("��ѡ��".equals(selectBean.getMajor())) {
					sql = "select * from " + Global.TAB_NAME + " where large_Area = '" + selectBean.getLargeArea()
							+ "' and fill_Date >= '" + selectBean.getStartDate() + "' and fill_Date<= '"
							+ selectBean.getEndDate()
							+ "' ORDER BY  large_Area ASC,sch_Name ASC,tea_Name ASC,cus_Name ASC,total_Score DESC ";
				} else {
					sql = "select * from " + Global.TAB_NAME + " where large_Area = '" + selectBean.getLargeArea()
							+ "' and sch_Name = '" + selectBean.getSchName() + "' and fill_Date >= '"
							+ selectBean.getStartDate() + "' and fill_Date<= '" + selectBean.getEndDate()
							+ "' ORDER BY  large_Area ASC,sch_Name ASC,tea_Name ASC,cus_Name ASC,total_Score DESC ";
				}
			} else {
				if ("��ѡ��".equals(selectBean.getMajor())) {
					sql = "select * from " + Global.TAB_NAME + " where large_Area = '" + selectBean.getLargeArea()
							+ "' and sch_Name = '" + selectBean.getSchName() + "' and fill_Date >= '"
							+ selectBean.getStartDate() + "' and fill_Date<= '" + selectBean.getEndDate()
							+ "' ORDER BY  large_Area ASC,sch_Name ASC,tea_Name ASC,cus_Name ASC,total_Score DESC ";
				} else {
					sql = "select * from " + Global.TAB_NAME + " where large_Area = '" + selectBean.getLargeArea()
							+ "' and sch_Name = '" + selectBean.getSchName() + "' and cus_Name = '"
							+ selectBean.getMajor() + "' and fill_Date >= '" + selectBean.getStartDate()
							+ "' and fill_Date<= '" + selectBean.getEndDate()
							+ "' ORDER BY  large_Area ASC,sch_Name ASC,tea_Name ASC,cus_Name ASC,total_Score DESC ";
				}
			}
		}
		System.out.println(sql);
		Connection conn = C3P0Util.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<Investigation> list = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); // ִ�в�ѯ���ݿ�Ĳ���
			list = new ArrayList<Investigation>();
			while (rs.next()) {
				Investigation investigation = new Investigation();
				investigation.setStu_Class(rs.getString("stu_Class"));
				investigation.setLarge_Area(rs.getString("large_Area"));
				investigation.setSch_Name(rs.getString("sch_Name"));
				investigation.setTea_Name(rs.getString("tea_Name"));
				investigation.setCus_Name(rs.getString("cus_Name"));
				investigation.setTea_Attendance(rs.getDouble("tea_Attendance"));
				investigation.setCls_Explain(rs.getDouble("cls_Explain"));
				investigation.setCls_Quesions(rs.getDouble("cls_Quesions"));
				investigation.setQues_Answer(rs.getDouble("ques_Answer"));
				investigation.setCls_Coach(rs.getDouble("cls_Coach"));
				investigation.setCls_Discipline(rs.getDouble("cls_Discipline"));
				investigation.setCls_Skill(rs.getDouble("cls_Skill"));
				investigation.setCls_Progress(rs.getDouble("cls_Progress"));
				investigation.setExam_Explain(rs.getDouble("exam_Explain"));
				investigation.setClass_Homework(rs.getDouble("class_Homework"));
				investigation.setTotal_Score(rs.getDouble("total_Score"));
				investigation.setAverage(rs.getDouble("average"));
				investigation.setStu_Advice(rs.getString("stu_Advice"));
				list.add(investigation);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("��ѯ���ݴ���~");
			C3P0Util.close(conn, null, rs);
			stmt.close();
		} finally {
			C3P0Util.close(conn, null, rs);
			stmt.close();
		}
		return list;
	}

	/**
	 * ��̨ͳ��(ǰ����������)
	 * 
	 * @param large_Area
	 * @param sch_Name
	 * @param major
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SQLException
	 */
	public List<Investigation> getSelectInfo1(SelectBean selectBean) throws SQLException {
		String sql = null;
		if ("��ѡ��".equals(selectBean.getLargeArea())) {
			if ("��ѡ��".equals(selectBean.getMajor())) {
				sql = "SELECT t.large_Area,t.sch_Name,t.tea_Name,t.cus_Name,t.stu_Class, COUNT(t.tea_Name) a,SUM(t.average)/COUNT(1) b FROM tab_researchinfo t where  fill_Date >= '"
						+ selectBean.getStartDate() + "' and fill_Date<= '" + selectBean.getEndDate()
						+ "' GROUP BY t.tea_Name,t.cus_Name,t.stu_Class ORDER BY large_Area ASC,sch_Name ASC,cus_Name ASC,b DESC";
			} else {
				sql = "SELECT t.large_Area,t.sch_Name,t.tea_Name,t.cus_Name,t.stu_Class, COUNT(t.tea_Name) a,SUM(t.average)/COUNT(1) b FROM tab_researchinfo t where cus_Name = '"
						+ selectBean.getMajor() + "' and fill_Date >= '" + selectBean.getStartDate()
						+ "' and fill_Date<= '" + selectBean.getEndDate()
						+ "' GROUP BY t.tea_Name,t.cus_Name,t.stu_Class ORDER BY large_Area ASC,sch_Name ASC,cus_Name ASC,b DESC";
			}
		} else {
			if ("��ѡ��".equals(selectBean.getSchName())) {
				if ("��ѡ��".equals(selectBean.getMajor())) {
					sql = "SELECT t.large_Area,t.sch_Name,t.tea_Name,t.cus_Name,t.stu_Class,COUNT(t.tea_Name) a,SUM(t.average)/COUNT(1) b FROM tab_researchinfo t where large_Area = '"
							+ selectBean.getLargeArea() + "' and fill_Date >= '" + selectBean.getStartDate()
							+ "' and fill_Date<= '" + selectBean.getEndDate()
							+ "' GROUP BY t.tea_Name,t.cus_Name,t.stu_Class ORDER BY large_Area ASC,sch_Name ASC,cus_Name ASC,b DESC";
				} else {
					sql = "SELECT t.large_Area,t.sch_Name,t.tea_Name,t.cus_Name,t.stu_Class,COUNT(t.tea_Name) a,SUM(t.average)/COUNT(1) b FROM tab_researchinfo t where large_Area = '"
							+ selectBean.getLargeArea() + "' and sch_Name = '" + selectBean.getSchName()
							+ "' and fill_Date >= '" + selectBean.getStartDate() + "' and fill_Date<= '"
							+ selectBean.getEndDate()
							+ "' GROUP BY t.tea_Name,t.cus_Name,t.stu_Class ORDER BY large_Area ASC,sch_Name ASC,cus_Name ASC,b DESC";
				}
			} else {
				if ("��ѡ��".equals(selectBean.getMajor())) {
					sql = "SELECT t.large_Area,t.sch_Name,t.tea_Name,t.cus_Name,t.stu_Class,COUNT(t.tea_Name) a,SUM(t.average)/COUNT(1) b FROM tab_researchinfo t where large_Area = '"
							+ selectBean.getLargeArea() + "' and sch_Name = '" + selectBean.getSchName()
							+ "' and fill_Date >= '" + selectBean.getStartDate() + "' and fill_Date<= '"
							+ selectBean.getEndDate()
							+ "' GROUP BY t.tea_Name,t.cus_Name,t.stu_Class ORDER BY large_Area ASC,sch_Name ASC,cus_Name ASC,b DESC";
				} else {
					sql = "SELECT t.large_Area,t.sch_Name,t.tea_Name,t.cus_Name,t.stu_Class,COUNT(t.tea_Name) a,SUM(t.average)/COUNT(1) b FROM tab_researchinfo t where large_Area = '"
							+ selectBean.getLargeArea() + "' and sch_Name = '" + selectBean.getSchName()
							+ "' and cus_Name = '" + selectBean.getMajor() + "' and fill_Date >= '"
							+ selectBean.getStartDate() + "' and fill_Date<= '" + selectBean.getEndDate()
							+ "' GROUP BY t.tea_Name,t.cus_Name,t.stu_Class ORDER BY large_Area ASC,sch_Name ASC,cus_Name ASC,b DESC";
				}
			}
		}
		System.out.println(sql);
		Connection conn = C3P0Util.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<Investigation> list = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); // ִ�в�ѯ���ݿ�Ĳ���
			list = new ArrayList<Investigation>();
			while (rs.next()) {
				Investigation investigation = new Investigation();
				investigation.setStu_Class(rs.getString("stu_Class"));
				investigation.setLarge_Area(rs.getString("large_Area"));
				investigation.setSch_Name(rs.getString("sch_Name"));
				investigation.setTea_Name(rs.getString("tea_Name"));
				investigation.setCus_Name(rs.getString("cus_Name"));
				investigation.setPeopleCount(rs.getInt("a"));
				/********* ������������ *********/
				double average = rs.getDouble("b");
				BigDecimal b = new BigDecimal(average);
				double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				investigation.setAverage(f1);
				/********* ������������ *********/
				list.add(investigation);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("��ѯ���ݴ���~");
			C3P0Util.close(conn, null, rs);
			stmt.close();
		} finally {
			C3P0Util.close(conn, null, rs);
			stmt.close();
		}
		return list;
	}

	/**
	 * �����û�ID���������д�İ༶����ʦ
	 * 
	 * @param unionid
	 * @return
	 */
	public List<String> getRecentInputClass(String unionid) {
		String sql = "SELECT DISTINCT t.stu_Class FROM " + Global.TAB_NAME + " t WHERE t.user_Id = '" + unionid + "'";
		Connection conn = C3P0Util.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<String> classList = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); // ִ�в�ѯ���ݿ�Ĳ���
			classList = new ArrayList<String>();
			while (rs.next()) {
				classList.add(rs.getString("stu_Class"));
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return classList;
	}
	
	
	/**
	 * �����û�ID���������д�İ༶����ʦ
	 * 
	 * @param unionid
	 * @return
	 */
	public List<String> getRecentInputTeacher(String unionid) {
		String sql = "SELECT DISTINCT t.tea_Name FROM " + Global.TAB_NAME + " t WHERE t.user_Id = '" + unionid + "'";
		Connection conn = C3P0Util.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<String> teacherList = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); // ִ�в�ѯ���ݿ�Ĳ���
			teacherList = new ArrayList<String>();
			while (rs.next()) {
				teacherList.add(rs.getString("tea_Name"));
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return teacherList;
	}

}
