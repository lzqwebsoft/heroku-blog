// 新增评论
function add_article_comment() {
	KE.sync("comment_content");
	var form_data = $("#commentAddForm").serialize();
	$.ajax({
		url: $("#context-path").text()+"/comment/add.html",
		data: form_data,
		type: "post",
		success: function(data, status) {
		    var messages_div = $("#add-comment-info-div");
			if(data.status!=null) {
				// 当评论后台验证失败
				var info_context = "<ul>";
				$.each(data.messages, function(idx, element) {
					info_context += "<li>" + element + "</li>";
				});
				info_context += "</ul>";
				messages_div.css({
					color: "red"
				}).show().html(info_context);
			} else {
				// 当评论后台验证成功
				messages_div.css("color", "#0000FF").show().html("评论成功！");
				$("#article_comment").html(data);
				$("#commentAddForm :text").val("");
				$("#prompt_replay_info").hide();
				$("#parent_comment_id").val("");
				KE.html("comment_content", "");
			}
		},
		error: function(xhr, strError, errorObj) {
	    	alert(errorObj);
	    }
	});
}

// 删除评论
function delete_article_comment(commentId) {
	$.ajax({
		url: $("#context-path").text()+"/comment/delete/"+commentId+".html",
		type: "post",
		success: function(data, status) {
			$("#article_comment").html(data);
		},
		error: function(xhr, strError, errorObj) {
	    	alert(errorObj);
	    }
	});
}