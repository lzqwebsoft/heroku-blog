function change_type(obj) {
    var model = document.getElementById("type_model");
    var type_selectbox = document.getElementById("type");
    var new_field = document.getElementById("new_type");
    var model_value = model.value;
    if (Number(model_value) == 0) {
        model.value = 1;
        type_selectbox.style.display = "none";
        new_field.style.display = "";
        obj.innerHTML = "选择分类";
    } else {
        model.value = 0;
        type_selectbox.style.display = "";
        new_field.style.display = "none";
        obj.innerHTML = "新建分类";
    }
}

// 自定义code插件
KindEditor.lang({
    simpcode: '设置code样式'
});
KindEditor.plugin('simpcode', function (K) {
    var self = this, name = 'simpcode';
    self.clickToolbar(name, function () {
        self.cmd.wrap('<code></code>');
    });
});
// 自定义blockquote插件
KindEditor.lang({
    blockquote: '引用'
});
KindEditor.plugin('blockquote', function (K) {
    var self = this, name = 'blockquote';
    self.clickToolbar(name, function () {
        self.cmd.wrap('<blockquote></blockquote>');
    });
});

// 添加文本编辑器
var editor;
editor = KindEditor.ready(function (K) {
    editor = K.create('#article_content', {
        themeType: 'simple',
        items: ["source", "|", "undo", "redo", "|", "preview", "print",
            "simpcode", "code", "cut", "copy", "paste", "plainpaste",
            "wordpaste", "|", "justifyleft", "justifycenter",
            "justifyright", "justifyfull", "insertorderedlist",
            "insertunorderedlist", "indent", "outdent", "subscript",
            "superscript", "clearhtml", "quickformat", "selectall", "|",
            "fullscreen", "/", "formatblock", "fontname", "fontsize", "|",
            "forecolor", "hilitecolor", "bold", "italic", "underline",
            "blockquote", "strikethrough", "lineheight", "removeformat",
            "|", "image", "flash", "media", "insertfile", "table", "hr",
            "emoticons", "baidumap", "pagebreak", "anchor", "link",
            "unlink", "|", "about"],
        width: "100%", // 编辑器的宽度为70%
        height: "400px", // 编辑器的高度为100px
        filterMode: false, // 不会过滤HTML代码
        designMode: true,   // 编辑模式
        resizeType: 1, // 编辑器只能调整高度
        // wellFormatMode: false, // 美化HTML关闭
        uploadJson: $("#context-path").text() + "/images/upload.html",
        fileManagerJson: $("#context-path").text() + "/images/list.html",
        allowFileManager: true,
        allowFileUpload: false,
        allowMediaUpload: false,
        allowFlashUpload: false
    });
});

function check_article_input() {
    window.onbeforeunload = null;
    editor.sync();
    var prompt_info = $("#prompt_info");
    var pattern_type = $("#patternTypeId").val();
    if (pattern_type == null || pattern_type == "" || pattern_type == "0") {
        prompt_info.removeClass("hidden").html("<strong>错误：</strong>请选择文章种类!");
        return false;
    }
    var article_title = $("#article_title").val();
    if ($.trim(article_title) == "") {
        prompt_info.removeClass("hidden").html("<strong>错误：</strong>文章标题不能为空!");
        return false;
    }
    var count = editor.count('text');
    if (count <= 0) {
        prompt_info.removeClass("hidden").html("<strong>错误：</strong>文章内容不能为空!");
        return false;
    }
    var type_model = $("#type_model").val();
    if (type_model == '1') {
        var article_type = $('#new_type').val();
        if ($.trim(article_type) == "") {
            prompt_info.removeClass("hidden").html(
                "<strong>错误：</strong>文章类别不能为空!");
            return false;
        }
    } else if (type_model == '0') {
        var article_type = $('#type').val();
        if (article_type == "" || article_type == "0") {
            prompt_info.removeClass("hidden").html(
                "<strong>错误：</strong>请选择文章类别!");
            return false;
        }
    }
    prompt_info.addClass("hidden").html("");
    return true;
}

// 定时循还执行文章保存,每5分钟执行一次
var timeid = window.setInterval(autoSaveArticle, 300000);

function autoSaveArticle() {
    editor.sync();
    var count = editor.count('text');
    if (count <= 0) {
        return;
    }
    var formData = $("#article").serialize();
    $.ajax({
        url: $("#context-path").text() + "/article/autoSave.html",
        type: "post",
        data: formData,
        dataType: "json",
        success: function (json) {
            if (json.status == 0) {
                var message = json.messages ? json.messages : "系统自动保存成功！";
                $("#auto_prompt_info").css({color: "green"}).html(message);
                if (json.datas && json.datas.article_id) {
                    $("#id").val(json.datas.article_id);
                    $("#editOrCreate").val("EDIT");
                }
            } else {
                var message = json.messages ? json.messages : "系统自动保存失败！";
                $("#auto_prompt_info").css({color: "red"}).html(message);
            }
        }
    });
}

$(function () {
    // 切换编辑器
    $("body").on("click", "#convert-button", function () {
        var count = editor.count('text');
        if ($("#id").length > 0 && $("#id").val() != '' || count > 0) {
            // 是编辑，则切换时提醒用户HTML转markdown信息的丢失
            if (window.confirm("HTML转Markdown内容会有损失，确定转换?")) {
                var form = $("#article");
                form.find("button[name='save']").val(2).trigger("click");
            }
        } else {
            window.location.href = $("#context-path").text() + "/article/new.html";
        }
    });
    window.onbeforeunload = function (evt) {
        evt.preventDefault();
        var message = '文章是否已保存, 确定要离开?';
        if (typeof evt == 'undefined') {
            evt = window.event;
        }
        if (evt) {
            evt.returnValue = message;
        }

        return message;
    }
});