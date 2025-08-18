package com.rubypaper.controller;

import java.lang.reflect.Member;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.rubypaper.domain.Board;
import com.rubypaper.service.BoardService;

@SessionAttributes("member")
@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@ModelAttribute("member")
	public com.rubypaper.domain.Member setMember() {
		return new com.rubypaper.domain.Member();
	}
	
//	@RequestMapping(value = "/getBoardList",method = {RequestMethod.GET,  RequestMethod.POST} )
//	public String getBoardList(Model model) {
//		List<Board> boardList = boardService.getBoardList();
//		model.addAttribute("boardList",boardList);
//		
//		return "getBoardList";
//	}
	
	@GetMapping("/getBoardList")
	public String getBoardList(@ModelAttribute("member") com.rubypaper.domain.Member member,Model model) {
		if (member.getId() == null)
			return "redirect:login";
		model.addAttribute("boardList",boardService.getBoardList());
		return "getBoardList";
	}
	
	@GetMapping("/insertBoard")
	public String insertBoardView() {
		return "insertBoard";
	}
	
	@PostMapping("/insertBoard")
	public String insertBoard(Board board) {
		boardService.insertBoard(board);
		return "redirect:getBoardList";
	}
	
	@GetMapping("/getBoard")
	public String getBoard(Board board, Model model) {
		model.addAttribute("board", boardService.getBoard(board));
		return "getBoard";
	}
	
	@PostMapping("/updateBoard")
	public String updateBoard(Board board) {
		boardService.updateBoard(board);
		return "forward:getBoardList";
	}
	
	@GetMapping("/deleteBoard")
	public String deleteBoard(Board board) {
		boardService.deleteBoard(board);
		return "forward:getBoardList";
	}
	
	@GetMapping("/hello")
	public void hello(Model model) {
		model.addAttribute("greeting","Hello 타임리프");
	}
	
	
	
	
}
