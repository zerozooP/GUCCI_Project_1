<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="/WEB-INF/header.jsp"></jsp:include>
		<script src="/js/summernote/summernote-lite.js"></script>
		<script src="/js/summernote/lang/summernote-ko-KR.js"></script>
		<link rel="stylesheet" href="/css/summernote/summernote-lite.css">
		<title>글 수정</title>
		<script>
			
			const strFileList = "${fileList}"; 				/*[ Controller에서 model로 받은 fileList ]*/
			const arrFileList = strFileList.split(", "); 	/*[ split 으로 나눠 배열로 저장 ]*/
			const fileList = arrFileList.length; 			/*[ 리스트의 갯수를 가져옴 ]*/
			let fileIdx = strFileList=="[]" ? 0 : fileList; /*[ Controller에서 model로 받은 fileList 값이 없다면 0으로 선언 ]*/
			
			function addFile(){
				
				const fileDivs = $('div[data-name="fileDiv"]');
				if(fileDivs.length > 2 ) {
					alert("파일은 최대 세 개까지 업로드 할 수 있습니다.");
					return false;
				}
				document.getElementById("changeYn").value = "Y";
				
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
				
				document.getElementById("changeYn").value = "Y";

				
				const prevTag = $(elem).prev().prop("tagName");
				if (prevTag === 'BUTTON') {
					const file = $(elem).prevAll('input[type="file"]');
					const filename = $(elem).prevAll('input[type="text"]');
					file.val('');
					filename.val('파일 찾기');
					
					$(elem).prevAll('input[name="fileIdxs"]').remove();
					return false;
				}
				
				const target = $(elem).parents('div[data-name="fileDiv"]');
				target.remove();
			}
			
			function changeFilename (file){
				
				document.getElementById("changeYn").value = "Y";

				
				file = $(file);
				const filename = file[0].files[0].name;
				console.log(filename);
				const target = file.prevAll('input.upload-name');
				target.val(filename);
				
				file.prevAll('input[name="fileIdxs"]').remove();
			}
			
			$(function() {
				
				$("#category").val("${edit.category}").prop("selected", true); // 글 카테고리 동적 selected
				
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
					<h1 class="text-center">Edit Form</h1>
		            <div class="container">
						<form action="/bbs_edit" method="post" enctype="multipart/form-data">
							<input type="hidden" id="changeYn" name="changeYn" value="N" />
							<input type="hidden" name="num" value=${edit.num}>
							<input type="hidden" name="ctgr" value='${category}'>
							<div class="mb-3">
								<label for="title">제목</label>
								<input type="text" class="form-control" name="title" id="title" value=${edit.title} required>
							</div>
							
							<label for="category">카테고리</label>
							<select class="form-select" id="category" name="category" required>
							  	<option value="FreeBoard">자유게시판</option>
							  	<option value="meetingLog">회의록</option>
							  	<option value="something">something</option>
							</select>
							
							<div class="mb-3">
								<label for="reg_id">작성자</label>
								<input type="text" class="form-control" name="uid" id="uid" value=${edit.uid} disabled>
							</div>
							<div class="mb-3">
								<label for="summernote">내용</label>
								<textarea class="form-control" name="content" id="summernote" required>${edit.content}</textarea>
							</div>
							
							<c:set value="${fileList}" var="filelist"/>
							
							<!-- 저장된 파일이 없는 파일 영역 -->
							<c:if test="${empty filelist}">
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
							</c:if>
							
							<!-- 저장된 파일이 있는 파일 영역 -->
							<c:if test="${!empty filelist}">
								<!-- 파일 업로드 -->
								<c:forEach var="files" items="${fileList}" varStatus="status">
									<div data-name="fileDiv" class="form-group filebox bs3-primary">
										<label for="file_${status.index}" class="col-sm-2 control-label">파일${status.count}</label>
										<div class="col-sm-10">
											<input type="hidden" name="fileIdxs" value="${files.idx}" />
											<input type="text" class="upload-name" value="${files.originalName}" readonly />
											<label for="file_${status.index}" class="control-label">찾아보기</label>
											<input type="file" name="files" id="file_${status.index}" class="upload-hidden" onchange="changeFilename(this)" />
										
											<c:if test="${status.first}">
												<button type="button" onclick="addFile()" class="btn btn-bordered btn-xs visible-xs-inline visible-sm-inline visible-md-inline visible-lg-inline">
													<i class="fa fa-plus" aria-hidden="true"></i>
												</button>
											</c:if>
											
											<button type="button" onclick="removeFile(this)" class="btn btn-bordered btn-xs visible-xs-inline visible-sm-inline visible-md-inline visible-lg-inline">
												<i class="fa fa-minus" aria-hidden="true"></i>
											</button>
										</div>
									</div>
								</c:forEach>
							</c:if>
							
							
							<div id="btnDiv">
								<button type="submit" class="btn btn-sm btn-primary">수정</button>
								<a href="/bbs_detail/${edit.num}/${category}" class="btn btn-sm btn-primary" >취소</a>
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