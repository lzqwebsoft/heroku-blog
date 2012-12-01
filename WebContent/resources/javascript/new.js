function change_type(obj) {
	var model = document.getElementById("type_model");
	var type_selectbox = document.getElementById("type");
	var new_field = document.getElementById("new_type");
	var model_value = model.value;
	if(Number(model_value)==0) {
		model.value = 1;
		type_selectbox.style.display="none";
		new_field.style.display="";
		obj.innerHTML="选择分类";
	} else {
		model.value = 0;
		type_selectbox.style.display="";
		new_field.style.display="none";
		obj.innerHTML="新建分类";
	}
}

function encodeHtml(s){
	var REGX_HTML_ENCODE = /"|&|'|<|>|[\x00-\x20]|[\x7F-\xFF]|[\u0100-\u2700]/g;
	return (typeof s != "string") ? s :
	  s.replace(REGX_HTML_ENCODE,
				function($0){
					var c = $0.charCodeAt(0), r = ["&#"];
					// 这里用于处理空格
					//c = (c == 0x20) ? 0xA0 : c;
					r.push(c); r.push(";");
					return r.join("");
				});
}