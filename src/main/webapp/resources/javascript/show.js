// 删除文章
function confirm_article_delete(message, id) {
	var option = confirm(message);
	if(option) {
		$.ajax({
			url: $("#context-path").text()+"/delete/"+id+".html",
			type: "post",
			success: function(data, status) {
				window.location=$("#context-path").text();
			},
			error: function(xhr, strError, errorObj) {
		    	alert(errorObj);
		    }
		});
	}
}

// 回复评论，用于产生子评论
function replay_comment(parent_id, reviewer){
	// 设置父评论id
	$("#parent_comment_id").val(parent_id);
	$("#info_prompt_message").text("回复："+reviewer);
	$("#prompt_replay_info").show("normal");
}
// 取消回复评论
function cancel_replay_comment() {
	$("#prompt_replay_info").hide();
	$("#parent_comment_id").val("");
}