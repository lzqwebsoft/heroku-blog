<%@ page contentType="text/html; charset=UTF-8" %>
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
			<h1><span>飘痕</span></h1>
			<h2><span>心诚则灵</span></h2>
		</div>
	</div>
    
    <div id="article_type_link">
       <p><a href="http://lzqwebsoft.herokuapp.com">首页</a>&nbsp;»&nbsp;<a href="javascript:void(0)">javaSE</a>&nbsp;»&nbsp;博客正文</p>
    </div>
    
    <div id="article_content">
       <h3>MSNLite表情包制作生成器（Java实现）</h3>
       <p class="article_date">
          <span>发表于：2012-9-25&nbsp;10:28，已有100次阅读</span>
          <span class="article_operate">
             <a href="#reply_article">评论</a>(0)&nbsp;
             <a href="javascript:void(0)">编辑</a>&nbsp;
             <a href="javascript:void(0)">删除</a>&nbsp;
          </span> 
       </p>
       <div class="article_context">
          最近看到一则新闻说小米收购了MSNLite（hada.me）团队，打算开发PC端的米聊客户端，于是对MSNLite产生了兴趣，同时也在自己的机器上安装了MSNLite，乖乖，使用了几天确实感觉她比以前的MSN快多了，并且功能强大，无广告；但唯一让我感觉不足的是表情包太难找了(不知是不是我的搜索技术不过关)。找来找去发现在她的官方论坛上有一篇关于表情包的解释（http://bbs.hada.me/thread-1045-1-10.html）。于是有了自己写一个表情包制作工具的想法（其实这个制作工具她的论坛上也有一个，好像不是很稳定），程序源于生活，就当是练练手：
<br /><br />
从官网的信息可以知道，MSNLite的表情包MEP实质上是ZIP格式的重命名，唯一多了一个索引文件MsnliteEmoticons.dat（UTF-8）：用于引导表情图片的批量导入；从网上下载一些可用的MEP包重命名ZIP然后解压查看MsnliteEmoticons.dat文件内容，发现格式如下：装在0:204处。
<pre class="brush: text;">
Emoticons
{
..........
    $ee1d5f
    {
        local_path 2d27536e0981cec8f6654f3f638e0cfe.gif
        desc b11
        group 百度Hi熊表情包
    }
    $d32a6c
    {
        local_path 253faad76aea56ae371e1642bc556044.gif
        desc b12
        group 百度Hi熊表情包
    }
........
}
SaveTimes 1
</pre>
现在是不是就可以开始动手大干一场了？嘿嘿。先别急，还是先简单的介绍一下Java的ZIP压缩所要用到的几个类（当然了，如果你已经了解了这方面的知识，你就跳过去直接进入编码阶段吧）：

实现ZIP要导入java.util.zip包中的ZipFile，ZipOutputStream，ZipInputSream和ZipEntry类。

而在每一个压缩文件中都会存在多个子文件，每个子文件在Java中使用的就是ZipEntry表示。

一般压缩要用到ZipOutputStream和ZipEntry两个类，先由文件流创建一个ZipOutputStream对象，再将每一个文件包装成ZipEntry对象，接着调用ZipOutputStream对象的putNextEntry()方法为压缩文件

建立一个各ZipEntry条目，最后将其对应文件的内容写入到ZipOutputStream对象流中，这样就可以实现了压缩。

好了讲了这样多的废话，现在那就直奔主题直接帖上我的代码（我想其实不用我给，聪明的你都可以自己编写了），如下：
<pre class="brush: java">
import java.io.*;
import java.util.zip.*;
import java.util.Random;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class MSNListEmoticonsCreater {

    // Store some valid keyboard character
	private char[] keyCodes = new char[]{'a','b','c','d','e','f','g','h','i',
			'j','k','l','m','n','o','p','q','r',
			's','t','u','v','w','x','y','z',
			'A','B','C','D','E','F','G','H','I',
			'J','K','L','M','N','O','P','Q','R',
			'S','T','U','V','W','X','Y','Z',
			'!','^','&','{','}','$','<','>',',',
			'.','?','|','_','+','*','(',')','#',
			':',';','@','%'};

	/*
	 * Get user input information
	 */
	public String getInputString() {
		Scanner userIn = new Scanner(System.in);
		String inText = userIn.nextLine();
		return inText;
	}

    /*
     * Filter get emoticons files
.........
</pre>
上面的代码的使用方法，是先要创建一个表情目录，将一些需要添加的图片统一放到里面，然后运行本程序，在终端中先告诉它刚新建表情目录的路径，然后再为将要生成的表情组份输入一个名字，这样它就会在你的程序目录下生成一个“组份名.mep”的文件，这就是最后需要导入到MSNLite的表情包。
<br /><br />
唉，写了这样多的代码发现上面的代码存在一个严重的缺陷就是不支持中文，也就是说在你新建的表情目录及组份名时最好指定的是英文名，真是硬伤，关键是我不知道怎样实现Java的编码转换，如果聪明的你知道，欢迎留言，O(∩_∩)O。
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
