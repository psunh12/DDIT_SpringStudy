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
	//DB연동
	$("#btnSubmit05").on("click",function(){
		$("#studVO").attr("action","/springform/register05");
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
<form:form modelAttribute="studVO"  action="/springform/register" method="post">
	<table>
		<tr>
			<th>학생ID</th>
			<!-- form:input와 input type="text"는 같다  -->
			<td><form:hidden path="enabled"  />
				<form:input path="studId"  />
			</td>
		</tr>
		<tr>
			<th>학생 명</th>
			<!-- name 및 id 속성이 path속성으로 합쳐짐 -->
			<td>
				<form:input path="studNm" />
				<font color="red">
					<form:errors path="studNm" />
				</font>
			</td>
		</tr>
		<tr>
			<th>학생 비밀번호</th>
			<td>
				<form:password path="studPw" />
				<font color="red">
					<form:errors path="studPw" />
				</font>
			</td>
		</tr>
		<tr>
			<th>학생 상세정보</th>
			<td>
				<form:textarea path="studDet" rows="6" cols="30" />
			</td>
		</tr>
		<tr>
			<th>학생 취미</th>
			<td>
				<!-- input type="checkbox" => form:checkbox -->
				<form:checkbox path="hobby" value="sports" />
				<label for="hobby1">스포츠</label><br />
				<form:checkbox path="hobby" value="music" />
				<label for="hobby2">음악감상</label><br />
				<form:checkbox path="hobby" value="movie" />
				<label for="hobby3">영화감상</label>
			</td>
		</tr>
		<tr>
			<th>학생 성별</th>
			<td>
				<!-- input type="radio" => form:radiobutton -->
				<form:radiobutton path="gender" value="female" label="여성" /><br />
				<form:radiobutton path="gender" value="male"   label="남성" /><br />
				<form:radiobutton path="gender" value="other"  label="기타" />
			</td>
		</tr>
		<tr>
			<th>학생 국적</th>
			<td>
<!-- 				<select id="nationality" name="nationality"> -->
<!-- 					<option value="">선택하세요</option> -->
<!-- 					<option value="korea" selected>한국</option> -->
<!-- 					<option value="germany">독일</option> -->
<!-- 					<option value="usa">미국</option> -->
<!-- 				</select> -->
				<form:select path="nationality" items="${nationalityMap}" />
			</td>
		</tr>
	</table>
	<!-- form:button은 기본 type이 submit -->
	<form:button name="register">등록</form:button>
	<button type="submit">등록</button>
	<input type="submit" value="등록" />
	<button type="button" id="btnSubmit02">register02로전송</button>
	<button type="button" id="btnSubmit03">register03로전송</button>
	<button type="button" id="btnSubmit05">register05로전송</button>
</form:form>