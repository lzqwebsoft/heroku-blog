var editor;
editor = KindEditor.ready(function (K) {
    editor = K.create('#blog_description', {
        themeType: 'simple',
        width: "100%", //编辑器的宽度为70%
        height: "350px", //编辑器的高度为350px
        filterMode: false, //不会过滤HTML代码
        designMode: true,   // 编辑模式
        resizeType: 1, //编辑器只能调整高度
        items: ['source', '|', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic',
            'underline', 'strikethrough', 'removeformat', '|', 'hr', 'link', 'unlink']
    });
});
// ============================article tab================================
//当检索条件改变时，更新博客列表
function select_condition_changed() {
    var type_id = $("#article_type :selected").val();
    var key_word = $("#key_words").val();
    $.ajax({
        url: $("#context-path").text() + "/article/select.html",
        type: "post",
        data: {
            "articleTypeId": type_id,
            "title": key_word
        },
        success: function (data, status) {
            $("#article_list_table").html(data);
        },
        error: function (xhr, strError, errorObj) {
            alert(errorObj);
        }
    });
}

//更新文章是否允许评论
function update_allow_comment(id, allowComment, obj) {
    var obj = $(obj);
    $.ajax({
        url: $("#context-path").text() + "/update/allow_comment/" + id + ".html",
        data: {"allowComment": allowComment},
        type: "post",
        success: function (data, status) {
            if (allowComment) {
                obj.text("禁止评论");
                obj.attr("onclick", "update_allow_comment('" + id + "', false, this);");
            } else {
                obj.text("允许评论");
                obj.attr("onclick", "update_allow_comment('" + id + "', true, this);");
            }
        },
        error: function (xhr, strError, errorObj) {
            alert(errorObj);
        }
    });
}

//更新文章设置置顶
function update_set_top(id, isTop, obj) {
    var obj = $(obj);
    $.ajax({
        url: $("#context-path").text() + "/update/is_top/" + id + ".html",
        data: {"isTop": isTop},
        type: "post",
        success: function (data, status) {
            if (isTop) {
                obj.text("取消置顶");
                obj.attr("onclick", "update_set_top('" + id + "', false, this);");
            } else {
                obj.text("置顶");
                obj.attr("onclick", "update_set_top('" + id + "', true, this);");
            }
        },
        error: function (xhr, strError, errorObj) {
            alert(errorObj);
        }
    });
}

//实现翻页
function article_page_update(url, typeId, title, pageNo) {
    $.ajax({
        url: $("#context-path").text() + "/article/select.html",
        type: "post",
        data: {
            "articleTypeId": typeId,
            "title": title,
            "pageNo": pageNo
        },
        success: function (data, status) {
            $("#article_list_table").html(data);
        },
        error: function (xhr, strError, errorObj) {
            alert(errorObj);
        }
    });
}

//删除文章
function confirm_article_delete(message, id, typeId, title, pageNo) {
    var option = confirm(message);
    if (option) {
        $.ajax({
            url: $("#context-path").text() + "/delete/article/" + id + ".html",
            type: "post",
            data: {
                "articleTypeId": typeId,
                "title": title,
                "pageNo": pageNo
            },
            success: function (data, status) {
                $("#article_list_table").html(data);
            },
            error: function (xhr, strError, errorObj) {
                alert(errorObj);
            }
        });
    }
}

//===================================================================

//========================article type tab===========================
//添加博客类型功能
function add_article_type() {
    var articleType = $("#txtArticleType").val();
    $.ajax({
        url: "article_type/add.html",
        type: "post",
        data: {"article_type_name": articleType},
        success: function (data, status) {
            $("#type_list").html(data);
        },
        error: function (xhr, strError, errorObj) {
            alert(errorObj);
        }
    });
}

// 删除功能
function confirm_article_type_delete(message, id) {
    var option = confirm(message);
    if (option) {
        $.ajax({
            url: "article_type/delete/" + id + ".html",
            type: "post",
            success: function (data, status) {
                $("#type_list").html(data);
            },
            error: function (xhr, strError, errorObj) {
                alert(errorObj);
            }
        });
    }
}

// 隐藏或显示功能
function disable_article_type(id, disable) {
    $.ajax({
        url: "article_type/disable/" + id + ".html",
        type: "post",
        data: {"disable": disable},
        success: function (data, status) {
            $("#type_list").html(data);
        },
        error: function (xhr, strError, errorObj) {
            alert(errorObj);
        }
    });
}

