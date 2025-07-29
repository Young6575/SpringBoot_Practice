package edu.pnu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import edu.pnu.service.MemberService;

@RestController
@RequestMapping("/api")
public class MemberController {

	@Autowired	
	private MemberService service;
	
	@GetMapping("/member")
	public List<MemberDTO> getAllMember() {
		return service.getAllMember();
	}
	
	@GetMapping("/member/{id}")   // 검색(Read – select One)
	public MemberDTO getMemberById(@PathVariable Integer id) {
		return service.getMemberById(id);
	}
	
	@PostMapping("/member")
	public String postMember(@RequestBody MemberDTO memberDTO) {
		return service.postMember(memberDTO);
	}
	
	@PutMapping("/member/{id}")
	public String putMember(@PathVariable Integer id,
							   @RequestBody MemberDTO memberDTO) {
		
		return service.putMember(id, memberDTO);			
	}
	
	@PatchMapping("/member/{id}") // 수정(Update – 일부 정보 수정)
	public String patchMember(@PathVariable Integer id,
								 @RequestBody MemberDTO memberDTO) {
		return service.patchMember(id, memberDTO);							
	}	
	
	
	@DeleteMapping("/member/{id}") // 삭제(Delete - delete)
	public void deleteMember(@PathVariable Integer id) {
		service.deleteMember(id);
	}
	
	
}
