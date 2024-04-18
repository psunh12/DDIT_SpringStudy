package kr.or.ddit.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.or.ddit.vo.MemberVO;
import lombok.extern.slf4j.Slf4j;

//자바빈으로 관리
@Slf4j
@Repository
public class MemberDao {
	
	//쿼리 실행 객체를 DI
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	//로그인 회원 검색
	public MemberVO detail(String username) {
		log.info("MemberDao->detail->username : " + username);
		//selectOne("namespace.id",파라미터)
		return this.sqlSessionTemplate.selectOne("member.detail", username);
	}
	
	
	
}





