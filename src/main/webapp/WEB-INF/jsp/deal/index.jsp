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
    <title>Deals</title>
</head>
<body>
<h3 class="text-center alert alert-primary">Договори</h3>
<div class="container">
    <div class="row card">
        <div class="card-header">В якості продавця</div>
        <div class="card-body">
            <c:choose>
                <c:when test="${dealsSeller != null and not dealsSeller.isEmpty()}">
                    <table class="table table-striped mb-3 text-center">
                        <thead class="thead-light">
                        <tr>
                            <th>Id</th>
                            <th>Покупець</th>
                            <th>Тип</th>
                            <th>Площа</th>
                            <th>Район</th>
                            <th>Кількість кімнат</th>
                            <th>Дата</th>
                            <th>Опис</th>
                            <th>Ціна</th>
                            <th>Комісія</th>
                            <th>Виручка</th>
                        </tr>
                        </thead>
                        <tbody>
                        <% Double profit = 0.0;%>
                        <c:forEach var="deal" items="${dealsSeller}">
                            <c:set var="deal" value="${deal}" scope="page"/>
                            <% Deal deal = (Deal)pageContext.getAttribute("deal");%>
                            <% profit += deal.getPropertyForSale().getPrice() * (1 - deal.getCommission());%>
                            <tr>
                                <td>${deal.id}</td>
                                <td>${deal.buyer.username}</td>
                                <td>${deal.propertyForSale.type}</td>
                                <td>${deal.propertyForSale.area}</td>
                                <td>${deal.propertyForSale.district.name}</td>
                                <td>${deal.propertyForSale.numberOfRooms}</td>
                                <td> <%= sdf.format(deal.getDateOfDeal()) %></td>
                                <td>
                                    <%= deal.getPropertyForSale().getDescription().substring(0, Math.min(30, deal.getPropertyForSale().getDescription().length())) + "..." %>
                                </td>
                                <td>${deal.propertyForSale.price}</td>
                                <td>${deal.commission}</td>
                                <td>${deal.propertyForSale.price * (1 - deal.commission)}</td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                            <td>Всього</td>
                            <td><%=profit%></td>
                        </tr>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <h5 class="text-center alert alert-info">У вас немає угод в якості продавця</h5>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
    <div class="row card">
        <div class="card-header">У якості покупця</div>
        <div class="card-body">
            <c:choose>
                <c:when test="${dealsBuyer != null and not dealsBuyer.isEmpty()}">
                    <table class="table table-striped mb-3 text-center">
                        <thead class="thead-light">
                        <tr>
                            <th>Id</th>
                            <th>Продавець</th>
                            <th>Тип</th>
                            <th>Площа</th>
                            <th>Район</th>
                            <th>Кількість кімнат</th>
                            <th>Дата</th>
                            <th>Опис</th>
                            <th>Ціна</th>
                        </tr>
                        </thead>
                        <tbody>
                        <% Double priceSum = 0.0;%>
                        <c:forEach var="deal" items="${dealsBuyer}">
                            <c:set var="deal" value="${deal}" scope="page"/>
                            <% Deal deal = (Deal)pageContext.getAttribute("deal");%>
                            <% priceSum += deal.getPropertyForSale().getPrice();%>
                            <tr>
                                <td>${deal.id}</td>
                                <td>${deal.seller.username}</td>
                                <td>${deal.propertyForSale.type}</td>
                                <td>${deal.propertyForSale.area}</td>
                                <td>${deal.propertyForSale.district.name}</td>
                                <td>${deal.propertyForSale.numberOfRooms}</td>
                                <td> <%= sdf.format(deal.getDateOfDeal()) %></td>
                                <td>
                                    <%= deal.getPropertyForSale().getDescription().substring(0, Math.min(30, deal.getPropertyForSale().getDescription().length())) + "..." %>
                                </td>
                                <td>${deal.propertyForSale.price}</td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td></td><td></td><td></td><td></td><td></td><td></td><td></td>
                            <td>Вього</td>
                            <td><%=priceSum%></td>
                        </tr>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <h5 class="text-center alert alert-info">У вас немає угод в якості покупця</h5>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</div>
</body>
</html>
