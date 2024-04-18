<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<h3>회원게시판-등록(로그인 한 회원만 접근 가능)</h3>
<form action="/logout" method="post">
	<button type="submit" class="btn btn-primary">로그아웃</button>
	<sec:csrfInput />
</form>