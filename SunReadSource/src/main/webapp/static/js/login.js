$(document).ready(function(){
	$(".btn-login").on("click", function(){
		var username = $("#username").val();
		var password = $("#password").val();
		$.ajax({
			type: 'POST',
			url: 'http://localhost:8080/oauth/token?grant_type=password&username=' + username + '&password=' + password,
			beforeSend: function(request) {
				request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
				request.setRequestHeader("Authorization", "Basic MzUzYjMwMmM0NDU3NGY1NjUwNDU2ODdlNTM0ZTdkNmE6Mjg2OTI0Njk3ZTYxNWE2NzJhNjQ2YTQ5MzU0NTY0NmM=");
			},
			success: function(data){
				//sessionStorage.setItem("access_token", data.access_token);
				//sessionStorage.setItem("token_type", data.token_type);
				//sessionStorage.setItem("refresh_token", data.refresh_token);
				//sessionStorage.setItem("expires_in", data.expires_in);
				//sessionStorage.setItem("scope", data.scope);

				$.ajax({
					type: 'GET',
					url: 'http://localhost:8080/api/user/fromtoken',
					beforeSend: function(request) {
						request.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
						request.setRequestHeader("Authorization", "bearer " + data.access_token);
					},
					success: function(dataLogin){
						sessionStorage.setItem("userId", dataLogin.id);
						window.location.href="index.html";
					}
				});
			},
			error : function(error){
				alert('用户名或密码错误');
			},
		});
});
});