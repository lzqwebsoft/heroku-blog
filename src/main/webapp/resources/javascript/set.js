$(function() {
	var tagBoxs = [];
	$("#tabs li a").each(function(idx){
		var styleId = $(this).attr("id");
		var index = styleId.lastIndexOf("_");
		tagBoxs[idx]= styleId.substring(0, index);
	});
	$("#tabs li a").click(function() {
		$("#tabs li a").removeClass("selected");
		var obj = $(this);
		var styleId = obj.attr("id");
		obj.addClass("selected");
		var index = styleId.lastIndexOf("_");
		var selectBox = styleId.substring(0, index)
		$.each(tagBoxs, function(idx, item) {
			if(item==selectBox)
				$("#"+item).show();
			else
				$("#"+item).hide();
		});
	});
	
	// 保存博客的配置信息
	$("#saveBlogInfoButton").click(function(){
		// 将kindeditor的内容同步到textarea中
		KE.sync("blog_description");
		var form_data = $("#blogInfoForm").serialize();
		$.ajax({
			url: "handleInfo.html",
			type: "post",
			data: form_data,
			success: function(data, status) {
			    if(data.status=="FAILURE") {
			    	var error_ul = $("<ul></ul>");
			    	$.each(data.messages, function(idx, message) {
			    		error_ul.append("<li>"+message+"</li>")
			    	});
			    	$("#configure-messages").hide().text("");
			    	$("#configure-errors").append(error_ul).show();
			    } else {
			    	$("#configure-errors").hide().html("");
			    	$("#configure-messages").text(data.messages).show();
			    }
		    },
		    error: function(xhr, strError, errorObj) {
		    	alert(errorObj);
		    }
		});
	});
});