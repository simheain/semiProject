<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
	
<style>
.container {
	width: 90%;
	padding: 10px;
}

th, td {
	text-align: center;
}

.btnWriteDiv {
	text-align: right;
}

.login_box {
	text-align: right;
}

/*
        div{
            border: 1px solid black;
        }
        */
#btn_login {
	font-weight: bolder;
	width: 100%;
	height: 100%;
}
</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-12 mb-5 d-flex justify-content-center">
				<h1>자유 게시판</h1>
			</div>
		</div>

		<div class="row login_box">
			<div class="col-9"></div>
			<div class="col-3 login_container">
				<div class="row">
					<div class="col-7">
						<div class="row">
							<input type="text" placeholder="id">
						</div>
						<div class="row">
							<input type="password" placeholder="password">
						</div>

					</div>
					<div class="col-5">
						<button type="button" class="btn btn-warning" id="btn_login">로그인</button>
					</div>
				</div>
			</div>
		</div>

		<br>
		<div class="row">
			<div class="col btnWriteDiv">
				<button type="button" class="btn btn-primary" id="btn_write">
					글 작성</button>
			</div>
		</div>

		<br>
		<table class="table table-bordered">
			<thead>
				<tr>
					<th class="col-md-1">글번호</th>
					<th class="col-md-5">제목</th>
					<th class="col-md-2">작성자</th>
					<th class="col-md-2">작성일</th>
					<th class="col-md-1">조회수</th>
					<th class="col-md-1">추천수</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list}" var="dto">
				<tr>
					<td>${dto.getBoard_seq()}</td>
					<td><a href="${pageContext.request.contextPath}/toDetailView.bo?board_seq=${dto.getBoard_seq()}&currentPage=${naviMap.get('currentPage')}">${dto.getBoard_title()}</a></td>
					<td>${dto.getWriter_nickname()}</td>
					<td>${dto.getWritten_date()}</td>
					<td>${dto.getView_count()}</td>
					<td>${dto.getRecommended_count()}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
			<div class="row">
			<nav class="col" aria-label="Page navigation example">
			  <ul class="pagination justify-content-center">
				  <c:if test="${naviMap.get('needPrev') eq true}">
				  	<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/toBoard.bo?currentPage=${naviMap.get('startNavi')-1}">Previous</a></li>
				  </c:if>
				  <!--startNavi ->endNavi  -->
				  <c:forEach var="i" begin="${naviMap.get('startNavi')}" end="${naviMap.get('endNavi')}">
				  	<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/toBoard.bo?currentPage=${i}">${i}</a></li>
				  </c:forEach>
				  <c:if test="${naviMap.get('needNext') eq true}">
				  	<li class="page-item"><a class="page-link" href="${pageContext.request.contextPath}/toBoard.bo?currentPage=${naviMap.get('endNavi')+1}">Next</a></li>
				  </c:if>			    
			  </ul>
			</nav>
		</div>
	</div>
	
	<script>
	
	// 로그인 버튼 : btn_login
		let btn_login = document.getElementById("btn_login");
		btn_login.addEventListener("click",function(e){
			location.href="${pageContext.request.contextPath}/";
		})
	
	
	// 글쓰기 버튼 : btnWrite
		let btn_write = document.getElementById("btn_write");
		btn_write.addEventListener("click",function(e){
			location.href="${pageContext.request.contextPath}/toWrite.bo";
		})

	</script>
	
	
	
</body>
</html>

