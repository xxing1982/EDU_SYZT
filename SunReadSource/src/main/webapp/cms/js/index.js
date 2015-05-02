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
	else if (urlStr.match("/user")) {
		$(".nav-sidebar li").eq(2).addClass("active");
	}
	else if (urlStr.match("/objectivequestion")){
		$(".nav-sidebar li").eq(3).addClass("active");
	}
	else if (urlStr.match("/subjectivequestion")){
		$(".nav-sidebar li").eq(4).addClass("active");
	}
	else{
		$(".nav-sidebar li").eq(0).addClass("active");
	}
}