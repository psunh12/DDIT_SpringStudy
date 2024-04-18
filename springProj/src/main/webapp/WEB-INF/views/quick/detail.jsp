<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<div class="card card-primary">
<p>${quickVO}</p>
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
					name="emailAdres" id="emailAdres" value="${quickVO.emailAdres}" 
					placeholder="이메일" required readonly />
			</div>
			<div class="form-group">
				<label for="exampleInputPassword1">Password</label> 
				<input type="password" class="form-control" 
					name="password" id="password" value="${quickVO.emailAdres}" 
					placeholder="비밀번호" required readonly />
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
			<div>
				<div class="filter-container p-0 row" style="padding: 3px; position: relative; width: 100%; display: flex; flex-wrap: wrap; height: 177px;">
					<c:forEach var="quickAttachVO" items="${quickVO.quickAttachVOList}" varStatus="stat">
						<div class="filtr-item col-sm-2" data-category="${stat.count}, 4" data-sort="white sample" style="opacity: 1; transform: scale(1) translate3d(${stat.index*94}px, 0px, 0px); backface-visibility: hidden; perspective: 1000px; transform-style: preserve-3d; position: absolute; width: 91.4px; transition: all 0.5s ease-out 0ms, width 1ms ease 0s;">
							<a href="#modalPicture" class="clsPicture" data-picture-url="${quickAttachVO.pictureUrl}" data-toggle="modal" >
								<img src="/resources/upload${quickAttachVO.pictureUrl}" class="img-fluid mb-2" alt="white sample">
							</a>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>

		<div class="card-footer">
			<button type="submit" class="btn btn-primary">Submit</button>
		</div>
		<sec:csrfInput />
	</form>
</div>
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