package kr.or.ddit.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.dao.StudDao;
import kr.or.ddit.mapper.StudMapper;
import kr.or.ddit.service.StudService;
import kr.or.ddit.vo.HobbyVO;
import kr.or.ddit.vo.StudVO;

//스프링이 자바빈으로 등록해서 관리
@Service
public class StudServiceImpl implements StudService {

	//DI(의존성 주입)
	//IoC(제어의 역전)
	@Autowired
	StudMapper studMapper;
	
	//학생 등록
	@Override
	public int createRegister(StudVO studVO) {
		return this.studMapper.createRegister(studVO);
	}

	//학생 상세
	@Override
	public StudVO detail(String studId) {
		return this.studMapper.detail(studId);
	}

	//학생 수정
	@Override
	public int createModify(StudVO studVO) {
		return this.studMapper.createModify(studVO);
	}

	//학생 삭제
	@Override
	public int createDelete(String studId) {
		return this.studMapper.createDelete(studId);
	}

	//학생 목록
	@Override
	public List<StudVO> list(Map<String, Object> map) {
		return this.studMapper.list(map);
	}

	//학생 등록(추가)
	@Override
	public int register(StudVO studVO) {
		//StudVO[studId=a005,studNm=개똥이,studPw=java, enabled=null
		//	, studDet=상세정보, hobby=[sports,movie], hobbyVOList=null]
		//1) STUD 테이블에 insert
		int result = this.studMapper.createRegister(studVO);
		
		//2) HOBBY 테이블에 insert
		List<HobbyVO> hobbyVOList = new ArrayList<HobbyVO>();
		String[] hobby = studVO.getHobby();//[sports,movie]
		for(String str : hobby) {
			HobbyVO hobbyVO = new HobbyVO();
			hobbyVO.setStudId(studVO.getStudId());//a005->a005
			hobbyVO.setHobby(str);//sports->movie
			
			result += this.studMapper.insertHobby(hobbyVO);
			
			hobbyVOList.add(hobbyVO);
		}
		//StudVO[...hobby=[sports,movie]
		//		, hobbyVOList=[[studId=a005,hobby=sports],[studId=a005,hobby=movie]]]
		studVO.setHobbyVOList(hobbyVOList);
		
		return result;
	}

	@Override
	public int updateAjax(StudVO studVO) {
		return this.studMapper.updateAjax(studVO);
	}

	@Override
	public int getTotal(Map<String, Object> map) {
		return this.studMapper.getTotal(map);
	}

}
