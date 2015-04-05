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
			success: function(date){
				alert(data);
			},
			error : function(error){
				alert(error);
			},
		});
	});
});