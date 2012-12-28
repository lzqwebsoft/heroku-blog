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

//当检索条件改变时，更新博客列表
function select_condition_changed() {
	var type_id=$("#article_type :selected").val();
	var key_word=$("#key_words").val();
	$.ajax({
		url: $("#context-path").text()+"/article/select.html",
		type: "post",
		data: {
		    "articleTypeId": type_id,
		    "title": key_word
		},
		success: function(data, status) {
			$("#article_list_table").html(data);
		},
		error: function(xhr, strError, errorObj) {
	    	alert(errorObj);
	    }
	});
}