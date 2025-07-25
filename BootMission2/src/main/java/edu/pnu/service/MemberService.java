package edu.pnu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.pnu.domain.MemberDTO;

public class MemberService {
	
	List<MemberDTO> list = new ArrayList<>(); 
	
	public MemberService() {
		for (int i = 1; i <= 10 ; i++ ) {
			list.add(MemberDTO.builder().id(i).name("name"+i).pass("pass" + i)
										.regidate(new Date()).build());
		}
	}
	
	
	public List<MemberDTO> getAllMember() {
		return list;
	}
	
	public MemberDTO getMemberById(Integer id) {
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i).getId().equals(id)) {
				return list.get(i);
			}
		}
		return null;
	}
	public MemberDTO postMember(MemberDTO memberDTO) {
		
		list.add(memberDTO);
		return memberDTO;
		
	}
	
	public MemberDTO putMember(Integer id, MemberDTO memberDTO) {
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == id) {
				list.remove(i);
				list.add(i, memberDTO);
				return memberDTO;
			}
		}
		return null;							
	}
	
	public MemberDTO patchMember(Integer id, MemberDTO memberDTO) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == id) {
				MemberDTO orgin = list.get(i);
				if (memberDTO.getPass() != null) {
					orgin.setPass(memberDTO.getPass());
				}
				if (memberDTO.getName() != null) {
					orgin.setName(memberDTO.getName());
				}
				if (memberDTO.getRegidate() != null) {
					orgin.setRegidate(memberDTO.getRegidate());
				}
				list.set(i, orgin);
				return orgin;
			}
		}
		return null;							
	}

	
	
	
	public void deleteMember(Integer id) {
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i).getId().equals(id)) {
				list.remove(i);
			}
		}
	}
	
	
	
}
