// JavaScript Document
$(function() {
	$(window).scroll(function(){
		if(getScrollPos().y > 300)
			  $("#gototop").show();
			else
			  $("#gototop").hide(); 
	});
	
	$("#about-link").click(function() {
		// 创建一个弹出对话框
		var dialog_body=createFrame({
			  width: 500,
			  height: 250,
			  title: "关于本站点",
			  zIndex: 1023,
			  noButton: '确定'
		   });
		$.ajax({
			url: $("#context-path").text()+"/about.html",
			type: "post",
			success: function(data, status) {
			    $(dialog_body).html(data);
		    },
		    errror: function(xhr, strError, errorObj) {
		    	alert(errorObj);
		    }
		});
//		"<h2 style='margin:0 0 5px 10px;'>Welcome you access my zone!</h2>"+
//		                        "<p style='margin:0 0 5px 15px;'>In building...</p>"+
//								"<p style='margin:0 0 5px 15px;'>Connect with me: <a style='color:red' href='https://twitter.com/lzqwebsoft'>Twitter</a></p>"+
//								"<p style='margin:0 0 5px 15px;'>本站点使用Heroku云平台建立，仅用于学习Java。</p>"+
//								"<p style='margin: 0px 0px 20px 15px;'>使用Servlet用例: <a style='color:red' href='/simple'>Simple</a>。</p>"+
//								"<hr style='margin-bottom: 5px;' />"+
//								"<p style='font-size:12px; margin-bottom:20px; text-align:center;'>Copyright © 2012,"+
//								"Powered by <a style='color:red' href='http://www.heroku.com'>Heroku</a></p>");
		return false;
	});
	
	// 创建一个登录窗体
	$("#login-link").click(function() {
		// 创建一个弹出对话框
		var dialog_body=createFrame({
			  width: 300,
			  height: 200,
			  title: "博主登录",
			  zIndex: 1023,
			  yesButton: '登录',
			  noButton: '取消',
			  buttonClick: function(obj){
			     click_login(obj);
			  }
		   });
		var dialog_body = $(dialog_body);
		// 设置当按下Enter键后触发提交表单登录事件
		dialog_body.keydown(function(event) {
			if(event.keyCode ==13) {
				click_login(dialog_body);
			}
		});
		dialog_body.css("text-align", "center");
		
		var form_tag = $('<form action="/login.html" method="post"></form>');
		
		var info_p = $("<p style='color:red'></p>").hide();
		
		var name_p = $("<p>帐号：</p>").css({
			"margin-top": "20px",
			"margin-bottom": "10px"
		});
		var name_field = $("<input type='text' name='username' />").css({
			"width": "150px",
			"height": "22px"
		});
		name_p.append(name_field);
		
		var password_p = $("<p id='password_p'>密码：</p>").css("margin-bottom","25px");
		var password_field = $("<input type='password' name='password' />").css({
			"width": "150px",
			"height": "22px"
		});
		password_p.append(password_field);
		
		var captcha_p = $("<p id='captcha_p' style='display: none;'>验证码：</p>").css({
			"margin-bottom": "25px",
		});
		var captcha_field = $("<input type='text' name='captcha' />").css({
			"width": "63px",
			"height": "22px",
			"margin-right": "5px"
		});
		var captcha_img = $("<img src='captcha.jpg' alt='换一张' title='换一张'/>").css({
			"vertical-align": "middle",
			"cursor": "pointer"
		});
		captcha_img.click(function() {
			$(this).attr("src", "captcha.jpg?"+ Math.floor(Math.random()*100));
		});
		captcha_p.append(captcha_field);
		captcha_p.append(captcha_img);
		
		form_tag.append(info_p);
		form_tag.append(name_p);
		form_tag.append(password_p);
		form_tag.append(captcha_p);
		
		dialog_body.append(form_tag);
		
		// 初始化对话框时，进行登录错误次数的判断
		var error_num = $("#error_login_count_p").text();
		if(error_num!=null&&$.trim(error_num).length>0&&Number(error_num)>=3) {
			password_p.css("margin-bottom","10px");
			captcha_p.show("normal");
		}
			
		return false;
	});
});

function click_login(obj){
	var loginDailog = $(obj);
    var form_data = loginDailog.find("form").serialize();
    $.ajax({
   	 url: $("#context-path").text()+"/login.html",
   	 type: "post",
   	 data: form_data,
   	 success: function(data, status) {
   	    if(data.status=='SUCCESS') {
   	    	// 登录成功后刷新页面
   	    	window.location.reload();
   	    } else {
   	    	loginDailog.find("form p:first-child").text(data.messages).show();
   	    }
   	    if(data.error_num!=null&&data.error_num>=3) {
   	    	loginDailog.find("#password_p").css("margin-bottom","10px");
   	    	loginDailog.find("#captcha_p").show("normal");
   	    }
   	    $("#error_login_count_p").text(data.error_num);
     },
     error: function(xhr, strError, errorObj) {
       	alert(errorObj);
     }
    });
}