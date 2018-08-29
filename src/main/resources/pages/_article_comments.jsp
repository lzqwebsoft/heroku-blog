<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:forEach items="${comments}" var="comment">
    <div class="root_comment">
        <p>
            <c:choose>
                <c:when test="${comment.isBlogger}">
                    <font color="red">博主</font>发表于：<fmt:formatDate value="${comment.createAt}" pattern="yyyy-MM-dd HH:mm:ss" />
                </c:when>
                <c:otherwise>
                    <a href="<c:out value="${comment.website}" default="javascript:void(0)" />">${comment.reviewer}</a>${comment.fromLocalLabel}发表于：<fmt:formatDate value="${comment.createAt}" pattern="yyyy-MM-dd HH:mm:ss" />
                </c:otherwise>
            </c:choose>
            <span class="pull-right">
                <c:choose>
                    <c:when test="${comment.isBlogger}">
                        <a href="#reply_article" title="回复" onclick="replay_comment('${comment.id}', '博主')">回复</a>&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="#reply_article" title="回复" onclick="replay_comment('${comment.id}', '${comment.reviewer}')">回复</a>&nbsp;
                    </c:otherwise>
                </c:choose>
                <c:if test="${sessionScope.user!=null}">
                    <a href="javascript:void(0)" title="删除" onclick="delete_article_comment('${comment.id}')">删除</a>
                </c:if>
            </span>
        </p>
        <div class="comment_content">
            <c:out value="${comment.content}" escapeXml="false" />
        </div>
        <c:forEach items="${comment.allChildComments}" var="childComment">
            <div class="child_comment">
                <p>
                    <c:choose>
                        <c:when test="${childComment.isBlogger}">
                            <font color="red">博主</font>发表于：<fmt:formatDate value="${childComment.createAt}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </c:when>
                        <c:otherwise>
                            Re：<a href="<c:out value="${childComment.website}" default="javascript:void(0)" />">${childComment.reviewer}</a>${childComment.fromLocalLabel}发表于：<fmt:formatDate value="${childComment.createAt}" pattern="yyyy-MM-dd HH:mm:ss" />
                        </c:otherwise>
                    </c:choose>
                    <span class="pull-right">
                        <c:choose>
                            <c:when test="${childComment.isBlogger}">
                                <a href="#reply_article" title="回复" onclick="replay_comment('${childComment.id}', '博主', '${comment.id}')">回复</a>&nbsp;
                            </c:when>
                            <c:otherwise>
                                <a href="#reply_article" title="回复" onclick="replay_comment('${childComment.id}', '${childComment.reviewer}', '${comment.id}')">回复</a>&nbsp;
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${sessionScope.user!=null}">
                            <a href="javascript:void(0)" title="删除" onclick="delete_article_comment('${childComment.id}')">删除</a>
                        </c:if>
                    </span>
                </p>
                <div class="comment_content">
                    <c:choose>
                        <c:when test="${childComment.parentComment!=null}">
                             <c:choose>
                             <c:when test="${childComment.parentComment.isBlogger}">回复<font color="red">博主</font>：<c:out value="${childComment.content}" escapeXml="false" /></c:when>
                             <c:otherwise>回复<a href="<c:out value="${childComment.parentComment.website}" default="javascript:void(0)" />"><c:out value="${childComment.parentComment.reviewer}" /></a>：<c:out value="${childComment.content}" escapeXml="false" /></c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${childComment.content}" escapeXml="false" />
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:forEach>
    </div>
</c:forEach>
<c:if test="${comments==null||fn:length(comments)<=0}">
    <p class="no_comments">
        <fmt:message key="page.info.no.comments" />
    </p>
</c:if>