<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<script type="text/javascript">
//document내의 모든 요소들이 로딩된 후 실행
$(function(){
	console.log("개똥이");
	
	//회원 등록
	$("#btnCreate").on("click",function(){
		location.href="/stud/create";
	});
	
	$(".edit").css("display","none");
	
	//수정/일반 모드 토글
	$("#modify").on("click",function(){
		$("#studNm").removeAttr("readonly");	
		$("#studPw").removeAttr("readonly");	
		
		$(".general").css("display","none");
		$(".edit").css("display","block");
	});
	
	$("#cancel").on("click",function(){
		$("#studNm").attr("readonly",true);	
		$("#studPw").attr("readonly",true);
		
		$(".general").css("display","block");
		$(".edit").css("display","none");
	});
	
	//수정
	$("#confirm").on("click",function(){
		let studId = $("#studId").val();
		let studNm = $("#studNm").val();
		let studPw = $("#studPw").val();
		
		let data = {
			"studId":studId,
			"studNm":studNm,
			"studPw":studPw
		};
		console.log("data : ",data);
		
		$.ajax({
			url:"/stud/updateAjax",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(data),
			type:"post",
			dataType:"text",
			beforeSend:function(xhr){
				xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
			},
			success:function(result){
				console.log("result :",result);
				
				//목록에 반영
				$("tr").each(function(){
					let tdVal = $(this).children("td").eq(1).children("a").eq(0).html();
					
					console.log("tdVal : " + tdVal + ", studId : " + studId);
					
					if(tdVal==studId){
						$(this).children("td").eq(2).html($("#studNm").val());
						$(this).children("td").eq(4).html($("#studPw").val());
					}
				});
				
				$("#cancel").click();
			}
		});
	});
	
	$("#delete").on("click",function(){
		if(!confirm("삭제하시겠습니까?")){
			alert("삭제가 취소되었습니다.");
			return;
		}
		
		let studId = $("#studId").val();
		let studNm = $("#studNm").val();
		let studPw = $("#studPw").val();
		
		let data = {
			"studId":studId,
			"studNm":studNm,
			"studPw":studPw
		};
		console.log("data : ",data);
		
		$.ajax({
			url:"/stud/deleteAjax",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(data),
			type:"post",
			dataType:"text",
			beforeSend:function(xhr){
				xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
			},
			success:function(result){
				console.log("result :",result);
				
				//목록에 반영
				$("tr").each(function(){
					let tdVal = $(this).children("td").eq(1).children("a").eq(0).html();
					
					console.log("tdVal : " + tdVal + ", studId : " + studId);
					
					if(tdVal==studId){
						$(this).remove();
					}
					
					$("#modal-detail").modal("hide");
				});
			}
		});
	});
	
	//상세보기 modal
	$(document).on("click",".aDetail",function(){
		let studId = $(this).data("studId");
		console.log("studId : " + studId);
		
		let data = {
			"studId":studId	
		};
		console.log("data : ",data);
		
		$.ajax({
			url:"/stud/detailAjax",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(data),
			type:"post",
			dataType:"json",
			beforeSend:function(xhr){
				xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
			},
			success:function(result){
				console.log("result :",result);
				
				$("#studId").val(result.studId);
				$("#studNm").val(result.studNm);
				$("#studPw").val(result.studPw);
			}
		});
	});
	
	//검색
	$("#btnSearch").on("click",function(){
		let keyword = $.trim($("input[name='keyword']").val());//알탄
		
		console.log("keyword : " + keyword);
		
		///stud/list?currentPage=1 or /stud/list
		//검색 시 페이지번호는 1로 초기화
		let currentPage = "1";
		
		//json 오브젝트
		let data = {
			"keyword":keyword,
			"currentPage":currentPage
		};
		
		console.log("data : ", data);
		
		//아작났어유..(피)씨다타써
		//contentType : 보내는 타입
		//dataType : 응답 타입
		$.ajax({
			url:"/stud/listAjax",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(data),
			type:"post",
			dataType:"json",
			beforeSend:function(xhr){
				xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
			},
			success:function(result){
				//result : List<StudVO>
//	 			console.log("result",result);
				//console.log("result : " + JSON.stringify(result));
				//result : List<StudVO>
				
				let str = "";
				let studPwStr = "";
				
				//목록을 초기화
				$("#studBody").html("");
				
				$.each(result.content,function(idx,studVO){
					console.log("studVO["+idx+"] : ", studVO);
					//{rnum: 1, studId: 'a001', studNm: '김은대', studPw: 'asdfasdf', enabled: '1'}
					str += "<tr>";
					str += "<td>"+studVO.rnum+"</td>";
					str += "<td>"+studVO.studId+"</td>";
					str += "<td>"+studVO.studNm+"</td>";
					if(studVO.enabled=="1"){
						studPwStr = "회원";
					}else{
						studPwStr = "탈퇴회원";
					}
					str += "<td><span class='tag tag-success'>"+studPwStr+"</span></td>";
					str += "<td>"+studVO.studPw+"</td>";
					str += "</tr>";
				});
				//요소.append : 누적, 요소.html : 새로고침, 요소.innerHTML : JavaScript에서 새로고침
				$("#studBody").append(str);
				//페이징 처리
				$("#divPaging").html(result.pagingArea);
			}
		});
	});
	
	/*
	JSON은 자바스크립트 객체 표기법으로 작성된 텍스트임
	client -> server (string 형식으로 전달)
	server -> client (string 형식으로 전달)
	object -> string 으로 변환하는 방식을 serialize 라고 한다!
	string -> object로 다시 변환하는 방식을 deserialize라고 함!
	*/
	//아작났어유..(피)씨다타써
	//contentType : 보내는 타입
	//dataType : 응답 타입
	//{"keyword":"신용","currentPage":3} 
	
	// /stud/list?currentPage=3 or /stud/list
	let currentPage = "${param.currentPage}";
	
	if(currentPage==""){
		currentPage = "1";
	}
	
	//json 오브젝트
	///stud/list?currentPage=2&keyword=개똥이5
	//data를 구성 시 currentPage와 조건들은 꼭 고려하기!
	let data = {
		"keyword":"${param.keyword}",
		"currentPage":currentPage
	};
	
	console.log("data : ", data);
	
	$.ajax({
		url:"/stud/listAjax",
		type:"post",
		data:JSON.stringify(data),
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		beforeSend:function(xhr){
			xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
		},
		success:function(result){
			//result : ArticlePage<StudVO>
// 			console.log("result",result);
			//console.log("result : " + JSON.stringify(result));
			//result.content : List<StudVO>
			
			let str = "";
			let studPwStr = "";
			
			$.each(result.content,function(idx,studVO){
				console.log("studVO["+idx+"] : ", studVO);
				//{rnum: 1, studId: 'a001', studNm: '김은대', studPw: 'asdfasdf', enabled: '1'}
				str += "<tr>";
				str += "<td>"+studVO.rnum+"</td>";
				str += "<td><a href='#modal-detail' data-toggle='modal' class='aDetail' data-stud-id='"+studVO.studId+"'>"+studVO.studId+"</a></td>";
				str += "<td>"+studVO.studNm+"</td>";
				if(studVO.enabled=="1"){
					studPwStr = "회원";
				}else{
					studPwStr = "탈퇴회원";
				}
				str += "<td><span class='tag tag-success'>"+studPwStr+"</span></td>";
				str += "<td>"+studVO.studPw+"</td>";
				str += "</tr>";
			});
			//요소.append : 누적, 요소.html : 새로고침, 요소.innerHTML : JavaScript에서 새로고침
			$("#studBody").append(str);
			//페이징 처리
			$("#divPaging").html(result.pagingArea);
		}
	});
});
</script>
<div class="row">
	<div class="col-12">
		<div class="card">
			<div class="card-header">
				<h3 class="card-title">학생 목록</h3>
				<div class="card-tools">
					<!-- ///검색 시작 /// -->
					<div class="input-group input-group-sm" style="width: 150px;">
						<input type="text" name="keyword" id="keyword"
							class="form-control float-right" placeholder="Search" />
						<div class="input-group-append">
							<button type="button" id="btnSearch" class="btn btn-default">
								<i class="fas fa-search"></i>
							</button>
						</div>
					</div>
					<!-- ///검색 시작 /// -->
				</div>
			</div>

			<div class="card-body table-responsive p-0">
				<table class="table table-hover text-nowrap">
					<thead>
						<tr>
							<th>순번</th>
							<th>학생 아이디</th>
							<th>학생 명</th>
							<th>회원여부</th>
							<th>비밀번호</th>
						</tr>
					</thead>
					<tbody id="studBody">
					</tbody>
				</table>
			</div>
		</div>
		<div class="row justify-content-center" id="divPaging">
			
		</div>
	</div>
	<div class="col-12 justify-content-right">
