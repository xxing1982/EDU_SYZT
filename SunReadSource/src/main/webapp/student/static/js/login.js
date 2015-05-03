$(document).ready(function(){
	$(".btn-login").on("click", function(){
		var username = $("#username").val();
		var password = $("#password").val();
		$(this).attr("disabled", true).text("登录中...");
		$.ajax({
			type: 'POST',
			url: '/oauth/token?grant_type=password&username=' + username + '&password=' + password,
			beforeSend: function(request) {
				request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
				request.setRequestHeader("Authorization", "Basic MzUzYjMwMmM0NDU3NGY1NjUwNDU2ODdlNTM0ZTdkNmE6Mjg2OTI0Njk3ZTYxNWE2NzJhNjQ2YTQ5MzU0NTY0NmM=");
			},
			success: function(data){
				sessionStorage.setItem("access_token", data.access_token);
				//sessionStorage.setItem("token_type", data.token_type);
				//sessionStorage.setItem("refresh_token", data.refresh_token);
				//sessionStorage.setItem("expires_in", data.expires_in);
				//sessionStorage.setItem("scope", data.scope);

				$.ajax({
					type: 'GET',
					url: '/api/user/fromtoken',
					beforeSend: function(request) {
						request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
						request.setRequestHeader("Authorization", "bearer " + data.access_token);
					},
					success: function(dataLogin){
						for(var i = 0; i < dataLogin.roles.length; i++){
							if (dataLogin.roles[i].id == 2) {
								sessionStorage.setItem("teacherId", dataLogin.id);
								window.location.href="teacher/prototype/index.html";
								return;
							}
							if (dataLogin.roles[i].id == 5) {
								sessionStorage.setItem("cmsId", dataLogin.id);
								window.location.href="cms/index.html";
								return;
							}
						}
						sessionStorage.setItem("userId", dataLogin.id);
						window.location.href="student/protype/index.html";
					}
				});
			},
			error : function(error){
				alert('用户名或密码错误');
				$(".btn-login").attr("disabled", false).text("立即登录");
			},
		});
});
});