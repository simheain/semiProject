<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>
<!-- include libraries(jQuery, bootstrap) -->
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script
	src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<!-- include summernote css/js -->
<link
	href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/lang/summernote-ko-KR.js"></script>
<style>
@font-face {
	font-family: 'NotoSansKR';
	src: url('font/NotoSansKR-Black.otf') format('opentype')
}

div {
	border: 0px solid black;
}

input {
	width: 100%;
	height: 100%;
}

.boxBtn {
	text-align: center;
}

.write {
	text-align: center;
}
</style>
<script>

        // 썸머노트 라이브러리 사용
        $(document).ready(function () {
        	$('#board_content').val("${board_data.BOARD_CONTENT}");
            $('#board_content').summernote({
                height: 600, // 에디터 높이
                focus: true,  // 에디터 로딩후 포커스를 맞출지 여부
                lang: "ko-KR",	// 한글 설정
                placeholder: '내용',
                disableResizeEditor: true,	// 크기 조절 기능 삭제
                // 툴바 설정
                toolbar: [
                    ['fontname', ['fontname']],
                    ['fontsize', ['fontsize']],
                    ['style', ['bold', 'italic', 'underline', 'strikethrough', 'clear']],
                    ['color', ['forecolor', 'color']],
                    ['table', ['table']],
                    ['para', ['ul', 'ol', 'paragraph']],
                    ['height', ['height']],
                    ['insert', ['picture', 'link', 'video']],
                    ['view', ['fullscreen', 'help']]
                ],
                // 폰트 추가
                fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New', '맑은 고딕', '궁서', '굴림체', '굴림', '돋움체', '바탕체'],
                fontSizes: ['8', '9', '10', '11', '12', '14', '16', '18', '20', '22', '24', '28', '30', '36', '50', '72']
            });
	});
    </script>
</head>

<body>
	<div class="write">
		<h2>게시글 작성</h2>
	</div>

	<div class="container">
	<form id="boardForm" action="${pageContext.request.contextPath}/writeProc.bo" method="post" enctype="multipart/form-data">
		<table class="table table-bordered">
			<tr>
				<th class="col-2">제목</th>
				<td class="col-10"><input type="text" id="title" name="title">
				</td>
			</tr>
			<tr>
				<th class="col-2">파일첨부</th>
				<td class="col-10"><input type="file" class="custom-file-input"
					name="file"> <label class="custom-file-label"
					for="inputGroupFile02" aria-describedby="inputGroupFileAddon02"></label>
				</td>
			</tr>
			<tr>
				<th class="col-2">내용</th>
				<td class="col-10 summernote_content">
						<textarea id="board_content" class="summernote" name="board_content"></textarea>
				</td>
			</tr>
		</table>
	</form>
	</div>

	<div class="boxBtn">
		<button type="button" class="btn btn-secondary" id="btnBack">뒤로가기</button>
		<button type="button" class="btn btn-primary" id="btnWrite">작성</button>
	</div>
	
	<script>
	


	
	let title = document.getElementById("title"); // title 객체
	let board_content = document.getElementById("board_content"); // textarea의 summernote 객체
		// 뒤로가기 버튼 눌렀을 때 이벤트 처리
		let btnBack = document.getElementById("btnBack");
		btnBack.addEventListener("click",function(){
			location.href="${pageContext.request.contextPath}/toBoard.bo?currentPage=1";
		})
		
		
		
		
		
		// 작성 버튼 눌렀을 때 이벤트 처리
		let btnWrite = document.getElementById("btnWrite");
		btnWrite.addEventListener("click",function(){
			
			//욕설 필터링 정규식
			let regex_word1 = /[썅|ㅄ]{1}/gm;
			let regex_word2 = /(시.*?발)|(병신.*?)|(씨.*?발)|(존.*?나)|(ㅈ.*?ㄴ)|(ㅅ.*?ㅂ)|(ㅂ.*?ㅅ)/gm;			
			
			if(title.value == ""){ // 제목란이 비어있을 때 경고처리
				alert("제목을 입력해주세요");
				return;
			}else if(board_content.value == ""){// 내용란이 비어있을 때 경고처리
				alert("내용을 입력해주세요");
				return;
			}else if(regex_word1.test(title.value)||regex_word2.test(title.value)||regex_word1.test(board_content.value)||regex_word2.test(board_content.value)){
				alert("제목이나 내용에 부적절한 언어가 포함되어있습니다.");
				return;
			}else{
			let rs =confirm("수정 시 글꼴 등은 수정할 수 없습니다. 유의해서 작성해주세요");
			if(rs){
				document.getElementById("boardForm").submit();
				}
			}
		});
		
		
	</script>
	
</body>
</html>