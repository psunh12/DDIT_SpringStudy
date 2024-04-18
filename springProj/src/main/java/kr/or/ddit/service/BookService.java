package kr.or.ddit.service;

import java.util.List;

import kr.or.ddit.vo.BookVO;

public interface BookService {

	public int createPost(BookVO bookVO);

	//책 상세보기
	public BookVO detail(BookVO bookVO);

	//책 수정
	public int updatePost(BookVO bookVO);

	//책 삭제
	public int deletePost(BookVO bookVO);

	//책 목록
	public List<BookVO> list(String keyword);
	
}
