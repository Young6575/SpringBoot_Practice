package edu.pnu.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.MemberDTO;

@RestController
@RequestMapping("/api")
public class MemebrController {
	
	// 데이터 저장용 객체 생성
	private List<MemberDTO> list = new ArrayList<>();
	
	public MemebrController() { // 데이터 초기화
		for (int i = 1; i <= 2 ; i++ ) {
			list.add(MemberDTO.builder().id(i).name("name"+i).pass("pass" + i)
										.regidate(new Date()).build());
		}
	}
	
	
	@GetMapping("/member")
	public List<MemberDTO> getAllMember() {
		return list;
	}
	
	@GetMapping("/member/{id}")   // 검색(Read – select One)
	public MemberDTO getMemberById(@PathVariable Integer id) {
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i).getId().equals(id)) {
				return list.get(i);
			}
		}
		return null;
	}
	@PostMapping("/member")
	public MemberDTO postMember(@RequestBody MemberDTO memberDTO) {
		
		list.add(memberDTO);
		return memberDTO;
		
	}
	
	@PutMapping("/member/{id}")
	public MemberDTO putMember(@PathVariable Integer id,
							   @RequestBody MemberDTO memberDTO) {
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getId() == id) {
				list.remove(i);
				list.add(i, memberDTO);
				return memberDTO;
			}
		}
		return null;							
	}
	
	@PatchMapping("/member/{id}") // 수정(Update – 일부 정보 수정)
	public MemberDTO patchMember(@PathVariable Integer id,
								 @RequestBody MemberDTO memberDTO) {
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

	
	
	
	@DeleteMapping("/member/{id}") // 삭제(Delete - delete)
	public void deleteMember(@PathVariable Integer id) {
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i).getId().equals(id)) {
				list.remove(i);
			}
		}
	}

}
