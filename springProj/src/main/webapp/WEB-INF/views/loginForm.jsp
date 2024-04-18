<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div class="card card-info">
	<div class="card-header">
		<h3 class="card-title">로그인</h3>
	</div>

	<form class="form-horizontal" action="/login" method="post">
		<div class="card-body">
			<div class="form-group row">
				<label for="inputEmail3" class="col-sm-2 col-form-label">아이디</label>
				<div class="col-sm-10">
					<input type="text" name="username" class="form-control" id="username"
						placeholder="아이디" />
				</div>
			</div>
			<div class="form-group row">
				<label for="inputPassword3" class="col-sm-2 col-form-label">비밀번호</label>
				<div class="col-sm-10">
					<input type="password" name="password" class="form-control" id="password"
						placeholder="비밀번호">
				</div>
			</div>
			<div class="form-group row">
				<div class="offset-sm-2 col-sm-10">
					<div class="form-check">
						<!-- 로그인 상태 유지 체크박스
						체크 시 : PERSISTENT_LOGINS에 로그인 로그 정보가 insert
						 -->
						<input type="checkbox" name="remember-me" 
							class="form-check-input" id="remember-me" />
						<label class="form-check-label" for="remember-me">자동 로그인</label>
					</div>
				</div>
			</div>
		</div>

		<div class="card-footer">
			<button type="submit" class="btn btn-info">로그인</button>
			<button type="reset" class="btn btn-default float-right">Cancel</button>
		</div>
		
		<!-- csrf : Cross Site(크로스 사이트) Request(요청) Forgery(위조) -->
		<sec:csrfInput />
	</form>
</div>





