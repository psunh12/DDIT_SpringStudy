package kr.or.ddit.service;

import java.util.List;
import java.util.Map;

import kr.or.ddit.vo.StudVO;

public interface StudService {

	//학생 등록
	public int createRegister(StudVO studVO);

	//학생 상세
	public StudVO detail(String studId);

	//학생 수정
	public int createModify(StudVO studVO);

	//학생 삭제
	public int createDelete(String studId);

	//학생 목록
	public List<StudVO> list(Map<String, Object> map);

	//학생 등록(추가)
	public int register(StudVO studVO);
	
	
	public int updateAjax(StudVO studVO);

	public int getTotal(Map<String, Object> map);
	
}
