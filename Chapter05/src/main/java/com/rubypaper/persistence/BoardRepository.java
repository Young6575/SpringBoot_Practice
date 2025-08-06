package com.rubypaper.persistence;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rubypaper.domain.Board;
import com.rubypaper.domain.dto.BoardDTO;

public interface BoardRepository extends JpaRepository<Board, Long> {
	List<Board> findByTitle(String searchKeyword);
	List<Board> findByContentContaining(String content);
	List<Board> findByTitleContainingOrContentContaining(String title, String content);
	List<Board> findByTitleContainingOrderBySeqDesc(String searchKewword);
	List<Board> findByTitleLike(String title);
	
	// 테스트
	List<Board> findByTitleContaining(String title);
	List<Board> findByTitleContainingAndCntGreaterThan(String title, Long cnt);
	List<Board> findByCntGreaterThanEqualAndCntLessThanEqualOrderBySeq(Long min, Long max);
	List<Board> findByTitleContainingOrContentContainingOrderBySeqDesc(String title, String content);
	Page<Board> findByTitleContainingOrderBySeqAsc(String title, Pageable paging);
	
	// @Query Annotation 사용
	@Query ("SELECT b FROM Board b WHERE b.title like %?1% ORDER BY b.seq DESC")
	List<Board> queryAnnotationTest1(String searchKeyword);
	
	@Query("SELECT b FROM Board b WHERE b.title like %:searchKeyword% ORDER BY b.seq DESC")
	List<Board> queryAnnotationTest2(String searchKeyword);
	
	@Query("select b.seq, b.title, b.writer, b.createDate from Board b where b.title like %?1% order by b.seq desc")
	List<Object[]> queryAnnotationTest3(String searchKeyword);
	
	// Board객체로 바로 전달받기
	@Query("select new com.rubypaper.domain.dto.BoardDTO(b.seq, b.title, b.writer)" // bean객체 양식에 따라 풀주소 적기!
		+ "from Board b "
		+ "where b.title like %:searchKeyword% order by b.seq desc")
	List<BoardDTO> queryAnnotationTest4(String searchKeyword);
	
	// 네이티브 쿼리사용 --> 잘 사용안함(DB마다 바꿔줘야해서) 
	@Query(value="select seq,title,writer,create_date from board where title like '%'||:searchKeyword||'%' "
			+ "order by seq desc", nativeQuery = true) 
	List<Object[]> queryAnnotationTest5(String searchKeyword);
	
	@Query("select b from Board b order by b.seq desc")
	List<Board> queryAnnotationTest6(Pageable paging);
	
	@Query("select b from Board b order by b.seq desc")
	Page<Board> queryAnnotationTest7(Pageable paging);
	
	
	
}
