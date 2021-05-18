<%@ page import="com.course.work.buy_and_sale_house.entity.Deal" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<% SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");%>
<html>
<head>
    <title>Райони</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="container">
    <form action="${pageContext.request.contextPath}/district" method="post" class="mb-2">
        <div class="form-label-group">
            <label for="name">Назва</label>
            <input id="name" class="form-control" type="text" name="name"/>
        </div>
        <button class="btn btn-primary">Додати</button>
    </form>
    <h3>Райони</h3>
    <h4>${message}</h4>
    <table class="table table-striped mb-3 text-center">
        <thead class="thead-light">
        <tr>
            <th>
                Id
            </th>
            <th>
                Назва
            </th><th></th><th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="district" items="${districts}">
            <tr>
                <td>${district.id}</td>
                <td>${district.name}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/district/${district.id}" method="get">
                        <button class="btn btn-secondary">Змінити</button>
                    </form>
                </td>
                <td><form method="post" action="${pageContext.request.contextPath}/district/delete">
                    <input type="hidden" name="id" value="${district.id}"/>
                    <button class="btn btn-danger">Видалити</button>
                </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
