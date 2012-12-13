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
			  height: 300,
			  title: "关于本站点",
			  zIndex: 1023,
			  noButton: '确定'
		   });
		$(dialog_body).html("<h2 style='margin:0 0 5px 10px;'>Welcome you access my zone!</h2>"+
		                        "<p style='margin:0 0 5px 15px;'>In building...</p>"+
								"<p style='margin:0 0 5px 15px;'>Connect with me: <a style='color:red' href='https://twitter.com/lzqwebsoft'>Twitter</a></p>"+
								"<p style='margin:0 0 5px 15px;'>本站点使用Heroku云平台建立，仅用于学习Java。</p>"+
								"<p style='margin: 0px 0px 20px 15px;'>使用Servlet用例: <a style='color:red' href='/simple'>Simple</a>。</p>"+
								"<hr style='margin-bottom: 5px;' />"+
								"<p style='font-size:12px; margin-bottom:20px; text-align:center;'>Copyright © 2012,"+
								"Powered by <a style='color:red' href='http://www.heroku.com'>Heroku</a></p>");
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
			         },
			         error: function(xhr, strError, errorObj) {
			        	 alert(errorObj);
			         }
			     });
			  }
		   });
		var dialog_body = $(dialog_body);
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
		
		var password_p = $("<p>密码：</p>").css("margin-bottom","25px");
		var password_field = $("<input type='password' name='password' />").css({
			"width": "150px",
			"height": "22px"
		});
		password_p.append(password_field);
		
		form_tag.append(info_p);
		form_tag.append(name_p);
		form_tag.append(password_p);
		
		dialog_body.append(form_tag);
		return false;
	});
});