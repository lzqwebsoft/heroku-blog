<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>MSNLite表情包制作生成器（Java实现）-lzqwebsoft's blog</title>
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/style/comment.css" />
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/style/show.css" />
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/comment.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/default.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/show.js"></script>
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/javascript/shl/styles/shCoreDefault.css" />  
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/javascript/shl/styles/shThemeDefault.css" />  
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/javascript/shl/styles/shCore.css" />
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/shl/scripts/shCore.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/shl/scripts/shAutoloader.js"></script>
<script type="text/javascript" charset="utf-8" src="<%= request.getContextPath() %>/resources/javascript/kindeditor3.5/kindeditor.js"></script>
</head>

<body>
<%@ include file="/WEB-INF/pages/common/header.jsp" %>

<div id="container">
	<div id="intro">
		<div id="pageHeader">
			<c:if test="${requestScope.blogInfo!=null}">
            <h1><span>${requestScope.blogInfo.head}</span></h1>
            <h2><span>${requestScope.blogInfo.descriptions}</span></h2>
            </c:if>
		</div>
	</div>
    
    <div id="article_type_link">
       <p><a href="<%= request.getContextPath() %>">首页</a>&nbsp;»&nbsp;<a href="javascript:void(0)"><c:out value="${article.type.name}" /></a>&nbsp;»&nbsp;博客正文</p>
    </div>
    
    <div id="article_content">
       <h3><c:out value="${article.title}"/></h3>
       <p class="article_date">
          <span>发表于：<fmt:formatDate value="${article.createAt}" pattern="yyyy-MM-dd HH:mm:ss"/>  ，已有100次阅读</span>
          <span class="article_operate">
             <a href="#reply_article">评论</a>(0)&nbsp;
             <a href="<%=request.getContextPath()%>/edit/${article.id}.html">编辑</a>&nbsp;
             <a href="javascript:void(0)" onclick="confirm_article_delete('<spring:message code="page.confirm.delete.article" arguments="${article.title}"  />', '${article.id}')">删除</a>&nbsp;
          </span> 
       </p>
       <div class="article_context">
          <c:out value="${article.content}" escapeXml="false"/>
       </div>
       <div class="article_link">
          <span class="previous_article_link">上篇：<a href="javascript:void(0)">文章标题</a></span>
          <span class="next_article_link">下篇：<a href="javascript:void(0)">文章标题</a></span>
       </div>
       <div class="assume_you_like">
          <h3>相关文章：</h3>
          <ul>
              <li><a href="javascript:void(0)">文章标题</a><span>2012/9/7</span></li>
              <li><a href="javascript:void(0)">文章标题</a><span>2012/9/7</span></li>
              <li><a href="javascript:void(0)">文章标题</a><span>2012/9/7</span></li>
              <li><a href="javascript:void(0)">文章标题</a><span>2012/9/7</span></li>
              <li><a href="javascript:void(0)">文章标题</a><span>2012/9/7</span></li>
       	  </ul>
      </div>
      <div class="article_comment">
          <h3>评论：</h3>
          <div class="root_comment">
             <p>
                <a href="javascript:void(0)">lzqwebsoft</a>
                发表于：2012-9-12&nbsp;13:55:20
                <span>
                   <a href="javascript:void(0)" title="回复">回复</a>&nbsp;
                   <a href="javascript:void(0)" title="删除">删除</a>
                </span>
             </p>
             <div class="comment_content">页面原形评论测试</div>
             <div class="child_comment">
                <p>
                    Re：<a href="javascript:void(0)">lzqwebsoft</a>
                    发表于：2012-9-12&nbsp;13:55:20
                    <span>
                       <a href="javascript:void(0)" title="回复">回复</a>&nbsp;
                       <a href="javascript:void(0)" title="删除">删除</a>
                    </span>
                </p>
                <div class="comment_content">页面原形回复测试</div>
             </div>
          </div>
          <div class="root_comment">
             <p>
                <a href="javascript:void(0)">lzqwebsoft</a>
                发表于：2012-9-12&nbsp;13:55:20
                <span>
                   <a href="javascript:void(0)" title="回复">回复</a>&nbsp;
                   <a href="javascript:void(0)" title="删除">删除</a>
                </span>
             </p>
             <div class="comment_content">页面原形评论测试2</div>
          </div>
      </div>
      
      <div class="reply_article" id="reply_article">
         <h3>发表评论：</h3>
         <div class="input_reply_div">
            <p><input type="text" name="author" /> 昵称</p>
            <p><input type="text" name="web_site" /> 个人网站</p>
            <p>
               <textarea id="comment_content" name="content" onkeyup="string_number(this)" onblur="string_number(this)"></textarea>
            </p>
            <p>
              <input type="submit" value="发表评论" />
            </p>
            <p id="wordcount" style="float: right;">
                您还可输入<span id="str">120</span>个字
            </p>
         </div>
      </div>
      
      <div class="copyright_declare">
      Powered by <a href="http://www.heroku.com">Heroku</a>,Design by <a href="https://twitter.com/lzqwebsoft">Johnny</a>.
      </div>
      
   </div>
</div>
<script type="text/javascript">
function path()
{
  var args = arguments,
      result = []
      ;
       
  for(var i = 0; i < args.length; i++)
      result.push(args[i].replace('@', '../resources/javascript/shl/scripts/'));
       
  return result;
};
 
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
SyntaxHighlighter.all();

KE.show({
	id : "comment_content",
	skinType: "custom01",
	width : "460px", //编辑器的宽度为70%
	height : "120px", //编辑器的高度为100px
	filterMode : false, //不会过滤HTML代码
	resizeMode : 0, //编辑器只能调整高度
	items: ['emoticons','fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold',
			'italic', 'underline', 'strikethrough', 'removeformat', '|', 'about'],
	/*afterCreate : function(id) {
		//可视化模式下输入内容时触发
		KE.event.input(KE.g[id].iframeDoc, function(){
			var len;
		    //如果字数（只统计纯文本）超出120就截取
		    var count = KE.count(id, 'text');
			if(count > 120) {
				var textValue = KE.text(id);
				var strValue = KE.html(id);
				KE.html(id,strValue);
			} 
       });
	},*/
	afterChange : function(id) {
		var count = KE.count(id, 'text');
		if(count>=120){
			KE.$('wordcount').innerHTML="您输入的超过了<span id='str'>120</span>个字";
		} 
		else {
			len = 120 - count;
			KE.$('wordcount').innerHTML="您还可以输入<span id='str'>120</span>个字";
			KE.$('str').innerHTML=len;
		}
		KE.util.setData(id);
	}
});
</script>

<%@ include file="/WEB-INF/pages/common/footer.jsp" %>
</body>
</html>
