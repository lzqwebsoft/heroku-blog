﻿<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>博客设置-lzqwebsoft's blog</title>
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/style/comment.css" />
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/resources/style/set.css" />
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/comment.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/default.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/set.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/article_type_tab.js" ></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/resources/javascript/kindeditor3.5/kindeditor.js"></script>
<link rel="stylesheet" href="<%= request.getContextPath() %>/resources/style/lightbox.css" type="text/css" media="screen" />
<script type="text/javascript" charset="utf-8" src="<%= request.getContextPath() %>/resources/javascript/lightbox/lightbox.js"></script>

</head>

<body>
<%@ include file="/WEB-INF/pages/common/header.jsp" %>

<div id="container">
	<div id="back_intro">
		<h2>博客管理</h2>
        <div class="tabs" id="tabs">
           <ul>
              <li><a id="article_list_tag" class="selected" href="javascript:void(0)">文章列表</a></li>
              <li><a id="type_list_tag" href="javascript:void(0)">类别管理</a></li>
              <li><a id="draft_list_tag" href="javascript:void(0)">草稿箱</a></li>
              <li><a id="blog_images_list_tag" href="javascript:void(0)">博客用图</a></li>
              <li><a id="configure_info_tag" href="javascript:void(0)">信息设置</a></li>
           </ul>
        </div>
        <div id="article_list">
           <div id="select_article">
              <p>
                 <label for="article_type">类别：</label>
                 <select name="article_type">
                    <option value="0">全部</option>
                    <option value="1">JavaSE&nbsp;&nbsp;(0)</option>
                    <option value="2">J2EE&nbsp;&nbsp;(0)</option>
                    <option value="3">Ruby&nbsp;&nbsp;(0)</option>
                    <option value="4">Ruby On Rails&nbsp;&nbsp;(0)</option>
                    <option value="5">JavaScript/AJAX&nbsp;&nbsp;(0)</option>
                    <option value="6">网络转载&nbsp;&nbsp;(0)</option>
                 </select>
                 &nbsp;
                 <label for="title">标题：</label>
                 <input type="text" name="title" maxlength="30" style="width:400px" />
              </p>
           </div>
           <table id="lstBox" cellspacing="0">
           <tr>
             <th class="tdleft">标题</th>
             <th style="width:50px;">阅读</th>
             <th style="width:50px;">评论</th>
             <th style="width:70px;">评论权限</th>
             <th style="width:140px;">操作</th>
           </tr>
           <tr>
              <td class='tdleft'>
                  <a href='javascript:void(0)'>Rails自动校验</a><span class='gray'>（2012-02-13 13:38）</span>
              </td>
              <td>154</td>
              <td>0</td>
              <td><a href='javascript:void(0)' class='lock'>禁止评论</a></td>
              <td>
                  <a href='javascript:void(0)'>编辑</a> | 
                  <a href='javascript:void(0)'>置顶</a> | 
                  <a href='javascript:void(0)' name=del>删除</a>
              </td>
           </tr>
           <tr>
              <td class='tdleft'>
                 <a href='javascript:void(0)'>Git简单的安装与使用</a><span class='gray'>（2011-11-07 17:16）</span>
              </td>
              <td>648</td>
              <td>0</td>
              <td><a href='javascript:void(0)' class='lock'>禁止评论</a></td>
              <td>
                  <a href='javascript:void(0)'>编辑</a> | 
                  <a href='javascript:void(0)'>置顶</a> | 
                  <a href='javascript:void(0)' name=del>删除</a>
              </td>
           </tr>
           <tr>
              <td class='tdleft'>
                 <a href='javascript:void(0)'>结合SyntaxHighlighter给KindEditor3.5.5添加[插入源代码]自定义组件</a><span class='gray'>（2011-11-07 17:16）</span>
              </td>
              <td>648</td>
              <td>0</td>
              <td><a href='javascript:void(0)' class='lock'>禁止评论</a></td>
              <td>
                  <a href='javascript:void(0)'>编辑</a> | 
                  <a href='javascript:void(0)'>置顶</a> | 
                  <a href='javascript:void(0)' name=del>删除</a>
              </td>
           </tr>
           </table>
           <div class="page_nav">
           <span> 5条数据 共1页</span>
           <strong>1</strong>
           </div>
        </div>
        
        <%-- 文章类型 --%>
        <div id="type_list" style="display:none">
           <jsp:include flush="true" page="/WEB-INF/pages/_article_type_tab.jsp" />
        </div>
        
        <div id="draft_list" style="display:none">
        <table id="lstBox" cellspacing="0">
           <tr>
             <th class="tdleft">标题</th>
             <th style="width:50px;">阅读</th>
             <th style="width:50px;">评论</th>
             <th style="width:70px;">评论权限</th>
             <th style="width:140px;">操作</th>
           </tr>
           <tr>
              <td class='tdleft'>
                  <a href='javascript:void(0)'>Rails自动校验</a><span class='gray'>（2012-02-13 13:38）</span>
              </td>
              <td>154</td>
              <td>0</td>
              <td><a href='javascript:void(0)' class='lock'>&nbsp;</a></td>
              <td>
                  <a href='javascript:void(0)'>编辑</a> | 
                  <a href='javascript:void(0)'>置顶</a> | 
                  <a href='javascript:void(0)' name=del>删除</a>
              </td>
           </tr>
           <tr>
              <td class='tdleft'>
                 <a href='javascript:void(0)'>Git简单的安装与使用</a><span class='gray'>（2011-11-07 17:16）</span>
              </td>
              <td>648</td>
              <td>0</td>
              <td><a href='javascript:void(0)' class='lock'>&nbsp;</a></td>
              <td>
                  <a href='javascript:void(0)'>编辑</a> | 
                  <a href='javascript:void(0)'>置顶</a> | 
                  <a href='javascript:void(0)' name=del>删除</a>
              </td>
           </tr>
           <tr>
              <td class='tdleft'>
                 <a href='javascript:void(0)'>结合SyntaxHighlighter给KindEditor3.5.5添加[插入源代码]自定义组件</a><span class='gray'>（2011-11-07 17:16）</span>
              </td>
              <td>648</td>
              <td>0</td>
              <td>&nbsp;</td>
              <td>
                  <a href='javascript:void(0)'>编辑</a> | 
                  <a href='javascript:void(0)'>置顶</a> | 
                  <a href='javascript:void(0)' name=del>删除</a>
              </td>
           </tr>
           </table>
           <div class="page_nav">
           <span> 1条数据 共1页</span>
           <strong>1</strong>
           </div>
        </div>
        
        <div id="blog_images_list" style="display:none;">
          <dl>
             <dd>
                <a rel="lightbox[roadtrip]" title="测试图片" href="<%= request.getContextPath() %>/resources/images/header.jpg">
                    <img src="<%= request.getContextPath() %>/resources/images/header.jpg" width="140" height="104" alt="测试图片" title="测试图片" />
                </a>
             </dd>
             <dt>
                <a href="<%= request.getContextPath() %>/resources/images/header.jpg" rel="lightbox">测试图片</a>&nbsp;&nbsp;<a href="javascript:void(0)" >删除</a>
             </dt>
          </dl>
          <dl>
             <dd>
                <a rel="lightbox[roadtrip]" title="测试图片" href="<%= request.getContextPath() %>/resources/images/header.jpg">
                    <img src="<%= request.getContextPath() %>/resources/images/header.jpg" width="140" height="104" alt="测试图片" title="测试图片" />
                </a>
             </dd>
             <dt>
                <a href="<%= request.getContextPath() %>/resources/images/header.jpg" rel="lightbox">测试图片</a>&nbsp;&nbsp;<a href="javascript:void(0)" >删除</a>
             </dt>
          </dl>
        </div>
        
        <div id="configure_info" style="display:none;">
           <form:form action="handleInfo.html" id="blogInfoForm" method="post" modelAttribute="blogInfo">
           <form:hidden path="id" />
           <p id="configure-messages" style="display:none; color:blue"></p>
           <p id="configure-errors" style="display:none; color:red"></p>
           <p><spring:message code="page.label.head"/><br /><form:input path="head" maxlength="30" cssStyle="width:430px"/></p>
           <p><spring:message code="page.label.descriptions" /><br /><form:input path="descriptions" maxlength="30" cssStyle="width:430px" /></p>
           <p><spring:message code="page.label.email" /><br /><form:input path="email" maxlength="30" cssStyle="width:430px" /></p>
           <p><spring:message code="page.label.about"/><br /><form:textarea id="blog_description" path="about"></form:textarea></p>
           <p>
             <input id="saveBlogInfoButton" type="button" value="保存" />
           </p>
           </form:form>
           <script type="text/javascript">
			   KE.show({
					id : "blog_description",
					//skinType: "custom01",
					width : "500px", 
					height : "250px", 
					filterMode : false, //不会过滤HTML代码
					resizeMode : 1, //编辑器只能调整高度
					items: ['source', '|','fontname', 'fontsize', '|', 'textcolor','bgcolor', 'bold', 'italic',
							'underline', 'strikethrough', 'removeformat', '|', 'hr', 'link', 'unlink']
			   });
		   </script>
        </div>
        
      <div class="copyright_declare">
      Powered by <a href="http://www.heroku.com">Heroku</a>,Design by <a href="https://twitter.com/lzqwebsoft">Johnny</a>.
      </div>
	</div>
</div>

<%@ include file="/WEB-INF/pages/common/footer.jsp" %>
</body>
</html>
