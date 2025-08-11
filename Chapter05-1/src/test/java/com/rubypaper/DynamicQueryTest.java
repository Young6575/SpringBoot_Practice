package com.rubypaper;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.BooleanBuilder;
import com.rubypaper.domain.Board;
import com.rubypaper.domain.QBoard;
import com.rubypaper.persistence.DynamicBoardRepository;

@SpringBootTest
public class DynamicQueryTest {

	@Autowired
	private DynamicBoardRepository boardRepo;
	
	@Test
	public void testDynamicQuery() {
		String searchCondition = "content";
		String searchKeyword = "테스트 내용 20";
		
		BooleanBuilder builder = new BooleanBuilder();
		QBoard qboard = QBoard.board;
		
		if (searchCondition.equals("title")) {
			builder.and(qboard.title.contains(searchKeyword));
		} else if (searchCondition.equals("content")) {
			builder.and(qboard.content.contains("%"+searchKeyword+"%"));
		}
		Pageable paging = PageRequest.of(0,5);
		Page<Board> boardList =
				boardRepo.findAll(builder, paging);
		
		System.out.println("검색 결과");
		for (Board board : boardList) {
			System.out.println("--->" + board.toString());
		}
	}
}
