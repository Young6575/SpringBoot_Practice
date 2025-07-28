package edu.pnu.service;

import java.util.List;

import edu.pnu.dao.LogDao;
import edu.pnu.dao.MemberDao;
import edu.pnu.domain.MemberDTO;

public class MemberService {
	
MemberDao memberdao = new MemberDao();
LogDao logdao = new LogDao();
	
	
	public List<MemberDTO> getAllMember() {
		
		return memberdao.getAllMember();
	}
	
	public MemberDTO getMemberById(Integer id) {
		return memberdao.getMemberById(id);
	}
	public String postMember(MemberDTO memberDTO) {
		return memberdao.postMember(memberDTO);
		
	}
	
	public String putMember(Integer id, MemberDTO memberDTO) {
		return memberdao.putMember(id, memberDTO);						
	}
	
	public String patchMember(Integer id, MemberDTO memberDTO) {
		return memberdao.patchMember(id, memberDTO);							
	}
	
	public void deleteMember(Integer id) {
		memberdao.deleteMember(id);
	}

}
