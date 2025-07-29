package edu.pnu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.dao.MemberDao;
import edu.pnu.domain.MemberDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	
	@Autowired
	private MemberDao memberdao;
	
//	private final MemberDao memberdao; @autowired 안써도 final만 써도 됌. @RequiredArgsConstructor
	
	
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
