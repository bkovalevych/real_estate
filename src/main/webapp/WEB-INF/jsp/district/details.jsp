<%--
  Created by IntelliJ IDEA.
  User: bohdan
  Date: 23.03.2021
  Time: 13:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="com.course.work.buy_and_sale_house.entity.Deal" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.course.work.buy_and_sale_house.entity.RequestToBuy" %>
<%@ page import="com.course.work.buy_and_sale_house.entity.PropertyForSale" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>
<% SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");%>
<html>
<head>
    <title>Район Деталі</title>
</head>
<body>
<h3 class="text-center alert alert-primary">Район</h3>
<div class="container">
    <div class="row card">
        <div class="card-header">Змінити назву району</div>
        <div class="card-body">
            <form action="${pageContext.request.contextPath}/district/${district.id}" method="post">
                <div class="form-floating">
                    <input id="streetName" name="streetName"
                           class="form-control" type="text" placeholder="Shevchenko"
                           required
                           value="${district.name}"
                    >
                    <label for="streetName">Змінити назву</label>
                </div>
                <div class="input-group">
                    <button class="btn btn-secondary">Змінити</button>
                </div>

            </form>
        </div>
    </div>
</div>
</body>
</html>
