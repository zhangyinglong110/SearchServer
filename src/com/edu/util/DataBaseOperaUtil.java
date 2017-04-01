package com.edu.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.edu.bean.Investigation;
import com.edu.bean.SelectBean;
import com.edu.global.Global;

/**
 * 校验数据可否被插入数据库
 * 
 * @author Administrator
 *
 */
public class DataBaseOperaUtil {

	/**
	 * 向数据库插入数据
	 * 
	 * @param bean
	 * @return
	 */
	public int insertData(Investigation bean) {
		int result = 0; // 插入数据库后返回的结果
		Connection conn = C3P0Util.getConnection();// 获取数据库的链接
		PreparedStatement ps = null;
		// sql语句
		String sql = "insert into " + Global.TAB_NAME
				+ " (user_Id,large_Area,sch_Name,tea_Name,cus_Name,fill_Date,tea_Attendance,cls_Explain,cls_Quesions,ques_Answer,cls_Coach,cls_Discipline,cls_Skill,cls_Progress,exam_Explain,class_Homework,total_Score,stu_Advice,average)"
				+ "value (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			// 获得PreparedStatement对象
			ps = conn.prepareStatement(sql);
			ps.setString(1, bean.getUser_Id());
			ps.setString(2, bean.getLarge_Area());
			ps.setString(3, bean.getSch_Name());
			ps.setString(4, bean.getTea_Name());
			ps.setString(5, bean.getCus_Name());
			ps.setString(6, bean.getFill_Date());
			ps.setDouble(7, bean.getTea_Attendance());
			ps.setDouble(8, bean.getCls_Explain());
			ps.setDouble(9, bean.getCls_Quesions());
			ps.setDouble(10, bean.getQues_Answer());
			ps.setDouble(11, bean.getCls_Coach());
			ps.setDouble(12, bean.getCls_Discipline());
			ps.setDouble(13, bean.getCls_Skill());
			ps.setDouble(14, bean.getCls_Progress());
			ps.setDouble(15, bean.getExam_Explain());
			ps.setDouble(16, bean.getClass_Homework());
			ps.setDouble(17, bean.getTotal_Score());
			ps.setString(18, bean.getStu_Advice());
			ps.setDouble(19, bean.getAverage());
			result = ps.executeUpdate();
			System.out.println("插入数据库成功");
		} catch (Exception e) {
			System.out.println("插入数据库出现错误");
			e.printStackTrace();
			C3P0Util.close(conn, ps, null);
		} finally {
			C3P0Util.close(conn, ps, null);
		}
		return result;
	}

	/**
	 * 查询数据库是否已经插入过数据
	 * 
	 * @param bean
	 * @return
	 * @throws SQLException
	 * @throws UnsupportedEncodingException
	 */
	public int chekResult(Investigation bean) throws SQLException, UnsupportedEncodingException {
		int result = 0;
		String sql = "select count(*) from " + Global.TAB_NAME + " where user_Id = '" + bean.getUser_Id()
				+ "' and sch_Name = '" + bean.getSch_Name() + "' and tea_Name = '" + bean.getTea_Name()
				+ "' and cus_Name = '" + bean.getCus_Name() + "'";
		System.out.println(sql);
		Connection conn = C3P0Util.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); // 执行查询数据库的操作
			if (rs.next()) {
				result = rs.getInt(1);
				System.out.println("检查数据已经存在");
			} else {
				System.out.println("检查数据已经存在");
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			C3P0Util.close(conn, null, rs);
			stmt.close();
		} finally {
			C3P0Util.close(conn, null, rs);
			stmt.close();
		}

		return result;
	}

	/**
	 * 检查是否已经投过票
	 * 
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	public int chekIsRepeat(Investigation bean) throws SQLException {
		int result = 0;
		String sql = "SELECT COUNT(*) FROM tab_researchinfo WHERE user_Id = '" + bean.getUser_Id()
				+ "' and large_Area ='" + bean.getLarge_Area() + "' AND sch_Name = '" + bean.getSch_Name()
				+ "' AND cus_Name = '" + bean.getCus_Name() + "' AND tea_Name = '" + bean.getTea_Name() + "'";

		String sqlString = "SELECT * FROM tab_researchinfo WHERE user_Id = '" + bean.getUser_Id()
				+ "' and large_Area = '" + bean.getLarge_Area() + "' AND sch_Name = '" + bean.getSch_Name()
				+ "' and cus_Name = '" + bean.getCus_Name() + "' AND tea_Name = '" + bean.getTea_Name()
				+ "' and date_format(fill_Date,'%Y-%m') = date_format('" + bean.getFill_Date() + "','%Y-%m')";
		System.out.println(sqlString);
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
	 * 
	 * @param large_Area
	 *            大区
	 * @param cus_Name
	 *            专业名称
	 * @return
	 * @throws SQLException
	 */
	public List<Investigation> queryRanking(String large_Area, String cus_Name) throws SQLException {
		String sql = "SELECT A.large_Area,A.sch_Name,A.cus_Name,A.tea_Name,SUM(A.average)/COUNT(1) b from "
				+ Global.TAB_NAME + " A where A.large_Area = '" + large_Area
				+ "' GROUP BY A.tea_Name,A.large_Area,A.sch_Name,A.cus_Name ORDER BY b desc;";
		System.out.println("排名的sql:-----" + sql);
		Connection conn = C3P0Util.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<Investigation> list = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); // 执行查询数据库的操作
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
			System.out.println("查询数据错误~");
			C3P0Util.close(conn, null, rs);
			stmt.close();
		} finally {
			C3P0Util.close(conn, null, rs);
			stmt.close();
		}
		return list;
	}

	/**
	 * 后台统计
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
		if ("请选择".equals(selectBean.getLargeArea())) {
			if ("请选择".equals(selectBean.getMajor())) {
				sql = "select * from " + Global.TAB_NAME + " where  fill_Date >= '" + selectBean.getStartDate()
						+ "' and fill_Date<= '" + selectBean.getEndDate()
						+ "' ORDER BY  large_Area ASC,sch_Name ASC,cus_Name ASC,total_Score DESC ";
			} else {
				sql = "select * from " + Global.TAB_NAME + " where cus_Name = '" + selectBean.getMajor()
						+ "' and fill_Date >= '" + selectBean.getStartDate() + "' and fill_Date<= '"
						+ selectBean.getEndDate()
						+ "' ORDER BY  large_Area ASC,sch_Name ASC,cus_Name ASC ,total_Score DESC";
			}
		} else {
			if ("请选择".equals(selectBean.getSchName())) {
				if ("请选择".equals(selectBean.getMajor())) {
					sql = "select * from " + Global.TAB_NAME + " where large_Area = '" + selectBean.getLargeArea()
							+ "' and fill_Date >= '" + selectBean.getStartDate() + "' and fill_Date<= '"
							+ selectBean.getEndDate()
							+ "' ORDER BY  large_Area ASC,sch_Name ASC,cus_Name ASC ,total_Score DESC";
				} else {
					sql = "select * from " + Global.TAB_NAME + " where large_Area = '" + selectBean.getLargeArea()
							+ "' and sch_Name = '" + selectBean.getSchName() + "' and fill_Date >= '"
							+ selectBean.getStartDate() + "' and fill_Date<= '" + selectBean.getEndDate()
							+ "' ORDER BY  large_Area ASC,sch_Name ASC,cus_Name ASC ,total_Score DESC";
				}
			} else {
				if ("请选择".equals(selectBean.getMajor())) {
					sql = "select * from " + Global.TAB_NAME + " where large_Area = '" + selectBean.getLargeArea()
							+ "' and sch_Name = '" + selectBean.getSchName() + "' and fill_Date >= '"
							+ selectBean.getStartDate() + "' and fill_Date<= '" + selectBean.getEndDate()
							+ "' ORDER BY  large_Area ASC,sch_Name ASC,cus_Name ASC,total_Score DESC";
				} else {
					sql = "select * from " + Global.TAB_NAME + " where large_Area = '" + selectBean.getLargeArea()
							+ "' and sch_Name = '" + selectBean.getSchName() + "' and cus_Name = '"
							+ selectBean.getMajor() + "' and fill_Date >= '" + selectBean.getStartDate()
							+ "' and fill_Date<= '" + selectBean.getEndDate()
							+ "' ORDER BY  large_Area ASC,sch_Name ASC,cus_Name ASC,total_Score DESC";
				}
			}
		}

		// sql = "select * from " + Global.TAB_NAME + " where large_Area = '" +
		// selectBean.getLargeArea()
		// + "' and sch_Name = '" + selectBean.getSchName() + "' and cus_Name =
		// '" + selectBean.getMajor()
		// + "' and fill_Date >= '" + selectBean.getStartDate() + "' and
		// fill_Date<= '" + selectBean.getEndDate()
		// + "'";
		System.out.println(sql);
		Connection conn = C3P0Util.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<Investigation> list = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql); // 执行查询数据库的操作
			list = new ArrayList<Investigation>();
			while (rs.next()) {
				Investigation investigation = new Investigation();
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
			System.out.println("查询数据错误~");
			C3P0Util.close(conn, null, rs);
			stmt.close();
		} finally {
			C3P0Util.close(conn, null, rs);
			stmt.close();
		}
		return list;
	}

	/**
	 * 后台统计(前段搜索界面)
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
		if ("请选择".equals(selectBean.getLargeArea())) {
			if ("请选择".equals(selectBean.getMajor())) {
				sql = "SELECT t.large_Area,t.sch_Name,t.tea_Name,t.cus_Name,COUNT(t.tea_Name) a,SUM(t.average)/COUNT(1) b FROM tab_researchinfo t where  fill_Date >= '"
						+ selectBean.getStartDate() + "' and fill_Date<= '" + selectBean.getEndDate()
						+ "' GROUP BY t.tea_Name,t.cus_Name ORDER BY large_Area ASC,sch_Name ASC,cus_Name ASC,b DESC";
			} else {
				sql = "SELECT t.large_Area,t.sch_Name,t.tea_Name,t.cus_Name,COUNT(t.tea_Name) a,SUM(t.average)/COUNT(1) b FROM tab_researchinfo t where cus_Name = '"
						+ selectBean.getMajor() + "' and fill_Date >= '" + selectBean.getStartDate()
						+ "' and fill_Date<= '" + selectBean.getEndDate()
						+ "' GROUP BY t.tea_Name,t.cus_Name ORDER BY large_Area ASC,sch_Name ASC,cus_Name ASC,b DESC";
			}
		} else {
			if ("请选择".equals(selectBean.getSchName())) {
				if ("请选择".equals(selectBean.getMajor())) {
					sql = "SELECT t.large_Area,t.sch_Name,t.tea_Name,t.cus_Name,COUNT(t.tea_Name) a,SUM(t.average)/COUNT(1) b FROM tab_researchinfo t where large_Area = '"
							+ selectBean.getLargeArea() + "' and fill_Date >= '" + selectBean.getStartDate()
							+ "' and fill_Date<= '" + selectBean.getEndDate()
							+ "' GROUP BY t.tea_Name,t.cus_Name ORDER BY large_Area ASC,sch_Name ASC,cus_Name ASC,b DESC";
				} else {
					sql = "SELECT t.large_Area,t.sch_Name,t.tea_Name,t.cus_Name,COUNT(t.tea_Name) a,SUM(t.average)/COUNT(1) b FROM tab_researchinfo t where large_Area = '"
							+ selectBean.getLargeArea() + "' and sch_Name = '" + selectBean.getSchName()
							+ "' and fill_Date >= '" + selectBean.getStartDate() + "' and fill_Date<= '"
							+ selectBean.getEndDate()
							+ "' GROUP BY t.tea_Name,t.cus_Name ORDER BY large_Area ASC,sch_Name ASC,cus_Name ASC,b DESC";
				}
			} else {
				if ("请选择".equals(selectBean.getMajor())) {
					sql = "SELECT t.large_Area,t.sch_Name,t.tea_Name,t.cus_Name,COUNT(t.tea_Name) a,SUM(t.average)/COUNT(1) b FROM tab_researchinfo t where large_Area = '"
							+ selectBean.getLargeArea() + "' and sch_Name = '" + selectBean.getSchName()
							+ "' and fill_Date >= '" + selectBean.getStartDate() + "' and fill_Date<= '"
							+ selectBean.getEndDate()
							+ "' GROUP BY t.tea_Name,t.cus_Name ORDER BY large_Area ASC,sch_Name ASC,cus_Name ASC,b DESC";
				} else {
					sql = "SELECT t.large_Area,t.sch_Name,t.tea_Name,t.cus_Name,COUNT(t.tea_Name) a,SUM(t.average)/COUNT(1) b FROM tab_researchinfo t where large_Area = '"
							+ selectBean.getLargeArea() + "' and sch_Name = '" + selectBean.getSchName()
							+ "' and cus_Name = '" + selectBean.getMajor() + "' and fill_Date >= '"
							+ selectBean.getStartDate() + "' and fill_Date<= '" + selectBean.getEndDate()
							+ "' GROUP BY t.tea_Name,t.cus_Name ORDER BY large_Area ASC,sch_Name ASC,cus_Name ASC,b DESC";
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
			rs = stmt.executeQuery(sql); // 执行查询数据库的操作
			list = new ArrayList<Investigation>();
			while (rs.next()) {
				Investigation investigation = new Investigation();
				investigation.setLarge_Area(rs.getString("large_Area"));
				investigation.setSch_Name(rs.getString("sch_Name"));
				investigation.setTea_Name(rs.getString("tea_Name"));
				investigation.setCus_Name(rs.getString("cus_Name"));
				investigation.setPeopleCount(rs.getInt("a"));
				/*********设置四舍五入*********/
				double average = rs.getDouble("b");
				BigDecimal b = new BigDecimal(average);
				double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				investigation.setAverage(f1);
				/*********设置四舍五入*********/
				list.add(investigation);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			System.out.println("查询数据错误~");
			C3P0Util.close(conn, null, rs);
			stmt.close();
		} finally {
			C3P0Util.close(conn, null, rs);
			stmt.close();
		}
		return list;
	}

}
