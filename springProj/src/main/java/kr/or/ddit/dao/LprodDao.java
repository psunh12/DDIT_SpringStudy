package kr.or.ddit.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.LprodVO;
//자바빈으로 등록
@Repository
public class LprodDao {
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	//map{"keyword":"","currentPage":1}
	public List<LprodVO> list(Map<String, Object> map) {
		return this.sqlSessionTemplate.selectList("lprod.list", map);
	}

	//상품 분류 상세
	public LprodVO listOne(LprodVO lprodVO) {
		return this.sqlSessionTemplate.selectOne("lprod.listOne", lprodVO);
	}

	//상품 분류 수정
	public int updateOne(LprodVO lprodVO) {
		return this.sqlSessionTemplate.update("lprod.updateOne", lprodVO);
	}

	//상품 분류 삭제
	public int deleteOne(LprodVO lprodVO) {
		return this.sqlSessionTemplate.delete("lprod.deleteOne", lprodVO);
	}

	//상품 분류 추가
	public int insertOne(LprodVO lprodVO) {
		return this.sqlSessionTemplate.update("lprod.insertOne", lprodVO);
	}

	//전체 글 수
	public int getTotal(Map<String, Object> map) {
		return this.sqlSessionTemplate.selectOne("lprod.getTotal", map);
	}

	//마지막 아이디 값 가져오기
	public int getMaxLprodId() {
		return this.sqlSessionTemplate.selectOne("lprod.getMaxLprodId");
	}
	
}
