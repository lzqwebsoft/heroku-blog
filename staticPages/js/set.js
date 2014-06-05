var editor;
editor = KindEditor.ready(function(K) {
    editor = K.create('#blog_description', {
        themeType : 'simple',
        width : "100%", //编辑器的宽度为70%
        height : "200px", //编辑器的高度为100px
        filterMode : false, //不会过滤HTML代码
        resizeType : 1, //编辑器只能调整高度
        items: ['source', '|','fontname', 'fontsize', '|', 'forecolor','hilitecolor', 'bold', 'italic',
                'underline', 'strikethrough', 'removeformat', '|', 'hr', 'link', 'unlink']
    });
});

$(function() {
    var tagBoxs = [];
    $("#tabs li").each(function(idx){
        var styleId = $(this).attr("id");
        var index = styleId.lastIndexOf("_");
        tagBoxs[idx]= styleId.substring(0, index);
    });
    $("#tabs li").click(function() {
        $("#tabs li").removeClass("active");
        var obj = $(this);
        var styleId = obj.attr("id");
        obj.addClass("active");
        var index = styleId.lastIndexOf("_");
        var selectBox = styleId.substring(0, index)
        $.each(tagBoxs, function(idx, item) {
            if(item==selectBox)
                $("#"+item).show();
            else
                $("#"+item).hide();
        });
    });
});