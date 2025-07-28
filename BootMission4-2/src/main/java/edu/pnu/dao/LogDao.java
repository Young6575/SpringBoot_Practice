package edu.pnu.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

public class LogDao extends JDBConnect {
	
	Statement stmt = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;
	

	
	public String addLog(Map<String, Object> map) {
		
//		{
//			"method" : get
//			"sqlString" : "SELECT * FROM bootmission.member;"
//			"success" : true
//			"result" : mdto
//			"result" : [
//				{
//					"id" : 1,
//					"name" : pass1,
//					...
//				},
//				{
//					...
//				}
//			]
//		}
		
		String method = (String) map.get("method");
		String sqlString = (String) map.get("sqlString");
		boolean success = (boolean) map.get("success");
		
		String query = " insert into dblog (method, sqlstring,success) values (?, ?, ?) ";
		
			try {
				psmt = getCon().prepareStatement(query);
				
				psmt.setString(1, method);
				psmt.setString(2, sqlString);
				psmt.setBoolean(3, success);
				psmt.executeUpdate(); 
				
			} catch (Exception e) {
				System.out.println("Log 추가 중 오류");
				e.printStackTrace();
			}
		
		return method + "의 로그가 추가되었습니다"; 
		
	}

}
