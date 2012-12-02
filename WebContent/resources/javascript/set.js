function showTab(name) {
	var tags_content = ['article_list', 'type_list', 'draft_list', 'blog_images_list', 'configure_info'];
	for(var i=0; i<tags_content.length; i++){
		var tag_context = document.getElementById(tags_content[i]);
		tag_context.style.display="none";
	}
	
	for(var i=0; i<tags_content.length; i++){
		var f = document.getElementById(tags_content[i]+"_tag");
		f.className="";
	}
	
	document.getElementById(name).style.display="block";
	document.getElementById(name+"_tag").className="selected";
	return false;
}

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