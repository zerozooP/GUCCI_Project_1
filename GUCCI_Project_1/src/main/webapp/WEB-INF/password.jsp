<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
		<jsp:include page="/WEB-INF/header.jsp"></jsp:include>

        <title>Password Reset - SB Admin</title>

        <script>
			function update(){
				if(document.getElementById("pwd").value.length<8){
					alert("비밀번호를 8자리 이상 입력하세요");
					return;
				}
		
				var serData = $('#updateform').serialize();
				$.ajax({
					url:'/update',
					method:'post',
					cache:false,
					data:serData,
					dataType:'json',
					success:function(res){
						alert(res.update ? '회원정보 수정완료':'실패');
						location.href='/index';
					},
					error:function(xhr,status,err){
						alert('에러'+err);
					}
				});
				
				return false;
			}
		</script>
</head>
    <body class="bg-primary">
        <div id="layoutAuthentication">
            <div id="layoutAuthentication_content">
                <main>
                    <div class="container">
                        <div class="row justify-content-center">
                            <div class="col-lg-5">
                                <div class="card shadow-lg border-0 rounded-lg mt-5">
                                    <div class="card-header"><h3 class="text-center font-weight-light my-4">Password Update</h3></div>
                                    <div class="card-body">
                                        <div class="small mb-3 text-muted">Enter your New password.</div>
                                        <form id="updateform" onsubmit="return update();">
                                            <div class="form-floating mb-3">
                                                <input class="form-control" name="pwd" id="pwd" type="password" placeholder="password" />
                                                <label for="pwd">Password</label>
                                            </div>
                                            <div class="d-flex align-items-center justify-content-between mt-4 mb-0">
                                                <a class="small" href="login">Return to login</a>
                                                <button class="btn btn-primary" type="submit">Reset Password</button>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="card-footer text-center py-3">
                                        <div class="small"><a href="register">Need an account? Sign up!</a></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
            <div id="layoutAuthentication_footer">
				<jsp:include page="/WEB-INF/footer.jsp"></jsp:include>
            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>
        <script src="/js/scripts.js"></script>
    </body>
</html>