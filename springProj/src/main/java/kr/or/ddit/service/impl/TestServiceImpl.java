package kr.or.ddit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.mapper.TestMapper;
import kr.or.ddit.service.TestService;

@Service
public class TestServiceImpl implements TestService {
	
	@Autowired
	TestMapper testMapper;
	
}