// 更新类型的名称功能
function edit_article_type(dom_id, type_id) {
    var span = $("#" + dom_id);
    span.hide();

    var type_text = span.text();
    var parent_tag = span.parent();

    var brother_span = $("<span></span>");
    var edit_field = $("<input type='text' maxlength='30' class='form-control' />");
    edit_field.css({
        width: "250px",
        display: "inline",
        marginRight: "5px"
    });
    edit_field.val(type_text);
    brother_span.append(edit_field);

    var save_link = $("<a href='javascript:void(0)' style='margin-right:5px;'>保存</a>");
    save_link.click(function () {
        var newTypeName = edit_field.val();
        if (newTypeName == null || $.trim(newTypeName) == "") {
            alert("类型名称不能为空！");
        } else {
            $.ajax({
                url: "article_type/update/" + type_id + ".html",
                type: "post",
                data: {"article_type_name": newTypeName},
                success: function (data, status) {
                    span.text(newTypeName);
                },
                error: function (xhr, strError, errorObj) {
                    alert(errorObj);
                }
            });
        }
        brother_span.empty();
        span.show();
    });
    brother_span.append(save_link);

    var cancel_link = $("<a href='javascript:void(0)'>取消</a>");
    cancel_link.click(function () {
        brother_span.empty();
        span.show();
    });
    brother_span.append(cancel_link);

    parent_tag.append(brother_span);
}

//===================================================================

//===========================draft tab===============================
//实现草稿翻页
function draft_page_update(url, pageNo) {
    $.ajax({
        url: url,
        type: "post",
        data: {
            "pageNo": pageNo
        },
        success: function (data, status) {
            $("#draft_list").html(data);
        },
        error: function (xhr, strError, errorObj) {
            alert(errorObj);
        }
    });
}

//删除草稿
function confirm_draft_delete(message, id, pageNo) {
    var option = confirm(message);
    if (option) {
        $.ajax({
            url: $("#context-path").text() + "/delete/draft/" + id + ".html",
            type: "post",
            data: {
                "pageNo": pageNo
            },
            success: function (data, status) {
                $("#draft_list").html(data);
            },
            error: function (xhr, strError, errorObj) {
                alert(errorObj);
            }
        });
    }
}

//===================================================================

//============================image tab==============================
function delete_image(message, id, pageNo) {
    var option = confirm(message);
    if (option) {
        $.ajax({
            url: "images/delete/" + id + ".jpg",
            type: "post",
            data: {
                "pageNo": pageNo
            },
            success: function (data, status) {
                $("#blog_images_list").html(data);
            },
            error: function (xhr, strError, errorObj) {
                alert(errorObj);
            }
        });
    }
}

//实现图片翻页
function image_page_update(url, pageNo) {
    $.ajax({
        url: url,
        type: "post",
        data: {
            "pageNo": pageNo
        },
        success: function (data, status) {
            $("#blog_images_list").html(data);
        },
        error: function (xhr, strError, errorObj) {
            alert(errorObj);
        }
    });
}

//===================================================================
$(function () {
    // ========================= 选项卡切换特效===========================
    var tagBoxs = [];
    $("#tabs li").each(function (idx) {
        var styleId = $(this).attr("id");
        var index = styleId.lastIndexOf("_");
        tagBoxs[idx] = styleId.substring(0, index);
    });
    $("#tabs li").click(function () {
        $("#tabs li").removeClass("active");
        var obj = $(this);
        var styleId = obj.attr("id");
        obj.addClass("active");
        var index = styleId.lastIndexOf("_");
        var selectBox = styleId.substring(0, index);
        $.each(tagBoxs, function (idx, item) {
            if (item == selectBox)
                $("#" + item).show();
            else
                $("#" + item).hide();
        });
    });
    // ==============================================================

    // 保存博客的配置信息
    $("#saveBlogInfoButton").click(function () {
        // 将kindeditor的内容同步到textarea中
        editor.sync();
        var form_data = $("#blogInfoForm").serialize();
        $.ajax({
            url: $("#blogInfoForm").attr("action"),
            type: "post",
            data: form_data,
            success: function (data, status) {
                if (data.status == 1) {
                    var error_ul = $("<ul></ul>");
                    $.each(data.messages, function (idx, message) {
                        error_ul.append("<li>" + message + "</li>")
                    });
                    $("#configure-messages").hide().text("");
                    $("#configure-errors").append(error_ul).show();
                } else if (data.status == 0) {
                    $("#configure-errors").hide().html("");
                    $("#configure-messages").text(data.messages).show();
                }
            },
            error: function (xhr, strError, errorObj) {
                alert(errorObj);
            }
        });
    });
});