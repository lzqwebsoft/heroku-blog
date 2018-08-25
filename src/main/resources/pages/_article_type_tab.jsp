<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<table class="table table-hover table-vcenter">
    <thead>
        <tr>
            <th>类别</th>
            <th class="text-center" style="width: 120px;">文章</th>
            <th class="text-center" style="width: 100px;">操作</th>
        </tr>
    </thead>
    <tbody>
        <c:if test="${requestScope.articleTypes!=null&&fn:length(requestScope.articleTypes)>0}">
            <c:forEach items="${requestScope.articleTypes}" var="articleType">
                <tr>
                    <td><span id="article_type_${articleType.id}"><c:out value="${articleType.name}" /></span></td>
                    <td class="text-center"><c:out value="${fn:length(articleType.articles)}" /></td>
                    <td class="text-center">
                        <a href='javascript:void(0)' onclick="edit_article_type('article_type_${articleType.id}', '${articleType.id}'); return false">编辑</a> |
                        <a href='javascript:void(0)' onclick="confirm_article_type_delete('<spring:message code="page.confirm.delete.articletype" arguments="${articleType.name}" />', '${articleType.id}');">删除</a><br />
                        <c:if test="${articleType.disable}"> <a href='javascript:void(0)' onclick="disable_article_type('${articleType.id}', false)" name=del>显示</a> | 隐藏 </c:if>
                        <c:if test="${!articleType.disable}"> 显示 | <a href='javascript:void(0)' onclick="disable_article_type('${articleType.id}', true)" name=del>隐藏</a></c:if>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
    </tbody>
</table>
<div id="add_type_div">
    <form class="form-inline" role="form" onsubmit="return false;">
        <div class="form-group">
            <input id="txtArticleType" class="form-control" type="text" size="50" placeholder="请输入新的类别名称" />
        </div>
        <button id="btnAdd" class="btn btn-default" onclick="add_article_type()">添加分类</button>
        <c:if test="${requestScope.errorInfo!=null}">
           <label id="type-info" style="color: red;"><c:out value="${requestScope.errorInfo}" /></label>
       </c:if>
    </form>
</div>