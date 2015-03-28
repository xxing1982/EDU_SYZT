$(document).ready(function(){
	TopNav();
});

//top navigate event
function TopNav(){
	//bind navigate event 
	$(".nav-underline li a").on("click", function(){
		$(".nav-underline").find(".myActive").removeClass("myActive");
		$(this).parent().addClass("myActive");
	});
	//show underline
	var urlStr = window.location.href;
	if (urlStr.match("readingCenter")) {
		$(".nav li").eq(1).addClass("myActive");
	}
	else if (urlStr.match("readingSea")) {
		$(".nav li").eq(2).addClass("myActive");
	}
	else if (urlStr.match("readingTraining")) {
		$(".nav li").eq(3).addClass("myActive");
	}
	else if (urlStr.match("prizeCenter")) {
		$(".nav li").eq(4).addClass("myActive");
	}
	else{
		$(".nav li").eq(0).addClass("myActive");
	}
}

//left navigate event in page reading center
function ReadingCenterLeftNav(){
	$(".list-group a").on("click", function(){
		$(".list-group").find(".myActive").removeClass("myActive");
		$(this).addClass("myActive");
	});

	var urlStr = window.location.href;
	if (urlStr.match("readingCenter/myNote")) {
		$(".list-group a").eq(1).addClass("myActive");
	}
	else if (urlStr.match("readingCenter/myEvaluating")) {
		$(".list-group a").eq(2).addClass("myActive");
	}
	else if (urlStr.match("readingCenter/myEvaluate")) {
		$(".list-group a").eq(3).addClass("myActive");
	}
	else{
		$(".list-group a").eq(0).addClass("myActive");
	}
}

//Set reading center body height
function SetReadingCenterBodyHeight(){
	$("#readingCenter-page").css('min-height' ,document.documentElement.clientHeight);
	$(window).resize(function(){
		$("#readingCenter-page").css('min-height' ,document.documentElement.clientHeight);
	});
}

function SetReadingCenterRightHeight(){
	$(".readingCenter-right").css('min-height' ,document.documentElement.clientHeight - 100);
	$(window).resize(function(){
		$(".readingCenter-right").css('min-height' ,document.documentElement.clientHeight - 100);
	});
}

//reading center -> my bookshelf -> book list
function SetBookList(){
	$(".bookshelf-tab").on("click", function(){
		$(".bookshelf-tab.myActive").removeClass("myActive");
		$(this).addClass("myActive");
	});

	var urlStr = window.location.href;
	if (urlStr.match("readingCenter/addBook/advanced")) {
		$(".readingCenter-right-body .find .bookshelf-tab").eq(1).addClass("myActive");
	}
	else if (urlStr.match("readingCenter/addBook/popular")) {
		$(".readingCenter-right-body .find .bookshelf-tab").eq(2).addClass("myActive");
	}
	else if (urlStr.match("readingCenter/addBook/recommend")) {
		$(".readingCenter-right-body .find .bookshelf-tab").eq(3).addClass("myActive");
	}
	else{
		$(".readingCenter-right-body .find .bookshelf-tab").eq(0).addClass("myActive");
	}
}

