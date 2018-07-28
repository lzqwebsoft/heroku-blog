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

var editor;
$(function () {
    // 自定义的SimpleMDE工具栏按钮
    var toolbarBuiltInButtons = [
        {
            name: "bold",
            action: SimpleMDE.toggleBold,
            className: "fa fa-bold",
            title: "粗体"
        },
        {
            name: "italic",
            action: SimpleMDE.toggleItalic,
            className: "fa fa-italic",
            title: "斜体",
        },
        {
            name: "strikethrough",
            action: SimpleMDE.toggleStrikethrough,
            className: "fa fa-strikethrough",
            title: "删除线"
        },
        {
            name: "heading",
            action: SimpleMDE.toggleHeadingSmaller,
            className: "fa fa-header",
            title: "标题",
        },
        "|",
        {
            name: "code",
            action: SimpleMDE.toggleCodeBlock,
            className: "fa fa-code",
            title: "代码块"
        },
        {
            name: "quote",
            action: SimpleMDE.toggleBlockquote,
            className: "fa fa-quote-left",
            title: "引用块"
        },
        {
            name: "unordered-list",
            action: SimpleMDE.toggleUnorderedList,
            className: "fa fa-list-ul",
            title: "列表"
        },
        {
            name: "ordered-list",
            action: SimpleMDE.toggleOrderedList,
            className: "fa fa-list-ol",
            title: "数字列表",
            default: true
        },
        "|",
        {
            name: "link",
            action: SimpleMDE.drawLink,
            className: "fa fa-link",
            title: "插入链接",
            default: true
        },
        {
            name: "image",
            action: function (mkeditor) {
                if ($('#image-upload-dialog').length <= 0) {
                    var dialogHtml = '<div class="modal fade" tabindex="-1" role="dialog" id="image-upload-dialog">'
                        + '<div class="modal-dialog" role="document">'
                        + '<div class="modal-content">'
                        + '   <div class="modal-header">'
                        + '        <button type="button" class="close" data-dismiss="modal" aria-label="Close">'
                        + '            <span aria-hidden="true">&times;</span>'
                        + '        </button>'
                        + '        <h4 class="modal-title">插入图片</h4>'
                        + '    </div>'
                        + '    <div class="modal-body">'
                        + '        <form class="form-inline">'
                        + '            <div class="form-group">'
                        + '                <label for="image-url">图片地址:</label>'
                        + '                <input type="text" class="form-control" id="image-url" placeholder="http://">'
                        + '                <input type="file" name="file" id="file-upload" multiple accept="image/*"> '
                        + '            </div>'
                        + '            <button id="upload-button" type="button" class="btn btn-default">上传图片</button>'
                        + '        </form>'
                        + '    </div>'
                        + '    <div class="modal-footer">'
                        + '        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                        + '        <button id="dialog-insert-button" type="button" class="btn btn-primary">确定</button>'
                        + '    </div>'
                        + '</div>'
                        + '</div>';
                    var dialog = $(dialogHtml);
                    dialog.appendTo($('body'));
                }
                $('#image-upload-dialog #image-url').val('');
                $("#image-upload-dialog #file-upload").val('');
                $('#image-upload-dialog').modal('show');
            },
            className: "fa fa-picture-o",
            title: "插入图片",
            default: true
        },
        {
            name: "table",
            action: SimpleMDE.drawTable,
            className: "fa fa-table",
            title: "插入表格"
        },
        {
            name: "horizontal-rule",
            action: SimpleMDE.drawHorizontalRule,
            className: "fa fa-minus",
            title: "分割线"
        },
        "|",
        {
            name: "preview",
            action: SimpleMDE.togglePreview,
            className: "fa fa-eye no-disable",
            title: "预览",
            default: true
        },
        {
            name: "side-by-side",
            action: SimpleMDE.toggleSideBySide,
            className: "fa fa-columns no-disable no-mobile",
            title: "分屏",
            default: true
        },
        {
            name: "fullscreen",
            action: SimpleMDE.toggleFullScreen,
            className: "fa fa-arrows-alt no-disable no-mobile",
            title: "全屏",
            default: true
        },
        '|',
        {
            name: "guide",
            action: "https://simplemde.com/markdown-guide",
            className: "fa fa-question-circle",
            title: "Markdown手册"
        }
    ];
    editor = new SimpleMDE({
        element: document.getElementById("article_content"),
        showIcons: ['strikethrough', 'code', 'table'],
        spellChecker: false,
        forceSync: true,
        toolbar: toolbarBuiltInButtons
    });
    $("body").on("click", "#upload-button", function () {
        $("#file-upload").trigger("click");
    });
    $("body").on("change", "#file-upload", function () {
        var current = $(this)[0];
        console.log(current.value);
        if (current.value != '') {
            var formData = new FormData();
            formData.append("imgFile", current.files[0]);
            $.ajax({
                url: $("#context-path").text()+"/images/upload.html",
                type: 'POST',
                data: formData,
                processData: false, // 必须false才会避开jQuery对formdata 的默认处理
                contentType: false, // 必须false才会自动加上正确的Content-Type
                success: function (responseStr) {
                    try {
                        if(responseStr.url != null && responseStr.error == 0) {
                            $("#image-url").val(responseStr.url);
                            return;
                        }
                    } catch(e){}
                    alert('上传文件失败');
                },
                error: function (responseStr) {
                    alert("失败:" + JSON.stringify(responseStr));// 将json对象转成json字符串。
                }
            });
        }
    });
    // 插入图片链接地址到Markdown编辑器
    $("body").on("click", "#dialog-insert-button", function () {
        $('#image-upload-dialog').modal('hide');
        var cm = editor.codemirror;
        var stat = editor.getState();
        var options = editor.options;
        var url = $("#image-url").val() || "http://";
        _replaceSelection(cm, stat.image, options.insertTexts.image, url);
    });
    
    // 切换编辑器
    $("body").on("click", "#convert-button", function() {
        var content = editor.value();
        if ($("#id").length > 0 && $("#id").val() != '' || content != "") {
            // 是编辑，则切换时提醒用户HTML转markdown信息的丢失
            if (window.confirm("确定转换HTML编辑器吗?")) {
                var form = $("#article");
                form.find("button[name='save']").val(2).trigger("click");
            }
        } else {
            window.location.href = $("#context-path").text() + "/article/newhtml.html";
        }
    });
});

