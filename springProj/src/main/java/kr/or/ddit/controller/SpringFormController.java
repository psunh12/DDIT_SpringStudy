package kr.or.ddit.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.or.ddit.service.StudService;
import kr.or.ddit.vo.StudVO;
import lombok.extern.slf4j.Slf4j;

//프링이가 자바빈(객체)으로 등록하여 관리
@RequestMapping("/springform")
@Slf4j
@Controller
public class SpringFormController {
	
	//DI(의존성 주입)
	//IoC(제어의 역전)
	@Autowired
	StudService service;
	
	/* 스프링 폼 태그 라이브러리
	 스프링 폼은 HTML 폼을 표시하기 위한 태그 라이브러리
	 스프링 폼을 이용하면 HTML 폼과 자바 객체를 쉽게 바이딩할 수 있음
	 뷰(jsp)와 Controller가 자바 객체를 공유해서 사용할 수 있음
	 */
	@GetMapping("/registerForm01")
	public String registerForm01(Model model) {
		log.info("registerForm01");
		
		StudVO studVO = new StudVO();
		//5.폼 객체의 프로퍼티 값을 지정하여 모델을 통하여 전달할 수 있음 
		//StudVO[seq=0,studId=null,studNm=null,studPw=null, enabled=1]
		studVO.setStudId("a001");
		studVO.setStudNm("개똥이");
		studVO.setStudPw("java");
		studVO.setEnabled("1");
		studVO.setStudDet("개똥이는 개똥이다.\n그래서 개똥이다.");
		//체크박스 미리 클릭해놓기
		String[] hobby = {"sports","movie"};
		studVO.setHobby(hobby);
		//성별 세팅
		studVO.setGender("female");
		//국적 세팅
		Map<String,String> nationalityMap = new HashMap<String, String>();
		nationalityMap.put("korea", "한국");
		nationalityMap.put("germany", "독일");
		nationalityMap.put("usa", "미국");
		model.addAttribute("nationalityMap", nationalityMap);
		
		//1. 폼 객체(model)의 속성명("studVO")과 스프링 폼 태그의 modelAttribute 속성값이 일치해야 함
		//model{studVO=학생객체}
		model.addAttribute("studVO", studVO);
		
		//forwarding : jsp
		return "springform/registerForm";
	}
	
	/*
	요청URI :  /springform/register
	요청파라미터 : {studId=a005,studNm=개똥이,studPw=java, studDet=상세정보
			  , hobby=[sports,movie], gender=female, nationality=korea}
	요청방식 : post
	*/
	//2. 컨트롤러 메서드의 매개변수로 자바빈즈 객체가 전달이 되면 
	//	  forwarding하는 JSP에 다시 화면으로 전달함
	//입력값 검증을 할 도메인 클래스(StudVO)에 골뱅이Validated를 지정함
	//입력값 검증 대상의 도메인 클래스 직후에 BindingResult를 정의함. 
	//BindingResult에는 요청 파라미터 데이터의 바인딩(set..) 오류와 
	// 입력값 검증 오류 정보가 저장됨
	// result.hasErrors() : 바인딩 시 오류 발생 시 true를 반환함
	@PostMapping("/register")
	public String register(@Validated StudVO studVO, BindingResult result) {
		//StudVO[studId=a005,studNm=개똥이,studPw=java, enabled=null
		//	, studDet=상세정보, hobby=[sports,movie], hobbyVOList=null
		//  , gender=female, nationality=korea]
		log.info("register : " + studVO);
		log.error("바인딩 result : " + result.hasErrors());//true(오류발생), false(오류없음)
		
//		int result = this.service.register(studVO);
//		log.info("register->result : " + result);
		
		//forwarding
		return "springform/registerForm";
	}
	/*
	요청URI :  /springform/register02
	요청파라미터 : {studId=a005,studNm=개똥이,studPw=java}
	요청방식 : post
	 */
	//3. 폼 객체의 속성명은 직접 지정하지 않으면(골뱅이ModelAttribute("studVO")) 
	//	폼 객체의 클래스명(StudVO)의 맨처음 문자를
	//	소문자로 변환(studVO)하여 처리함
	@PostMapping("/register02")
	public String register02(@ModelAttribute("studVO") StudVO userVO) {
		//StudVO[studId=a005,studNm=개똥이,studPw=java, enabled=null]
		log.info("register02 : " + userVO);
		
		//forwarding
		return "springform/registerForm";
	}
	/*
	요청URI :  /springform/register03
	요청파라미터 : {studId=a005,studNm=개똥이,studPw=java}
	요청방식 : post
	 */
	//4. 골뱅이ModelAttribute 애너테이션으로 폼 객체의 속성명을 직접 지정할 수 있음
	@PostMapping("/register03")
	public String register03(@ModelAttribute("userVO") StudVO userVO) {
		//StudVO[seq=0,studId=a005,studNm=개똥이,studPw=java, enabled=null]
		log.info("register03 : " + userVO);
		
		userVO.setStudNm("허주희");
		
		//forwarding
		return "springform/registerForm03";
	}
	/*
	요청URI :  /springform/register05
	요청파라미터 : {studId=a005,studNm=개똥이,studPw=java, enabled=1}
	요청방식 : post
	 */
	//4. 골뱅이ModelAttribute 애너테이션으로 폼 객체의 속성명을 직접 지정할 수 있음
	@PostMapping("/register05")
	public String register05(@ModelAttribute("studVO") StudVO studVO) {
		//StudVO[seq=0,studId=a005,studNm=개똥이,studPw=java, enabled=1]
		log.info("register05 : " + studVO);
		
		//STUD 테이블에 insert
		int result = this.service.createRegister(studVO);
		log.info("register05->result : " + result);
		
		//forwarding : jsp
		return "springform/detail";
	}
}


