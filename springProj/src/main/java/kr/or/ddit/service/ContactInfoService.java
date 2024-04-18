package kr.or.ddit.service;

import kr.or.ddit.vo.ContactInfoVO;

public interface ContactInfoService {

	//방문 정보 입력
	public int createPost(ContactInfoVO contactInfoVO);

	//방문 정보 상세
	public ContactInfoVO detail(String ciCd);
	
}
