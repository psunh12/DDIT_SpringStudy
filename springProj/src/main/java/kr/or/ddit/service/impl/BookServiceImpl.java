package kr.or.ddit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.ddit.dao.BookDao;
import kr.or.ddit.service.BookService;
import kr.or.ddit.vo.BookVO;

//서비스 클래스 : 비즈니스 로직
//스프링 MVC 구조에서 Controller와 DAO를 연결하는 역할
/*
스프링 프레임워크는 개발자가 직접 클래스를 생성하는 것을 지양하고,
* 프링은 인터페이스를 좋아해. 자꾸자꾸 좋아지면 Impl은 어떡해
인터페이스를 통해 접근하는 것을 권장하고 있기 때문.(확장성)
그래서 서비스 레이어는 인터페이스(BookService)와 클래스(BookServiceImpl)를 함께 사용함

Impl : implement의 약자
*/
//"프링아 이 클래스 서비스 클래야"라고 알려주자. 프링이가 자바빈으로 등록해줌.
@Service
public class BookServiceImpl implements BookService {

	//데이터베이스 접근을 위해 BookDao 인스턴스를 주입받자
	//DI(Dependency Injection):의존성 주입
	//IoC(Inversion of Control):제어의 역전
	@Autowired
	BookDao bookDao;
	
	@Override
	public int createPost(BookVO bookVO) {
		return this.bookDao.createPost(bookVO);
	}

	@Override
	public BookVO detail(BookVO bookVO) {
		return this.bookDao.detail(bookVO);
	}

	//책 수정
	@Override
	public int updatePost(BookVO bookVO) {
		return this.bookDao.updatePost(bookVO);
	}

	//책 삭제
	@Override
	public int deletePost(BookVO bookVO) {
		return this.bookDao.deletePost(bookVO);
	}

	//책 목록
	@Override
	public List<BookVO> list(String keyword) {
		return this.bookDao.list(keyword);
	}
	
}




