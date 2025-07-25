package edu.pnu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

public class LogDao {
	
	private Connection con;
	Statement stmt = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;
	
	public LogDao() {
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
	
	public String addLog(Map<String, Object> map) {
		String key = map.keySet().iterator().next();
		String query = " insert into dblog (method, sqlstring) values (?, ?) ";
		
		
		try {
			psmt = con.prepareStatement(query);
			
			psmt.setString(1, key);
			psmt.setString(2, (String)map.get(key));
			psmt.executeUpdate();
			
			
		} catch (Exception e) {
			System.out.println("Log 추가 중 오류");
			e.printStackTrace();
		}
		return key + "의 로그가 추가되었습니다"; 
		
	}

}
