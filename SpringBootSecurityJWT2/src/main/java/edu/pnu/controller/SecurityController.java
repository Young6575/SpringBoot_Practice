package edu.pnu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityController {

	@GetMapping({"/","/index"})
	public String index() {
		return "index2";
	}
	
	@GetMapping("/member")
	public String member() {
		return "member2";
	}

	@GetMapping("/manager")
	public String manager() {
		return "manager2";
	}
	
	@GetMapping("/admin")
	public String admin() {
		return "admin2";
	}
}
