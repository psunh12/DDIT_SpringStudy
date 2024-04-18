package kr.or.ddit.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.dao.LprodDao;
import kr.or.ddit.service.LprodService;
import kr.or.ddit.vo.LprodVO;
//자바빈으로 등록됨
@Service
public class LprodServiceImpl implements LprodService {

	@Autowired
	LprodDao lprodDao;
	
	//map{"keyword":"","currentPage":1}
	@Override
	public List<LprodVO> list(Map<String, Object> map) {
		return this.lprodDao.list(map);
	}

	//상품 분류 상세
	@Override
	public LprodVO listOne(LprodVO lprodVO) {
		return this.lprodDao.listOne(lprodVO);
	}

	//상품 분류 수정
	@Override
	public int updateOne(LprodVO lprodVO) {
		return this.lprodDao.updateOne(lprodVO);
	}

	//상품 분류 삭제
	@Override
	public int deleteOne(LprodVO lprodVO) {
		return this.lprodDao.deleteOne(lprodVO);
	}

	//상품 분류 추가
	@Override
	public int insertOne(LprodVO lprodVO) {
		return this.lprodDao.insertOne(lprodVO);
	}

	//전체 글 수
	@Override
	public int getTotal(Map<String, Object> map) {
		return this.lprodDao.getTotal(map);
	}

	@Override
	public int getMaxLprodId() {
		return this.lprodDao.getMaxLprodId();
	}
	
}
