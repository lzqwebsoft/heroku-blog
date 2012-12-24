// 删除文章
function confirm_article_delete(message, id) {
	var option = confirm(message);
	if(option) {
		$.ajax({
			url: $("#context-path").text()+"/delete/"+id+".html",
			type: "post",
			success: function(data, status) {
			    window.location.reload();
			},
			error: function(xhr, strError, errorObj) {
		    	alert(errorObj);
		    }
		});
	}
}