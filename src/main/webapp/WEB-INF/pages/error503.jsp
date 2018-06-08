<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title><spring:message code="page.title" /></title>
<%@ include file="/WEB-INF/pages/common/default_css.jsp"%>
</head>

<body>
    <%@ include file="/WEB-INF/pages/common/header.jsp"%>

    <div id="blog-header" class="container">
        <h2>
            <spring:message code="info.error503.title" />
        </h2>
        <div class="media">
            <a class="pull-left" href="javascript:void(0);"> <img class="media-object" src="<%=request.getContextPath()%>/resources/images/error503.jpg" />
            </a>
            <div class="media-body">
                <h4>&nbsp;</h4>
                <p>
                    <spring:message code="info.error503.content" />
                </p>
            </div>
        </div>

        <!-- 页面底端说明 -->
        <%@ include file="/WEB-INF/pages/common/footer.jsp"%>
    </div>

    <%@ include file="/WEB-INF/pages/common/default_js.jsp"%>
</body>
</html>