function check_article_input() {
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
    var content = editor.value();
    if (content == '' || content.length <= 0) {
        prompt_info.removeClass("hidden").html("<strong>错误：</strong>文章内容不能为空!");
        return false;
    }
    var type_model = $("#type_model").val();
    if (type_model == '1') {
        var article_type = $('#new_type').val();
        if ($.trim(article_type) == "") {
            prompt_info.removeClass("hidden").html("<strong>错误：</strong>文章类别不能为空!");
            return false;
        }
    } else if (type_model == '0') {
        var article_type = $('#type').val();
        if (article_type == "" || article_type == "0") {
            prompt_info.removeClass("hidden").html("<strong>错误：</strong>请选择文章类别!");
            return false;
        }
    }
    prompt_info.addClass("hidden").html("");
    return true;
}

function _replaceSelection(cm, active, startEnd, url) {
    if (/editor-preview-active/.test(cm.getWrapperElement().lastChild.className))
        return;
    var text;
    var start = startEnd[0];
    var end = startEnd[1];
    var startPoint = cm.getCursor("start");
    var endPoint = cm.getCursor("end");
    if (url) {
        end = end.replace("#url#", url);
    }
    if (active) {
        text = cm.getLine(startPoint.line);
        start = text.slice(0, startPoint.ch);
        end = text.slice(startPoint.ch);
        cm.replaceRange(start + end, {
            line: startPoint.line,
            ch: 0
        });
    } else {
        text = cm.getSelection();
        cm.replaceSelection(start + text + end);

        startPoint.ch += start.length;
        if (startPoint !== endPoint) {
            endPoint.ch += start.length;
        }
    }
    cm.setSelection(startPoint, endPoint);
    cm.focus();
}

// 定时循还执行文章保存,每5分钟执行一次
var timeid = window.setInterval(autoSaveArticle, 300000);
function autoSaveArticle() {
    var content = editor.value();
    if(content == '' || $.trim(content).length <= 0) {
        return;
    }
    var formData = $("#article").serialize();
    $.ajax({
        url: $("#context-path").text()+"/article/autoSave.html",
        type: "post",
        data: formData,
        dataType: "json",
        success: function(data) {
            if(data.status=="SUCCESS") {
                var message = "系统自动保存成功！";
                if(data.messages)
                    message = data.messages;
                $("#auto_prompt_info").css({color: "green"}).html(message);
                if(data.article_id) {
                    $("#id").val(data.article_id);
                    $("#editOrCreate").val("EDIT");
                }
            }
        },
        error: function(xhr, strError, errorObj) {
            $("#auto_prompt_info").css({color: "red"}).html(errorObj);
        }
    });
}