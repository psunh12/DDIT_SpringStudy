package kr.or.ddit.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.service.StudService;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.vo.StudVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/stud")
@Controller
public class StudController {
	
	//DI(의존성 주입)
	//IoC(제어의 역전)
	@Autowired
	StudService studService;
	
	//security-context.xml을 참고
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	/*
	요청URI : /stud/create?register
	요청파라미터 : 
	파람즈 : register
	요청방식 : get(HTTP method)
	*/
	@RequestMapping(value="/create",method=RequestMethod.GET,params="register")
	public String create() {
		log.info("create");
		
		//forwarding : jsp
		return "stud/create";
	}
	
	/*
	요청URI : /stud/create?register
	요청파라미터 : {studId=a001,studNm=개똥이,studPw=java,studDet=학생상세내용
				,gender=여성,nationality=korea,postNum=12345,studAddress=대전 중구
				,studAddress2=123-33}
	파람즈 : register
	요청방식 : post
	*/
	@RequestMapping(value="/create",method=RequestMethod.POST,params="register")
	public String createRegister(StudVO studVO) {
		
		//StudVO(rnum=0, studId=a011, studNm=개똥이011, studPw=java, enabled=null
		//, studDet=<p><strong>학생상세</strong></p>, hobby=null, gender=female
		//, nationality=korea, postNum=63309, studAddress=제주특별자치도 제주시 첨단로 242
		//, studAddress2=123, studAuthVOList=null, hobbyVOList=null)
		log.info("studVO : " + studVO);
		
		String studPw = passwordEncoder.encode(studVO.getStudPw());
		log.info("createRegister->studPw : " + studPw);
		
		studVO.setStudPw(studPw);
		
		//학생 등록
		int result = this.studService.createRegister(studVO);
		log.info("createRegister->result : " + result);
		
		//redirect : URL재요청
		return "redirect:/stud/detail?studId="+studVO.getStudId();
	}
	
	/*
	 요청URI : /stud/detail?studId=a001
	 요청파라미터 : studId=a001
	 요청방식 : get
	 */
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public String detail(String studId, Model model) {
		log.info("detail->studId : " + studId);
		StudVO studVO = this.studService.detail(studId);
		//StudVO(studId=a001, studNm=개똥이, studPw=JAVA, enabled=1)
		log.info("detail->studVO : " + studVO);
		
		//mav.addObject("속성명", 데이터);
		//session.setAttribute("속성명", 데이터);
		model.addAttribute("studVO", studVO);
		
		//forwarding : jsp
		return "stud/detail";
	}
	
	@ResponseBody
	@PostMapping(value="/detailAjax")
	public StudVO detailAjax(@RequestBody StudVO studVO, Model model) {
		log.info("detail->studVO : " + studVO);
		
		String studId = studVO.getStudId();
		
		studVO = this.studService.detail(studId);
		//StudVO(studId=a001, studNm=개똥이, studPw=JAVA, enabled=1)
		log.info("detail->studVO : " + studVO);
		
		return studVO;
	}
	
	/* 
	요청URI : /stud/create?modify
	요청파라미터 : {studId=a001,studNm=개똥이2,studPw=java2}
	파람즈 : modify
	요청방식 : post
	*/
	@RequestMapping(value="/create",method=RequestMethod.POST,params="modify")
	public String createModify(String studId, String studNm, String studPw) {
		StudVO studVO = new StudVO(studId, studNm, studPw);
		log.info("createModify->studVO : " + studVO);
		
		//update
		int result = this.studService.createModify(studVO);
		log.info("createModify->result : " + result);
		
		//redirect : URL재요청
		return "redirect:/stud/detail?studId="+studId;
	}
	
	/* 
	요청URI : /stud/create?delete
	요청파라미터 : {studId=a001,studNm=개똥이2,studPw=java2}
	파람즈 : delete
	요청방식 : post
	*/
	@RequestMapping(value="/create",method=RequestMethod.POST,params="delete")
	public String createDelete(String studId, String studNm, String studPw) {
		StudVO studVO = new StudVO(studId, studNm, studPw);
		log.info("studId : " + studId);
		
		int result = this.studService.createDelete(studId);
		log.info("createDelete->result : " + result);
		
		//redirect : URI 재요청
		return "redirect:/stud/list";		
	}
	
	/*
	 요청URI : /stud/list
	 요청파라미터 : 
	 요청방식 : get
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model, Map<String,Object> map,
			@RequestParam(value="currentPage",required=false,defaultValue="1") int currentPage) {
		
		map.put("currentPage", currentPage);
		
		List<StudVO> studVOList = this.studService.list(map);
		log.info("list->studVOList : " + studVOList);
		
		model.addAttribute("studVOList", studVOList);
		
		//forwarding : jsp
		return "stud/list";
	}
	/*
	 요청URI : /stud/listAjax
	 요청파라미터(json) : {"keyword":"신용","currentPage":3} 
	 요청방식 : get
	 
	 골뱅이ResponseBody : object -> string 으로 변환하는 방식을 serialize
	 required=false : 선택(map에 아무것도 들어오지 않아도 괜찮다)
	 */
	@ResponseBody
	@RequestMapping(value="/listAjax",method=RequestMethod.POST)
	public ArticlePage<StudVO> listAjax(@RequestBody(required=false) Map<String,Object> map) {
		//map : null 또는 map : {"keyword":"알탄","currentPage":1}
		log.info("map : " + map);
		
		//map : {"keyword":"","currentPage":1}
		
		List<StudVO> studVOList = this.studService.list(map);
		log.info("list->studVOList : " + studVOList);		
		
		int total = this.studService.getTotal(map);
		log.info("list->total : " + total);
		int size = 10;
		
		String currentPage = map.get("currentPage").toString();
		
		String keyword = map.get("keyword").toString();
		log.info("listAjax->keyword : " + keyword);
		
		ArticlePage<StudVO> data = new ArticlePage<StudVO>(total,
				Integer.parseInt(currentPage), size, studVOList, keyword);
		
		String url = "/stud/list";
		data.setUrl(url);
		
		
		return data;
	}
	
	@ResponseBody
	@RequestMapping(value="/updateAjax",method=RequestMethod.POST)
	public int updateAjax(@RequestBody StudVO studVO) {
		log.info("updateAjax->studVO : " + studVO);
		
		int result = this.studService.createModify(studVO);
		log.info("updateAjax->result : " + result);		
		
		return result;
	}
	
	@ResponseBody
	@PostMapping(value="/deleteAjax")
	public int deleteAjax(@RequestBody StudVO studVO) {
		log.info("deleteAjax->studVO : " + studVO);
		
		String studId = studVO.getStudId();
		
		int result = this.studService.createDelete(studId);
		log.info("updateAjax->result : " + result);		
		
		return result;
	}
}




