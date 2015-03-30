$(document).ready(function(){
	TopNav();
});

function TopNav(){
	//bind navigate event 
	$(".nav-sidebar li").on("click", function(){
		$(".nav-sidebar").find(".active").removeClass("active");
		$(this).addClass("active");
	});
}