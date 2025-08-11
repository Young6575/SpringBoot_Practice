package com.rubypaper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rubypaper.domain.Board;
import com.rubypaper.domain.Member;
import com.rubypaper.persistence.BoardRepository;
import com.rubypaper.persistence.MemberRepository;

@RestController
@RequestMapping("/test")
public class TestController2 {
	
	@Autowired
	private BoardRepository boardRepo;
	@Autowired
	private MemberRepository memberRepo;
	
	@GetMapping("/board")
	public List<Board> getBoards() {
		return boardRepo.findAll();
	}
	
	@GetMapping("/member")
	public List<Member> getMembers() {
		return memberRepo.findAll();
	}
	
	
}
