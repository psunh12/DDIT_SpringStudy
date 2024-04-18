<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<script type="text/javascript">
$(function(){
	//상품 분류 코드 추가
	$("#btnNew").on("click",function(){
		 //1) 3개의 값을 가져오기
	      let lprodIdNew = $("#lprodIdNew").val();
	      let lprodGuNew = $("#lprodGuNew").val();
	      let lprodNmNew = $("#lprodNmNew").val();
	      //2) json오브젝트 생성
	      let data = {
	         "lprodId":lprodIdNew,
	         "lprodGu":lprodGuNew,   
	         "lprodNm":lprodNmNew
	      };
	      //3) 비동기처리
	      //요청URI : /lprod/insertOne
	      //요청파라미터(json) : {"lprodId":"38","lprodGu":"P103","lprodNm":"문구류"}
	      //요청방식 : post
	      //응답타입 : json
	      $.ajax({
	         url:"/lprod/insertOne",
	         contentType:"application/json;charset=utf-8",
	         data:JSON.stringify(data),
	         type:"post",
	         dataType:"text",
	         beforeSend:function(xhr){
	            xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
	         },
	         success:function(result){
	      //4) success 시 목록의 마지막 행에 추가
	            let str="";
	            str += "<tr>";
	            str += "<td>"+lprodIdNew+"</td>";
	            str += "<td><a data-lprod-id='"+lprodIdNew+"' class='aModal' data-toggle='modal' href='#modal-sm'>"+lprodGuNew+"</a></td>";
	            str += "<td>"+lprodNmNew+"</td>";
	            str += "</tr>";
	         
	         //id가 lprodTbody인 요소의 첫번째 자식요소로 추가됨
	         $("#lprodTbody").prepend(str);
	         $("#modal-insert").modal("hide");
	         }
	      });
	});
	
	//아이디 자동 생성
	$("#btnInsert").on("click",function(){
		//			session.getAttribute("total")
		let total = sessionStorage.getItem("total");
		console.log("total : " + total);
		
		//lprod테이블의 MAX(LPROD_ID) + 1
// 			contentType:"application/json;charset=utf-8",
// 			data:JSON.stringify(data),
		/*
		요청URI : /lprod/getMaxLprodId
		요청파라미터 :
		요청방식 : post
		*/
		$.ajax({
			url:"/lprod/getMaxLprodId",
			type:"post",
			dataType:"text",
			beforeSend:function(xhr){
				xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
			},
			success:function(result){
				console.log("result : ",result);
				
				lprodId = Number(result) + 1;
				
				$("#lprodIdNew").val(lprodId);
				$("#lprodIdNew").attr("readonly",true);
			}
		});
		
		//tbody에 접근해서 자식요소들(<tr>들) 중에서 마지막 자식 요소(last())의 자식 중에서 0번째(<td>38</td>)의 text값
		//let lprodId = $("#lprodTbody").children().first().children("td").eq(0).html();
		//console.log("lprodId : " + lprodId);
		//Number(숫자형문자)->숫자형으로 변환
	});
	
	//중복 체크
	/*
	blur() 메서드 - 해당 요소가 포커스 잃을 때만 발생.
	focusout() 메서드 - 해당 요소 뿐만 아니라, 그 안의 요소가 포커스 잃을 때도 발생.
	*/
	//달러("#lprodGuNew").on("focusout",function(){})
	$("#lprodGuNew").focusout(function(){
		let lprodGu = $(this).val();
		console.log("lprodGu : " + lprodGu);
		
		let aVal;
		let cnt = 0;
		$("tr").each(function(){
			//(this).children("td").eq(1) : <td>..</td> 
			aVal = $(this).children("td").eq(1).children("a").eq(0).html();
			//등록하려는 Gu값과 목록의 Gu값이 같은 값이 있으면
			if(lprodGu==aVal){
				//등록 버튼 비활성화
				$("#btnNew").attr("disabled","disabled");
				$("#lprodGuNew").attr("class","form-control is-invalid");
				//중복 됨
				cnt++;
			}
		});
		//중복 없음
		if(cnt==0){
			//등록 버튼 활성화
			$("#btnNew").removeAttr("disabled");
			$("#lprodGuNew").attr("class","form-control is-warning");
		}
	});
	
	//상품 분류 코드 삭제
	$("#delete").on("click",function(){
		$("#modal-sm").modal("hide");
		$("#modal-del").modal("show");
	});
	
	//상품 분류 코드 삭제 실행
	$("#lprodIdDel").on("click",function(){
		let lprodId = $("#lprodId").val();//삭제 대상 키본키 값
		console.log("lprodId : " + lprodId);
		
		let data = {
			"lprodId":lprodId	
		};
		console.log("data",data);
		
		/*
		요청URI : /lprod/deleteOne
		요청파라미터(json) : {"lprodId":75}
		요청방식 : post
		리턴타입 : int
		*/
		$.ajax({
			url:"/lprod/deleteOne",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(data),
			type:"post",
			dataType:"text",
			beforeSend:function(xhr){
	            xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
	         },
			success:function(result){
				console.log("result : ", result);
				
				if(result=="1"){//1행 삭제됨
					console.log("삭제처리하기");
				
					let tdVal;
					$("tr").each(function(){
						//this : 반복중에 포커스 된 바로 그 tr 요소
						//(this).children("td").eq(0) : <td>11</td>
						tdVal = $(this).children("td").eq(0).html();
						
						//삭제 대상의 Id값과 반복 중에 포커스 된 Id값이 같으면 
						//그 tr을 제거
						if(lprodId==tdVal){
							$(this).remove();
						}
					});
				}//END IF
				$("#modal-del").modal("hide");
			}
		});
	});
	
	//modal 수정 실행
	$("#confirm").on("click",function(){
		let lprodId = $("#lprodId").val();
		let lprodGu = $("#lprodGu").val();//수정대상
		let lprodNm = $("#lprodNm").val();//수정대상
		//JSON오브젝트
		let data = {
			"lprodId":lprodId,
			"lprodGu":lprodGu,	
			"lprodNm":lprodNm
		};
				
		console.log("data : ", data);
		/*
		요청URI : /lprod/updateOne
		요청파라미터(json) : {"lprodId":"36","lprodGu":"TT092","lprodNm":"트리거 테스트 2"}
		요청방식(비동기) : post
		*/
		$.ajax({
			url:"/lprod/updateOne",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(data),
			type:"post",
			dataType:"text",
			beforeSend:function(xhr){
	            xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
	         },
			success:function(result){
				console.log("result : ", result);
				
				$("#modalTitle").html(lprodGu);
				$("#modalBody").html(lprodNm);
				//일반모드
				$(".general").css("display","block");
				$(".edit").css("display","none");
				
				let tdVal;
				//부모창 목록도 변경내역 반영
				$("tr").each(function(){
					//.children : [0]td, [1]td, [2]td
					//(this).children("td").eq(0) : <td>11</td>
					tdVal = $(this).children("td").eq(0).html();//11
					//클릭한 Id값과 반복중인 Id값이 같을 때
					if(lprodId==tdVal){
						$(this).children("td").eq(1).html(
			"<a data-lprod-id='"+lprodId
				+"' class='aModal' data-toggle='modal' href='#modal-sm'>"+lprodGu
				+"</a>"
								);
						$(this).children("td").eq(2).html(lprodNm);
					}
				});
			}
		});
	});
	
	//일반모드 / 수정모드 토글
	$("#modify").on("click",function(){
		$(".general").css("display","none");
		$(".edit").css("display","block");
	});
	$("#cancel").on("click",function(){
		$(".general").css("display","block");
		$(".edit").css("display","none");
	});
	
	//모달 다루기
// 	$(".aModal").("click",function(){});	//정적 요소 이벤트 처리
	$(document).on("click",".aModal",function(){	//동적 요소 이벤트 처리
		//this : 여러개의 동일 클래스 요소 중에서 방금 클릭한 바로 그 요소
		//data-lprod-id="1"
		let lprodId = $(this).data("lprodId");
		console.log("lprodId : " + lprodId);
		
		let data = {
			"lprodId":lprodId
		};
		console.log("data : ", data);
		
		//LPROD 테이블에서 1행 검색
		/*
		요청URI : /lprod/listOne
		요청파라미터(json) : {"lprodId":"2"}
		요청방식 : post
		*/
		$.ajax({
			url:"/lprod/listOne",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(data),
			type:"post",
			dataType:"json",
			beforeSend:function(xhr){
	            xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
	         },
			success:function(result){
				//{"lprodId":"2","lprodGu":"P102","lprodNm":"전자제품"}
				console.log("result : ", result);
				
				//text 입력
				$("#lprodId").val(result.lprodId);
				$("#lprodGu").val(result.lprodGu);
				$("#lprodNm").val(result.lprodNm);
				
				//title/body입력
				$("#modalTitle").html(result.lprodGu);
				$("#modalBody").html(result.lprodNm);
			}
		});
	});
	
	//검색
	$("#btnSearch").on("click",function(){
		let keyword = $.trim($("#keyword").val());
		console.log("keyword : " + keyword);
		
		//json오브젝트
		let data = {
			"keyword":keyword,
			"currentPage":1
		};
		
		console.log("data : ", data);
		console.log("data : " + JSON.stringify(data));
		
		$.ajax({
			url:"/lprod/listAjax",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(data),
			type:"post",
			dataType:"json",
			beforeSend:function(xhr){
	            xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
	         },
			success:function(result){
				console.log("result : ", result);
				
				let str = "";
				
				$("#lprodTbody").html("");
				
				//result : ArticlePage 
				//result.content : List<LprodVO>
				$.each(result.content,function(idx, lprodVO){
					str += "<tr>";
					str += "<td>"+lprodVO.lprodId+"</td>";
					str += "<td><a data-lprod-id='"+lprodVO.lprodId+"' class='aModal' data-toggle='modal' href='#modal-sm'>"+lprodVO.lprodGu+"</a></td>";
					str += "<td>"+lprodVO.lprodNm+"</td>";
					str += "</tr>";
				});
				
				$("#lprodTbody").append(str);
				//페이징 블록 넣기
				$("#divPagingArea").html(result.pagingArea);
//	 			result.forEach(function(lprodVO){
//	 			});
			}
		});
	});
	// /lprod/list?currentPage=15
	let currentPage = "${param.currentPage}";
	console.log("currentPage : " + currentPage);
	
	if(currentPage==""){
		currentPage = 1;
	}
	
	//json오브젝트
	let data = {
		"keyword":"${param.keyword}",
		"currentPage":currentPage
	};
	
	console.log("data : ", data);
	console.log("data : " + JSON.stringify(data));
	
	//아작나써유..(피)씨다타써. HTML(HyperText Markup Language)
	//ajax : Asynchronous JavaScript And XML(eXtensible Markup Language)
	$.ajax({
		url:"/lprod/listAjax?currentPage="+currentPage,
		type:"post",
		data:JSON.stringify(data),
		contentType:"application/json;charset=utf-8",
		dataType:"json",
		beforeSend:function(xhr){
            xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
         },
		success:function(result){
			//기존 result : List<LprodVO>
         	//페이징처리 후 result : ArticlePage<LprodVO> data
			console.log("result : ", result);
			
			let str = "";
			
			$("#lprodTbody").html("");
			
			//result.content : List<LprodVO>
			//페이징처리 후 result : ArticlePage<LprodVO> data
			$.each(result.content,function(idx, lprodVO){
				str += "<tr>";
				str += "<td>"+lprodVO.lprodId+"</td>";
				str += "<td><a data-lprod-id='"+lprodVO.lprodId+"' class='aModal' data-toggle='modal' href='#modal-sm'>"+lprodVO.lprodGu+"</a></td>";
				str += "<td>"+lprodVO.lprodNm+"</td>";
				str += "</tr>";
			});
			
			$("#lprodTbody").append(str);
			
			console.log(result.pagingArea);
			
			//페이징 블록 넣기
			$("#divPagingArea").html(result.pagingArea);
			
			//result : ArticlePage객체
			//result.total : 전체행의수
			/*
			 sessionStorage
			 1) 브라우저 세션 기간 동안 만 사용할 수 있으며 탭이나 창을 닫을 때 삭제된다.
			 2) 새로고침을 해도 유지된다.
			 3) 변경 된 사항은 현재 페이지에서 닫힐 때까지 저장되어 사용할 수 있다.
			 4) 탭이 닫히면 저장된 데이터가 삭제 된다.
			*/
			//session.setAttribute("total",result.total)
			sessionStorage.setItem("total",result.total);
// 			result.forEach(function(lprodVO){
// 			});
		}
	});
});
</script>
<div class="row">
	<div class="col-12">
		<div class="card">
			<div class="card-header">
				<h3 class="card-title">상품 분류 검색</h3>
				<div class="card-tools">
					<!-- lprodGu, lprodNm이 검색 대상 -->
					<div class="input-group input-group-sm" 
						style="width: 150px;float:right;">
						<input type="text" name="keyword" id="keyword" value="${param.keyword}"
							class="form-control float-right" placeholder="Search" />
						<div class="input-group-append">
							<button type="button" id="btnSearch" class="btn btn-default">
								<i class="fas fa-search"></i>
							</button>
						</div>
					</div>
