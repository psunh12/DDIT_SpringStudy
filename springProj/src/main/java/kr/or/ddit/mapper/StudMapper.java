package kr.or.ddit.mapper;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.HobbyVO;
import kr.or.ddit.vo.StudVO;

public interface StudMapper {
	//로그인을 위한 회원 검색
	//메소드 명 detail == 매퍼 xml의 <select id="detail
	public StudVO detail(String username);
	
	public int createRegister(StudVO studVO);

	public int createModify(StudVO studVO);

	public int createDelete(String studId);

	public List<StudVO> list(Map<String, Object> map);

	public int insertHobby(HobbyVO hobbyVO);

	public int updateAjax(StudVO studVO);

	public int getTotal(Map<String, Object> map);

}
