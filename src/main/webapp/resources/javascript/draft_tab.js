// 实现草稿翻页
function draft_page_update(url, pageNo) {
	$.ajax({
		url: url,
		type: "post",
		data: {
		    "pageNo": pageNo
		},
		success: function(data, status) {
			$("#draft_list").html(data);
		},
		error: function(xhr, strError, errorObj) {
	    	alert(errorObj);
	    }
	});
}

//删除草稿
function confirm_draft_delete(message, id, pageNo) {
	var option = confirm(message);
	if(option) {
		$.ajax({
			url: $("#context-path").text()+"/delete/draft/"+id+".html",
			type: "post",
			data: {
			    "pageNo": pageNo
			},
			success: function(data, status) {
			    $("#draft_list").html(data);
			},
			error: function(xhr, strError, errorObj) {
		    	alert(errorObj);
		    }
		});
	}
}