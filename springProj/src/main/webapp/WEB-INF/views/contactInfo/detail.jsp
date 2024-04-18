<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<script type="text/javascript" src="/resources/ckeditor5/ckeditor.js"></script>
<link type="text/css" rel="stylesheet" 
	href="/resources/ckeditor5/sample/css/sample.css" media="screen" />
<script type="text/javascript">
$(function(){
	$(".ck-blurred").keydown(function(){
		console.log("str : " + window.editor.getData());
		
		$("#ciMesg").val(window.editor.getData());
	});
	
	$(".ck-blurred").on("focusout",function(){
		$("#ciMesg").val(window.editor.getData());
	});
	
	//자동 입력
	$("#btnAuto").on("click",function(){
		$("#ciName").val("개똥이");
		$("#ciMail").val("test@test.com");
		$("#ciSubj").val("취업상담");
		$("#ciMesg").val("취업절차에 대해 문의하고자 합니다.");
		$("#ciRegDt").val("2024-02-17");
	});
	
	//이미지 미리보기 시작////////////////
	let sel_file = [];
	$("#uploadFile").on("change",handleImgFileSelect);
	//e : onchange 이벤트 객체
	function handleImgFileSelect(e){
		//이벤트가 발생 된 타겟 안에 들어있는 이미지 파일들을 가져와보자
		let files = e.target.files;
		//이미지가 여러개가 있을 수 있으므로 이미지들을 각각 분리해서 배열로 만듦
		let fileArr = Array.prototype.slice.call(files);
		//파일 타입의 배열 반복. f : 배열 안에 들어있는 각각의 이미지 파일 객체
		/*
		let arr = ["피자","떡볶이","탕수육"];
		//*******
		arr.forEach(function(str){
			console.log("str : " + str);
		});
		
		$.each(arr,function(idx,str){
			console.log("str[" + idx + "] : " + str);
		});
		*/
		fileArr.forEach(function(f){
			//이미지 파일이 아닌 경우 이미지 미리보기 실패 처리(MIME타입)
			if(!f.type.match("image.*")){
				alert("이미지 확장자만 가능합니다.");
				//함수 종료
				return;
			}
			//이미지 객체를 전역 배열타입 변수에 넣자
			sel_file.push(f);
			//이미지 객체를 읽을 자바스크립트의 reader 객체 생성
			let reader = new FileReader();
			
			$(".clsCiImgUrl").html("");
			
			//e : reader가 이미지 객체를 읽는 이벤트
			reader.onload = function(e){
				//e.target : f(이미지 객체)
				//e.target.result : reader가 이미지를 다 읽은 결과
				$(".clsCiImgUrl").css({"background-image":"url("+e.target.result+")","background-position":"center","background-size":"cover"});
// 				let img_html = "<img src=\"" + e.target.result + "\" style='width:100%;' />";
				//p 사이에 이미지가 렌더링되어 화면에 보임
				//객체.append : 누적, .html : 새로고침, .innerHTML : J/S
// 				$("#body-content").html(img_html);
			}
			//f : 이미지 파일 객체를 읽은 후 다음 이미지 파일(f)을 위해 초기화 함
			reader.readAsDataURL(f);
		});//end forEach
	}
	//이미지 미리보기 끝////////////////
});
</script>
<script type="text/javascript">
//핸들러함수
function fn_chk(){
	if(jQuery.trim($("#ciRegDt").val())==""){
// 		alert("방문자 예정일을 입력해주세요");
		$("#codeCiRegDt").html("방문자 예정일을 입력해주세요");
		return false;//submit이 안됨
	}
	
	return true;//submit됨
}
</script> 
<!-- 
요청URI : /contactInfo/createPost
요청파라미터 : {ciName=개똥이,ciMail=test@test.com,ciSubj=채용상담,ciImgUrl=파일객체,
		    ciMesg=채용절차에 대한 상담,ciRegDt=2024/02/17}
요청방식 : post
-->  
<!-- onsubmit : form이 submit될 때 핸들러함수를 거쳐와야 함 -->
<form:form modelAttribute="contactInfoVO" 
	action="/contactInfo/createPost?${_csrf.parameterName}=${_csrf.token}" 
	method="post" enctype="multipart/form-data" onsubmit="return fn_chk()">
<section class="content">
	<div class="card">		
		<div class="card-body row">
			<div
				class="col-5 text-center d-flex align-items-center justify-content-center clsCiImgUrl"
				style="background-image:url('/resources/upload${contactInfoVO.ciImgUrl}');background-position:center;background-size:cover;">
			</div>
			<div class="col-7">
				<div class="form-group">
					<label for="ciName">방문자 명</label> 
					<form:input path="ciName" class="form-control" />
					<code><form:errors path="ciName" /> </code>
				</div>
				<div class="form-group">
					<label for="ciMail">이메일</label> 
					<form:input path="ciMail" class="form-control" />
					<code><form:errors path="ciMail" /> </code>
				</div>
				<div class="form-group">
					<label for="ciSubj">방문 주제</label> 
					<form:input path="ciSubj" class="form-control" />
					<code><form:errors path="ciSubj" /> </code>
				</div>
				<div class="form-group">
					<label for="uploadFile">방문자 사진</label>
					<div class="custom-file">
						<input type="file" name="uploadFile" id="uploadFile"
							 class="custom-file-input" />
						<label class="custom-file-label" for="uploadFile">Choose file</label>
					</div> 
				</div>
				<div class="form-group">
					<label for="ciMesg">방문 내용</label>
					<div id="ckCiMesg"></div>
					<textarea name="ciMesg" id="ciMesg" class="form-control" rows="4">${contactInfoVO.ciMesg}</textarea>
				</div>
				<div class="form-group">
					<label for="ciRegDt">방문자 예정일</label> 
					<input type="date" name="ciRegDt" id="ciRegDt"
						value='<fmt:formatDate value="${contactInfoVO.ciRegDt}" pattern="yyyy-MM-dd" />' class="form-control" />
<%-- 					<form:input path="ciRegDt" class="form-control" /> --%>
					<code id="codeCiRegDt"></code>
				</div>
				<div class="form-group">
					<button type="submit" class="btn btn-primary">방문 신청</button>
					<button type="reset" class="btn btn-warning">다시 입력</button>
					<button type="button" id="btnAuto" class="btn btn-info">자동 입력</button>
				</div>
			</div>
		</div>
	</div>
</section>
<sec:csrfInput />
</form:form>
<script type="text/javascript">
ClassicEditor
 .create(document.querySelector('#ckCiMesg'),
		{ckfinder:{uploadUrl:'/upload/uploads?${_csrf.parameterName}=${_csrf.token}'}})
 .then(editor=>{window.editor=editor;})
 .catch(err=>{console.error(err.stack);});
</script>
<script type="text/javascript">
//EL태그의 데이터를 J/S 변수에 할당
let ciMesg = '${contactInfoVO.ciMesg}';

console.log("ciMesg : " + ciMesg);

window.editor.setData(ciMesg);
</script>





