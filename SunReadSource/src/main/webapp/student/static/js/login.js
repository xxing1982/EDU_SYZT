$(document).ready(function(){
	$("#username").focus();
	$(document).keydown(function(event){
		//press Enter
		if(event.keyCode == 13){
			$(".btn-login").click();
		}
	});
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
                        var roleForwardMap = {
                            ROLE_USER : {
                                idKey: 'serId',
                                url: 'student/protype/index.html'                            
                            },
                            ROLE_TEACHER : {
                                idKey: 'teacherId',
                                url: 'teacher/prototype/index.html'                          
                            },
                            ROLE_STUDENT : {
                                idKey: 'userId',
                                url: 'student/protype/index.html'                        
                            },
                            ROLE_PARENT : {
                                idKey: 'teacherId',
                                url: 'teacher/prototype/index.html'                           
                            },
                            ROLE_CMS : {
                                idKey: 'cmsId',
                                url: 'cms/index.html'                         
                            },
                            ROLE_SYSTEM_SUPER_ADMIN : {
                                idKey: 'cmsId',
                                url: 'cms/index.html'                    
                            },
                            ROLE_SYSTEM_ADMIN : {
                                idKey: 'cmsId',
                                url: 'cms/index.html'                        
                            },
                            ROLE_SCHOOLE_DISTRICT : {
                                idKey: 'adminId',
                                url: 'teacher/prototype/index.html'                
                            },
                            ROLE_GROUP : {
                                idKey: 'adminId',
                                url: 'teacher/prototype/index.html'           
                            }
                        }
                        var role = dataLogin.roles[0],
                            roleForward = roleForwardMap[role.name];
                        sessionStorage.setItem(roleForward.idKey, dataLogin.id);
                        window.location.href = roleForward.url;
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