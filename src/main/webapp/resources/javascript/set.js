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

function edit_article_type(id){
	var span = document.getElementById(id);
	span.style.display = "none";
	var a_links = span.getElementsByTagName("a");
	var type_text = a_links[0].innerHTML;
	var parent_tag = span.parentNode;
	
	var brother_span = document.createElement("span");
	var edit_field = document.createElement("input");
	edit_field.type="text"
	edit_field.maxlength="30";
	edit_field.style.padding="2px 4px";
	edit_field.style.border="1px solid #CCCCCC";
	edit_field.value = type_text;
	brother_span.appendChild(edit_field);
	
	var blank_text = document.createTextNode(" ");
	brother_span.appendChild(blank_text);
	
	var save_link = document.createElement("a");
	save_link.href="javascript:void(0)";
	save_link.innerHTML = "保存"
	brother_span.appendChild(save_link);
	
	var blank_text = document.createTextNode(" ");
	brother_span.appendChild(blank_text);
	
	var cancel_link = document.createElement("a");
	cancel_link.href="javascript:void(0)";
	cancel_link.innerHTML = "取消"
	cancel_link.onclick = function() {
		parent_tag.removeChild(brother_span);
		span.style.display = "";
	}
	
	brother_span.appendChild(cancel_link);
	
	parent_tag.appendChild(brother_span);
}