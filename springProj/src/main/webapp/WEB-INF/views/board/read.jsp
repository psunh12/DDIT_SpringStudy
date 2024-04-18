<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="card card-primary">
	<div class="card-header">
		<h3 class="card-title">Quick Example</h3>
	</div>

	<form action="/board/read?register" method="post">
		<div class="card-body">
			<div class="form-group">
				<label for="exampleInputEmail1">Email address</label> 
				<input type="email" name="memEmail" class="form-control" id="memEmail"
					placeholder="Enter email" />
			</div>
			<div class="form-group">
				<label for="exampleInputPassword1">Password</label> 
				<input type="password" name="memPass" class="form-control" id="memPass"
					placeholder="Password" />
			</div>
			<div class="form-group">
				<label for="exampleInputFile">File input</label>
				<div class="input-group">
					<div class="custom-file">
						<input type="file" name="uploadFile" class="custom-file-input" 
								id="uploadFile">
						<label class="custom-file-label" for="uploadFile">Choose
							file</label>
					</div>
					<div class="input-group-append">
						<span class="input-group-text">Upload</span>
					</div>
				</div>
			</div>
			<div class="form-check">
				<input type="checkbox" class="form-check-input" id="exampleCheck1">
				<label class="form-check-label" for="exampleCheck1">Check me
					out</label>
			</div>
		</div>

		<div class="card-footer">
			<!-- Params매핑 
			요청URI : /board/read?register
			요청파라미터 : register
			요청방식 : get
			-->
			<a href="/board/read?register" class="btn btn-primary">Submit</a>
			<!-- Params매핑 
			요청URI : /board/read?register
			요청파라미터 : register
			요청방식 : post
			-->
			<button type="submit" class="btn btn-primary">SubmitPost</button>
			<!-- Params매핑 
			요청URI : /board/read?modify
			요청파라미터 : register
			요청방식 : get
			-->
			<a href="/board/read?modify" class="btn btn-primary">Modify</a>
		</div>
	</form>
</div>