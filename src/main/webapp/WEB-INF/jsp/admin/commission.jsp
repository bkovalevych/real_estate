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
    <title>Admin Commission</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="container">
    <table class="table table-striped mb-3 text-center">
        <h4>Change current commission</h4>
        <form action="${pageContext.request.contextPath}/admin/commission" method="post">
            <div class="form-group">
                <label>нова комісія</label>
                <input class="form-control" type="number" name="commission" placeholder="0.2"
                       min="0.01" max="1" step="0.01"
                />
            </div>
            <button class="btn btn-primary">змінити</button>
        </form>
        <thead class="thead-light">
        <tr>
            <th>
                Id
            </th>
            <th>
                Дійсна з
            </th>
            <th>
                Комісія
            </th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="commission" items="${commissions}">
            <tr>
                <td>${commission.id}</td>
                <td>${commission.isRealFrom}</td>
                <td>${commission.commission}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
