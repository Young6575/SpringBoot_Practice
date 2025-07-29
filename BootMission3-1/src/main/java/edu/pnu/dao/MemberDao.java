package edu.pnu.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import edu.pnu.domain.MemberDTO;



@Repository
public class MemberDao {
	
	private Connection con;
	Statement stmt = null;
	PreparedStatement psmt = null;
	ResultSet rs = null;
	int result;
	

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
	
	public List<MemberDTO> getAllMember() {
		List<MemberDTO> mdto = new ArrayList<>();
		
		String query = "SELECT * FROM bootmission.member;";
		
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
		} catch (SQLException e) {
			System.out.println("getAllMember 실행 중 오류 발생");
			e.printStackTrace();
		}
		return mdto;
	}
	
	public MemberDTO getMemberById(Integer id) {
		MemberDTO mdto = new MemberDTO();
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
			
		} catch (Exception e) {
			System.out.println("id로 검색 중 오류 발생");
			e.printStackTrace();
		}
		return mdto;
	}
	
	public String postMember(MemberDTO memberDTO) {
		
		String query = " insert into member (pass, name) values (?, ?) ";
		
		try {
			psmt = con.prepareStatement(query);
			
			psmt.setString(1, memberDTO.getPass());
			psmt.setString(2, memberDTO.getName());
			
			result = psmt.executeUpdate();
			
		} catch (Exception e) {
			System.out.println("Insert 중 오류 발생");
			e.printStackTrace();
		}
	
		if (result == 1) {
			return memberDTO + "Insert 성공";
		}
		else {
			return "Insert 실패";
		}
	}
	
	public String putMember(Integer id, MemberDTO memberDTO) {
		String query=" UPDATE member SET "
				+ " pass = ?, name = ? "
				+" WHERE id = ? ";
		
		try {
			psmt = con.prepareStatement(query);
			
			psmt.setString(1, memberDTO.getPass());
			psmt.setString(2, memberDTO.getName());
			psmt.setInt(3,id);
			
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("Put 중에 오류발생");
			e.printStackTrace();
		}
		
		if (result == 1) {
			return memberDTO + "Put 성공";
		}
		else {
			return "Put 실패";
		}					
		
	}
	
	public String patchMember(Integer id, MemberDTO memberDTO) {
		MemberDao dao = new MemberDao();
		MemberDTO dto = dao.getMemberById(id);
		
		
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
		
	} catch (Exception e) {
		System.out.println("Patch 중 오류 발생");
		e.printStackTrace();
	}

	if (result == 1) {
		return memberDTO + "Patch 성공";
	}
	else {
		return "Patch 실패";
	}												
	}

	public void deleteMember(Integer id) {
		String query = " DELETE FROM member WHERE id = ? ";
		
		try {
			psmt = con.prepareStatement(query);
			psmt.setInt(1, id);
			
			result = psmt.executeUpdate();
		} catch (Exception e) {
			System.out.println("Delte 중 오류발생");
			e.printStackTrace();
		}
	}
	
	

}