<!-- 
		스프링 시큐리티 표현식 : 인증 정보, 권한 정보를 다룰 수 있음. 로그인 한 사용자 정보를 확인.
		1) 인증 정보
		 - isAuthenticated() : 로그인 되었다면 true
		 - isAnonymous() : 로그인 안되었다면 true
		 - principal : 로그인 한 사용자 정보(UserDetails 인터페이스를 구현한 클래스의 객체)
		   1(username, password) : N(authorities)
		2) 권한 정보
		 - hasRole(role명) : 해당 role이 있으면 true
		 - hasAnyrole(role명1, role명2) : 여러 role 중 하나라도 해당되는가?
		 
-->					
<!-- 로그인 한 경우 -->
<sec:authorize access="isAuthenticated()">
	<!--<sec땡땡authentication property="principal"/>-->
	<c:set var="disabled" value="" />
</sec:authorize>
<!-- 로그인 안한 경우 -->
<sec:authorize access="isAnonymous()">
	<c:set var="disabled" value="disabled" />
</sec:authorize>
					<div class="input-group input-group-sm" 
						style="width: 150px;float:right;">
						<button type="button" data-toggle="modal" data-target="#
"
						 id="btnInsert" ${disabled} 						 
						  class="btn btn-block btn-outline-primary btn-sm">
						상품분류 추가</button>
					</div>
				</div>
			</div>

			<div class="card-body table-responsive p-0">
				<table class="table table-hover text-nowrap">
					<thead>
						<tr>
							<th>상품분류 아이디</th>
							<th>상품분류 코드</th>
							<th>상품분류 명</th>
						</tr>
					</thead>
					<tbody id="lprodTbody">
						<!-- jstl의 forEach를 사용하여 List<LprodVO>를 출력해보자 -->
