$(function() {
	$("#update-captcha-link").click(function() {
		$("#captcha-image").attr("src", "captcha.jpg?"+ Math.floor(Math.random()*100));
	});
});