<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<script type="text/javascript">
$(function(){
	//취미 + / -
	$(".divLikesAdd").on("click",function(){
		console.log("취미 추가");
		
		let len = $(".clsLikesTitle").length;
		console.log("len : " + len);
		
		let str = "<div>";
			str+= "<div style='width:50%;float:left;'>";
			str+= "<label for='likesTitle"+(len+1)+"'>취미명</label>";
			str+= "<input type='text' class='form-control clsLikesTitle'";
			str+= " name='likesVOList["+len+"].likesTitle' id='likesTitle"+(len+1)+"'";
			str+= " placeholder='취미명' />";
			str+= "</div>";
			str+= "<div style='width:50%;float:left;'>";
			str+= "<label for='likesCont"+(len+1)+"'>취미상세</label>";
			str+= "<input type='text' class='form-control clsLikesCont'";
			str+= " name='likesVOList["+len+"].likesCont' id='likesCont"+(len+1)+"'";
			str+= " placeholder='취미상세' />";
			str+= "</div>";
			str+= "</div>";
			
			$("#divLikes").append(str);
	});
	
	$(".divLikesMinus").on("click",function(){
		console.log("취미 삭제");
		
		let len = $(".clsLikesTitle").length;
		
		if(len<2){
			alert("취미 영역은 최소 한 개가 있어야 합니다.");	
		}else{
			/*
			<div id="divLikes">
				<div></div>
				<div></div>->삭제됨
			</div>
			*/
			$("#divLikes").children().last().remove();
		}
	});
	
	//카드 + / -
	$(".divCardAdd").on("click",function(){
		console.log("카드 추가");
		
		let len = $(".clsNo").length;
		console.log("len : " + len);
		
		let str = "<div>";
			str+= "<div style='width:50%;float:left;'>";
			str+= "<label for='cardNo"+(len+1)+"'>카드번호</label>"; 
			str+= "<input type='text' class='form-control clsNo'";
			str+= "name='cardVOList["+len+"].no' id='cardNo"+(len+1)+"'";
			str+= "placeholder='카드번호' />";
			str+= "</div>";
			str+= "<div style='width:50%;float:left;'>";
			str+= "<label for='cardValidMonth"+(len+1)+"'>유효연월</label>"; 
			str+= "<input type='text' class='form-control clsValidMonth'";
			str+= "name='cardVOList["+len+"].validMonth' id='cardValidMonth"+(len+1)+"'";
			str+= "placeholder='유효연월' />";
			str+= "</div>";
			str+= "</div>";
			
			$("#divCard").append(str);
	});
	
	$(".divCardMinus").on("click",function(){
		console.log("카드 삭제");
		
		let len = $(".clsNo").length;
		
		if(len<2){
			alert("카드 영역은 최소 한 개가 있어야 합니다.");	
		}else{
			/*
			<div id="divLikes">
				<div></div>
				<div></div>->삭제됨
			</div>
			*/
			$("#divCard").children().last().remove();
		}
	});
	
	$("#btnAjaxSubmit").on("click",function(){
		console.log("개똥이");
		
		let emailAdres = $("#emailAdres").val();
		let password = $("#password").val();
		//<input type="file"..
		let inputFile = $("#uploadFile");
		//1.jpg,2.jpg,3.jpg
		let files = inputFile[0].files;
		
		let formData = new FormData();
		formData.append("emailAdres",emailAdres);
		formData.append("password",password);
		
		for(let i=0;i<files.length;i++){
			formData.append("uploadFile",files[i]);
		}
		
		//카드
		$(".clsNo").each(function(idx,no){
			formData.append("cardVOList["+idx+"].no",$(this).val());
		});
		
		$(".clsValidMonth").each(function(idx,validMonth){
			formData.append("cardVOList["+idx+"].validMonth",$(this).val());
		});
		
		//취미
		$(".clsLikesTitle").each(function(idx,likesTitle){
			formData.append("likesVOList["+idx+"].likesTitle",$(this).val());
		})
		
		$(".clsLikesCont").each(function(idx,likesCont){
			formData.append("likesVOList["+idx+"].likesCont",$(this).val());
		});
		
		$.ajax({
			url:"/quick/createAjaxPost",
			processData:false,
			contentType:false,
			data:formData,
			type:"post",
			dataType:"text",
			beforeSend:function(xhr){
				xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
			},
			success:function(result){
				console.log("result : ", result);
			}
		});
	});
});
</script>
<div class="card card-primary">
	<div class="card-header">
		<h3 class="card-title">Quick Example</h3>
	</div>
	<!-- 
	요청URI : /quick/createPost
	요청파라미터 : {emailAdres=test@test.com,password=java,uploadFile=파일객체}
	요청방식 : post
	-->
	<form action="/quick/createPost?${_csrf.parameterName}=${_csrf.token}" method="post" 
		enctype="multipart/form-data">
		<div class="card-body">
			<div class="form-group">
				<label for="exampleInputEmail1">Email address</label> 
				<input type="email" class="form-control"
					name="emailAdres" id="emailAdres"
					placeholder="이메일" required />
			</div>
			<div class="form-group">
				<label for="exampleInputPassword1">Password</label> 
				<input type="password" class="form-control" 
					name="password" id="password"
					placeholder="비밀번호" required />
			</div>
			<div class="form-group">
				<label for="exampleInputFile">File input</label>
				<div class="input-group">
					<div class="custom-file">
						<input type="file" class="custom-file-input" 
						name="uploadFile" id="uploadFile" multiple />
						<label class="custom-file-label" for="uploadFile">
						Choose file</label>
					</div>
				</div>
			</div>
			<hr />
			<div class="form-group" id="divCard">
				<div>
					<div style="width:50%;float:left;">
						<label for="exampleInputEmail1">카드번호</label> 
						<input type="text" class="form-control clsNo"
							name="cardVOList[0].no" id="emailAdres"
							placeholder="카드번호" />
					</div>
					<div style="width:50%;float:left;">
						<label for="exampleInputEmail1">유효연월</label> 
						<input type="text" class="form-control clsValidMonth"
							name="cardVOList[0].validMonth" id="emailAdres"
							placeholder="유효연월" />
					</div>
				</div>
				<div>
					<div style="width:50%;float:left;">
						<label for="exampleInputEmail1">카드번호</label> 
						<input type="text" class="form-control clsNo"
							name="cardVOList[1].no" id="emailAdres"
							placeholder="카드번호" />
					</div>
					<div style="width:50%;float:left;">
						<label for="exampleInputEmail1">유효연월</label> 
						<input type="text" class="form-control clsValidMonth"
							name="cardVOList[1].validMonth" id="emailAdres"
							placeholder="유효연월" />
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-lg-6">
					<div class="btn-group w-100 divCardAdd">
						<span class="btn btn-success col fileinput-button dz-clickable">
							<i class="fas fa-plus"></i>
						</span>
					</div>
				</div>
				<div class="col-lg-6">
					<div class="btn-group w-100 divCardMinus">
						<span class="btn btn-success col fileinput-button dz-clickable">
							<i class="fas fa-minus"></i>
						</span>
					</div>
				</div>
			</div>
			<hr />
			<!-- ///// 취미 시작 /////////// -->
			<div class="form-group" id="divLikes">
				<div>
					<div style="width:50%;float:left;">
						<label for="likesTitle1">취미명</label>					
						<input type="text" class="form-control clsLikesTitle"
							name="likesVOList[0].likesTitle" id="likesTitle1"
							placeholder="취미명" />
					</div>
					<div style="width:50%;float:left;">
						<label for="likesCont1">취미상세</label>
						<input type="text" class="form-control clsLikesCont"
							name="likesVOList[0].likesCont" id="likesCont1"
							placeholder="취미상세" />
					</div>
				</div>
				<div>
					<div style="width:50%;float:left;">
						<label for="likesTitle2">취미명</label> 
						<input type="text" class="form-control clsLikesTitle"
							name="likesVOList[1].likesTitle" id="likesTitle2"
							placeholder="취미명" />
					</div>
					<div style="width:50%;float:left;">
						<label for="likesCont2">취미상세</label>
						<input type="text" class="form-control clsLikesCont"
							name="likesVOList[1].likesCont" id="likesCont2"
							placeholder="취미상세" />
					</div>
				</div>
			</div>
			<!-- ///// 취미 끝 /////////// -->
			<div class="row">
				<div class="col-lg-6">
					<div class="btn-group w-100 divLikesAdd">
						<span class="btn btn-success col fileinput-button dz-clickable">
							<i class="fas fa-plus"></i>
						</span>
					</div>
				</div>
				<div class="col-lg-6">
					<div class="btn-group w-100 divLikesMinus">
						<span class="btn btn-success col fileinput-button dz-clickable">
							<i class="fas fa-minus"></i>
						</span>
					</div>
				</div>
			</div>
			<hr />
		</div>
		
		<div class="card-footer">
			<button type="button" id="btnAjaxSubmit" class="btn btn-primary">등록</button>
		</div>
		<sec:csrfInput />
	</form>
</div>