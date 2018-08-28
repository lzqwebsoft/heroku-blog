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

// 添加文本编辑器
window.wangEditor.plus = {
    // editor create之后调用
    init: function (editorSelector) {
        $(editorSelector + " .w-e-toolbar").append('<div class="w-e-menu" onclick="window.wangEditor.plus.toggleCode(\'' + editorSelector + '\')"><i class="_wangEditor_btn_code icon-embed2" ></i></div>');
        $(editorSelector + " .w-e-toolbar").append('<div class="w-e-menu" onclick="window.wangEditor.plus.toggleSource(\'' + editorSelector + '\')"><i class="_wangEditor_btn_source icon-file-empty"></i></div>');
        $(editorSelector + " .w-e-toolbar").append('<div class="w-e-menu" onclick="window.wangEditor.plus.toggleFullscreen(\'' + editorSelector + '\')"><i class="_wangEditor_btn_fullscreen icon-enlarge"></i></div>');
    },
    toggleFullscreen: function (editorSelector) {
        $(editorSelector).toggleClass('fullscreen-editor');
        if ($(editorSelector + ' ._wangEditor_btn_fullscreen').hasClass("icon-enlarge")) {
            $(editorSelector + ' ._wangEditor_btn_fullscreen').removeClass("icon-enlarge").addClass("icon-shrink");
        } else {
            $(editorSelector + ' ._wangEditor_btn_fullscreen').removeClass("icon-shrink").addClass("icon-enlarge");
        }
    },
    toggleSource: function (editorSelector) {
// $(editorSelector + '.w-e-menu').toggleClass("menu-item-disabled").click(false);
        if ($(editorSelector + ' ._wangEditor_btn_source').hasClass("icon-file-empty")) {
            $(editorSelector + ' ._wangEditor_btn_source').removeClass("icon-file-empty").addClass("icon-file-text2");
            $(editorSelector + " .w-e-text").hide();   // 隐藏富文本编辑器
            var content = html_beautify(editor.txt.html(), {
                'indent_inner_html': true,
                'indent_size': 4,
                'indent_char': ' ',
                'wrap_line_length': 200,
                'brace_style': 'expand',
                'preserve_newlines': true,
                'max_preserve_newlines': 5,
                'indent_handlebars': false,
                'extra_liners': ['/html']
            });  // 格式化HTML代码
            $('#content').val(content).show().height($(".w-e-text-container").height());
        } else {
            $(editorSelector + ' ._wangEditor_btn_source').removeClass("icon-file-text2").addClass("icon-file-empty");
            editor.txt.html($('#content').hide().val());
            $(editorSelector + " .w-e-text").show();   // 显示富文本编辑器
        }
    },
    toggleCode: function (editorSelector) {
        var $startElem = editor.selection.getSelectionStartElem();
        var $endElem = editor.selection.getSelectionEndElem();
        var isSeleEmpty = editor.selection.isSelectionEmpty();
        var selectionText = editor.selection.getSelectionText();
        var $selectionElem = editor.selection.getSelectionContainerElem();
        var nodeName = $selectionElem.getNodeName();
        var $code = void 0;

        if (!$startElem.equal($endElem)) {
            // 跨元素选择，不做处理
            editor.selection.restoreSelection();
            return;
        }
        if (!isSeleEmpty) {
            if (nodeName == "CODE") {
                // 选取的是code，则去除<code>包裹
                $selectionElem[0].parentNode.replaceChild(document.createTextNode(selectionText), $selectionElem[0]);
                editor.selection.restoreSelection();

            } else {
                // 选取不是空，用 <code> 包裹即可
                $code = $('<code>' + selectionText + '</code>');
                editor.cmd.do('insertElem', $code);
                editor.selection.createRangeByElem($code, false);
                editor.selection.restoreSelection();

            }
        } else {
            // 没有选取则插件pre，代码段
            if ($('#insert-sources-dialog').length <= 0) {
                var dialogHtml = '<div class="modal fade" tabindex="-1" role="dialog" id="insert-source-dialog">'
                    + '<div class="modal-dialog" role="document">'
                    + '<div class="modal-content">'
                    + '   <div class="modal-header">'
                    + '        <button type="button" class="close" data-dismiss="modal" aria-label="Close">'
                    + '            <span aria-hidden="true">&times;</span>'
                    + '        </button>'
                    + '        <h4 class="modal-title">插入代码</h4>'
                    + '    </div>'
                    + '    <div class="modal-body">'
                    + '        <form>'
                    + '            <div class="form-group">'
                    + '                <select id="source_language" class="form-control"></select>'
                    + '            </div>'
                    + '            <div class="form-group">'
                    + '                 <textarea id="source_content" class="form-control" style="height: 300px; resize: none"></textarea>'
                    + '            </div>'
                    + '        </form>'
                    + '    </div>'
                    + '    <div class="modal-footer">'
                    + '        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>'
                    + '        <button id="insert-source-button" type="button" class="btn btn-primary">确定</button>'
                    + '    </div>'
                    + '</div>'
                    + '</div>';
                var dialog = $(dialogHtml);
                var sourceLanguages = {
                    'javascript': 'Javascript',
                    'html': 'HTML',
                    'xml': 'XML',
                    'css': 'CSS',
                    'cpp': 'C/C++',
                    'java': 'Java',
                    'jfx': 'JavaFX',
                    'csharp': 'C#',
                    'vb': 'ASP/VB',
                    'php': 'PHP',
                    'python': 'Python',
                    'ruby': 'Ruby',
                    'sql': 'SQL',
                    'shell': 'Shell',
                    'none': '文本'
                };
                for (var key in sourceLanguages) {
                    dialog.find("#source_language").append('<option value="' + key + '">' + sourceLanguages[key] + '</option>');
                }
                dialog.appendTo($('body'));
                dialog.find("#insert-source-button").click(function () {
                    $('#insert-source-dialog').modal('hide');
                    var $text = $("#insert-source-dialog #source_content");
                    var text = $text.val() || $text.html();
                    if (text === "") {
                        return;
                    }
                    text = replaceHtmlSymbol(text);
                    var $language = $("#insert-source-dialog #source_language");
                    var language = $language.val() == "" && $language.val() == "none" ? "language-text" : "language-" + $language.val();
                    editor.cmd.do('insertHTML', '<pre><code class="' + language + '">' + text + '</code></pre><p><br></p>');
                });
            }
            $('#insert-source-dialog #source_language').val('javascript');
            $("#insert-source-dialog #source_content").val('');
            $('#insert-source-dialog').modal('show');
        }
    }
};
var E = window.wangEditor;
var editor = new E('#article_content');
var $text1 = $('#content');
// 自定义菜单配置
editor.customConfig.menus = ['head', 'bold', 'fontSize', 'fontName', 'italic', 'underline', 'strikeThrough', 'foreColor', 'backColor', 'link', 'list', 'justify', 'quote', 'emoticon', 'image', 'table', 'video', 'undo', 'redo'];
editor.customConfig.uploadImgServer = $("#context-path").text() + "/images/upload.html";
editor.customConfig.uploadImgMaxSize = 7 * 1024 * 1024;
editor.customConfig.uploadFileName = 'imgFile';
editor.customConfig.uploadImgHooks = {
    customInsert: function (insertImg, result, editor) {
        var url = result.url;
        insertImg(url);
    }
};
editor.customConfig.onchange = function (html) {
    $text1.val(html)
};
var localEmotions = [];
var baseurl = window.location.origin != null ? window.location.origin : "";
for (var i = 0; i < 134; i++) {
    localEmotions.push({
        alt: '',
        src: baseurl + $("#context-path").text() + '/resources/js/ke4/plugins/emoticons/images/' + i + ".gif"
    });
}
editor.customConfig.emotions = [
    {
        title: '默认',
        type: 'image',
        content: localEmotions
    },
    {
        // tab 的标题
        title: 'emoji',
        type: 'emoji',
        content: ['😀', '😃', '😄', '😁', '😆']
    }
];
editor.customConfig.zIndex = 1020;
editor.create();
$("#article_content .w-e-text-container").append($('#content'));
E.plus.init('#article_content');
editor.txt.html($text1.val());

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
    var content = editor.txt.text();
    if ($.trim(content) == "") {
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

// 定时循还执行文章保存,每5分钟执行一次
var timeid = window.setInterval(autoSaveArticle, 300000);

function autoSaveArticle() {
    var content = $text1.val();
    if ($.trim(content) == "") {
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

// 替换 html 特殊字符
function replaceHtmlSymbol(html) {
    if (html == null) {
        return '';
    }
    return html.replace(/</gm, '&lt;').replace(/>/gm, '&gt;').replace(/"/gm, '&quot;');
}

$(function () {
    // 切换编辑器
    $("body").on("click", "#convert-button", function () {
        var count = editor.txt.text();
        if ($("#id").length > 0 && $("#id").val() != '' || count != '') {
            // 是编辑，则切换时提醒用户HTML转markdown信息的丢失
            if (window.confirm("HTML转Markdown内容会有损失，确定转换?")) {
                var form = $("#article");
                form.find("button[name='save']").val(2).trigger("click");
            }
        } else {
            window.location.href = $("#context-path").text() + "/article/new.html";
        }
    });

    $(window).scroll(function () {
        var header = $(".w-e-toolbar");
        var sticky = header[0].offsetTop;
        if (window.pageYOffset > sticky + 51) {
            header.addClass("sticky");
            if ($("#article_content").hasClass('fullscreen-editor')) {
                $(".w-e-text-container").css("margin-top", header.height());
            }
        } else {
            header.removeClass("sticky");
            $(".w-e-text-container").css("margin-top", 0);
        }
    });
});