<%-- 						<c:forEach var="lprodVO" items="${lprodVOList}" varStatus="stat"> --%>
<!-- 							<tr> -->
<%-- 								<td>${stat.index},${stat.count},${lprodVO.lprodId}</td> --%>
<%-- 								<td>${lprodVO.lprodGu}</td> --%>
<%-- 								<td>${lprodVO.lprodNm}</td> --%>
<!-- 							</tr> -->
<%-- 						</c:forEach> --%>
					</tbody>
				</table>
			</div>
			
			<div class="row" id="divPagingArea">
				
			</div>			
		</div>

	</div>
</div>
<!-- ///// 모달 시작(작은 크기) /// -->
<div class="modal fade" id="modal-sm">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
      	<input type="hidden" id="lprodId" class="form-control is-warning" value="" />
        <h4 class="modal-title general" id="modalTitle"></h4>
        <input type="text" id="lprodGu" class="form-control is-warning edit"
        	 value="" style="display:none;" /> 
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p class="general" id="modalBody"></p>
        <input type="text" id="lprodNm" class="form-control is-warning edit"
        	 value="" style="display:none;" />
      </div>
      <div class="modal-footer justify-content-between">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <!-- 일반모드 -->
        <button type="button" id="modify" ${disabled} class="btn btn-primary general">수정</button>
        <button type="button" id="delete" ${disabled} class="btn btn-primary general">삭제</button>
        <!-- 수정모드 -->
        <button type="button" id="confirm" style="display:none" 
        	class="btn btn-primary edit">확인</button>
        <button type="button" id="cancel" style="display:none" 
        class="btn btn-primary edit">취소</button>
      </div>
    </div>
    <!-- /.modal-content -->
  </div>
  <!-- /.modal-dialog -->
