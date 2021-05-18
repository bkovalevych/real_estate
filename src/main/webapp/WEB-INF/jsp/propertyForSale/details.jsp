<%--
  Created by IntelliJ IDEA.
  User: bohdan
  Date: 19.03.2021
  Time: 14:38
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
    <title>Нерухомості</title>
</head>
<body>




<div class="container">
    <div class="row">
        <nav class="navbar">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/propertyForSale?isMy=true">Повернутись</a>
                </li>
            </ul>
        </nav>
    </div>
    <c:if test="${deal != null}">
        <div class="row justify-content-center">
            <div class="col w-75 card text-center">
                <div class="card-header">Заключена угода</div>
                <div class="card-body">
                    <h5 class="card-title">Статус - ${deal.status}</h5>
                    <h5 class="card-title">Комісія - ${deal.commission}</h5>
                    <h5 class="card-title">Продавець - ${deal.seller.username}</h5>
                    <h5 class="card-title">Покупець - ${deal.buyer.username}</h5>
                    <h5 class="card-title">Узгоджено покупцем - ${deal.acceptedByBuyer}</h5>
                    <h5 class="card-title">Узгоджено продавцем - ${deal.acceptedBySeller}</h5>

                    <h5 class="card-title">Площа - ${deal.propertyForSale.area}</h5>
                    <h5 class="card-title">Вулиця - ${deal.propertyForSale.district.name}</h5>
                    <h5 class="card-title">Кількість кімнат - ${deal.propertyForSale.numberOfRooms}</h5>
                    <h5 class="card-title">Ціна - ${deal.propertyForSale.price}</h5>
                    <h5 class="card-title">Виручка - ${deal.propertyForSale.price * (1 - deal.commission)}</h5>

                    <h5 class="card-title">
                        <% PropertyForSale sale = (PropertyForSale) request.getSession().getAttribute("sale");%>
                        Дата - <%= sdf.format(sale.getDate()) %>
                    </h5>
                </div>
            </div>
        </div>
    </c:if>

    <c:if test="${isMy and !sale.status.equals('SOLD')}">
        <div class="row justify--content-around">
            <div class="col card">
                <div class="card-header">Одобрені перегляди</div>
                <div class="card-body">
                    <table class="table table-striped mb-3 text-center">
                        <thead class="thead-light">
                        <tr>
                            <th>Id</th>
                            <th>Ім'я користувача</th>
                            <th>Телефон</th>
                            <th>Опис</th>
                            <th>Ціна</th>
                            <th>Додати в пропозицію</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="req" items="${sale.selectedRequestToBuy}">
                            <tr>
                                <td>${req.id}</td>
                                <td>${req.user.username}</td>
                                <td>${req.user.phoneNumber}</td>
                                <td>${req.description}</td>
                                <td><c:choose>
                                    <c:when test="${req.minPrice == null and req.maxPrice == null}">
                                        ціна не важлива
                                    </c:when>
                                    <c:otherwise>
                                        <c:if test="${req.minPrice != null}">
                                            мін $${req.minPrice}&nbsp;
                                        </c:if>
                                        <c:if test="${req.maxPrice != null}">
                                            макс ${req.maxPrice}
                                        </c:if>
                                    </c:otherwise>
                                </c:choose>
                                </td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/propertyForSale/${sale.id}/addCandidate" method="post">
                                        <input type="hidden" name="requestId" value="${req.id}" />
                                        <input type="submit" value="Додати" class="btn btn-secondary" />
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col card">
                <div class="card-header">Кандидати</div>
                <div class="card-body">
                    <table class="table table-striped mb-3 text-center">
                        <thead class="thead-light">
                        <tr>
                            <th>Id</th>
                            <th>Ім'я користувача</th>
                            <th>Телефон</th>
                            <th>Опис</th>
                            <th>Підтверджено користувачем</th>
                            <th>Договір</th>
                            <th>Видалити</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="req" items="${sale.candidates}">
                            <tr>
                                <td>${req.id}</td>
                                <td>${req.user.username}</td>
                                <td>${req.user.phoneNumber}</td>
                                <td>${req.description}</td>
                                <td><c:choose>
                                    <c:when test="${sale.equals(req.saleChosenByUser)}">
                                        так
                                    </c:when>
                                    <c:otherwise>
                                        ні
                                    </c:otherwise>
                                </c:choose>
                                </td>
                                <td>
                                    <c:if test="${sale.equals(req.saleChosenByUser)}">
                                        <form action="${pageContext.request.contextPath}/propertyForSale/${sale.id}/deal" method="post">
                                            <input type="hidden" name="requestId" value="${req.id}" />
                                            <input type="submit" value="заключити" class="btn btn-primary" />
                                        </form>
                                    </c:if>
                                </td>
                                <td>
                                    <form action="${pageContext.request.contextPath}/propertyForSale/${sale.id}/deleteCandidate" method="post">
                                        <input type="hidden" name="requestId" value="${req.id}" />
                                        <input type="submit" value="видалити" class="btn btn-danger" />
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${isMy and !sale.status.equals('SOLD')}">
        <div class="row justify-content-center">
            <div class="col col-lg-5 card text-center">
                <div class="card-header">Змінити статус</div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/propertyForSale/${sale.id}/changeStatus" method="post">
                        <div class="form-group row">
                            <div class="col">
                                <div class="form-floating">
                                    <select name="status" id="status" class="form-control" required>
                                        <option value="HIDDEN"
                                                <c:if test="${sale.status == 'HIDDEN'}"> selected </c:if>
                                        >Приховано</option>
                                        <option value="OK"
                                                <c:if test="${sale.status == 'OK'}"> selected </c:if>
                                        >Відкрито</option>
                                    </select>
                                    <label for="status" class="form-label">Статус</label>
                                </div>
                                <div class="text-center">
                                    <input type="submit" value="Змінити" class="btn btn-primary"/>
                                </div>
                            </div>
                            <div class="col">
                                <div class="text-center input-group mb-2">
                                    <p class="alert alert-warning">Попередження! Інші не зможуть бачити пропозицію</p>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </c:if>

    <div class="row justify-content-around">
        <div class="col card text-center">
            <div class="card-header">
                Нерухомість на продажу
            </div>
            <div class="card-body">
                <h5 class="card-title">Статус - ${sale.status}</h5>
                <h5 class="card-title">Id - ${sale.id}</h5>
                <h5 class="card-title">Ім'я користувача - ${sale.user.username}</h5>
                <h5 class="card-title">Номер телефону - ${sale.user.phoneNumber}</h5>
                <h5 class="card-title">Тип - ${sale.type}</h5>
                <h5 class="card-title">Площа - ${sale.area}</h5>
                <h5 class="card-title">Район - ${sale.district.name}</h5>
                <h5 class="card-title">Кількість кімнат - ${sale.numberOfRooms}</h5>
                <h5 class="card-title">Ціна - ${sale.price}</h5>

                <h5 class="card-title">
                    <% PropertyForSale sale = (PropertyForSale) request.getSession().getAttribute("sale");%>
                    Дата - <%= sdf.format(sale.getDate()) %>
                </h5>
                <h5 class="card-title">Опис</h5>
                <p>
                    <%= sale.getDescription() %>
                </p>
                <h5 class="card-title">Повний опис</h5>
                <p>
                    <%= sale.getFullDescription() %>
                </p>
            </div>
        </div>
        <c:if test="${isMy and !sale.status.equals('SOLD')}">
            <div class="col card text-center">
                <div class="card-header">Змінити значення</div>
                <div class="card-body">
                    <form id="form_change" action="${pageContext.request.contextPath}/propertyForSale/${sale.id}/changeValues" method="post">
                        <input type="hidden" name="isMy" value="true">
                        <div class="form-floating mb-2">
                            <select id="district_p" name="district" class="form-control" required>
                                <c:if test="${not empty districts}">
                                    <c:forEach var="districtVar" items="${districts}">
                                        <c:if test="${not districtVar.hidden}">
                                            <option value="${districtVar.id}"
                                                    <c:if test="${sale.district == districtVar}">
                                                        selected
                                                    </c:if>
                                            >${districtVar.name}</option>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </select>
                            <label for="district_p" >Район</label>
                        </div>
                        <div class="form-floating mb-2">
                            <input type="text" name="address" id="address_p" class="form-control" placeholder="address" required
                            value="${sale.address}"/>
                            <label for="address_p" class="form-label">Адреса</label>
                        </div>
                        <div class="form-group row mb-2">
                            <div class="col col-xs-2 form-floating">
                                <input id="price_p" type="number" name="price" min="1" max="99999999" required
                                       class="form-control" placeholder="price"
                                       value="${sale.price}"
                                />
                                <label for="price_p" class="form-label">Ціна, $</label>
                            </div>
                            <div class="col col-xs-2 form-floating">
                                <input id="area_p" name="area" type="number" step="0.1" min="1" max="999999" required
                                       class="form-control" placeholder="area"
                                       value="${sale.area}"
                                />
                                <label for="area_p" class="form-label">Площа, кв м</label>
                            </div>
                        </div>
                        <div class="form-group row mb-2">
                            <div class="col col-xs-2 form-floating">
                                <input type="number" aria-label="number of" name="numberOfRooms" id="numberOfRooms_p" min="1" max="999" required class="form-control" placeholder="2"
                                value="${sale.numberOfRooms}"/>
                                <label for="numberOfRooms_p">Кількість кімнат</label>
                            </div>
                            <div class="col col-xs-2 form-floating">
                                <select name="type" id="type_p" class="form-control" required>
                                    <option value="Apartment"
                                            <c:if test="${sale.type == 'Apartment'}"> selected </c:if>
                                    >Квартира</option>
                                    <option value="House"
                                            <c:if test="${sale.type == 'House'}"> selected </c:if>
                                    >Будинок</option>
                                </select>
                                <label for="type_p" class="form-label">Тип</label>
                            </div>
                        </div>

                        <div class="form-floating mb-2">
                            <textarea class="form-control" rows="3" name="description" id="description_p"
                                      required >${sale.description}</textarea>
                            <label for="description_p" class="form-label">Короткий опис</label>
                        </div>
                        <div class="form-floating mb-2">
                            <textarea class="form-control" rows="10" name="fullDescription" id="full_description_p"
                          required>${sale.fullDescription}</textarea>
                            <label for="full_description_p" class="form-label">Повний опис</label>
                        </div>
                        <div class="text-center">
                            <input type="submit" value="change" class="btn btn-success btn-lg"/>
                        </div>
                        <div class="text-center input-group mb-2">
                            <p class="alert alert-warning">Попередження! При зміні значень, кандидати будуть видалені та підбір оновиться</p>
                        </div>
                    </form>
                </div>
            </div>

        </c:if>
    </div>


</div>
</body>
</html>
