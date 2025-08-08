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

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;

@RestController
@RequestMapping("/api")
public class MemberController {

	@Autowired	
	private MemberService service;
	
	@GetMapping("/member")
	public List<Member> getAllMember() {
		return service.getAllMember();
	}
	
	@GetMapping("/member/{id}")   // 검색(Read – select One)
	public Member getMemberById(@PathVariable Integer id) {
		return service.getMemberById(id);
	}
	
	@PostMapping("/member")
	public Member postMember(@RequestBody Member Member) {
		return service.postMember(Member);
	}
	
	@PutMapping("/member/{id}")
	public Member putMember(@PathVariable Integer id,
							   @RequestBody Member Member) {
		
		return service.putMember(id, Member);			
	}
	
	@PatchMapping("/member/{id}") // 수정(Update – 일부 정보 수정)
	public Member patchMember(@PathVariable Integer id,
								 @RequestBody Member Member) {
		return service.patchMember(id, Member);							
	}	
	
	
	@DeleteMapping("/member/{id}") // 삭제(Delete - delete)
	public void deleteMember(@PathVariable Integer id) {
		service.deleteMember(id);
	}
	
	
}
