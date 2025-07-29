package edu.pnu.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.pnu.dao.LogDao;
import edu.pnu.dao.MemberDao;
import edu.pnu.domain.MemberDTO;

@SuppressWarnings("unchecked")
@Service
public class MemberService {
	
	@Autowired
	private MemberDao memberdao;
	
	@Autowired
	private LogDao logdao;
	
	
	
	public List<MemberDTO> getAllMember() {
		Map<String,Object> map = memberdao.getAllMember();
		logdao.addLog(map);
		
		return (List<MemberDTO>) map.get("result"); 
	}
	
	public MemberDTO getMemberById(Integer id) {
		Map<String, Object> map = memberdao.getMemberById(id); 
		logdao.addLog(map);
		
		return  (MemberDTO) map.get("result");
	}
	
	public String postMember(MemberDTO memberDTO) {
		Map<String, Object> map = memberdao.postMember(memberDTO); 
		logdao.addLog(map);
		
		if (map.get("success").equals(true)) {
			return "Post 성공";
		} else {
			return "Post 실패";
		}
	}
	
	public String putMember(Integer id, MemberDTO memberDTO) {
		Map<String, Object> map = memberdao.putMember(id, memberDTO);
		logdao.addLog(map);
		
		if (map.get("success").equals(true)) {
			return "Put 성공";
		} else {
			return "Put 실패";
		}	
	}
	
	public String patchMember(Integer id, MemberDTO memberDTO) {
		Map<String, Object> map = memberdao.patchMember(id, memberDTO);
		logdao.addLog(map);
		
		if (map.get("success").equals(true)) {
			return "Patch 성공";
		} else {
			return "Patch 실패";
		}							
	}
	
	public String deleteMember(Integer id) {
		Map<String, Object> map = memberdao.deleteMember(id);
		logdao.addLog(map);
		
		if (map.get("success").equals(true)) {
			return "Delete 성공";
		} else {
			return "Delete 실패";
		}	
	}

}
