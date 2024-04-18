package kr.or.ddit.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.or.ddit.service.LprodService;
import kr.or.ddit.util.ArticlePage;
import kr.or.ddit.vo.LprodVO;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/lprod")
@Slf4j
@Controller
public class LprodController {
	
	@Autowired
	LprodService lprodService;
	
	/*
	 요청URI : /lprod/list or /lprod/list?currentPage=1
	 요청파라미터 : ?currentPage=1
	 요청방식 : get
	 
	 //forwarding
	 return "lprod/list";
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String list(Model model, Map<String,Object> map,
			@RequestParam(value="currentPage",required=false,defaultValue="1") int currentPage,
			@RequestParam(value="keyword",required=false,defaultValue="") String keyword) {
		
		//map = null
		//map{"keyword":"","currentPage":""}
		
		if(map!=null) {
			keyword = (String)map.get("keyword");	
			
			if(map.get("currentPage")==null) {
				map.put("currentPage", currentPage);
			}
		}else {
			map = new HashMap<String, Object>();
			map.put("keyword", "");
			map.put("currentPage", 1);
		}
		
		map.put("currentPage", currentPage);
		
		//map{"keyword":"","currentPage":1}
		
		//전체 글 수
		int total = this.lprodService.getTotal(map);
		log.info("list->total : " + total);
		//한 화면에 보여지는 행의 수
		int size = 10;
		
		//1. LprodVO를 만들기
		//2. mybatisAlias.xml에 alias 추가
		//3. select
		List<LprodVO> lprodVOList = this.lprodService.list(map);
		log.info("list->lprodVOList : " + lprodVOList);
		
		model.addAttribute("data", new ArticlePage<LprodVO>(total
				, currentPage, size, lprodVOList,keyword));
		
		//forwarding : jsp
		return "lprod/list";
	}
	
	/*
	 요청URI : /lprod/listAjax
	 요청파라미터(json) : null 또는 {"keyword":""} 또는 {"keyword":"컴"}
	 요청방식 : post
	 
	 //forwarding
	 return "lprod/list";
	 */
	@ResponseBody
	@RequestMapping(value="/listAjax",method=RequestMethod.POST)
	public ArticlePage<LprodVO> listAjax(
			@RequestBody(required=false) Map<String,Object> map
			) throws ParseException {
		//map{"keyword":"S7","currentPage":1}
		log.info("listAjax->map : " + map);
		
		String keyword = map.get("keyword").toString();
		int currentPage = Integer.parseInt(map.get("currentPage").toString());
		
		//map{"keyword":"","currentPage":1}
		
		int total = this.lprodService.getTotal(map);
		log.info("listAjax->total : " + total);
		
		int size = 10;
		
		//Map<String,Object> map
		List<LprodVO> lprodVOList = this.lprodService.list(map);
		log.info("list->lprodVOList : " + lprodVOList);
		
		ArticlePage<LprodVO> data = new ArticlePage<LprodVO>(total
				, currentPage, size, lprodVOList, keyword);
		
		String url = "/lprod/list";
		
		data.setUrl(url);
		
		return data;
	}
	
	//LPROD 테이블에서 1행 검색
	/*
	요청URI : /lprod/listOne
	요청파라미터(json) : {"lprodId":"2"}
	요청방식 : post
	
	ResponseBody->{"lprodId":"2","lprodGu":"P102","lprodNm":"전자제품"}
	*/
	@ResponseBody
	@RequestMapping(value="/listOne",method=RequestMethod.POST)
	public LprodVO listOne(@RequestBody LprodVO lprodVO) {
		//LprodVO[lprodId=2,lprodGu=null,lprodNm=null]
		log.info("listOne->lprodVO : " + lprodVO);
		
		lprodVO = this.lprodService.listOne(lprodVO);
		//LprodVO[lprodId=2,lprodGu=P102,lprodNm=전자제품]
		log.info("listOne->lprodVO(후) : " + lprodVO);
		
		return lprodVO;
	}
	
	/*
	요청URI : /lprod/updateOne
	요청파라미터(json) : {"lprodId":"802","lprodGu":"T802","lprodNm":"상품분류명T802"}
	요청방식(비동기) : post
	
	리턴타입이 int, String일 경우 dataType:"text",
	*/
	@ResponseBody
	@PostMapping("/updateOne")
	public int updateOne(@RequestBody LprodVO lprodVO) {
		log.info("updateOne : " + lprodVO);
		
		int result = this.lprodService.insertOne(lprodVO);
//		int result = this.lprodService.updateOne(lprodVO);
		log.info("updateOne : " + result);
		
		return result;
	}
	
	/*
	요청URI : /lprod/deleteOne
	요청파라미터(json) : {"lprodId":75}
	요청방식 : post
	리턴타입 : int
	*/
	@ResponseBody
	@PostMapping("/deleteOne")
	public int deleteOne(@RequestBody LprodVO lprodVO) {
		//LprodVO[lprodId=2,lprodGu=null,lprodNm=null]
		log.info("lprodVO : " + lprodVO);
		
		int result = this.lprodService.deleteOne(lprodVO);
		log.info("deleteOne->result : " + result);//1행 삭제됨
		
		return result;
	}
	
	/*
	요청URI : /lprod/insertOne
	요청파라미터(json) : {"lprodId":"36","lprodGu":"TT092","lprodNm":"트리거 테스트 2"}
	요청방식(비동기) : post
	
	리턴타입이 int, String일 경우 dataType:"text",
	*/
	@ResponseBody
	@PostMapping("/insertOne")
	public int insertOne(@RequestBody LprodVO lprodVO) {
		log.info("insertOne : " + lprodVO);
		
		int result = this.lprodService.insertOne(lprodVO);
		log.info("insertOne : " + result);
		
		return result;
	}
	
	/*
	 요청URI : /lprod/getMaxLprodId
	요청파라미터 :
	요청방식 : post
	 */
	@ResponseBody
	@PostMapping("/getMaxLprodId")
	public int getMaxLprodId() {
		int result = this.lprodService.getMaxLprodId();
		log.info("getMaxLprodId->result : " + result);
		
		return result;
	}
}





