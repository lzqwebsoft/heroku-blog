<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="currDate" class="java.util.Date"></jsp:useBean>  
<hr />
<footer>
    <p>
        Powered by <a href="http://www.heroku.com">Heroku</a>, UI by <a href="http://www.bootcss.com/">Bootstrap</a>, Design by <a href="https://twitter.com/lzqwebsoft">lzqwebsoft</a>.
    </p>
    <p>Copyright &copy; 2012 - <fmt:formatDate value="${currDate}" pattern="yyyy" /></p>
</footer>