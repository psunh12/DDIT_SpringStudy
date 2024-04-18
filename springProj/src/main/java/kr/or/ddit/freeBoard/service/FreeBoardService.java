package kr.or.ddit.freeBoard.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.freeBoard.vo.FreeBoardVO;

public interface FreeBoardService {
	//메소드 시그니처
	
	//자유게시판 목록
	public List<FreeBoardVO> list(Map<String, Object> map);

	//전체 행의 수
	public int getTotal();

}
