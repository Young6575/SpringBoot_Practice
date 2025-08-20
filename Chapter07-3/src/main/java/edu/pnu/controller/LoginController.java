package edu.pnu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;

@Controller
public class LoginController {

	@Autowired
	MemberService memberService;
	
	@GetMapping("/login")
	public void login() {}

	@GetMapping("/loginSuccess")
	public void loginSuccess() {}
	
//	@GetMapping("/auth1")
//	public @ResponseBody ResponseEntity<?> auth1(@AuthenticationPrincipal SecurityUser user) {
//		
//	}
	
	@GetMapping("/join")
	public void join() {}
	
	@PostMapping("/join")
	public String joinProc(Member member) { 
		memberService.save(member);
		
		return "welcome";
	}
	
}
