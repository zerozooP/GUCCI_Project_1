<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<jsp:include page="/WEB-INF/header.jsp"></jsp:include>
		<style>
			a { text-decoration:none }
			a:link { color : black; }
			a:visited { color : #bc3cbc; }
			a:hover { color : red; }
		</style>
		<title>프로젝트 게시판</title>
		<script>
			window.onload = function(){
				$("#ctgr")[0].innerHTML = "${category}"
			}
		
			

			function bbs_add(uid){
				if (uid == "") {
					alert("로그인 후 이용가능합니다.")
					location.href="/login";
				} else{
				location.href="/bbs_add";
				}
			}
		</script>
	</head>
	<body>
		<jsp:include page="/WEB-INF/topnav.jsp"></jsp:include>
		<div id="layoutSidenav">
		
			<jsp:include page="/WEB-INF/sidenav.jsp"></jsp:include>
			<div id="layoutSidenav_content">
				<main>
					<div class="container">
						<br>
						<br>
						<h1 class="text-center">Project Board!</h1>
						<h2 class="text-center" id="ctgr"></h2>
						<br>
						<br>
						<table class="table table-hover table-striped text-center" style="border: 1px solid;">
							<thead>
								<tr>
									<th class="col-2">번호</th>
									<th class="col-4">제목</th>
									<th class="col-2">글쓴이</th>
									<th class="col-2">작성일</th>
									<th class="col-1">조회</th>
									<th class="col-1">좋아요</th>
								</tr>
							</thead>
							<tbody>
								<c:set value="${category}" var="category"/>
								<c:forEach items="${pageInfo.list}" var="list">
									<c:if test="${list.category==category}">
										<tr>
											<td>${list.num}</td>
											<td><a href="/bbs_detail/${list.num}/${category}">${list.title} [${list.reply}]</a></td>
											<td>${list.uid}</td>
											<td>${list.date}</td>
											<td>${list.cnt}</td>
											<td>${list.liked}</td>
										</tr>
									</c:if>
									<c:if test="${category=='all'}">
										<tr>
											<td>${list.num}</td>
											<td><a href="/bbs_detail/${list.num}/${category}">${list.title} [${list.reply}]</a></td>
											<td>${list.uid}</td>
											<td>${list.date}</td>
											<td>${list.cnt}</td>
											<td>${list.liked}</td>
										</tr>
									</c:if>
								</c:forEach>
							</tbody>
						</table>
							
						<div id="pagination">
						   <c:forEach var="i" items="${pageInfo.navigatepageNums}">
						      <c:choose>
						         <c:when test="${i==pageInfo.pageNum}">	<!-- (c:when = if) 현재 보는 페이지는 링크를 안걸고 -->
						            [${i}] 
						         </c:when>
						         <c:otherwise>							<!-- (c:otherwise = else) 다른 페이지는 링크 걸음 -->
						            [<a href="/bbs_list/${i}">${i}</a>] 
						         </c:otherwise>
						      </c:choose> 
						   </c:forEach>
						</div>
						
						<br>
						<button type="button" class="btn btn-outline-primary" onclick="bbs_add('${uid}');">글쓰기</button>
					</div>
				</main>
				<jsp:include page="/WEB-INF/footer.jsp"></jsp:include>
			</div>
		</div>
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="js/scripts.js"></script>
	</body>
</html>