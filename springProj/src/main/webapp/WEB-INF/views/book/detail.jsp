<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<title>책 상세보기</title>
</head>
<body>
<h1>책 상세</h1>
<!-- 
JSTL(JSP Standard Tag Library) : 개발자들이 자주 사용하는 패턴을 모아놓은 집합
=> BookController에서 보내준 데이터를 뷰(jsp)에 표현하도록 도와줌

method
1) GET : 데이터를 변경하지 않을 때. 목록/상세보기
2) POST : 데이터를 변경할 때. 입력/수정/삭제

업데이트 쎄대여
UPDATE BOOK
SET    TITLE='개똥이의 모험', CATEGORY='소설', PRICE=12000, CONTENT='재미있다냥'
WHERE  BOOK_ID = 1

등푸른생선 주세여
DELETE FROM BOOK
WHERE  BOOK_ID = 1

WHERE
1) 단일행 : =, <, >, <=, >=, !=, <>
2) 다중행 : IN(교집합), ANY(OR), ALL(AND), EXISTS(교집합)
 -->
<!-- mav.addObject("bookVO", bookVO); -->
<p>${bookVO}</p>
<!-- 
요청URI : /updatePost
요청파라미터 : {bookId=3,title=김정민과 박선혜의 콜라보, category=음악, price=1200000}
요청방식 : post
-->
<form id="frm" name="frm" action="/updatePost" method="post">
	<!-- 폼데이터 -->
	<input type="text" name="bookId" value="${bookVO.bookId}" readonly />
	<p>제목 : <input type="text" class="formdata" name="title" value="${bookVO.title}" readonly required placeholder="제목" /></p>
	<p>카테고리 : <input type="text" class="formdata" name="category" value="${bookVO.category}" readonly required placeholder="카테고리" /></p>
	<p>가격 : <input type="number" class="formdata" name="price" value="${bookVO.price}" readonly required placeholder="가격" /></p>
	<!-- ///////일반모드 시작/////// -->
	<p id="p1">
		<input type="button" id="edit" value="수정" />
		<input type="button" id="delete" value="삭제" />
		<input type="button" id="list" value="목록" />
	</p>
	<!-- ///////일반모드 끝/////// -->
	<!-- ///////수정모드 시작/////// -->
	<p id="p2" style="display:none;">
		<input type="submit" id="confirm" value="확인" />
		<input type="button" id="cancel" value="취소" />
	</p>
	<!-- ///////수정모드 끝/////// -->
	<sec:csrfInput/>
</form>
<script type="text/javascript">
//document 내의 모든 요소들이 로딩이 완료된 후에 실행
$(function(){
	console.log("개똥이");
	//수정 버튼 클릭 -> 수정모드로 전환
	$("#edit").on("click",function(){
		$("#p1").css("display","none");
		$("#p2").css("display","block");
		$(".formdata").removeAttr("readonly");
		$("#frm").attr("action","/updatePost");
	});
	//취소 버튼 클릭
	$("#cancel").on("click",function(){
		//주소표시줄 : /detail?bookId=3
		//param : bookId=3
		//param.bookId : 3
		location.href="/detail?bookId=${param.bookId}";
	});
	
	//삭제 버튼 클릭
	/*
	요청URI : /deletePost
	요청파라미터 : {bookId=3,title=김정민과 박선혜의 콜라보, category=음악, price=1200000}
	요청방식 : post
	*/
	$("#delete").on("click",function(){
		$("#frm").attr("action","/deletePost");
		
		let result = confirm("삭제하시겠습니까?");
		console.log("result : " + result);
		
		if(result > 0){//확인
			$("#frm").submit();
		}else{//취소
			alert("삭제가 취소되었습니다");
		}
	});
	//목록으로 이동
	$("#list").on("click",function(){
		location.href="/list";
	});
});
</script>
</body>
</html>






