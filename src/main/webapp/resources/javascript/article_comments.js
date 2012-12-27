// 新增评论
function add_article_comment() {
	KE.sync("comment_content");
	var form_data = $("#commentAddForm").serialize();
	$.ajax({
		url: $("#context-path").text()+"/comment/add.html",
		data: form_data,
		type: "post",
		success: function(data, status) {
			$("#article_comment").html(data);
			$("#commentAddForm :text").val("");
			$("#prompt_replay_info").hide();
			$("#parent_comment_id").val("");
			KE.html("comment_content", "");
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