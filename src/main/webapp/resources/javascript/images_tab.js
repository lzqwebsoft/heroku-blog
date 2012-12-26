function delete_image(message, id) {
	var option = confirm(message);
	if(option) {
		$.ajax({
			url: "images/delete/"+id+".html",
			type: "post",
			success: function(data, status) {
				$("#blog_images_list").html(data);
			},
			error: function(xhr, strError, errorObj) {
		    	alert(errorObj);
		    }
		});
	}
}