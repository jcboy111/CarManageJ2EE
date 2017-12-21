package com.cwj.taiqiangle.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	/*
		静态块，检查一下有没有驱动
	 */
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/carmanage?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=true","root","root");
	}

	/*
	 	用来测试的main
	 */
	public static void main(String[] args) throws SQLException {
		System.out.println(getConnection());
		
	}

	

}