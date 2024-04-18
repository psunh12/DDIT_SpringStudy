package kr.or.ddit.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.service.QuickService;
import kr.or.ddit.vo.Card2VO;
import kr.or.ddit.vo.QuickVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/quick")
@Controller
public class QuickController {
	
	@Autowired
	QuickService quickService;
	
	/**
	 * Quick 폼 화면
	 * @return
	 */
	@GetMapping("/create")
	public String create(@ModelAttribute("quickVO") QuickVO quickVO) {
		//<form:form modelAttribute="quickVO"
		//forwarding : jsp
		return "quick/create";
	}
	
	/**
	 * 요청URI : /quick/createPost
		요청파라미터 : {emailAdres=test@test.com,password=java,uploadFile=파일객체}
		요청방식 : post	 
		
		부모테이블(QUICK) : 자식테이블(QUICK_ATTACH) = 1 : N
		
		registerId : admin
	 */
	@PostMapping("/createPost")
	public String createPost(@Validated QuickVO quickVO,
			BindingResult brResult) {
		/*{emailAdres=test@test.com,password=java,uploadFile=파일객체,cardVOList=[
		 CardVO(no=1, validMonth=20240207, emailAdres=null), 
		 CardVO(no=2, validMonth=20240307, emailAdres=null)
		 ]}
		 */
		log.info("createPost->quickVO : " + quickVO);
		log.error("BindingResult : " + brResult.hasErrors());//true(오류발생),false(오류없음)
		
		//오류 발생 시
		if(brResult.hasErrors()) {
			//검사 결과 오류 확인
			List<ObjectError> allErrors = brResult.getAllErrors();
			//객체와 관련된 오류
			List<ObjectError> globalErrors = brResult.getGlobalErrors();
			//멤버변수와 관련된 오류
			List<FieldError> fieldErrors = brResult.getFieldErrors();
			
			for(ObjectError objectError : allErrors) {
				log.info("allError : " + objectError);
			}
			
			for(ObjectError objectError : globalErrors) {
				log.info("globalError : " + objectError);
			}
			
			for(FieldError fieldError: fieldErrors) {
				log.info("fieldError : " + fieldError.getDefaultMessage());
			}
			
			//redirect로는 rsResult가 안넘어가고, forwarding일때만 바인딩오류 정보가 넘겨짐
			return "quick/create";//if문 아래는 실행 안함
		}
		
		int result = this.quickService.createPost(quickVO);
		log.info("createPost->result : " + result);
		
		return "redirect:/quick/detail?emailAdres="+quickVO.getEmailAdres();
	}
	/**
	 * 요청URI : /quick/createAjaxPost
		요청파라미터 : {emailAdres=test@test.com,password=java,uploadFile=파일객체}
		요청방식 : post	 
		
		부모테이블(QUICK) : 자식테이블(QUICK_ATTACH) = 1 : N
		
		registerId : admin
	 */
	@ResponseBody
	@PostMapping("/createAjaxPost")
	public String createAjaxPost(QuickVO quickVO) {
		/*{emailAdres=test@test.com,password=java,uploadFile=파일객체,cardVOList=[
		 CardVO(no=1, validMonth=20240207, emailAdres=null), 
		 CardVO(no=2, validMonth=20240307, emailAdres=null)
		 ]}
		 */
		log.info("createAjaxPost->quickVO : " + quickVO);
		
		int result = this.quickService.createPost(quickVO);
		log.info("createAjaxPost->result : " + result);
		
		return quickVO.getEmailAdres();
	}
	
	/**
	 * 요청URI : /quick/detail?emailAdres=test@test.com
		요청파라미터 : {emailAdres=test@test.com}
		요청방식 : get
	 */
	@GetMapping("/detail")
	public String detail(@RequestParam("emailAdres") String emailAdres
			, Model model) {
		//detail->emailAdres : test@test.com
		log.info("detail->emailAdres : " + emailAdres);
		
		QuickVO quickVO = this.quickService.detail(emailAdres);
		log.info("detail->quickVO : " + quickVO);
		
		model.addAttribute("quickVO", quickVO);
		
		//forwarding : jsp
		return "quick/detail";
	}
	
	@ResponseBody
	@GetMapping("/insertAllTest")
	public String insertAllTest() {
		
		int result = this.quickService.insertAllTest();
		log.info("insertAllTest->result : " + result);
		
		return "success";
	}
}





