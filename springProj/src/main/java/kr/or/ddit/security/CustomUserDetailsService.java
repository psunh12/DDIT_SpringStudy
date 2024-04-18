package kr.or.ddit.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import kr.or.ddit.dao.MemberDao;
import kr.or.ddit.mapper.MemberMapper;
import kr.or.ddit.mapper.StudMapper;
import kr.or.ddit.vo.MemberVO;
import kr.or.ddit.vo.StudVO;
import lombok.extern.slf4j.Slf4j;

/*
UserDetailsService : 스프링 시큐리티에서 제공해주고 있는
사용자 상세 정보를 갖고 있는 인터페이스
 */
@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	//DI(Dependency Injection) : 의존성 주입
	//IoC(Inversion of Control) : 제어의 역전
	@Autowired
	private StudMapper studMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("CustomUserDetailsService->username : " + username);
		//1) 사용자 정보를 검색
		//username : 로그인 시 입력 받은 회원의 아이디. <input type="text" name="username"
		
		StudVO studVO = this.studMapper.detail(username);
		/*
		memberVO : MemberVO(userNo=4, userId=d001, userPw=$2a$10$vd.Y8PtJfTina10HtRQ6vOS/LXMf8.b4dejXsiXtI/3Wamkm4l1Ou, userName=개똥이, coin=1000, regDate=Mon Jan 29 00:00:00 KST 2024, updDate=null, enabled=1, 
		memberAuthVOList=[MemberAuthVO(userNo=4, auth=ROLE_ADMIN), MemberAuthVO(userNo=4, auth=ROLE_MEMBER)])
		 */
		log.info("studVO : " + studVO);
		//MVC에서는 Controller로 리턴하지 않고, CustomUser로 리턴함
		//CustomUser : 사용자 정의 유저 정보. extends User를 상속받고 있음
		//2) 스프링 시큐리티의 User 객체의 정보로 넣어줌 => 프링이가 이제부터 해당 유저를 관리
		//User : 스프링 시큐리에서 제공해주는 사용자 정보 클래스
		/*
		 memberVO(우리) -> user(시큐리티)
		 -----------------
		 userId        -> username
		 userPw        -> password
		 enabled       -> enabled
		 auth들                -> authorities
		 */
		return studVO == null?null:new CustomUser(studVO);
	}

}


