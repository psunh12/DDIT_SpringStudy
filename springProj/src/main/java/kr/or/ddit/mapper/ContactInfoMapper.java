package kr.or.ddit.mapper;

import kr.or.ddit.vo.ContactInfoVO;

public interface ContactInfoMapper {

	//방문 정보 등록
	public int createPost(ContactInfoVO contactInfoVO);

	//방문 정보 상세(ciCd=CI20240206001)
	public ContactInfoVO detail(String ciCd);

}
