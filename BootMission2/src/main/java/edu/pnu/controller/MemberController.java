package edu.pnu.controller;

import org.springframework.web.bind.annotation.RestController;

import edu.pnu.service.MemberService;


@RestController
public class MemberController {
	
	public MemberController() {
		MemberService service = new MemberService();
	}
	
}
