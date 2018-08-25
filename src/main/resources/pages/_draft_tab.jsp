<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:choose>
    <c:when test="${requestScope.page_drafts!=null&&requestScope.page_drafts.data!=null&&fn:length(requestScope.page_drafts.data)>0}">
        <table class="table table-hover table-vcenter">
            <thead>
                <tr>
                    <th class="tdleft">标题</th>
                    <th style="width: 50px;" class="text-center">阅读</th>
                    <th style="width: 50px;" class="text-center">评论</th>
                    <th style="width: 140px;" class="text-center">操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${requestScope.page_drafts.data}" var="draft">
                    <tr>
                        <td><a href='<%= request.getContextPath() %>/show/${draft.id}.html'><c:out value="${draft.title == '' ? '无标题' : draft.title}" /></a> <span>(<fmt:formatDate value="${draft.createAt}" pattern="yyyy-MM-dd HH:mm:ss"/>)</span></td>
                        <td class="text-center"><c:out value="${draft.readedNum}" /></td>
                        <td class="text-center"><c:out value="${draft.commentCount}" /></td>
                        <td class="text-center">
                            <a href='<%= request.getContextPath() %>/edit/${draft.id}.html'>编辑</a> | 
                            <a href='javascript:void(0)' onclick="confirm_draft_delete('<spring:message code="page.confirm.delete.article" arguments="${draft.title == '' ? '无标题' : fn:escapeXml(draft.title)}" />', '${draft.id}', '${requestScope.page_drafts.currentPageNo}')" name=del>删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        
        <c:if test="${requestScope.page_drafts!=null&&requestScope.page_drafts.data!=null&&fn:length(requestScope.page_drafts.data)>0}">
        <c:url value="/draft/page.html" var="page_drafts_url" />
        <ul class="pagination">
            <c:if test="${requestScope.page_drafts.hasPreviousPage}">
                <li><a href="javascript:void(0)" onclick="draft_page_update('${page_drafts_url}', '${requestScope.page_drafts.currentPageNo-1}')">&laquo;</a></li>
            </c:if>
            <c:forEach var="index" begin="${requestScope.page_drafts.pageRangeStart}" end="${requestScope.page_drafts.pageRangeEnd}" step="1">
                <c:choose>
                    <c:when test="${index!=requestScope.page_drafts.currentPageNo}">
                        <li><a href="javascript:void(0)" onclick="draft_page_update('${page_drafts_url}', '${index}')">${index}</a></li>
                    </c:when>
                    <c:otherwise>
                        <li class="active"><a href="javascript:void(0)">${index}<span class="sr-only">(current)</span></a></li>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
            <c:if test="${requestScope.page_drafts.hasNextPage}">
            <li><a href="javascript:void(0)" onclick="draft_page_update('${page_drafts_url}', '${requestScope.page_drafts.currentPageNo+1}')">&raquo;</a></li>
            </c:if>
        </ul>
        <div id="page_description">${requestScope.page_drafts.totalCount}篇文章, 共${requestScope.page_drafts.totalPageCount}页</div>
        </c:if>
    </c:when>

    <c:otherwise>
        <p>暂无内容</p>
    </c:otherwise>
</c:choose>