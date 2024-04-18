<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<title>책 목록</title>
<script type="text/javascript">
//document 내의 모든 요소들이 로딩된 후에 실행
$(function(){
	console.log("개똥이");
	
	//검색
	$("#btnSearch").on("click",function(){
		let keyword = $("input[name='keyword']").val();
		
		//json오브젝트
		let data = {
			"keyword":keyword
		};
		console.log("data : ", data);
		
		$.ajax({
			url:"/listAjax",
			contentType:"application/json;charset=utf-8",
			data:JSON.stringify(data),
			type:"post",
			dataType:"json",
			beforeSend:function(xhr){
				xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
			},
			success:function(result){
				console.log("result : ",result);
				
				let str = "";
				
				//목록 초기화
				$("#bookTbody").html("");
				
				let price;
				
				//result : List<BookVO>
				$.each(result,function(idx,bookVO){
					str += "<tr>";
					str += "<td>"+(idx+1)+"</td>";
					str += "<td><a href='/detail?bookId="+bookVO.bookId+"'>"+bookVO.title+"</a></td>";
					str += "<td>"+bookVO.category+"</td>";
					price = bookVO.price;
					str += "<td>"+price.toLocaleString("ko-KR")+"</td>";
					str += "</tr>";
				});
				//마지막 자식 요소로 누적
				$("#bookTbody").append(str);
			}
		});
	});
	
	//아작나써유..(피)씨다타써
// 		contentType:"application/json;charset=utf-8",
// 		data:JSON.stringify(data),
	$.ajax({
		url:"/listAjax",
		type:"post",
		dataType:"json",
		beforeSend:function(xhr){
			xhr.setRequestHeader("${_csrf.headerName}","${_csrf.token}");
		},
		success:function(result){
			console.log("result : ",result);
			
			let str = "";
			
			//목록 초기화
			$("#bookTbody").html("");
			
			let price;
			
			//result : List<BookVO>
			$.each(result,function(idx,bookVO){
				str += "<tr>";
				str += "<td>"+(idx+1)+"</td>";
				str += "<td><a href='/detail?bookId="+bookVO.bookId+"'>"+bookVO.title+"</a></td>";
				str += "<td>"+bookVO.category+"</td>";
				price = bookVO.price;
				str += "<td>"+price.toLocaleString("ko-KR")+"</td>";
				str += "</tr>";
			});
			//마지막 자식 요소로 누적
			$("#bookTbody").append(str);
		}
	});
});
</script>
</head>
<body>
<h1>책 목록</h1>
<p>
	<!-- action속성 및 값이 생략 시, 현재 URI(/list)를 재요청. 
		method는 GET(form 태그의 기본 HTTP 메소드는 GET임) 
	param : keyword=알탄
	요청URI : /list?keyword=알탄 or /list or /list?keyword=
	요청파라미터 : keyword=알탄
	요청방식 : get
	-->
	<form>
		<input type="text" name="keyword" value="${param.keyword}" 
			placeholder="검색어를 입력하세요" />
		<!-- submit / button / reset -->
		<button type="button" id="btnSearch">검색</button>
	</form>
</p>
<!-- mav.addObject("bookVOList", bookVOList); -->
<table border="1">
	<thead>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>카테고리</th>
			<th>가격</th>
		</tr>
	</thead>
	<tbody id="bookTbody">
		<!-- 
		forEach 태그? 배열(String[], int[][]), Collection(List, Set) 또는 
		Map(HashTable, HashMap, SortedMap)에 저장되어 있는 값들을 
		순차적으로 처리할 때 사용함. 자바의 for, do~while을 대신해서 사용함
		var : 변수
		items : 아이템(배열, Collection, Map)
		varStatus : 루프 정보를 담은 객체 활용
			- index : 루프 실행 시 현재 인덱스(0부터 시작)
			- count : 실행 회수(1부터 시작. 보통 행번호 출력)
		 -->
		 <!-- data : mav.addObject("bookVOList", bookVOList); -->
		 <!-- row : bookVO 1행 -->
<%-- 		 <c:forEach var="bookVO" items="${bookVOList}" varStatus="stat"> --%>
<!-- 		 	<tr> -->
<%-- 		 		<td>${stat.count}</td> --%>
<%-- 		 		<td><a href="/detail?bookId=${bookVO.bookId}">${bookVO.title}</a></td> --%>
<%-- 		 		<td>${bookVO.category}</td> --%>
<%-- 		 		<td><fmt:formatNumber value="${bookVO.price}" pattern="#,###" /></td> --%>
<!-- 		 	</tr> -->
<%-- 		 </c:forEach> --%>
	</tbody>
	<p>
		<a href="/create">책 생성</a>
	</p>
</table>
</body>
</html>




