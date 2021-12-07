<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 상세 조회</title>

<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous"></script>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
	integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
	crossorigin="anonymous"></script>

<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
	integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
	crossorigin="anonymous">
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
	integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
	crossorigin="anonymous"></script>

<link
	href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-bs4.min.js"></script>
	
	
<style>



html, body {
	height: 100%;
}

div {
	background-color:white;
	border: 0px solid black;
}

#title {
	background-color:white;
	width: 100%;
}

textarea {
	background-color:white;
	height: 100%;
	width: 100%;
	resize: none;
}

#board_content {
	background-color:white;
	height: 500px;
}

.board_box {
	background-color:white;
	width: 1200px;
	margin: auto;
}

.subContainer {
	background-color:white;
	text-align: center;
}

img {
	background-color:white;
	height: 50px;
}

.subContainer>.row>.col>button {
	background-color:white;
	border: none;
}

#report_reason{
width:100%;
height:100%;
}

.beforeBox{
	text-align:right;
}

.afterBox{
	display:none;
}

#modify_content{
	display:none;
	width:100%;
	height:500px;
}

.btn-modifyCmtSuc , .btn-modifyCmtCan{
	display:none;
}

</style>
</head>
<body>
	<div class="container">
		<div class="row">
			<h1>게시글 상세 조회</h1>
		</div>
	</div>
	<form id="modifyForm"
		action="${pageContext.request.contextPath}/modifyProc.bo"
		method="post">
		<div class="row board_box">
			<div class="col">
				<table class="table table-bordered">
					<tr>
						<th>제목</th>
						<td colspan="5"><input type="text" class="form-control"
							id="title" name="title" value="${dto.getBoard_title()}" readonly></td>
					</tr>

					<tr>
						<th>작성자</th>
						<td><input type="text" class="form-control" id="board_writer"
							name="board_writer" value="${dto.getWriter_nickname()}" readonly></td>
						<th>게시일</th>
						<td><input type="text" class="form-control"
							id="board_written_date" name="board_written_date"
							value="${dto.getWritten_date()}" readonly></td>
						<th>조회수</th>
						<td><input type="text" class="form-control" id="view_count"
							name="view_count" value="${dto.getView_count()}" readonly></td>
					</tr>
					<tr>
						<th>내용</th>
						<td colspan="5">
						<div class="form-control summernote" id="board_content" name="board_content" readonly>
						${dto.getBoard_content()}
						</div>
						<textarea id="modify_content" class="modify_content" name="board_content">${dto.getBoard_content()}</textarea>
						</td>
					</tr>
					
					<tr>
					<!-- 추천,신고 버튼 -->
						<td colspan="12">
						<div class="container subContainer">
					<div class="row">
						<div class="col">
							<button type="button" id="btnRecommend">
								<img
									src="https://news.imaeil.com/photos/2018/02/07/2018020710243848707_m.jpg">추천
							</button>
						</div>
						<div class="col">
							<button type="button" id="btnReport" data-bs-toggle="modal"
								data-bs-target="#reportPopup">
								<img
									src="https://i.pinimg.com/736x/57/62/24/5762245c37514d61a333d1d5d1434670.jpg">신고
							</button>

							<!-- Modal -->
							<div class="modal fade" id="reportPopup"
								data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
								aria-labelledby="staticBackdropLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<h5 class="modal-title" id="staticBackdropLabel">신고접수</h5>
											<button type="button" class="btn-close"
												data-bs-dismiss="modal" aria-label="Close"></button>
										</div>
										<div class="modal-body">
											<input type="text" id="report_reason" placeholder="신고사유를 작성해주세요.">
										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-secondary"
												data-bs-dismiss="modal">닫기</button>
											<button type="submit" class="btn btn-primary">전송</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				</td>
				</tr>
			</table>
			
			<!-- 게시글 수정, 삭제 버튼 아이디값 임의로 설정하였음.-->
			<c:if test="${dto.getId() eq 'aaa123'}">
			<div class="container beforeBox">
					<div class="row">
						<div class="col">
							<button type="button" class="btn btn-danger" id="btn_boardDelete">삭제</button>
							<button type="button" class="btn btn-warning" id="btn_boardModify">수정</button>
						</div>
					</div>
				</div>
			</c:if>
			
			<div class="row afterBox justify-content-center">
			<div class="col-2 d-flex justify-content-end">
				<button type="button" class="btn btn-secondary" id="btn_cancel">취소</button>
			</div>
			<div class="col-2 d-flex justify-content-start">
				<button type="button" class="btn btn-success" id="btn_complete">완료</button>
			</div>
		</div>

		<!-- board_seq, currentPage 히든 처리 -->
			<input type="text" id="board_seq" name="board_seq" value="${dto.getBoard_seq()}" hidden>
            <input type="text" name="currentPage" value="${currentPage}" hidden>
              </div>
			</div>
				</form>
				<br>
	<div class="container comment_container">
		<div class="cmt_box" id="cmt_box">
			<!-- 댓글 -->
		</div>
		<div class="cmt_input"></div>
		<form id="commentForm" method="post">
			<div class="row comment_body m-1">
				<div class="col-10 comment_input">
					<textarea class="form-control" id="comment_content"
						name="comment_content" placeholder="댓글 입력"></textarea>
				</div>

				<div
					class="col-2 comment_input d-flex align-items-center justify-content-center">
					<button type="button" id="btnSubmitCmt" class="btn btn-primary">등록</button>
				</div>
			</div>

			<!-- currentPage, board_seq 넘겨주는 div (hidden 처리) -->
           	 <input type="text" name="currentPage" value="${currentPage}" hidden>
			 <input type="text" name="board_seq" value="${dto.getBoard_seq()}" hidden>
		</form>
	</div>

	<script>
	
	// 추천 버튼 클릭했을 때 이벤트 처리
	
	
	// 신고 버튼 클릭했을 때 이벤트 처리
	let btnReport = document.getElementById("btnReport");
	btnReport.addEventListener("click",function(){
	})
	
	// 게시글 수정 버튼 클릭했을 때 이벤트 처리
	let btn_boardModify = document.getElementById("btn_boardModify");
	btn_boardModify.addEventListener("click",function(){
		$(".beforeBox").css("display","none");
		$(".afterBox").css("display","flex");
		$("#title").removeAttr("readonly");
		$("#modify_content").css("display","flex");
		$("#board_content").css("display","none");
	})
	
	// 게시글 수정 버튼 후 완료 버튼 눌렀을 때 이벤트 처리
	let btn_complete = document.getElementById("btn_complete");
	let title = document.getElementById("title");
	let modify_content = document.getElementById("modify_content");
	
	// 욕설 필터링
	let regex_word1 = /[썅|ㅄ]{1}/gm;
	let regex_word2 = /(시.*?발)|(병신.*?)|(씨.*?발)|(존.*?나)|(ㅈ.*?ㄴ)|(ㅅ.*?ㅂ)|(ㅂ.*?ㅅ)/gm;	
	btn_complete.addEventListener("click",function(){
		if(regex_word1.test(title.value)||regex_word2.test(title.value)||regex_word1.test(modify_content.value)||regex_word2.test(modify_content.value)){
			alert("제목이나 내용에 부적절한 언어가 포함되어있습니다.");
			return;}
		else if(title.value == "" || modify_content.value == ""){
			alert("제목 혹은 내용이 공백입니다. 다시 확인해주세요.");
			return;
		}
		document.getElementById("modifyForm").submit();
	})
	
	// 게시글 수정 버튼 후 취소 버튼 눌렀을 때 이벤트 처리
	let btn_cancel = document.getElementById("btn_cancel");
	btn_cancel.addEventListener("click",function(){
		location.href = "${pageContext.request.contextPath}/toDetailView.bo?board_seq=${dto.getBoard_seq()}&currentPage=${currentPage}";
	})
	
	// 게시글 삭제 버튼 클릭했을 때 이벤트 처리
	let btn_boardDelete = document.getElementById("btn_boardDelete");
	btn_boardDelete.addEventListener("click",function(){
		let rs = confirm("게시글을 정말 삭제하시겠습니까?");
		if(rs){
			location.href="${pageContext.request.contextPath}/deleteProc.bo?board_seq=${dto.getBoard_seq()}&currentPage=${currentPage}";	
		}
	})
	
	
	
	// 댓글 등록 
	$(document).ready(function(){
		getCommentList();
		// 댓글 등록 버튼 (btnSubmitCmt) , textarea (comment_content)
		$("#btnSubmitCmt").on("click",function(){
			//욕설 필터링 정규식
			let comment_content = document.getElementById("comment_content");
			let regex_word1 = /[썅|ㅄ]{1}/gm;
			let regex_word2 = /(시.*?발)|(병신.*?)|(씨.*?발)|(존.*?나)|(ㅈ.*?ㄴ)|(ㅅ.*?ㅂ)|(ㅂ.*?ㅅ)/gm;			if(regex_word1.test(comment_content.value) || regex_word2.test(comment_content.value)){
				alert("댓글에 부적절한 언어가 포함되어있습니다.");
				return;
			}
			
			let data = $("#commentForm").serialize();
			$("#comment_content").val("");
			$.ajax({
				url : "${pageContext.request.contextPath}/insertProc.co",
				type : "post",
				data : data
			}).done(function(rs){
			 if(rs =="success"){
					getCommentList();
				}else if(rs == "fail"){
					alert("댓글 등록 실패!");
				}
			}).fail(function(e){
				console.log(e);
			});
		});
	
		// 댓글 목록 출력
		function getCommentList(){
			console.log("abc");
			$.ajax({
				type: "get",
				url : "${pageContext.request.contextPath}/getCommentProc.co?board_seq=${dto.getBoard_seq()}",
				dataType : "json"
			}).done(function(data){
				 $("#cmt_box").empty();
				 console.log("456");
				for(let dto of data){
					console.log(dto);
					
					let comment = "<div class='row comment-header m-1'>"
					+ "<div class='col-12 d-flex justify-content-start cmt-info'>"
					+ dto.writer_nickname
					+ "</div>"
					+ "<div class='col-12 d-flex justify-content-start cmt-info'>"
					+ dto.written_date
					+ "</div>"
					+ "<div class='col-10 d-flex justify-content-start contentDiv-cmt'>"
					+ "<textarea class='content-cmt form-control' id='comment_content' name='comment_content' readonly>"
					+ dto.comment_content
					+"</textarea>"
					+"</div>"
					+"</div>";
					$("#cmt_box").append(comment);
					
					// 댓글 수정 삭제 버튼 영역
					if("${dto.getId()}" == ("aaa123")){
						let btns = "<div class='col-1' d-flex justify-content-center'>"
			          	+ "<button type='button' id='btn_commentModify' class='btn btn-modifyCmt' value='" + dto.comment_seq +"'>수정</button>"
			          	+ "<button type='button' class='btn btn-modifyCmtSuc' value='" + dto.comment_seq + "'>완료</button>"
		          		+ "<button type='button' id='btn_commentDelete' class='btn btn-deleteCmt' value='" + dto.comment_seq + "'>삭제</button>"
		          		+ "<button type='button' class='btn btn-modifyCmtCan' value='" + dto.comment_seq + "'>취소</button>"
		          		+ "</div>";

		          		$(".contentDiv-cmt:last").after(btns);
						}
					}
			}).fail(function(e){
				console.log(e);
			});
		};
		
		// 댓글 수정 버튼 누르고 완료 버튼 눌렀을 때
		$(document).on("click",".btn-modifyCmtSuc",function(e){
			let comment_modify_content = document.getElementById("comment_content");
			
			//욕설 필터링
			let regex_word1 = /[썅|ㅄ]{1}/gm;
			let regex_word2 = /(시.*?발)|(병신.*?)|(씨.*?발)|(존.*?나)|(ㅈ.*?ㄴ)|(ㅅ.*?ㅂ)|(ㅂ.*?ㅅ)/gm;
			if(regex_word1.test(comment_modify_content.value) || regex_word2.test(comment_modify_content.value)){
				alert("댓글에 부적절한 언어가 포함되어있습니다.");
				return;
			}
			let comment_content = $(e.target).parents(".comment-header").children(".contentDiv-cmt").children("textarea").val(); // 변수로 담음
			$.ajax({
				url:"${pageContext.request.contextPath}/modifyProc.co?comment_seq=" + $(e.target).val() + "&comment_content=" + comment_content ,
				type:"get"
			}).done(function(rs){
				if(rs=="success"){
					getCommentList();
				}else if(rs=="fail"){
					alert("댓글 수정에 실패하였습니다.");
				}
			}).fail(function(e){
				console.log(e);
			});
		
		})
		
		
		// 댓글 수정 버튼 누르고 취소 버튼 눌렀을 때
		$(document).on("click",".btn-modifyCmtCan",function(e){
			console.log("댓글 수정 취소 버튼 클릭");
			
			$(e.target).parents(".comment-header").children(".contentDiv-cmt").children("textarea").attr("readonly",true);
			$(e.target).siblings(".btn-modifyCmt").css("display","flex"); // 수정 버튼
			$(e.target).siblings(".btn-deleteCmt").css("display","flex"); // 삭제 버튼

			$(e.target).siblings(".btn-modifyCmtSuc").css("display","none"); //완료 버튼
			$(e.target).css("display","none"); // 취소 버튼
		})
		
		// 댓글 수정 버튼 클릭 시 완료랑 취소 버튼 띄우기 (readonly 해제)
		$(document).on("click",".btn-modifyCmt",function(e){
			console.log("댓글 수정 버튼 클릭");
			console.log($(e.target).siblings(".btn-modifyCmtSuc").val()); // console로 값 잘 찾아오는지 확인
			
			// $(e.target).siblings(".btn-modifyCmtSuc")
			$(e.target).parents(".comment-header").children(".contentDiv-cmt").children("textarea").attr("readonly",false);
			
			$(e.target).css("display","none");
			$(e.target).siblings(".btn-deleteCmt").css("display","none");
			
			$(e.target).siblings(".btn-modifyCmtSuc").css("display","flex"); //완료 버튼
			$(e.target).siblings(".btn-modifyCmtCan").css("display","flex"); // 취소 버튼

		});
		
		
		
		// 댓글 삭제 버튼 클릭 시 이벤트 처리
		$(document).on("click","#btn_commentDelete",function(e){
			console.log("댓글 삭제 버튼 클릭");
			$.ajax({
				url:"${pageContext.request.contextPath}/deleteProc.co?comment_seq=" + $(e.target).val(),
				type : 'get'
			}).done(function(rs){
				if(rs=="success"){
					let rs = confirm("정말 댓글을 삭제하시겠습니까?");
					if(rs){
						getCommentList();	
					}
				}else if(rs =="fail"){
					alert("댓글 삭제에 실패했습니다.");
				}
			}).fail(function (e){
				console.log(e);
			});
		});
	});
	</script>
</body>
</html>