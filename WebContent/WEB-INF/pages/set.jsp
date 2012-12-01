<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>博客设置-lzqwebsoft's blog</title>
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>resources/style/comment.css" />
<link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>resources/style/set.css" />
<script type="text/javascript" src="<%= request.getContextPath() %>resources/javascript/comment.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>resources/javascript/default.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>resources/javascript/set.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>resources/javascript/kindeditor3.5/kindeditor.js"></script>
<link rel="stylesheet" href="<%= request.getContextPath() %>resources/style/lightbox.css" type="text/css" media="screen" />
<script type="text/javascript" src="<%= request.getContextPath() %>resources/javascript/lightbox2.05/prototype.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>resources/javascript/lightbox2.05/scriptaculous.js?load=effects,builder"></script>
<script type="text/javascript" charset="utf-8" src="<%= request.getContextPath() %>resources/javascript/lightbox2.05/lightbox.js"></script>

</head>

<body>
<div id="modTopbar" class="mod-topbar">
  <div class="wrapper-box clearfix">
  
     <div class="left-box">&nbsp;</div>
     
     <div class="center-box">
         <ul class="q-menubox">
           <li class="q-menuitem">
              <a href="http://lzqwebsoft.herokuapp.com">首页</a>
           </li>
           <li class="q-menuitem">
              <a href="javascript:void(0)" onclick="show_about_dialog()">关于</a>
           </li>
         </ul>
     </div>
     
     <div class="right-box">
        <ul class="q-navbox">
           <li class="q-navitem">
              <a href="javascript:void(0)"><img alt="管理" src="resource/images/set_icon.png"/></a>
              <!--子菜单-->
              <ul>
                <li>
                   <a href="javascript:void(0)" onclick="show_login_dialog();" >登&nbsp;&nbsp;&nbsp;录</a>
                </li>
                <li>
                   <a href="new.jsp">发表博客</a>
                </li>
                <li>
                   <a href="change_password.jsp">修改密码</a>
                </li>
                <li>
                   <a href="javascript:void(0)">设&nbsp;&nbsp;&nbsp;置</a>
                </li>
             </ul>
           </li>
        </ul>
     </div>
     
  </div>
  
</div>

<div class="goBackToTop">
   <a id="gototop" title="回到顶端" href="#container">TOP</a>
</div>

