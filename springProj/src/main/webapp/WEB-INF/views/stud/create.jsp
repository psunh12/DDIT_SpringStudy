<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>  
<script src="https://ssl.daumcdn.net/dmaps/map_js_init/postcode.v2.js"></script>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<script type="text/javascript" src="/resources/ckeditor5/ckeditor.js"></script>
<link type="text/css" rel="stylesheet" 
	href="/resources/ckeditor5/sample/css/sample.css" media="screen" />
<script type="text/javascript">
$(function(){
	console.log("개똥이");
	
	$(".ck-blurred").keydown(function(){
		console.log("str : " + window.editor.getData());
		
		$("#studDet").val(window.editor.getData());
	});
	
	$(".ck-blurred").on("focusout",function(){
		$("#studDet").val(window.editor.getData());
	});
	
	//다음 우편번호 검색
	$("#btnPostNum").on("click",function(){
		console.log("우편번호 검색!");
		new daum.Postcode({
			//다음 창에서 검색이 완료되면
			oncomplete:function(data){
				$("#postNum").val(data.zonecode);//우편번호
				$("#studAddress").val(data.address);//주소
				$("#studAddress2").val(data.buildingName);//상세주소
			}
		}).open();
	});
});
</script>
<div class="card card-primary">
	<div class="card-header">
		<h3 class="card-title">학생 등록</h3>
	</div>

	<!-- 
	요청URI : /stud/create?register
	요청파라미터 : {studId=a001,studNm=개똥이,studPw=java,studDet=학생상세내용
				,gender=여성,nationality=korea,postNum=12345,studAddress=대전 중구
				,studAddress2=123-33}
	파람즈 : register
	요청방식 : post
	 -->
	<form name="frm" id="frm" action="/stud/create?register" method="post">
		<div class="card-body">
			<div class="form-group">
				<label for="studId">학생 아이디</label> 
				<input type="text" name="studId" class="form-control" id="studId"
					placeholder="학생 아이디" required />
			</div>
			<div class="form-group">
				<label for="studNm">학생 명</label> 
				<input type="text" name="studNm" class="form-control" id="studNm"
					placeholder="학생 명" required />
			</div>
			<div class="form-group">
				<label for="studPw">비밀번호</label> 
				<input type="password" name="studPw" class="form-control" id="studPw"
					placeholder="비밀번호" required />
			</div>
			<div class="form-group">
				<label for="studDet">학생상세</label> 
				<div id="ckStudDet"></div>
				<textarea name="studDet" id="studDet" class="form-control" 
				rows="4" style="display:none;"></textarea>
			</div>
			<div class="form-group">
				<label for="gender">성별</label> 
				<select name="gender" id="gender" class="form-control custom-select">
					<option value="" disabled="">선택해주세요</option>
					<option value="female">여성</option>
					<option value="male">남성</option>
					<option value="etc">기타</option>
				</select>
			</div>
			<div class="form-group">
				<label for="nationality">국적</label> 
				<select name="nationality" id="nationality" class="form-control custom-select">
					<option value="" disabled="">선택해주세요</option>
					<option value="korea">한국</option>
					<option value="america">미국</option>
					<option value="germany">독일</option>
				</select>
			</div>
			<div class="form-group">
				<label for="postNum">우편번호</label> 
				<input type="text" name="postNum" class="form-control" id="postNum"
					placeholder="우편번호" readonly required />
				<button type="button" id="btnPostNum" class="btn btn-default btn-sm">
					우편번호 검색</button>
			</div>
			<div class="form-group">
				<label for="studAddress">주소</label> 
				<input type="text" name="studAddress" class="form-control" id="studAddress"
					placeholder="주소" required />
			</div>
			<div class="form-group">
				<label for="studAddress2">상세주소</label> 
				<input type="text" name="studAddress2" class="form-control" id="studAddress2"
					placeholder="상세주소" />
			</div>
		</div>

		<div class="card-footer">
			<button type="submit" class="btn btn-primary">등록</button>
			<button type="reset" class="btn btn-secondary">취소</button>
		</div>
		<sec:csrfInput/>
	</form>
</div>
<script type="text/javascript">
ClassicEditor
 .create(document.querySelector('#ckStudDet'),
		{ckfinder:{uploadUrl:'/upload/uploads?${_csrf.parameterName}=${_csrf.token}'}})
 .then(editor=>{window.editor=editor;})
 .catch(err=>{console.error(err.stack);});
</script>