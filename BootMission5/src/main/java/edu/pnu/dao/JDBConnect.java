package edu.pnu.dao;

import java.sql.Connection;
import java.sql.DriverManager;


public class JDBConnect {
	
	private Connection con;

	public JDBConnect() {
		try {
			// JDBC 드라이버 로드
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//DB에 연결
			String url = "jdbc:mysql://localhost:3306/bootmission";
			String id = "musthave";
			String pwd = "tiger";
			con = DriverManager.getConnection(url, id, pwd);
			
			System.out.println("DB 연결 성공(기본 생성자)");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getCon() {
		return con;
	}
	
	
	
	
}
