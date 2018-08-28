// 自动高亮文章中的代码块
function path() {
    var args = arguments,result = [];
           
    for(var i = 0; i < args.length; i++)
        result.push(args[i].replace('@', 'js/shl/scripts/'));
    return result;
}
SyntaxHighlighter.autoloader.apply(null, path(
    'applescript            @shBrushAppleScript.js',
    'actionscript3 as3      @shBrushAS3.js',
    'bash shell             @shBrushBash.js',
    'coldfusion cf          @shBrushColdFusion.js',
    'cpp c                  @shBrushCpp.js',
    'c# c-sharp csharp      @shBrushCSharp.js',
    'css                    @shBrushCss.js',
    'delphi pascal          @shBrushDelphi.js',
    'diff patch pas         @shBrushDiff.js',
    'erl erlang             @shBrushErlang.js',
    'groovy                 @shBrushGroovy.js',
    'java                   @shBrushJava.js',
    'jfx javafx             @shBrushJavaFX.js',
    'js jscript javascript  @shBrushJScript.js',
    'perl pl                @shBrushPerl.js',
    'php                    @shBrushPhp.js',
    'text plain             @shBrushPlain.js',
    'py python              @shBrushPython.js',
    'ruby rails ror rb      @shBrushRuby.js',
    'sass scss              @shBrushSass.js',
    'scala                  @shBrushScala.js',
    'sql                    @shBrushSql.js',
    'vb vbnet               @shBrushVb.js',
    'xml xhtml xslt html    @shBrushXml.js'
));
SyntaxHighlighter.defaults['toolbar'] = false;
SyntaxHighlighter.defaults['gutter'] = false;
SyntaxHighlighter.defaults['class-name'] = "my_pre";
SyntaxHighlighter.all();

// 添加文本编辑器
var editor;
editor = KindEditor.ready(function(K) {
    editor = K.create('#comment_content', {
        themeType : 'simple',
        width : "100%", //编辑器的宽度为70%
        height : "120px", //编辑器的高度为100px
        filterMode : false, //不会过滤HTML代码
        resizeType : 0, //编辑器只能调整高度
        items: ['emoticons', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
                'italic', 'underline', 'strikethrough', 'removeformat', '|', 'about'],
        afterChange : function() {
            var count = editor.count('text');
            if(count>120){
                $('#wordcount').html("<font color='red'>您输入的超过了<span id='str'>120</span>个字</font>");
            }
            else {
                len = 120 - count;
                $('#wordcount').html("您还可以输入<span id='str'>120</span>个字");
                $('#wordcount #str').text(len);
                if(count>0 && $("#add-comment-info-div").attr('type')=="2") {
                    $("#add-comment-info-div").removeClass("show").addClass("hidden");
                }
            }
        }
    });
});

// 回复评论或删除回复
// 回复评论，用于产生子评论
function replay_comment(parent_id, reviewer){
    // 设置父评论id
    //$("#parent_comment_id").val(parent_id);
    $("#info_prompt_message").text("回复："+reviewer);
    $("#prompt_replay_info").show("normal");
}
// 取消回复评论
function cancel_replay_comment() {
    $("#prompt_replay_info").hide();
    //$("#parent_comment_id").val("");
}


var last_num = -1;
var last_heads = [];
$(function() {
    // 自动提取文章的标题生成对应的目录
    $("#article_content :header").each(function(idx, elm) {
        var current_elm = $(elm);
        current_elm.attr('id', 'st'+idx);
        var tagName = current_elm.get(0).tagName;
        var num = Number(tagName.substr(tagName.length-1));
        var new_node = $('<li><a href="#st'+idx+'">'+ current_elm.text() +'</a></li>');
        if(last_num==-1) {
            $("#auto_contents").append(new_node);
            last_heads.push([num, new_node]);
            $("#table_of_contents").removeClass("hidden").addClass("show");
        } else if(last_num >= num) {
            var hasPosition = false;
            var position = 0;
            for(var i=last_heads.length-1; i>=0; i--) {
                var tmp = last_heads[i];
                if(tmp[0]==num) {
                    tmp[1].parent("ul").append(new_node);
                    position = i;
                    hasPosition = true;
                    break;
                }
            }
            if(!hasPosition) {
                last_heads = [num, new_node];
            } else {
                last_heads.splice(position, last_heads.length-position, [num, new_node]);
            }
        } else if (last_num < num) {
            var tmp = last_heads[last_heads.length - 1];
            if(tmp[0] >= num) {
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
    $("#reply_comment").submit(function(event) {
        event.preventDefault();
        editor.sync();
        var nickname = $("#reviewer").val();
        var info_node = $("#add-comment-info-div");
        if($.trim(nickname)=="") {
            $("#reviewer").parent().addClass("has-error");
            $("#reviewer").focus();
            info_node.removeClass("hidden").addClass("show");
            info_node.attr('type', '1').html("<p><strong>错误：</strong>昵称不能为空！</p>");
            return false; 
        }
        var count = editor.count('text');
        if(count>120){
            editor.focus();
            info_node.removeClass("hidden").addClass("show");
            info_node.attr('type', "2").html("<p><strong>错误：</strong>评论内容不能超过120个字符！</p>");
            return false;
        } else if(count==0) {
            editor.focus();
            info_node.removeClass("hidden").addClass("show");
            info_node.attr('type', "2").html("<p><strong>错误：</strong>评论内容不能为空！</p>");
        }
    });

    $("#reviewer").change(function() {
        var nickname = $("#reviewer").val();
        var info_node = $("#add-comment-info-div");
        if($.trim(nickname)!="" && info_node.attr('type')=="1") {
            $("#reviewer").parent().removeClass("has-error");
            info_node.removeClass("show").addClass("hidden");
        }
    });
});