<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
		<jsp:include page="/WEB-INF/header.jsp"></jsp:include>

        <title>Password Reset - SB Admin</title>

        <script>
			function find(){
		
				var serData = $('#findform').serialize();
				$.ajax({
					url:'/findpw',
					method:'post',
					cache:false,
					data:serData,
					dataType:'json',
					success:function(res){
						alert(res.findpw ? '임시비밀번호 발급완료':'실패');
						location.href='/logout';
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
                                    <div class="card-header"><h3 class="text-center font-weight-light my-4">Password Recovery</h3></div>
                                    <div class="card-body">
                                        <div class="small mb-3 text-muted">Enter your email address and ID.</div>
                                        <form id="findform" onsubmit="return find();">
                                            <div class="form-floating mb-3">
                                                <input class="form-control" name="email" id="email" type="email" placeholder="Email address" />
                                                <label for="email">Email address</label>
                                            </div>
                                            <div class="form-floating mb-3">
                                                <input class="form-control" name="uid" id="uid" type="text" placeholder="Your ID" />
                                                <label for="email">ID</label>
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