package com.edu.util;

/**
 * 绠楀紡姹傚�肩被
 * 
 * @author Administrator
 *
 */
public class FormulaUtil {

	/**
	 * 鑾峰彇璇勫垎鐨勬�诲垎
	 * 
	 * @param d1
	 * @param d2
	 * @param d3
	 * @param d4
	 * @param d5
	 * @param d6
	 * @param d7
	 * @param d8
	 * @param d9
	 * @param d10
	 * @return
	 */
	public static double getTotalScore(double d1, double d2, double d3, double d4, double d5, double d6, double d7,
			double d8, double d9, double d10) {
		double sumValue = d1 + d2 + d3 + d4 + d5 + d6 + d7 + d8 + d9 + d10;
		System.out.println(d1);
		System.out.println(d2);
		System.out.println(d3);
		System.out.println(d4);
		System.out.println(d5);
		System.out.println(d6);
		System.out.println(d7);
		System.out.println(d8);
		System.out.println(d9);
		System.out.println(d10);
		return sumValue;
	}

}
