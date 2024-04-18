package kr.or.ddit.freeBoard.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.freeBoard.mapper.FreeBoardMapper;
import kr.or.ddit.freeBoard.service.FreeBoardService;
import kr.or.ddit.freeBoard.vo.FreeBoardVO;

@Service
public class FreeBoardServiceImpl implements FreeBoardService {

	//DI/IoC
	@Autowired
	FreeBoardMapper freeBoardMapper;
	
	/*자유게시판 목록*/
	@Override
	public List<FreeBoardVO> list(Map<String, Object> map){
		return this.freeBoardMapper.list(map);
	}
	
	//전체 행의 수
	@Override
	public int getTotal() {
		return this.freeBoardMapper.getTotal();
	}
	
}
