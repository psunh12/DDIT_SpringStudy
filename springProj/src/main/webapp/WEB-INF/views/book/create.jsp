<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<title>책 등록하기</title>
</head>
<body>
<h1>책 등록</h1>
<!-- 
요청URI : /crate
요청파라미터 : {title=개똥이의 모험, category=소설, price=12000}
요청방식 : post
-->
<form action="/create" method="post">
	<!-- 폼데이터 -->
	<p>제목 : <input type="text" name="title" required placeholder="제목" /></p>
	<p>카테고리 : <input type="text" name="category" required placeholder="카테고리" /></p>
	<p>가격 : <input type="number" name="price" required placeholder="가격" /></p>
	<p>
		<input type="submit" value="저장" />
		<input type="button" value="목록" onclick="javascript:location.href='/list'" />
	</p>
	<sec:csrfInput/>
</form>
</body>
</html>