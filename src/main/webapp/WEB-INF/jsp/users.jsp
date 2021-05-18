<%--
  Created by IntelliJ IDEA.
  User: bohdan
  Date: 03.03.2021
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<html>
<head>
    <title>Real Estate Користувачі</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<table class="table table-striped">
    <thead class="thead-light">
    <tr>
        <th>
            Нікнейм
        </th>
        <th>
            Ім'я
        </th>
        <th>
            Прізвище
        </th>
        <th>
            Номер телефону
        </th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">

        <tr>
            <td>${user.username}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.phoneNumber}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
