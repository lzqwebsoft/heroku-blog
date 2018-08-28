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

// 添加文本编辑器
var editor;
editor = KindEditor.ready(function(K) {
    editor = K.create('#article_content', {
        themeType : 'simple',
        width : "100%", //编辑器的宽度为70%
        height : "400px", //编辑器的高度为100px
        filterMode : false, //不会过滤HTML代码
        resizeType : 1, //编辑器只能调整高度
        allowFileUpload : false,
        allowMediaUpload : false,
        allowFlashUpload : false
    });
});

function check_article_input() {
    editor.sync();
    var prompt_info = $("#prompt_info");
    var pattern_type = $("#patternTypeId").val();
    if(pattern_type==null || pattern_type=="" || pattern_type=="0") {
        prompt_info.removeClass("hidden").html("<strong>错误：</strong>请选择文章种类!");
        return false;
    }
    var article_title = $("#article_title").val();
    if($.trim(article_title)=="") {
        prompt_info.removeClass("hidden").html("<strong>错误：</strong>文章标题不能为空!");
        return false;
    }
    var count = editor.count('text');
    if(count<=0) {
        prompt_info.removeClass("hidden").html("<strong>错误：</strong>文章内容不能为空!");
        return false;
    }
    var type_model = $("#type_model").val();
    if(type_model=='1') {
        var article_type = $('#new_type').val();
        if($.trim(article_type)=="") {
            prompt_info.removeClass("hidden").html("<strong>错误：</strong>文章类别不能为空!");
            return false;
        }
    } else if(type_model=='0') {
        var article_type = $('#type').val();
        if(article_type=="" || article_type == "0") {
            prompt_info.removeClass("hidden").html("<strong>错误：</strong>请选择文章类别!");
            return false;
        }
    }
    prompt_info.addClass("hidden").html("");
    return true;
}