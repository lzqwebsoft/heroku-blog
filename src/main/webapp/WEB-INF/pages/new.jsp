<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>编辑博客-lzqwebsoft's blog</title>
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/style/comment.css" />
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/style/new.css" />
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/comment.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/default.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/new.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/kindeditor3.5/kindeditor.js"></script>
</head>

<body>
<%@ include file="/WEB-INF/pages/common/header.jsp" %>

<div id="container">
   <div id="content-div">
      <h2>撰写博客</h2>
      <p><label for="article_title">标题</label></p>
      <p>
         <select name="selType">
         	<option value="0">请选择</option>
            <option value="1">原创</option>
            <option value="2">转载</option>
            <option value="3">翻译</option>
         </select>
         <input type="text" name="title" maxlength="200" size="80" />
      </p>
      <p><label for="article_content">博客内容</label></p>
      <p><textarea name="content"></textarea></p>
      <p><label for="article_type">文章类别</label></p>
      <p>
         <select id="type" name="type">
            <option value="0">请选择</option>
            <option value="1">JavaSE</option>
            <option value="2">J2EE</option>
            <option value="3">Ruby</option>
            <option value="4">Ruby On Rails</option>
         </select>
         <input id="new_type" name="new_type" type="text" style="display:none" />
         <input id="type_model" name="type_model" type="hidden" value="0" />
         <a href="javascript:void(0)" onclick="change_type(this); return false;">新建分类</a>
      </p>
      <p style="margin-bottom: 10px;">
         <label for="allow_discuss">允许评论？</label>
         <input type="radio" name="no_discuss" value="0" checked="checked"  />是&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
         <input type="radio" name="no_discuss" value="1"/>否
      </p>
      <p>
         <input type="submit" name="publish" value="发表文章" />
         <input type="submit" name="save" value="保存为草稿" />
      </p>
      
      
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
			resizeMode : 1, //编辑器只能调整高度
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
