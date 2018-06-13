// 判断浏览器
function browserVersions(){   
	var u = navigator.userAgent, app = navigator.appVersion;
	return {
		trident : u.indexOf('Trident') > -1, // IE内核
		presto : u.indexOf('Presto') > -1, // opera内核
		webKit : u.indexOf('AppleWebKit') > -1, // 苹果、谷歌内核
		gecko : u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, // 火狐内核
		mobile : !!u.match(/AppleWebKit.*Mobile.*/), // 是否为移动终端 
		ios : !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), // ios终端
		android : u.indexOf('Android') > -1, // android终端或者uc浏览器
		iPhone : u.indexOf('iPhone') > -1, // 是否为iPhone或者QQHD浏览器
		iPad: u.indexOf('iPad') > -1, // 是否iPad
		webApp : u.indexOf('Safari') == -1,// 是否web应该程序，没有头部与底部
		google:u.indexOf('Chrome')>-1
    };
}

$(function(){
	// 关于页面
	$("#about-button").click(function(event) {
		event.preventDefault();
		if($('#aboutDailog').find(".modal-dialog").length > 0) {
			$('#aboutDailog').modal('show');
		} else {
			$('#aboutDailog').load($(this).attr("href"), function() {
				$('#aboutDailog').modal('show');
			});
		}
	});
	// PC端添加背景
	var browser = browserVersions();
	if(!browser.mobile && !browser.android && !browser.iPhone && !browser.iPad) {
		$('body').append('<script type="text/javascript" color="249,145,87" opacity="0.7" zIndex="-2" count="200" src="//cdn.bootcss.com/canvas-nest.js/1.0.1/canvas-nest.min.js"></script>');
	}
	// 回到顶端
	$(window).scroll(function(){
		if($(this).scrollTop() > 300)
			$('#gototop').fadeIn();
		else
			$('#gototop').fadeOut();
    });
	$('#gototop').click(function () {
		$("html, body").animate({
			scrollTop: 0
		}, 600);
	});
});