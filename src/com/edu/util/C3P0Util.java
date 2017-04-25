package com.edu.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * ���ݿ⹤����
 * 
 * @author Poppy
 * 
 */
public class C3P0Util {
	static final String logStr = "C3P0Util";
	static ComboPooledDataSource cpds = null;

	static {
		// �����и��ŵ㣬д�������ļ����뻻���ݿ⣬��
		// cpds = new ComboPooledDataSource("oracle");//����oracle���ݿ�
		cpds = new ComboPooledDataSource("mysql");// ����mysql���ݿ�
	}

	// �������Դ
	public static DataSource getDataSource() {
		return cpds;
	}

	/**
	 * ������ݿ�����
	 * 
	 * @return Connection
	 */
	public static Connection getConnection() {
		try {
			Connection conn = cpds.getConnection();
			if(conn!=null){
				Log4j.info(C3P0Util.class, "���ݿ����ӳɹ���");
			}
			return conn;
		} catch (SQLException e) {
			Log4j.error(C3P0Util.class, "���ݿ������쳣��:"+e.getMessage());
			return null;
		}
	}

	/**
	 * ���ݿ�رղ���
	 * 
	 * @param conn
	 * @param st
	 * @param pst
	 * @param rs
	 */
	public static void close(Connection conn, PreparedStatement pst, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pst != null) {
			try {
				pst.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
