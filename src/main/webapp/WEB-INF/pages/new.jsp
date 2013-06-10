<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑博客-lzqwebsoft's blog</title>
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/style/comment.css" />
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/style/new.css" />
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/comment.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/default.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/new.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/kindeditor3.5/kindeditor.js"></script>
</head>

<body>
<%@ include file="/WEB-INF/pages/common/header.jsp" %>

<div id="container">
   <div id="content-div">
      <form:form action="${pageContext.request.contextPath}/article/publish.html" method="post" modelAttribute="article">
      <h2>撰写博客</h2>
      <p style="color: red;"><form:errors path="*"/></p>
      <p><label for="article_title">标题</label></p>
      <p>
         <form:select path="patternTypeId">
         	<option value="0">请选择</option>
            <form:options items="${patterns.patterns}" />
         </form:select>
         <form:input path="title" maxlength="200" cssStyle="width:460px" />
         <form:hidden path="id" />
      </p>
      <p><label for="article_content">博客内容</label></p>
      <p><form:textarea path="content" id="content" /></p>
      <p><label for="article_type">文章类别</label></p>
      <p>
         <form:select id="type" path="type.id">
            <option value="0">请选择</option>
            <form:options items="${articleTypes}" itemLabel="name" itemValue="id"/>
         </form:select>
         <input id="new_type" name="new_type" type="text" style="display:none" />
         <input id="type_model" name="type_model" type="hidden" value="0" />
         <a href="javascript:void(0)" onclick="change_type(this); return false;">新建分类</a>
      </p>
      <p>
         <label for="code_theme" style="padding-right: 10px;">代码主题</label>
         <form:select path="codeTheme">
            <form:options items="${codeThemes.themes}" />
         </form:select>
      </p>
      <p style="margin-bottom: 10px;">
         <label for="allow_discuss">允许评论？</label>
         <form:radiobutton path="allowComment" value="true"/>是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <form:radiobutton path="allowComment" value="false"/>否
         <form:hidden path="isTop" />
         <form:hidden path="readedNum" />
      </p>
      <p>
         <input type="hidden" name="editOrCreate" value="${editOrCreate}" />
         <input type="submit" name="publish" value="发表文章" />
         <input type="submit" name="save" value="保存为草稿" />
      </p>
      </form:form>
      
   	  <script type="text/javascript">
		//添加插入源代码插件  
		KE.lang['code']='插入源代码';  
		//编写插入源代码的事件处理  
		KE.plugin['code'] = {click : function(id) {  
			 KE.util.selection(id);  
			 //创建一个对话框  
			 var dialog = new KE.dialog({  
				id : id,  
				cmd : 'code',  
				file : 'code.html',  
				width : 330,  
				height : 220,  
				title : '插入源代码',  
				//loadingMode : true,  
				yesButton : KE.lang['yes'],  
				noButton : KE.lang['no']  
			});  
			//显示对话框  
			dialog.show();  
		},  
		//当按“确定”按钮时触发  
		exec : function(id) {  
			KE.util.select(id);  
			var iframeDoc = KE.g[id].iframeDoc;  
			var dialogDoc = KE.util.getIframeDoc(KE.g[id].dialog);  
			//if (!this.check(id)) return false;  
			//得到选中的语言  
			var language= KE.$('language', dialogDoc).options[KE.$('language', dialogDoc).options.selectedIndex].value;  
			//得到插入的源码  
			var sourceCode = KE.$('sourceCode', dialogDoc).value;
			// 将其转化为HTML转义字符串
			sourceCode = encodeHtml(sourceCode);
			this.insert(id, language, sourceCode);  
		},  
		insert : function(id, language, sourceCode) {  
			var html='<pre class=\"brush: '+language+'\">';  
			html+=sourceCode+'</pre><br />';  
			KE.util.insertHtml(id, html);  
			KE.layout.hide(id);  
			KE.util.focus(id);  
		}
	   };
	   KE.show({
			id : "content",
			skinType: "default",
			width : "700px", //编辑器的宽度为70%
			height : "450px", //编辑器的高度为100px
			filterMode : false, //不会过滤HTML代码
			resizeMode : 1, //编辑器只能调整高度,
			imageUploadJson : $("#context-path").text()+"/images/upload.html",  // 指定图片上传的URL
			fileManagerJson : $("#context-path").text()+"/images/list.html",     // 指定浏览远程图片的URL
			allowFileManager : true,
			items : [ 'source', '|', 'fullscreen', 'undo', 'redo', 'print', 'cut', 'copy', 'paste',  
			'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',  
			'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',  
			 'superscript', '|', 'selectall', '-',  
			 'title', 'fontname', 'fontsize', '|', 'textcolor', 'bgcolor', 'bold',  
			 'italic', 'underline', 'strikethrough', 'removeformat', '|','code', 'image',  
			 'flash', 'media', 'advtable', 'hr', 'emoticons', 'link', 'unlink', '|', 'about']
	   });
       </script>
      <div class="copyright_declare">
      Powered by <a href="http://www.heroku.com">Heroku</a>,Design by <a href="https://twitter.com/lzqwebsoft">Johnny</a>.
      </div>
   </div>
</div>

<%@ include file="/WEB-INF/pages/common/footer.jsp" %>
</body>
</html>
