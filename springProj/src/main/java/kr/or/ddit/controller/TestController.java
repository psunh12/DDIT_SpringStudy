package kr.or.ddit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import kr.or.ddit.service.TestService;

@Controller
public class TestController {
	
	@Autowired
	TestService testService;
	
}