<!-- 		<button type="button" id="btnCreate" class="btn btn-primary" style="float:right;">회원등록</button> -->
		<a href="/stud/create?register" class="btn btn-primary" style="float:right;">회원등록</a>
	</div>
</div>
<!-- 학생상세 모달 -->
<div class="modal fade" id="modal-detail">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">학생상세</h4>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p>
			<div class="card-body">
				<div class="form-group">
					<label for="studId">학생 아이디</label> 
					<input type="text" name="studId" class="form-control" id="studId"
						placeholder="학생 아이디" required readonly />
				</div>
				<div class="form-group">
					<label for="studNm">학생 명</label> 
					<input type="text" name="studNm" class="form-control" id="studNm"
						placeholder="학생 명" required readonly />
				</div>
				<div class="form-group">
					<label for="studPw">비밀번호</label> 
					<input type="password" name="studPw" class="form-control" id="studPw"
						placeholder="비밀번호" required readonly />
				</div>
			</div>
		</p>
      </div>
      <div class="modal-footer justify-content-between">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <!-- 일반모드 -->
        <button type="button" id="modify" class="btn btn-primary general">수정</button>
        <button type="button" id="delete" class="btn btn-primary general">삭제</button>
        <!-- 수정모드 -->
        <button type="button" id="confirm" class="btn btn-primary edit">확인</button>
        <button type="button" id="cancel" class="btn btn-primary edit">취소</button>
      </div>
    </div>
    <!-- /.modal-content -->
  </div>
  <!-- /.modal-dialog -->
</div>
<!-- /.modal -->