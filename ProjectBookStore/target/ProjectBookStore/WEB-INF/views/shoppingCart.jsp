<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 23.05.2020
  Time: 22:46
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shopping Cart</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles.css">
</head>
<body>
<jsp:include page="header.jsp" />
<jsp:include page="menu.jsp" />
<fmt:setLocale value="en_US" scope="session"/>

<div class="page-title">My Cart</div>
<c:if test="${empty cartForm or empty cartForm.cartLines}">
    <h2>There is no items in Cart</h2>
    <a href="${pageContext.request.contextPath}/allBooks">Show
        Book List</a>
</c:if>
<c:if test="${not empty cartForm and not empty cartForm.cartLines}">
    <form:form method="POST" modelAttribute="cartForm"
               action="${pageContext.request.contextPath}/shoppingCart">
        <c:forEach items="${cartForm.cartLines}" var="cartLineInfo"
                   varStatus="varStatus">
            <div class="book-preview-container">
                <ul>
                    <li><img class="book-image"
                             src="${pageContext.request.contextPath}/bookImage?code=${cartLineInfo.bookInfo.code}" />
                    </li>
                    <li>Code: ${cartLineInfo.bookInfo.code} <form:hidden
                            path="cartLines[${varStatus.index}].bookInfo.code" />

                    </li>
                    <li>Name: ${cartLineInfo.bookInfo.name}</li>
                    <li>Price: <span class="price">
                         <fmt:formatNumber value="${cartLineInfo.bookInfo.price}" type="currency"/>
                       </span></li>
                    <li>Quantity: <form:input
                            path="cartLines[${varStatus.index}].quantity" /></li>
                    <li>Subtotal:
                        <span class="subtotal">
                            <fmt:formatNumber value="${cartLineInfo.amount}" type="currency"/>
                         </span>
                    </li>
                    <li><a
                            href="${pageContext.request.contextPath}/shoppingCartRemoveBook{cartLineInfo.bookInfo.code}">
                        Delete </a></li>
                </ul>
            </div>
        </c:forEach>
        <div style="clear: both"></div>
        <input class="button-update-sc" type="submit" value="Update Quantity" />
        <a class="navi-item"
           href="${pageContext.request.contextPath}/shoppingCartCustomer">Enter
            Customer Info</a>
        <a class="navi-item"
           href="${pageContext.request.contextPath}/bookList">Continue
            Buy</a>
    </form:form>
</c:if>
<jsp:include page="footer.jsp" />
</body>
</html>
