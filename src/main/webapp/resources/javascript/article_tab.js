// 实现翻页
function article_page_update(url, typeId, title, pageNo) {
	$.ajax({
		url: $("#context-path").text()+"/article/select.html",
		type: "post",
		data: {
		    "articleTypeId": typeId,
		    "title": title,
		    "pageNo": pageNo
		},
		success: function(data, status) {
			$("#article_list_table").html(data);
		},
		error: function(xhr, strError, errorObj) {
	    	alert(errorObj);
	    }
	});
}

// 删除文章
function confirm_article_delete(message, id, typeId, title, pageNo) {
	var option = confirm(message);
	if(option) {
		$.ajax({
			url: $("#context-path").text()+"/delete/article/"+id+".html",
			type: "post",
			data: {
			    "articleTypeId": typeId,
			    "title": title,
			    "pageNo": pageNo
			},
			success: function(data, status) {
			    $("#article_list_table").html(data);
			},
			error: function(xhr, strError, errorObj) {
		    	alert(errorObj);
		    }
		});
	}
}

// 更新文章是否允许评论
function update_allow_comment(id, allowComment, obj){
	var obj = $(obj);
	$.ajax({
		url: $("#context-path").text()+"/update/allow_comment/"+id+".html",
		data: {"allowComment": allowComment},
		type: "post",
		success: function(data, status) {
			if(allowComment) {
				obj.text("禁止评论");
				obj.attr("onclick", "update_allow_comment('"+id+"', false, this);");
			} else {
				obj.text("允许评论");
				obj.attr("onclick", "update_allow_comment('"+id+"', true, this);");
			}
		},
		error: function(xhr, strError, errorObj) {
	    	alert(errorObj);
	    }
	});
}

//更新文章设置置顶
function update_set_top(id, isTop, obj){
	var obj = $(obj);
	$.ajax({
		url: $("#context-path").text()+"/update/is_top/"+id+".html",
		data: {"isTop": isTop},
		type: "post",
		success: function(data, status) {
			if(isTop) {
				obj.text("取消置顶");
				obj.attr("onclick", "update_set_top('"+id+"', false, this);");
			} else {
				obj.text("置顶");
				obj.attr("onclick", "update_set_top('"+id+"', true, this);");
			}
		},
		error: function(xhr, strError, errorObj) {
	    	alert(errorObj);
	    }
	});
}