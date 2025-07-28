package edu.pnu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pnu.domain.MemberDTO;

public class MemberDao {
	
	private Connection con;
	Statement stmt = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;
	int result;
	LogDao logdao = new LogDao();
	String sqlString;
	

	public MemberDao() {
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
	
	public Map<String,Object> getAllMember() {
		List<MemberDTO> mdto = new ArrayList<>();
		Map<String,Object> map = new HashMap<>();
		map.put("method", "get");
		
		String query = "SELECT * FROM bootmission.member;";
		map.put("sqlString", query);
		try {
			 stmt = con.createStatement();
			 rs = stmt.executeQuery(query);
			 while( rs.next()) {
				 MemberDTO dto = new MemberDTO();
				 dto.setId(rs.getInt("id"));
				 dto.setPass(rs.getString("pass"));
				 dto.setName(rs.getString("name"));
				 dto.setRigidate(rs.getDate("regidate"));
				 mdto.add(dto);
			 }
			 
//			 logdao.addLog(Map.of("getAllMember", query));
			 map.put("success", true);

		} catch (SQLException e) {
			System.out.println("getAllMember 실행 중 오류 발생");
			e.printStackTrace();
			map.put("success", false);
		}
		map.put("result", mdto);
		return map;
	}
	
	public Map<String, Object> getMemberById(Integer id) {
		MemberDTO mdto = new MemberDTO();
		Map<String, Object> map = new HashMap<>();
		
		map.put("method","get");
		String query = "SELECT * FROM bootmission.member "
					 + " where id = ? ";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, id);
			
			rs = psmt.executeQuery();
			
			while (rs.next()) {
			mdto.setId(rs.getInt(1));
			mdto.setPass(rs.getString(2));
			mdto.setName(rs.getString(3));
			mdto.setRigidate(rs.getDate(4));
			}
			
			map.put("sqlString", psmt.toString().split(": ")[1]);
			map.put("success",true);
			
		} catch (Exception e) {
			System.out.println("id로 검색 중 오류 발생");
			e.printStackTrace();
			map.put("success",false);
		}
		map.put("result",mdto);
		return map;
	}
	
	public Map<String, Object> postMember(MemberDTO memberDTO) {
		Map<String, Object> map = new HashMap<>();
		map.put("method", "Post");
		
		String query = " insert into member (pass, name) values (?, ?) ";
		
		try {
			psmt = con.prepareStatement(query);
			
			psmt.setString(1, memberDTO.getPass());
			psmt.setString(2, memberDTO.getName());
			
			result = psmt.executeUpdate();
			
			map.put("sqlString", psmt.toString().split(": ")[1]);
			map.put("success", true);
			
		} catch (Exception e) {
			System.out.println("Insert 중 오류 발생");
			e.printStackTrace();
			map.put("success", false);
		}
	
		return map;
	}
	
	public Map<String, Object> putMember(Integer id, MemberDTO memberDTO) {
		Map<String, Object> map = new HashMap<>();
		map.put("method", "Put");
		
		String query=" UPDATE member SET "
				+ " pass = ?, name = ? "
				+" WHERE id = ? ";
		
		try {
			psmt = con.prepareStatement(query);
			
			psmt.setString(1, memberDTO.getPass());
			psmt.setString(2, memberDTO.getName());
			psmt.setInt(3,id);
			
			psmt.toString();
			
			result = psmt.executeUpdate();
			
			map.put("sqlString", psmt.toString().split(": ")[1]);
			map.put("success", true);
			
		} catch (Exception e) {
			System.out.println("Put 중에 오류발생");
			e.printStackTrace();
			map.put("success", false);
		}
		
		return map;				
		
	}
	
	public Map<String, Object> patchMember(Integer id, MemberDTO memberDTO) {
		MemberDao dao = new MemberDao();
		Map<String, Object> mapTemp = dao.getMemberById(id);
		MemberDTO dto = (MemberDTO) mapTemp.get("result");
		
		Map<String, Object> map = new HashMap<>();
		map.put("method", "Patch");
		
		String query=" UPDATE member SET "
					+ " pass = ?, name = ? "
					+" WHERE id = ? ";
	
	try {
		psmt = con.prepareStatement(query);
		
		if (memberDTO.getPass() == null) {
			psmt.setString(1, dto.getPass());
			psmt.setString(2, memberDTO.getName());
		}
		else if (memberDTO.getName() == null) {
			psmt.setString(1, memberDTO.getPass());
			psmt.setString(2, dto.getName());
		}
		else {
			psmt.setString(1, memberDTO.getPass());
			psmt.setString(2, memberDTO.getName());
		}
		psmt.setInt(3,id);
		
		result = psmt.executeUpdate();
		
		map.put("sqlString", psmt.toString().split(": ")[1]);
		map.put("success", true);
		
	} catch (Exception e) {
		System.out.println("Patch 중 오류 발생");
		e.printStackTrace();
		map.put("success", false);
	}

	return map;												
	}

	public Map<String, Object> deleteMember(Integer id) {
		
		Map<String, Object> map = new HashMap<>();
		map.put("method", "Delete");
		
		String query = " DELETE FROM member WHERE id = ? ";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, id);
			
			result = psmt.executeUpdate();
			
			map.put("sqlString", psmt.toString().split(": ")[1]);
			map.put("success", true);
			
		} catch (Exception e) {
			System.out.println("Delte 중 오류발생");
			e.printStackTrace();
			map.put("success", false);
		}
		
		return map;
	}

}