<div id="container">
	<div id="back_intro">
		<h2>博客管理</h2>
        <div class="tabs" id="tabs">
           <ul>
              <li><a id="article_list_tag" class="selected" href="javascript:void(0)" onclick="showTab('article_list'); this.blur(); return false;">文章列表</a></li>
              <li><a id="type_list_tag" href="javascript:void(0)" onclick="showTab('type_list'); this.blur(); return false;">类别管理</a></li>
              <li><a id="draft_list_tag" href="javascript:void(0)" onclick="showTab('draft_list'); this.blur(); return false;">草稿箱</a></li>
              <li><a id="blog_images_list_tag" href="javascript:void(0)" onclick="showTab('blog_images_list'); this.blur(); return false;">博客用图</a></li>
              <li><a id="configure_info_tag" href="javascript:void(0)" onclick="showTab('configure_info'); this.blur(); return false;">信息设置</a></li>
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
                 <input type="text" name="title" maxlength="30" size="50" />
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
        
        <div id="type_list" style="display:none">
           <table id="lstBox" cellspacing="0">
           <tr>
             <th class="tdleft">类别</th>
             <th style="width:120px;">文章</th>
             <th style="width:100px;">操作</th>
           </tr>
           <tr>
              <td class='tdleft'>
                  <span id="article_type_1"><a href='javascript:void(0)'>JavaSE</a></span>
              </td>
              <td>0</td>
              <td>
                  <a href='javascript:void(0)' onclick="edit_article_type('article_type_1'); return false">编辑</a> | 
                  <a href='javascript:void(0)'>删除</a><br /> 
                  <a href='javascript:void(0)' name=del>显示</a> | 
                  <a href='javascript:void(0)' name=del>隐藏</a>
              </td>
           </tr>
           <tr>
              <td class='tdleft'>
                 <span id="article_type_2"><a href='javascript:void(0)'>J2EE</a></span>
              </td>
              <td>0</td>
              <td>
                  <a href='javascript:void(0)' onclick="edit_article_type('article_type_2'); return false">编辑</a> | 
                  <a href='javascript:void(0)'>删除</a><br /> 
                  <a href='javascript:void(0)' name=del>显示</a> | 
                  <a href='javascript:void(0)' name=del>隐藏</a>
              </td>
           </tr>
           <tr>
              <td class='tdleft'>
                 <span id="article_type_3"><a href='javascript:void(0)'>Ruby</a></span>
              </td>
              <td>0</td>
              <td>
                  <a href='javascript:void(0)' onclick="edit_article_type('article_type_3'); return false">编辑</a> | 
                  <a href='javascript:void(0)'>删除</a><br /> 
                  <a href='javascript:void(0)' name=del>显示</a> | 
                  <a href='javascript:void(0)' name=del>隐藏</a>
              </td>
           </tr>
           </table>
           <div id="add_type_div">
              <input id="txtCat" class="t_input" type="text" maxlength="30" size="50" />
              <input id="btnAdd" class="t_btn" type="button" value="添加分类" />
           </div>
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
                <a rel="lightbox[roadtrip]" title="测试图片" href="resource/images/header.jpg">
                    <img src="resource/images/header.jpg" width="140" height="104" alt="测试图片" title="测试图片" />
                </a>
             </dd>
             <dt>
                <a href="resource/images/header.jpg" rel="lightbox">测试图片</a>&nbsp;&nbsp;<a href="javascript:void(0)" >删除</a>
             </dt>
          </dl>
          <dl>
             <dd>
                <a rel="lightbox[roadtrip]" title="测试图片" href="resource/images/header.jpg">
                    <img src="resource/images/header.jpg" width="140" height="104" alt="测试图片" title="测试图片" />
                </a>
             </dd>
             <dt>
                <a href="resource/images/header.jpg" rel="lightbox">测试图片</a>&nbsp;&nbsp;<a href="javascript:void(0)" >删除</a>
             </dt>
          </dl>
        </div>
        
        <div id="configure_info" style="display:none;">
           <p>博客名称<br /><input type="text" name="blog_name" maxlength="30" size="50" /></p>
           <p>个性签名<br /><input type="text" name="signature" maxlength="30" size="50" /></p>
           <p>关联邮箱<br /><input type="text" name="blog_email" maxlength="30" size="50" /></p>
           <p>
              博客描述<br />
              <textarea id="blog_description" name="blog_description"></textarea>
           </p>
           <p>
             <input type="button" value="保存" />
           </p>
           <script type="text/javascript">
			   KE.show({
					id : "blog_description",
					skinType: "custom01",
					width : "460px", //编辑器的宽度为70%
					height : "200px", //编辑器的高度为100px
					filterMode : false, //不会过滤HTML代码
					resizeMode : 0, //编辑器只能调整高度
					items: ['fontname', 'fontsize', '|', 'textcolor','bgcolor', 'bold', 'italic',
							'underline', 'strikethrough', 'removeformat', '|', 'hr', 'link', 'unlink']
			   });
		   </script>
        </div>
        
      <div class="copyright_declare">
      Powered by <a href="http://www.heroku.com">Heroku</a>,Design by <a href="https://twitter.com/lzqwebsoft">Johnny</a>.
      </div>
	</div>
</div>

<!-- These extra divs/spans may be used as catch-alls to add extra imagery. -->
<div id="extraDiv1"><span></span></div><div id="extraDiv2"><span></span></div><div id="extraDiv3"><span></span></div>
<div id="extraDiv4"><span></span></div><div id="extraDiv5"><span></span></div><div id="extraDiv6"><span></span></div>
</body>
</html>
