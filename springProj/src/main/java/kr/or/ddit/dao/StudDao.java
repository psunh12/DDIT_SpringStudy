package kr.or.ddit.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.HobbyVO;
import kr.or.ddit.vo.StudVO;

//매퍼xml(stud_SQL.xml)을 실행시키는 
//DAO(Data Access Object) 클래스
//Repository 어노테이션 : 데이터에 접근하는 클래스
//스프링이 데이터를 관리하는 클래스라고 인지하여 자바빈으로 등록하여 관리함
@Repository
public class StudDao {
	
	//sql실행 객체
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	public int createRegister(StudVO studVO) {
		//insert("namespace.id",파라미터)
		return this.sqlSessionTemplate.insert("stud.createRegister",studVO);
	}

	//학생 상세
	public StudVO detail(String studId) {
		return this.sqlSessionTemplate.selectOne("stud.detail",studId);
	}

	//학생 수정
	public int createModify(StudVO studVO) {
		return this.sqlSessionTemplate.update("stud.createModify", studVO);
	}

	//학생 삭제
	public int createDelete(String studId) {
		return this.sqlSessionTemplate.delete("stud.createDelete", studId);
	}

	//학생 목록
	public List<StudVO> list(Map<String, Object> map) {
		return this.sqlSessionTemplate.selectList("stud.list",map);
	}

	//HOBBY 테이블에 insert
	public int insertHobby(HobbyVO hobbyVO) {
		//1번째 : [studId=a005,hobby=sports]
		//2번째 : [studId=a005,hobby=movie]
		return this.sqlSessionTemplate.insert("stud.insertHobby",hobbyVO);
	}

	public int updateAjax(StudVO studVO) {
		return this.sqlSessionTemplate.update("stud.updateAjax", studVO);
	}
	
}
