<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<div class="card card-primary">
	<div class="card-header">
		<h3 class="card-title">학생 등록</h3>
	</div>
<p>
<!-- model.addAttribute("studVO", studVO); -->
${studVO}
</p>
	<!-- 
	요청URI : /stud/create?modify
	요청파라미터 : {studId=a001,studNm=개똥이2,studPw=java2}
	파람즈 : modify
	요청방식 : post
	 -->
	<form name="frm" id="frm" action="/stud/create?register" method="post">
		<div class="card-body">
			<div class="form-group">
				<label for="studId">학생 아이디</label> 
				<input type="text" name="studId" value="${studVO.studId}" class="form-control" id="studId"
					placeholder="학생 아이디" required readonly />
			</div>
			<div class="form-group">
				<label for="studNm">학생 명</label> 
				<input type="text" name="studNm" value="${studVO.studNm}" class="form-control clsStud" id="studNm"
					placeholder="학생 명" required readonly />
			</div>
			<div class="form-group">
				<label for="studPw">비밀번호</label> 
				<input type="password" name="studPw" value="${studVO.studPw}" class="form-control clsStud" id="studPw"
					placeholder="비밀번호" required readonly />
			</div>
		</div>
		<!-- 일반모드 시작 -->
		<div id="spn1" class="card-footer">
			<button type="button" id="edit" class="btn btn-primary">수정</button>
			<button type="button" id="delete" class="btn btn-secondary">삭제</button>
		</div>
		<!-- 일반모드 끝 -->
		<!-- 수정모드 시작 -->
		<div id="spn2" class="card-footer" style="display:none;">
			<button type="submit" id="confirm" class="btn btn-primary">확인</button>
			<button type="button" id="cancel" class="btn btn-secondary">취소</button>
		</div>
		<!-- 수정모드 끝 -->
	</form>	
</div>
<script type="text/javascript">
$(function(){
	//수정버튼 클릭 : spn1:none / spn2 : block
	$("#edit").on("click",function(){
		$("#spn1").css("display","none");
		$("#spn2").css("display","block");
		
		//class가 clsStud인 요소들의 readonly 속성을 제거해보자
		$(".clsStud").removeAttr("readonly");
		
		//id가 frm인 form태그에 접근해서 action 속성의 값을
		// /stud/create?modify로 변경해보자
		$("#frm").attr("action","/stud/create?modify");
	});
	//취소버튼 클릭 : /stud/detail?studId=a001 재요청.
	$("#cancel").on("click",function(){
		//jstl변수 -> j/s 변수
		let studId = "${param.studId}";
		console.log("studId : " + studId);
		
		location.href="/stud/detail?studId="+studId;
	});
	
	//삭제버튼 클릭
	//id가 frm인 form태그에 접근해서 action 속성의 값을
	// /stud/create?delete로 변경해보자
	$("#delete").on("click",function(){
		$("#frm").attr("action","/stud/create?delete");
		
		let result = confirm("삭제하시겠습니까?");
		console.log("result : " + result);
		//true : 1, false : 0
		if(result>0){//삭제처리
			/* 
			요청URI : /stud/create?delete
			요청파라미터 : {studId=a001,studNm=개똥이2,studPw=java2}
			파람즈 : delete
			요청방식 : post
			*/
			$("#frm").submit();
		}else{//취소
			alert("취소되었습니다");
		}
	});
});
</script>





