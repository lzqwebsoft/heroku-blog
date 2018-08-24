// 添加文本编辑器
var E = window.wangEditor
var editor = new E('#comment_content')
var $text1 = $('#content')
editor.customConfig.onchange = function(html) {
    $text1.val(html);
    var content = editor.txt.text();
    if (content.length > 120) {
        $('#wordcount').html("<font color='red'>您输入的超过了<span id='str'>120</span>个字</font>");
    } else {
        len = 120 - content.length;
        $('#wordcount').html("您还可以输入<span id='str'>120</span>个字");
        $('#wordcount #str').text(len);
        if (content.length > 0 && $("#add-comment-info-div").attr('type') == "2") {
            $("#add-comment-info-div").removeClass("show").addClass("hidden");
        }
    }
}
editor.customConfig.onfocus = function() {
    $("#validateCodeZone").removeClass('hidden');
}
var localEmotions = [];
var baseurl = window.location.origin != null ? window.location.origin : "";
for (var i = 0; i < 134; i++) {
    localEmotions.push({
        alt : '',
        src : baseurl + $("#context-path").text() + '/resources/js/ke4/plugins/emoticons/images/' + i + ".gif"
    });
}
editor.customConfig.emotions = [ {
    title : '默认',
    type : 'image',
    content : localEmotions
} ];
editor.customConfig.zIndex = 1020;
editor.customConfig.menus = [ 'bold', 'fontSize', 'fontName', 'italic', 'emoticon', 'foreColor', 'backColor' ];
editor.create();
// 初始化 textarea 的值
$text1.val(editor.txt.html());

// 回复评论，用于产生子评论
function replay_comment(parent_id, reviewer, root_id) {
    // 设置父评论id
    $("#parent_comment_id").val(parent_id);
    // 设置顶级父id
    $("#root_comment_id").val(root_id);
    $("#info_prompt_message").text("回复：" + reviewer);
    $("#prompt_replay_info").show("normal");
}
// 取消回复评论
function cancel_replay_comment() {
    $("#prompt_replay_info").hide();
    $("#parent_comment_id").val("");
    $("#root_comment_id").val("");
}
// 删除评论
function delete_article_comment(commentId) {
    $.ajax({
        url : $("#context-path").text() + "/comment/delete/" + commentId + ".html",
        type : "post",
        success : function(data, status) {
            $("#article_comment").html(data);
        },
        error : function(xhr, strError, errorObj) {
            alert(errorObj);
        }
    });
}

