<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="/WEB-INF/header.jsp"></jsp:include>
		<script src="/js/summernote/summernote-lite.js"></script>
		<script src="/js/summernote/lang/summernote-ko-KR.js"></script>
		<link rel="stylesheet" href="/css/summernote/summernote-lite.css">
		
		
		<title>글쓰기</title>
		<script>
			function to_list() {
				location.href="/bbs_list/1";
			}
			
			let fileIdx = 0;

			function addFile(){
				const fileDivs = $('div[data-name="fileDiv"]');
				if(fileDivs.length > 2 ) {
					alert("파일은 최대 세 개까지 업로드 할 수 있습니다.");
					return false;
				}
				
				fileIdx++;
				
				/* jsp 환경에서 백틱`사용시 \${변수} 해당 형태로 사용가능 */
				const fileHtml = `
					<div data-name="fileDiv" class="form-group filebox bs3-primary">
						<label for="file_\${fileIdx}" class="col-sm-2 control-label"></label>
						<div class="col-sm-10">
							<input type="text" class="upload-name" value="파일 찾기" readonly />
							<label for="file_\${fileIdx}" class="control-label">찾아보기</label>
							<input type="file" name="files" id="file_\${fileIdx}" class="upload-hidden" onchange="changeFilename(this)" />

							<button type="button" onclick="removeFile(this)" class="btn btn-bordered btn-xs visible-xs-inline visible-sm-inline visible-md-inline visible-lg-inline">
								<i class="fa fa-minus" aria-hidden="true"></i>
							</button>
						</div>
					</div>
				`;
				$('#btnDiv').before(fileHtml);
			}
			
			function removeFile(elem){
				
				const prevTag = $(elem).prev().prop("tagName");
				if (prevTag === 'BUTTON') {
					const file = $(elem).prevAll('input[type="file"]');
					const filename = $(elem).prevAll('input[type="text"]');
					file.val('');
					filename.val('파일 찾기');
					return false;
				}
				
				const target = $(elem).parents('div[data-name="fileDiv"]');
				target.remove();
			}
			
			function changeFilename (file){
				
				file = $(file);
				const filename = file[0].files[0].name;
				console.log(filename);
				const target = file.prevAll('input');
				target.val(filename);
			}
			
			$(function() {
				
				$('#summernote').summernote({
					  height: 300,                 // 에디터 높이
					  minHeight: null,             // 최소 높이
					  maxHeight: null,             // 최대 높이
					  focus: true,                  // 에디터 로딩후 포커스를 맞출지 여부
					  lang: "ko-KR",					// 한글 설정
					  placeholder: '내용을 입력해주세요',	//placeholder 설정
					  toolbar: [
						    // [groupName, [list of button]]
						    ['fontname', ['fontname']],
						    ['fontsize', ['fontsize']],
						    ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
						    ['color', ['forecolor','color']],
						    ['table', ['table']],
						    ['para', ['ul', 'ol', 'paragraph']],
						    ['height', ['height']],
						    ['insert',['picture','link','video']],
						    ['view', ['fullscreen', 'help']]
						  ],
						fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋움체','바탕체'],
						fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72'],
						
					callbacks: {	
						onImageUpload : function(files) {
							uploadSummernoteImageFile(files[0],this);
						},
						onPaste: function (e) {
							var clipboardData = e.originalEvent.clipboardData;
							if (clipboardData && clipboardData.items && clipboardData.items.length) {
								var item = clipboardData.items[0];
								if (item.kind === 'file' && item.type.indexOf('image/') !== -1) {
									e.preventDefault();
								}
							}
						}
					}
				});
			});
			/*이미지 파일 업로드	*/
			function uploadSummernoteImageFile(file, editor) {
				data = new FormData();
				data.append("file", file);
				$.ajax({
					data : data,
					type : "POST",
					url : "/uploadSummernoteImageFile",
					contentType : false,
					processData : false,
					success : function(data) {
		            	//항상 업로드된 파일의 url이 있어야 한다.
						$(editor).summernote('insertImage', data.url);
					}
				});
			}
		</script>

	</head>
	<body>
		<jsp:include page="/WEB-INF/topnav.jsp"></jsp:include>
		<div id="layoutSidenav">
		
			<jsp:include page="/WEB-INF/sidenav.jsp"></jsp:include>
			<div id="layoutSidenav_content">
				<main>
					<br>
					<h1 class="text-center">Board Form</h1>
		            <div class="container">
						<form action="/bbs_add" method="post" enctype="multipart/form-data">
							<input type="hidden" id="changeYn" name="changeYn" value="N" />
							<input type="hidden" name="deleteYn" value="N" />
							
							<!-- Title -->
							<div class="mb-3">
								<label for="title">제목</label>
								<input type="text" class="form-control" name="title" id="title" placeholder="제목을 입력해 주세요" required>
							</div>
							
							<label for="category">카테고리</label>
							<select class="form-select" id="category" name="category" required>
							  	<option selected value="" >Select Category Menu</option>
							  	<option value="자유게시판">자유게시판</option>
							  	<option value="회의록">회의록</option>
							  	<option value="something">something</option>
							</select>
							
							<!-- 작성자 -->
							<div class="mb-3">
								<label for="uid">작성자</label>
								<input type="text" class="form-control" name="uid" id="uid" value="${uid}" disabled>
							</div>
							
							<!-- Contents -->
							<div class="mb-3">
								<label for="summernote">내용</label>
								<textarea class="form-control" name="content" id="summernote" required></textarea>
							</div>
							
							
							 <!-- 파일 업로드 -->
							<div data-name="fileDiv" class="form-group filebox bs3-primary">
								
								<label for="file_0" class="col-sm-2 control-label">파일1</label>
								<div class="col-sm-10">
									<input type="text" class="upload-name" value="파일 찾기" readonly />
									<label for="file_0" class="control-label">찾아보기</label>
									<input type="file" name="files" id="file_0" class="upload-hidden" onchange="changeFilename(this)" />
									
									<button type="button" onclick="addFile()" class="btn btn-bordered btn-xs visible-xs-inline visible-sm-inline visible-md-inline visible-lg-inline">							
										<i class="fa fa-plus" aria-hidden="true"></i>
									</button>	
								
									<button type="button" onclick="removeFile(this)" class="btn btn-bordered btn-xs visible-xs-inline visible-sm-inline visible-md-inline visible-lg-inline">
										<i class="fa fa-minus" aria-hidden="true"></i>
									</button>
								</div>
							</div>
							<div id="btnDiv">
								<button  type="submit" class="btn btn-sm btn-primary">작성</button>
								<button type="button" class="btn btn-sm btn-primary" onclick="to_list();">목록</button>
							</div>
						</form>
					</div>
				</main>
				<jsp:include page="/WEB-INF/footer.jsp"></jsp:include>
			</div>
		</div>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="js/scripts.js"></script>
	</body>
</html>