</div>
<!-- ///// 모달 끝(작은 크기) /// -->
<!-- ///// 삭제 모달 시작(작은 크기) ///  -->
<div class="modal fade" id="modal-del">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">상품분류코드 삭제</h4>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p>삭제하시겠습니까?&hellip;</p>
      </div>
      <div class="modal-footer justify-content-between">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" id="lprodIdDel" class="btn btn-primary">삭제</button>
      </div>
    </div>
    <!-- /.modal-content -->
  </div>
  <!-- /.modal-dialog -->
</div>
<!-- ///// 삭제 모달 끝(작은 크기) ///  -->
<!-- ///// 상품 분류 추가 모달 시작(작은 크기) ///  -->
<div class="modal fade" id="modal-insert">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title">상품 분류 추가</h4>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p>
        	<div class="form-group">
				<label class="col-form-label" for="lprodIdNew"><i class="fas fa-check"></i> 
				상품분류 아이디</label>
				<input type="text" class="form-control is-valid" id="lprodIdNew" name="lprodId" 
				placeholder="상품분류 아이디" />
			</div>
			<div class="form-group">
				<label class="col-form-label" for="lprodGuNew"><i class="far fa-bell"></i> 
				상품분류 코드</label>
				<input type="text" class="form-control is-warning" id="lprodGuNew" name="lprodGu" 
				placeholder="상품분류 코드" />
			</div>
			<div class="form-group">
				<label class="col-form-label" for="lprodNmNew"><i class="far fa-bell"></i>
				상품분류 명</label>
				<input type="text" class="form-control is-warning" id="lprodNmNew" name="lprodNm" 
				placeholder="상품분류 명" />
			</div>
        </p>
      </div>
      <div class="modal-footer justify-content-between">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        <button type="button" id="btnNew" class="btn btn-primary">등록</button>
      </div>
    </div>
    <!-- /.modal-content -->
  </div>
  <!-- /.modal-dialog -->
</div>
<!-- ///// 상품 분류 추가 모달 끝(작은 크기) ///  -->


