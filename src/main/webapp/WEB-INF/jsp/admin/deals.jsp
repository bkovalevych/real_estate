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
    <title>Admin Deals</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="container">
    <table class="table table-striped mb-3 text-center">
        <form action="${pageContext.request.contextPath}/admin/deals" method="get">
            <div class="form-label-group">
                <label for="from">З</label>
                <input id="from" class="form-control" type="date" name="start"
                       value='${start}'
                       autofocus/>
            </div>
            <div class="form-label-group">
                <label for="finish">По</label>
                <input id="finish" class="form-control" type="date" name="finish"
                       value='${finish}'
                       autofocus
                />
            </div>
            <button class="btn btn-primary">Оновоити</button>
        </form>
        <form action="${pageContext.request.contextPath}/admin/deals/download" method="post">
            <input type="hidden" name="finish" value='${finish}'/>
            <input type="hidden" name="start" value='${start}'/>
            <button class="btn btn-primary">Зберегти</button>
        </form>
        <thead class="thead-light">
        <tr>
            <th>
                Id
            </th>
            <th>
                Покупець
            </th>
            <th>
                Продавець
            </th>
            <th>
                Дата
            </th>
            <th>
                Комісія
            </th>
            <th>
                Ціна
            </th>
            <th>
                Виручка
            </th>
        </tr>
        </thead>
        <tbody>
        <% double priceSum = 0; %>
        <% double profitSum = 0; %>
        <c:forEach var="deal" items="${deals}">

            <%
                Deal d = (Deal)pageContext.getAttribute("deal");
                double price =  d.getPropertyForSale().getPrice();
                double commission = d.getCommission();
                double profit = commission * price;
                String date = sdf.format(d.getDateOfDeal());
                priceSum += price;
                profitSum += profit;
            %>
            <tr>
                <td>${deal.id}</td>
                <td>${deal.buyer.username}</td>
                <td>${deal.seller.username}</td>
                <td><%= date %></td>
                <td><%= commission%></td>
                <td><%= price%></td>
                <td><%= profit%></td>
            </tr>
        </c:forEach>
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td>Вього</td>
        <td><%= priceSum%></td>
        <td><%= profitSum%></td>
        </tbody>
    </table>
</div>

</body>
</html>
