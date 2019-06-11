<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="link-operation-bar">
    <button id="add-link-button" type="button" class="btn btn-default">添加链接</button>
</div>

<c:choose>
    <c:when test="${links!=null&&links.data!=null&&fn:length(links.data)>0}">
        <table class="table table-hover table-vcenter">
            <thead>
            <tr>
                <th>名称</th>
                <th class="text-center">地址</th>
                <th class="text-center">备注</th>
                <th class="text-center">创建时间</th>
                <th style="width: 140px;" class="text-center">操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${links.data}" var="link">
                <tr>
                    <td><c:out value="${link.name}"/></td>
                    <td class="text-center"><a href="<c:out value="${link.path}"/>" target="_blank"><c:out value="${link.path}"/></a></td>
                    <td class="text-center"><c:out value="${link.remark}"/></td>
                    <td class="text-center"><fmt:formatDate value="${link.createAt}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td class="text-center">
                        <a href='javascript:void(0);' onclick="edit_link('${link.id}', '${links.currentPageNo}');">编辑</a>&nbsp;|&nbsp;
                        <a href='javascript:void(0);' name=del
                           onclick="confirm_link_delete('<spring:message code="page.confirm.delete.link" arguments="${fn:escapeXml(link.name)}"/>', '${link.id}', '${links.currentPageNo}')">删除</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <c:if test="${links!=null&&links.data!=null&&fn:length(links.data)>0}">
            <c:url value="/link/page.html" var="page_link_url"/>
            <ul class="pagination">
                <c:if test="${links.hasPreviousPage}">
                    <li><a href="javascript:void(0)" onclick="link_page_update('${page_link_url}', '${links.currentPageNo-1}')">&laquo;</a></li>
                </c:if>
                <c:forEach var="index" begin="${links.pageRangeStart}" end="${links.pageRangeEnd}" step="1">
                    <c:choose>
                        <c:when test="${index!=links.currentPageNo}">
                            <li><a href="javascript:void(0)" onclick="link_page_update('${page_link_url}', '${index}')">${index}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="active"><a href="javascript:void(0)">${index}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <c:if test="${links.hasNextPage}">
                    <li><a href="javascript:void(0)" onclick="link_page_update('${page_link_url}', '${links.currentPageNo+1}')">&raquo;</a></li>
                </c:if>
            </ul>
            <div id="page_description">${links.totalCount}个相关链接, 共${links.totalPageCount}页</div>
        </c:if>
    </c:when>

    <c:otherwise>
        <p>暂无内容</p>
    </c:otherwise>
</c:choose>