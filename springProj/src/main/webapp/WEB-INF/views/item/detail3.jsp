<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<script type="text/javascript">
$(function(){
	$(".clsPicture").on("click",function(){
		//this : 클릭한 바로 그 이미지
		//data-picture-url=".."
		let pictureUrl = $(this).data("pictureUrl");
		console.log("pictureUrl : " + pictureUrl);		
		
		$(".modalPicBody > p").html("<img src='/resources/upload"+pictureUrl+"' style='width:100%;' />");
		
		//modalPicTitle을 이미지 명으로 처리해보자
		//pictureUrl : /2024/01/31/ea2a20d6-4a0b-478a-a89e-2cfd4b9a9791_coffee1.jpg
		let fileName = pictureUrl.substring(pictureUrl.lastIndexOf("_")+1);
		console.log("fileName : " + fileName);
		$(".modalPicTitle").html(fileName);
	});
});
</script>  
<h2>상세보기</h2>
<!-- 
요청URI : /item/registerPost3
요청파라미터 : {itemName=삼성태블릿,price=120000,description=쓸만함
		   ,uploadFile=파일객체들}
요청방식 : post
-->
<p>${item3VO}</p>
<form action="/item/registerPost3?${_csrf.parameterName}=${_csrf.token}" 
	method="post" enctype="multipart/form-data">
	<table>
		<tr>
			<th>상품명</th>
			<td><input type="text" name="itemName" value="${item3VO.itemName}" 
				 required readonly placeholder="상품명" /></td>
		</tr>
		<tr>
			<th>가격</th>
			<td><input type="text" name="price" value="${item3VO.price}"  
				required readonly placeholder="가격" /></td>
		</tr>
		<tr>
			<th>다중 상품이미지</th>
			<td><input type="file" name="uploadFile" placeholder="상품이미지"
				multiple /></td>
		</tr>
		<tr>
			<td colspan="2">
				<c:forEach var="attachVO" items="${item3VO.attachVOList}" varStatus="stat">
					<a href="#modalPicture" class="clsPicture" 
						data-picture-url="${attachVO.pictureUrl}" data-toggle="modal" > 
						<img src="/resources/upload${attachVO.pictureUrl}" />
					</a>
				</c:forEach>
			</td>
		</tr>
		<!-- 
		이미지들을 출력해보자(modal처리까지 해보자)
		-->
		<tr>
			<th>개요</th>
			<td><input type="text" name="description" value="${item3VO.description}" 
				 readonly placeholder="개요" /></td>
		</tr>
	</table>
	<button type="submit">상품 등록</button>
	<button type="reset">초기화</button>
	<sec:csrfInput />
</form>
<!-- ///// 이미지 크게 보기 모달 시작 /////-->
<div class="modal fade" id="modalPicture">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modalPicTitle">Large Modal</h4>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modalPicBody">
        <p>One fine body&hellip;</p>
      </div>
      <div class="modal-footer justify-content-between">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
  <!-- /.modal-dialog -->
</div>
<!-- ///// 이미지 크게 보기 모달 끝 /////-->


