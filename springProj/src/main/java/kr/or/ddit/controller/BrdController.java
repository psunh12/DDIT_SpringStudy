package kr.or.ddit.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

//회원게시판
@RequestMapping("/brd")
@Slf4j
@Controller
public class BrdController {
	//회원게시판-목록(누구나 접근 가능)
	@GetMapping("/list")
	public String list() {
		log.info("brd->list->누구나 접근 가능");
		//forwarding : jsp
		return "brd/list";
	}
	//회원게시판-등록(로그인 한 회원만 접근 가능)
	@PreAuthorize("hasRole('ROLE_MEMBER')")
	@GetMapping("/register")
	public String register() {
		log.info("brd->register->로그인 한 회원만 접근 가능");
		//forwarding : jsp
		return "brd/register";
	}
}


