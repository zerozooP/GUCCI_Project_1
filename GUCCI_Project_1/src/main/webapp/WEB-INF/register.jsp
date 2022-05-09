<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
		<jsp:include page="/WEB-INF/header.jsp"></jsp:include>
		
        <title>Register - SB Admin</title>
       
		<script>
			function adduser(){
				if(document.getElementById("uid").value==""){
			        alert("아이디를 입력하세요");
			        return;
			    }
				if(document.getElementById("pwd").value==""){
			        alert("비밀번호를 입력하세요");
			        return;
			    }
				if(document.getElementById("phone").value==""){
			        alert("전화번호를 입력하세요");
			        return;
			    }
				if(document.getElementById("name").value==""){
			        alert("이름을 입력하세요");
			        return;
			    }
				if(document.getElementById("email").value==""){
			        alert("이메일을 입력하세요");
			        return;
			    }
				if(document.getElementById("address").value==""){
			        alert("주소를 입력하세요");
			        return;
			    }
				if(document.getElementById("uid").value.length<4){
					alert("아이디를 4자리 이상 입력하세요");
					return;
				}
				if(document.getElementById("pwd").value.length<8){
					alert("비밀번호를 8자리 이상 입력하세요");
					return;
				}
		
				var serData = $('#addform').serialize();
				$.ajax({
					url:'/insertuser',
					method:'post',
					cache:false,
					data:serData,
					dataType:'json',
					success:function(res){
						alert(res.insertuser ? '회원가입 성공':'실패');
						location.href='/login';
					},
					error:function(xhr,status,err){
						alert('이미 가입된 아이디입니다'+err);
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
                            <div class="col-lg-7">
                                <div class="card shadow-lg border-0 rounded-lg mt-5">
                                    <div class="card-header"><h3 class="text-center font-weight-light my-4">Create Account</h3></div>
                                    <div class="card-body">
                                        <form id="addform" onsubmit="return adduser();">
                                            <div class="row mb-3">
                                                <div class="col-md-6">
                                                    <div class="form-floating mb-3 mb-md-0">
                                                        <input class="form-control" name="uid" id="uid" type="text" placeholder="Enter your ID" />
                                                        <label for="inputID">ID</label>
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-floating">
                                                        <input class="form-control" name="pwd" id="pwd" type="text" placeholder="Enter your Password" />
                                                        <label for="inputPassword">Password</label>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="form-floating mb-3">
                                                <input class="form-control" name="email" id="email" type="email" placeholder="name@example.com" />
                                                <label for="inputEmail">Email address</label>
                                            </div>
                                            <div class="form-floating mb-3">
                                                <input class="form-control" name="name" id="name" type="text" placeholder="Full Name" />
                                                <label for="inputEmail">Full Name</label>
                                            </div>
                                            <div class="form-floating mb-3">
                                                <input class="form-control" name="address" id="address" type="text" placeholder="Address" />
                                                <label for="inputEmail">Address</label>
                                            </div>
                                            <div class="form-floating mb-3">
                                                <input class="form-control" name="phone" id="phone" type="text" placeholder="010-xxxx-xxxx" />
                                                <label for="inputEmail">Phone Number</label>
                                            </div>
                                            <div class="mt-4 mb-0">
                                                <div class="d-grid"><input class="btn btn-primary btn-block" type="submit" value="Create Account"></div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="card-footer text-center py-3">
                                        <div class="small"><a href="login">Have an account? Go to login</a></div>
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