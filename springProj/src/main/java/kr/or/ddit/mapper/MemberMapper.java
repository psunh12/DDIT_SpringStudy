package kr.or.ddit.mapper;

import kr.or.ddit.vo.MemberVO;

public interface MemberMapper {

	//메소드명 detail => 매퍼xml의 id 가 동일 <select id="detail"
	public MemberVO detail(String username);
	
}
