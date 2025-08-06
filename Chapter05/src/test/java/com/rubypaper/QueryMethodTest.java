package com.rubypaper;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.rubypaper.domain.Board;
import com.rubypaper.persistence.BoardRepository;

@SpringBootTest
public class QueryMethodTest {

	
	@Autowired
	private BoardRepository boardRepo;
	
	@BeforeAll // 모든 테스트 실행 전에 한번 실행
	static void dataPrepare(@Autowired BoardRepository boardRepo) {
		Random rnd = new Random();
		
		System.out.println("dataPreapare()");
		for ( int i = 1; i <= 200; i++) {
			Board board = new Board();
			board.setTitle("테스트 제목 " + i);
			board.setWriter("테스터");
			board.setContent("테스트 내용 " + i);
			board.setCreateDate(new Date());
			board.setCnt(rnd.nextLong(101));
			boardRepo.save(board);
		}
	}
//	@Test
	public void testFindByTitle() {
		List<Board> boardList = boardRepo.findByTitle("테스트 제목 10");
		System.out.println("검색 결과");
		for (Board board : boardList) {
			System.out.println("--->" + board.toString());
		}
	}
	
//	@Test
	public void testFindByContentContaining() {
		List<Board> boardList = boardRepo.findByContentContaining("17");
		System.out.println("검색 결과");
		for (Board board : boardList) {
			System.out.println("--->" + board.toString());
		}
	}
	
//	@Test
	public void testFindByTitleContainingOrContentContaining() {
		List<Board> boardList = boardRepo.findByTitleContainingOrContentContaining("17", "17");
		System.out.println("검색 결과");
		for (Board board : boardList) {
			System.out.println("--->" + board.toString());
		}
	}
	
//	@Test
	public void testFindByTitleContainingOrOrderBySeqDesc() {
		List<Board> boardList = boardRepo.findByTitleContainingOrderBySeqDesc("17");
		System.out.println("검색 결과");
		for (Board board : boardList) {
			System.out.println("--->" + board.toString());
		}
	}
	
//	@Test
	public void test02() {
		List<Board> boardList = boardRepo.findByTitleContaining("1");
		System.out.println("검색결과");
		for (Board board : boardList) {
			System.out.println("--->" + board.toString());
		}
	}
	
//	@Test
	public void test03() {
		List<Board> boardList = boardRepo.findByTitleContainingAndCntGreaterThan("1", (long) 50);
		System.out.println("검색결과");
		for (Board board : boardList) {
			System.out.println("--->" + board.toString());
		}
	}
	
//	@Test
	public void test04() {
		List<Board> boardList = boardRepo.findByCntGreaterThanEqualAndCntLessThanEqualOrderBySeq((long) 10, (long) 50);
		System.out.println("검색결과");
		for (Board board : boardList) {
			System.out.println("--->" + board.toString());
		}
	}
	
//	@Test
	public void test05() {
		List<Board> boardList = boardRepo.findByTitleContainingOrContentContainingOrderBySeqDesc("10", "2");
		System.out.println("검색결과");
		for (Board board : boardList) {
			System.out.println("--->" + board.toString());
		}
	}
	
//	@Test
	public void test06() {
		List<Board> boardList = boardRepo.findByTitleLike("테스트 제목 1_");
		System.out.println("검색결과");
		for (Board board : boardList) {
			System.out.println("--->" + board.toString());
		}
	}
	
	@Test
	public void testFindByTitleContaining() {
		
		Pageable paging = PageRequest.of(0, 5, Sort.Direction.DESC, "seq");
		Page<Board> pageInfo = boardRepo.findByTitleContainingOrderBySeqAsc("제목", paging);
		System.out.println("PAGE SIZE : " + pageInfo.getSize());
		System.out.println("TOTAL PAGES : " + pageInfo.getTotalPages());
		System.out.println("TOTAL COUNT : " + pageInfo.getTotalElements());
		System.out.println("NEXT : " + pageInfo.nextPageable());
		
		
		for (Board board : pageInfo.getContent()) {
			System.out.println("--->" + board.toString());
		}
	}
	
	
	
	
}
