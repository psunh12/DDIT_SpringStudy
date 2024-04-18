package kr.or.ddit.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kr.or.ddit.service.BookService;
import kr.or.ddit.vo.BookVO;
import lombok.extern.slf4j.Slf4j;

/*
Controller 어노테이션
스프링 프레임워크에게 "이 클래스는 웹 브라우저의 요청(request)를
받아들이는 컨트롤러야" 라고 알려주는 것임.
스프링은 servlet-context.xml의 context:component-scan의 설정에 의해
이 클래스를 자바빈 객체로 등록(메모리에 바인딩).
*/
@Slf4j
@Controller
public class BookController {
	//서비스를 호출하기 위해 의존성 주입(Dependency Injection-DI)
	//IoC(Inversion of Control) - 제어의 역전.(개발자가 객체생성하지 않고 스프링이 객체를 미리 생성해놓은 것을 개발자가 요청)
	@Autowired
	BookService bookService;
	
	//요청URI : /create
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value="/create",method=RequestMethod.GET)
	public ModelAndView create() {
		/*
		 ModelAndView
		 1) Model : Controller가 반환할 데이터(String, int, List, Map, VO..)를 담당
		 2) View : 화면을 담당(뷰(View : JSP)의 경로)
		 */
		ModelAndView mav = new ModelAndView();
//		<beans:property name="prefix" value="/WEB-INF/views/" />
//		<beans:property name="suffix" value=".jsp" />
		// prefix(접두어) : /WEB-INF/views/
		// suffix(접미어) : .jsp
		// /WEB-INF/views/ + book/create + .jsp
		//forwarding
		mav.setViewName("book/create");
		
		return mav;
	}
	
	/* 
	요청URI : /create
	요청파라미터 : {title=총알탄 개똥이, category=소설, price=10000}
	요청방식 : post
	*/
	@RequestMapping(value="/create",method=RequestMethod.POST)
	public ModelAndView createPost(BookVO bookVO, ModelAndView mav) {
		//bookVO : BookVO [bookId=0, title=총알탄 개똥이, category=소설, 
		//				   price=10000, insertDate=null]
		log.info("bookVO : " + bookVO);
		
		//insert/update/delete 시 return 타입은 int
		//BOOK 테이블에 도서를 등록
		int result = this.bookService.createPost(bookVO);
		log.info("createPost->result : " + result);
		
		log.info("insert후->bookVO : " + bookVO);
		
//		ModelAndView mav = new ModelAndView();
		//redirect : URI를 재요청
		mav.setViewName("redirect:/create");
		
		return mav;
	}
	
	//책 상세보기
	//요청된 URI 주소 : http://localhost/detail?bookId=3
	//요청파라미터, 쿼리 스트링(Query String) : bookId=3 
	//요청방식 : get
	//매개변수 : bookVO => {"bookId":"3","title":"","category":"","price":0,"insertDate":""}
	@RequestMapping(value="/detail",method=RequestMethod.GET)
	public ModelAndView detail(BookVO bookVO, ModelAndView mav) {
		log.info("detail->bookVO : " + bookVO);
		
		bookVO = this.bookService.detail(bookVO);
		log.info("detail->bookVO(후) : " + bookVO);
		//model : 데이터를 jsp로 넘겨줌
		//session.setAttribute(속성명, 데이터)
		mav.addObject("bookVO", bookVO);
		//forwarding => 데이터를 jsp로 넘겨줌 / but, redirect => URL재요청. 데이터를 jsp로 못넘겨줌
		//view : jsp의 경로
		///WEB-INF/views/ + ... + .jsp
		mav.setViewName("book/detail");
		
		return mav;
	}
	
	/*
	요청URI : /updatePost
	요청파라미터 : {bookId=3,title=김정민과 박선혜의 콜라보, category=음악, price=1200000}
	요청방식 : post
	*/
	@RequestMapping(value="/updatePost",method=RequestMethod.POST)
	public ModelAndView updatePost(BookVO bookVO,
			ModelAndView mav) {
		log.info("updatePost=>bookVO : " + bookVO);
		
		int result = this.bookService.updatePost(bookVO);
		log.info("upldatePost->result : " + result);
		
		//상세화면으로 redirect. URI을 재요청
		mav.setViewName("redirect:/detail?bookId="+bookVO.getBookId());
		
		return mav;
	}
	
	/*
	요청URI : /deletePost
	요청파라미터 : {bookId=3,title=김정민과 박선혜의 콜라보, category=음악, price=1200000}
	요청방식 : post
	*/
	@RequestMapping(value="/deletePost",method=RequestMethod.POST)
	public ModelAndView deletePost(BookVO bookVO,
			ModelAndView mav) {
		log.info("deletePost->bookVO : " + bookVO);
		//책 삭제
		int result = this.bookService.deletePost(bookVO);
		log.info("deletePost->result : " + result);
		
		if(result>0) {//삭제 성공
			//목록으로 요청 이동(상세페이지가 없으므로)
			mav.setViewName("redirect:/list");
		}else {//삭제 실패
			//상세페이지로 이동
			mav.setViewName("redirect:/detail?bookId="+bookVO.getBookId());
		}
		
		return mav;
	}
	
	/*
	 요청URI : /list?keyword=알탄 or /list or /list?keyword=
	 요청파라미터 : keyword=알탄
	 요청방식 : get
	 
	 required=false : 선택사항. 파라미터가 없어도 무관
	 */
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView mav,
			@RequestParam(value="keyword",required=false,defaultValue="") String keyword) {
		log.info("list->keyword : " + keyword);
		
		List<BookVO> bookVOList = this.bookService.list(keyword);
		log.info("bookVOList : " + bookVOList);
		//select 결과 목록을 데이터로 넣어줌. jsp로 리턴될것임
		mav.addObject("bookVOList", bookVOList);
		//forwarding
		mav.setViewName("book/list");
		
		return mav;
	}
	/*
	 요청URI : /listAjax
	 요청파라미터(json) : (/list)null 또는 (검색버튼){"keyword":""} 또는 (검색버튼){"keyword":"알탄"}
	 요청방식 : post
	 
	 required=false : 선택사항. 파라미터가 없어도 무관
	 */
	@ResponseBody
	@RequestMapping(value="/listAjax",method=RequestMethod.POST)
	public List<BookVO> listAjax(@RequestBody(required=false) Map<String,Object> map) {
		log.info("map : " + map);
		
		String keyword = "";
		
		if(map!=null) {
			keyword = (String)map.get("keyword");
		}
		
		log.info("list->keyword : " + keyword);
		
		List<BookVO> bookVOList = this.bookService.list(keyword);
		log.info("bookVOList : " + bookVOList);
		
		return bookVOList;
	}
}