var last_num = -1;
var last_heads = [];
$(function() {
    // 自动提取文章的标题生成对应的目录
    $("#article_content :header").each(function (idx, elm) {
        var current_elm = $(elm);
        current_elm.attr('id', 'st' + idx)
        var tagName = current_elm.get(0).tagName;
        var num = Number(tagName.substr(tagName.length - 1));
        var new_node = $('<li><a href="#st' + idx + '">' + current_elm.html() + '</a></li>');
        if (last_num == -1) {
            $("#auto_contents").append(new_node);
            last_heads.push([num, new_node]);
            $("#table_of_contents").removeClass("hidden").addClass("show");
        } else if (last_num >= num) {
            var hasPosition = false;
            var position = 0;
            for (var i = last_heads.length - 1; i >= 0; i--) {
                var tmp = last_heads[i];
                if (tmp[0] == num) {
                    tmp[1].parent("ul").append(new_node);
                    position = i;
                    hasPosition = true;
                    break;
                }
            }
            if (!hasPosition) {
                last_heads = [num, new_node];
            } else {
                last_heads.splice(position, last_heads.length - position, [num, new_node]);
            }
        } else if (last_num < num) {
            var tmp = last_heads[last_heads.length - 1];
            if (tmp[0] >= num) {
                tmp[1].parent("ul").append(new_node);
            } else {
                var nested_ul = $('<ul class="nav"></ul>');
                nested_ul.append(new_node);
                tmp[1].append(nested_ul);
                last_heads.push([num, new_node]);
            }
        }
        last_num = num;
    });

    // 验证表单用户输入值
    $("#reply_comment").submit(function (event) {
        event.preventDefault();
        // 验证用户输入值
        var nickname = $("#reviewer").val();
        var info_node = $("#add-comment-info-div");
        if ($("#reviewer").length > 0 && $.trim(nickname) == "") {
            $("#reviewer").parent().addClass("has-error");
            $("#reviewer").focus();
            info_node.removeClass("alert-success").removeClass("hidden").addClass("alert-danger show");
            info_node.attr('type', '1').html("<p><strong>错误：</strong>昵称不能为空！</p>");
            return false;
        }
        var content = editor.txt.text();
        if (content.length > 120) {
            info_node.removeClass("alert-success").removeClass("hidden").addClass("alert-danger show");
            info_node.attr('type', "2").html("<p><strong>错误：</strong>评论内容不能超过120个字符！</p>");
            return false;
        } else if (content.length == 0) {
            info_node.removeClass("alert-success").removeClass("hidden").addClass("alert-danger show");
            info_node.attr('type', "2").html("<p><strong>错误：</strong>评论内容不能为空！</p>");
            return false;
        }
        // 图片验证码填写
        var validateCode = $("#validateCode").val();
        if ($("#validateCode").length > 0 && $.trim(validateCode) == "") {
            $("#validateCode").parent().addClass("has-error");
            $("#validateCode").focus();
            info_node.removeClass("alert-success").removeClass("hidden").addClass("alert-danger show");
            info_node.attr('type', '3').html("<p><strong>错误：</strong>请输入图片验证码！</p>");
            return false;
        }
        // 提交用户数据
        var form_data = $(this).serialize();
        $.ajax({
            url: $(this).attr("action"),
            data: form_data,
            type: "post",
            success: function (data, status) {
                var messages_div = $("#add-comment-info-div");
                if (data.status != null) {
                    // 当评论后台验证失败
                    var info_context = "<ul>";
                    $.each(data.messages, function (idx, element) {
                        info_context += "<li>" + element + "</li>";
                    });
                    info_context += "</ul>";
                    messages_div.removeClass("alert-success").removeClass("hidden").addClass("alert-danger show").html(info_context);
                } else {
                    // 当评论后台验证成功
                    messages_div.removeClass("alert-danger").removeClass("hidden").addClass("alert-success show").html("评论成功！");
                    $("#article_comment").html(data);
                    $("#reply_comment :text").val("");
                    $("#prompt_replay_info").hide();
                    $("#parent_comment_id").val("");
                    editor.txt.html("");
                    $("#validateCode").parent().removeClass("has-error");
                }
                // 提交后更新图片验证码
                $("#update-captcha-link").triggerHandler("click");
            },
            error: function (xhr, strError, errorObj) {
                alert(errorObj);
            }
        });
    });

    $("#reviewer").change(function () {
        var nickname = $("#reviewer").val();
        var info_node = $("#add-comment-info-div");
        if ($.trim(nickname) != "" && info_node.attr('type') == "1") {
            $("#reviewer").parent().removeClass("has-error");
            info_node.removeClass("show").addClass("hidden");
        }
    });

    // prism.js代码高亮显示
    var doc_pre = $("#article_content pre");
    doc_pre.each(function () {
        if ($(this).has("code").length > 0) {
            var class_val = $(this).has("code").attr("class");
            if (typeof class_val == typeof undefined || attr == false) {
                $(this).has("code").attr('class', 'language-none');
            }
        } else {
            var class_val = $(this).attr("class");
            if (class_val && class_val != "") {
                var class_arr = new Array();
                class_arr = class_val.split(';');
                class_arr = class_arr['0'].split(':');
                var lan_class = 'language-' + $.trim(class_arr['1']);
                var pre_content = '<code class="' + lan_class + '">' + $(this).html() + '</code>';
                $(this).html(pre_content).addClass("my_pre");
            }
        }
    });
    // 二唯码
    $(".qrcode").qrcode({
        text: window.location.href,
        width: 85,
        height: 85
    });
    $(".qrcode").hide();
    var canvas = $('.qrcode canvas');
    var img = canvas.get(0).toDataURL("image/png");
    $("#ewmimg").attr('src', img);
    // 分享按钮使用
    var shareTitle = $("title").text();
    var sinaTitle = '分享文章 「' + shareTitle + '」 （分享自@-ZQLUO-）';
    var tqzoneTitle = tqzoneTitle;
    var tqzoneSummary = '分享文章 「' + shareTitle + '」（分享自@一迹飘痕）';
    var twitterTitle = '分享文章 「' + shareTitle + '」（分享自@lzqwebsoft）';
    var facebookTitle = '分享文章 「' + shareTitle + '」（分享自@lzqwebsoft）';
    var picShare = $("#article_content img").length > 0 ? encodeURIComponent($("img")[0].src) : "";
    $('body').snsShare({
        tsina: {
            url: encodeURIComponent(window.location.href),
            title: sinaTitle,
            pic: picShare
        },
        tqzone: {
            url: encodeURIComponent(window.location.href),
            title: tqzoneTitle,
            pic: picShare,
            summary: tqzoneSummary
        },
        twitter: {
            url: encodeURIComponent(window.location.href),
            title: twitterTitle,
            pic: picShare
        },
        facebook: {
            url: encodeURIComponent(window.location.href),
            title: facebookTitle,
            pic: picShare
        }
    });
    // 微信分享
    $(".share_weixin").click(function () {
        $("#wemcn").show();
    });
    $("#ewmkg").click(function () {
        $("#wemcn").hide();
    });
});