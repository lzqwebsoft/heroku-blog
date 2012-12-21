// 添加博客类型功能
function add_article_type() {
	var articleType = $("#txtArticleType").val()
	$.ajax({
		url: "article_type/add.html",
		type: "post",
		data: {"article_type_name": articleType},
		success: function(data, status) {
			$("#type_list").html(data);
		},
		error: function(xhr, strError, errorObj) {
	    	alert(errorObj);
	    }
	});
}

// 删除功能
function confirm_article_type_delete(message, id) {
	var option = confirm(message)
	if(option) {
		$.ajax({
			url: "article_type/delete/"+id+".html",
			type: "post",
			success: function(data, status) {
				$("#type_list").html(data);
			},
			error: function(xhr, strError, errorObj) {
		    	alert(errorObj);
		    }
		});
	}
}

// 隐藏或显示功能
function disable_article_type(id, disable) {
	$.ajax({
		url: "article_type/disable/"+id+".html",
		type: "post",
		data: {"disable": disable},
		success: function(data, status) {
			$("#type_list").html(data);
		},
		error: function(xhr, strError, errorObj) {
	    	alert(errorObj);
	    }
	});
}

// 更新类型的名称功能
function edit_article_type(dom_id, type_id){
	var span = $("#"+dom_id);
	span.hide();
	var a_links = span.find("a:first");
	var type_text = a_links.text();
	var parent_tag = span.parent();
	
	var brother_span = $("<span></span>");
	var edit_field = $("<input type='text' maxlength='30' />");
	edit_field.css({
		padding : "2px 4px",
		border  : "1px solid #CCCCCC",
		marginRight: "5px"
	});
	edit_field.val(type_text);
	brother_span.append(edit_field);
	
	var save_link = $("<a href='javascript:void(0)' style='margin-right:5px;'>保存</a>");
	save_link.click(function() {
		var newTypeName = edit_field.val();
		if(newTypeName==null||$.trim(newTypeName)=="") {
			alert("类型名称不能为空！");
		}else {
			$.ajax({
				url: "article_type/update/"+type_id+".html",
				type: "post",
				data: {"article_type_name": newTypeName},
				success: function(data, status) {
					a_links.text(newTypeName);
				},
				error: function(xhr, strError, errorObj) {
					alert(errorObj);
				}
			});
		}
		brother_span.empty();
		span.show();
	});
	brother_span.append(save_link);
	
	var cancel_link = $("<a href='javascript:void(0)'>取消</a>");
	cancel_link.click(function() {
		brother_span.empty();
		span.show();
	});
	brother_span.append(cancel_link);
	
	parent_tag.append(brother_span);
}