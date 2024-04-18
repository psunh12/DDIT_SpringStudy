package kr.or.ddit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

//프링아 이 클래스는 컨트롤러야. 자바빈(객체)으로 등록해서 관리해줘.
//속성이 하나일 때는 속성명을 생략할 수 있다.
//[클래스 레벨]로 요청 경로를 지정
@RequestMapping("/board")
@Slf4j
@Controller
public class BoardController {
	/*
	 요청URI : /board/register
	 요청파라미터 : 
	 요청방식 : get
	 */
	//[메서드 레벨]의 요청 경로가 덧붙여져서 최종 경로를 만듦
	@RequestMapping(value="/register",method=RequestMethod.GET)
	public void register() {
		log.info("register");
	}
	
	/*
	 요청URI : /board/registerTest
	 요청파라미터 : 
	 요청방식 : get
	 return 타입 : void
	 */
	@RequestMapping(value="/registerTest",method=RequestMethod.GET)
	public void registerTest() {
		log.info("registerTest");
	}
	
	/*
	 요청URI : /board/100, /board/101
	 요청파라미터 : 
	 요청방식 : get
	 return 타입 : String
	 
	 경로(Path) 변수(Variable) 매핑
	 BoardVO의 boardNo 프로퍼티
	 */
	@RequestMapping(value="/{boardNo}")
	public String read(@PathVariable("boardNo") int boardNo) {
		log.info("read->boardNo : " + boardNo);
		
		//forwarding
		return "board/read";
	}
	
	/*
	 요청URI : /board/stud/1, /board/stud/2
	 요청파라미터 : 
	 요청방식 : get
	 return 타입 : String
	 메소드명 : readTest
	 
	 경로(Path) 변수(Variable) 매핑
	 BoardVO의 seq 프로퍼티
	 
	 return "board/read"
	 */
	@RequestMapping(value="/stud/{seq}",method=RequestMethod.GET)
	public String readTest(@PathVariable("seq") int seq) {
		log.info("readTest->seq : " + seq);
		//forwarding : jsp
		return "board/read";
	}
	
	/*Params매핑 
	요청URI : /board/read?register
	요청파라미터 : register
	요청방식 : get
	*/
	@RequestMapping(value="/read",method=RequestMethod.GET,params="register")
	public String readParams() {
		log.info("readParams");
		//forwarding
		return "board/read";
	}
	
	/*Params매핑 
	요청URI : /board/read?modify
	요청파라미터 : register
	요청방식 : get
	*/
	@RequestMapping(value="/read",method=RequestMethod.GET,params="modify")
	public String readParamsModify() {
		log.info("readParamsModify");
		//forwarding
		return "board/read";
	}
	
	/*Params매핑 
	요청URI : /board/read?register
	요청파라미터 : register
	요청방식 : post(HTTP method)
	*/
	@RequestMapping(value="/read",method=RequestMethod.POST,params="register")
	public String readParamsPost(String memEmail, String memPass, 
				MultipartFile uploadFile) {
		log.info("readParamsPost");
		log.info("memEmail : " + memEmail);
		log.info("memPass : " + memPass);
		log.info("uploadFile : " + uploadFile);
		//forwarding : jsp
		return "board/read";
	}
}


