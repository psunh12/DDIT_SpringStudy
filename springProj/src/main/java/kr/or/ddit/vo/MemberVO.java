package kr.or.ddit.vo;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MemberVO {
	private int userNo;
	private String userId;
	private String userPw;
	private String userName;
	private int coin;
	private Date regDate;
	private Date updDate;
	private String enabled;
	
	//회원 : 권한 = 1 : N
	//중첩된 자바빈
	private List<MemberAuthVO> memberAuthVOList;
}



