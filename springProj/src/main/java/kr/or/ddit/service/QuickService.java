package kr.or.ddit.service;

import java.util.List;

import kr.or.ddit.vo.Card2VO;
import kr.or.ddit.vo.QuickVO;

public interface QuickService {

	public int createPost(QuickVO quickVO);
	//상세
	public QuickVO detail(String emailAdres);
	
	public int insertAllTest();

}
