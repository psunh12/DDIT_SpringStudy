package kr.or.ddit.controller;

import java.util.List;

import javax.inject.Inject;
import javax.xml.ws.BindingType;

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

import kr.or.ddit.service.ContactInfoService;
import kr.or.ddit.vo.ContactInfoVO;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/contactInfo")
@Slf4j
@Controller
public class ContactInfoController {
	
	@Inject
	ContactInfoService contactInfoService;
	
	//<form:form modelAttribute="contactInfoVO" 
	@GetMapping("/create")
	public String create(ContactInfoVO contactInfoVO) {
		
		//forwarding : jsp
		return "contactInfo/create";
	}
	
	/*
	요청URI : /contactInfo/createPost
	요청파라미터 : {ciName=개똥이,ciMail=test@test.com,ciSubj=채용상담,ciImgUrl=파일객체,
			    ciMesg=채용절차에 대한 상담,ciRegDt=2024/02/17}
	요청방식 : post
	*/
	@PostMapping("/createPost")
	public String createPost(@Validated ContactInfoVO contactInfoVO,
			BindingResult brResult) {
		/*
		 ContactInfoVO(ciCd=null, ciName=개똥이, ciMail=test@test.com, ciSubj=취업상담, 
		 	ciImgUrl=null, ciMesg=<figure class="image"><img src="http://localhost/resources/upload/b5cde164-ba71-434f-bcf4-8afd3e58fee9.jpg"></figure>, 
		 	ciRegDt=Sat Feb 17 00:00:00 KST 2024, registerId=null, registDt=null, 
		 	uploadFile=org.springframework.web.multipart.support.StandardMultipartHttpServletRequest$StandardMultipartFile@42bed612)
		 */
		log.info("createPost->contactInfoVO : " + contactInfoVO);
		
		//brResult.hasErrors() : true(오류 발생), false(오류 없음)
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
			
			//forwarding
			return "contactInfo/create";
		}
		
		//파일업로드 및 insert처리(Impl에서..)
		int result = this.contactInfoService.createPost(contactInfoVO);
		log.info("createPost->result : " + result);
		
		return "redirect:/contactInfo/detail?ciCd="+contactInfoVO.getCiCd();
	}
	
	/*
	요청URI : /contactInfo/detail
	요청파라미터 : ciCd=CI20240206001
	요청방식 : get
	*/
//	<form:form modelAttribute="contactInfoVO" 
	@GetMapping("/detail")
	public String detail(ContactInfoVO contactInfoVO, Model model) {
		log.info("detail->contactInfoVO(전) : "+ contactInfoVO);
		
		contactInfoVO = this.contactInfoService.detail(contactInfoVO.getCiCd());
		log.info("detail->contactInfoVO(후) : "+ contactInfoVO);
		
		model.addAttribute("contactInfoVO", contactInfoVO);
		
		//forwarding : jsp
		return "contactInfo/detail";
	}
}





