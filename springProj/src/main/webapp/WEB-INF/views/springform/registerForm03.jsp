<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#btnSubmit02").on("click",function(){
		$("#studVO").attr("action","/springform/register02");
		$("#studVO").submit();
	});
	$("#btnSubmit03").on("click",function(){
		$("#studVO").attr("action","/springform/register03");
		$("#studVO").submit();
	});
});
</script>
<!-- 
1. 폼 객체(model)의 속성명("studVO")과 스프링 폼 태그의 modelAttribute 속성값이 일치해야 함
model{studVO=학생객체}
 -->
<!-- 
요청URI :  /springform/register
요청파라미터 : {studId=a005,studNm=개똥이,studPw=java}
요청방식 : post
-->
<form:form modelAttribute="userVO"  action="/springform/register" method="post">
	<table>
		<tr>
			<th>학생ID</th>
			<!-- form:input와 input type="text"는 같다  -->
			<td><form:input path="studId" /></td>
		</tr>
		<tr>
			<th>학생 명</th>
			<!-- name 및 id 속성이 path속성으로 합쳐짐 -->
			<td><form:input path="studNm" /></td>
		</tr>
		<tr>
			<th>학생 비밀번호</th>
			<td><form:password path="studPw" /></td>
		</tr>
	</table>
	<!-- form:button은 기본 type이 submit -->
	<form:button name="register">등록</form:button>
	<button type="button" id="btnSubmit02">register02로전송</button>
	<button type="button" id="btnSubmit03">register03로전송</button>
</form:form>