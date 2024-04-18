package kr.or.ddit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class LoginController {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	//로그인 폼
	@GetMapping("/login")
	public String loginForm() {
		//암호화(java라는 문자열을 암호화 처리)
		String pwd = "0000";
		//java : $2a$10$j7Ufvh1VYt.Py.21.n95OemPfjzGmgTB3hc6qAvR8IdiA2vOWFYLW
		//0000 : $2a$10$inYtjC1YLOLr79fvB2Ei/Oepeu6AGxb8HY/ihBlbtm2eZ0TZbmRQq
		
		String encodedPwd = this.passwordEncoder.encode(pwd);
		log.info("encodedPwd : " + encodedPwd);		
		
		//forwarding
		return "loginForm";
	}
}
