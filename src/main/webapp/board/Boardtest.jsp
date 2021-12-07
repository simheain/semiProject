<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<button type="button" id="test">테스트</button>
	
	   <a href="https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=2d4ac180ad6ea3f67c6cfbe9d55448bc&redirect_uri=https%3A%2F%2Fwww%2Edietshin%2Ecom%2Fmember%2Fkakao%2Easp&state=1638540174_191512">
	   <img src="/images/kakao_join_medium.png">
	   </a>
	 
	 
	<script>
		let test = document.getElementById("test");
		test.addEventListener("click",function(){
			location.href="${pageContext.request.contextPath}/toBoard.bo?currentPage=1";
		})
		
		
	</script>
</body>
</html>