<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 29.05.2020
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01
Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
    <title>Book search</title>
</head>
<body>
<jsp:include page="header.jsp" />
<jsp:include page="menu.jsp" />
<fmt:setLocale value="en_US" scope="session"/>
<div align="center">
    <h2>Book search</h2>
    <form method="get" action="/searchResult">
        <input type="text" name="keyword" />
        <input type="submit" value="Search" />
    </form>
</div>
</body>
</html>
