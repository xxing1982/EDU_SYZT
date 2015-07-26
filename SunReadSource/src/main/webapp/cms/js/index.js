$(document).ready(function(){
	TopNav();

    
});

function TopNav(){
	//bind navigate event 
	$(".nav-sidebar li").on("click", function(){
		$(".nav-sidebar").find(".active").removeClass("active");
		$(this).addClass("active");
	});

	var urlStr = window.location.href;
	if (urlStr.match("/book")) {
		$(".nav-sidebar li").eq(1).addClass("active");
	}
	else if (urlStr.match("/objectivequestion")){
		$(".nav-sidebar li").eq(2).addClass("active");
	}
	else if (urlStr.match("/subjectivequestion")){
		$(".nav-sidebar li").eq(3).addClass("active");
	}
	else if (urlStr.match("/region")){
		$(".nav-sidebar li").eq(4).addClass("active");
	}
	else if (urlStr.match("/edugroup")){
		$(".nav-sidebar li").eq(5).addClass("active");
	}
	else if (urlStr.match("/school")){
		$(".nav-sidebar li").eq(6).addClass("active");
	}
	else if (urlStr.match("/campus")){
		$(".nav-sidebar li").eq(7).addClass("active");
	}
	else if (urlStr.match("/clazz")){
		$(".nav-sidebar li").eq(4).addClass("active");
	}
	else if (urlStr.match("/teacher")){
		$(".nav-sidebar li").eq(4).addClass("active");
	}
	else if (urlStr.match("/student")){
		$(".nav-sidebar li").eq(4).addClass("active");
	}
	else{
		$(".nav-sidebar li").eq(0).addClass("active");
	}
}