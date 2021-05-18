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
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>


<% SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");%>
<html>
<head>
    <title>Нерухомість</title>
</head>
<body>




<div class="container">
    <div class="row">
        <nav class="navbar">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/requestToBuy?isMy=true">Назад</a>
                </li>
            </ul>
        </nav>
    </div>
    <c:if test="${isMy and !req.status.equals('SOLD')}">
        <div class="row justify--content-around">
            <div class="col card">
                <div class="card-header">Ваші пропозиції</div>
                <div class="card-body">
                    <table class="table table-striped mb-3 text-center">
                        <thead class="thead-light">
                        <tr>
                            <th>Id</th>
                            <th>Ім'я</th>
                            <th>Номер телефону</th>
                            <th>Рпис</th>
                            <th>Ціна</th>
                            <th>Площа</th>
                            <th>Кількість кімнат</th>
                            <th>Ваш вибір</th>
                            <th>Дія</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="sale" items="${req.selectedSale}">
                            <tr>
                                <td>${sale.id}</td>
                                <td>${sale.user.username}</td>
                                <td>${sale.user.phoneNumber}</td>
                                <td>${sale.description}</td>
                                <td>${sale.price}</td>
                                <td>${sale.area}</td>
                                <td>${sale.numberOfRooms}</td>
                                <c:choose>
                                    <c:when test="${sale.equals(req.saleChosenByUser)}">
                                        <td>
                                            Так
                                        </td>
                                        <td>
                                            <form action="${pageContext.request.contextPath}/requestToBuy/${req.id}/unselect" method="post">
                                                <input type="submit" value="Відхилити пропозицію" class="btn btn-danger" />
                                            </form>
                                        </td>

                                    </c:when>
                                    <c:otherwise>
                                        <td>
                                            Ні
                                        </td>
                                        <td>
                                            <form action="${pageContext.request.contextPath}/requestToBuy/${req.id}/chooseSale" method="post">
                                                <input type="hidden" name="saleId" value="${sale.id}" />
                                                <input type="submit" value="підтвердити" class="btn btn-primary" />
                                            </form>
                                        </td>
                                    </c:otherwise>
                                </c:choose>

                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </c:if>
    <c:if test="${isMy and !req.status.equals('SOLD')}">
        <div class="row justify-content-center">
            <div class="col col-lg-5 card text-center">
                <div class="card-header">Змінити статус</div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/requestToBuy/${req.id}/changeStatus" method="post">
                        <div class="form-group row">
                            <div class="col">
                                <div class="form-floating">
                                    <select name="status" id="status" class="form-control" required>
                                        <option value="HIDDEN"
                                                <c:if test="${req.status == 'HIDDEN'}"> selected </c:if>
                                        >Приховано</option>
                                        <option value="OK"
                                                <c:if test="${req.status == 'OK'}"> selected </c:if>
                                        >Відкрито</option>
                                    </select>
                                    <label for="status" class="form-label">Статус</label>
                                </div>
                                <div class="text-center">
                                    <input type="submit" value="змінити" class="btn btn-primary"/>
                                </div>
                            </div>
                            <div class="col">
                                <div class="text-center input-group mb-2">
                                    <p class="alert alert-warning">Попередження! Приховуючи, інші не зможуть басити посилання</p>
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
                Пропозиція купівлі
            </div>
            <div class="card-body">
                <h5 class="card-title">Статус - ${req.status}</h5>
                <h5 class="card-title">Id - ${req.id}</h5>
                <h5 class="card-title">Ім'я користувача - ${req.user.username}</h5>
                <h5 class="card-title">Номер телефону - ${req.user.phoneNumber}</h5>
                <h5 class="card-title">Тип - ${req.type}</h5>
                <h5 class="card-title">Площа -
                    <c:choose>
                        <c:when test="${req.minArea != null or req.maxArea != null}">
                            <c:if test="${req.minArea != null}">
                                мін ${req.minArea} кв. м&nbsp;
                            </c:if>
                            <c:if test="${req.maxArea != null}">
                                макс ${req.maxArea} кв. м&nbsp;
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            не має значення
                        </c:otherwise>
                    </c:choose>

                </h5>
                <h5 class="card-title">Райони -
                    <c:if test="${empty req.district}">
                        не має значення
                    </c:if>
                    <c:forEach var="districtItem" items="${req.district}">
                        <p>${districtItem.name}</p>
                    </c:forEach>
                </h5>
                <h5 class="card-title">
                    Кількість кімнат -
                    <c:choose>
                        <c:when test="${not req.numberOfRoomsNoMatter and req.numberOfRooms != 0}">
                            ${req.numberOfRooms}
                        </c:when>
                        <c:otherwise>
                            не важливо
                        </c:otherwise>
                    </c:choose>
                </h5>
                <h5 class="card-title">
                    Ціна -
                    <c:choose>
                        <c:when test="${req.minPrice != null or req.maxPrice != null}">
                            <c:if test="${req.minPrice != null}">
                                мін $ ${req.minPrice}&nbsp;
                            </c:if>
                            <c:if test="${req.maxPrice != null}">
                                макс $ ${req.maxArea}
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            не важливо
                        </c:otherwise>
                    </c:choose>
                </h5>

                <h5 class="card-title">
                    <% RequestToBuy requestToBuy = (RequestToBuy) request.getSession().getAttribute("reg");%>
                    Дата - <%= sdf.format(requestToBuy.getDate()) %>
                </h5>
                <h5 class="card-title">Опис</h5>
                <p>
                    <%= requestToBuy.getDescription() %>
                </p>
                <h5 class="card-title">Повний опис</h5>
                <p>
                    <%= requestToBuy.getFullDescription() %>
                </p>
            </div>
        </div>
        <c:if test="${isMy and !req.status.equals('SOLD')}">
            <div class="col card text-center">
                <div class="card-header">Зміна значеннь</div>
                <div class="card-body">
                    <form id="form_change" action="${pageContext.request.contextPath}/requestToBuy/${req.id}/changeValues" method="post">
                        <input type="hidden" name="isMy" value="true">
                        <div class="input-group mb-2">
                            <label for="district_p" >Район</label>
                            <select multiple id="district_p" name="district" class="form-control">
                                <option value="">Не має значення</option>
                                <c:if test="${not empty districts}">
                                    <c:forEach var="districtVar" items="${districts}">
                                        <c:if test="${not districtVar.hidden}">
                                            <option value="${districtVar.id}"
                                                    <c:if test="${req.district != null and req.district.contains(districtVar)}">
                                                        selected
                                                    </c:if>
                                            >${districtVar.name}</option>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                            </select>

                        </div>

                        <div class="form-group row mb-2">
                            <div class="col col-xs-2 form-floating">
                                <input id="min_price" type="number" name="minPrice" min="1" max="99999999"
                                       class="form-control" placeholder="price"
                                       value="${req.minPrice}"
                                />
                                <label for="min_price" class="form-label">Мін ціна, $</label>
                            </div>
                            <div class="col col-xs-2 form-floating">
                                <input id="max_price" name="maxPrice" type="number" step="0.1" min="1" max="999999"
                                       class="form-control" placeholder="area"
                                       value="${req.maxPrice}"
                                />
                                <label for="max_price" class="form-label">Иакс ціна, $</label>
                            </div>
                        </div>
                        <div class="form-group row mb-2">
                            <div class="col col-xs-2 form-floating">
                                <input id="min_area" type="number" name="minArea" min="1" max="99999999"
                                       class="form-control" placeholder="area"
                                       value="${req.minArea}"
                                />
                                <label for="min_area" class="form-label">Мін площа, кв. м</label>
                            </div>
                            <div class="col col-xs-2 form-floating">
                                <input id="max_area" name="maxArea" type="number" step="0.1" min="1" max="999999"
                                       class="form-control" placeholder="area"
                                       value="${req.maxArea}"
                                />
                                <label for="max_area" class="form-label">Макс площа, кв. м</label>
                            </div>
                        </div>
                        <div class="form-group row mb-2">
                            <div class="col col-xs-2 form-floating">
                                <input type="number" aria-label="number of" name="numberOfRooms" id="numberOfRooms_p" min="1" max="999" class="form-control" placeholder="2"
                                       value="${req.numberOfRooms}"/>
                                <label for="numberOfRooms_p">Кількість кімнат</label>
                            </div>
                            <div class="col col-xs-2 form-floating">
                                <select name="type" id="type_p" class="form-control" required>
                                    <option value="Apartment"
                                            <c:if test="${req.type == 'Apartment'}"> selected </c:if>
                                    >Квартира</option>
                                    <option value="House"
                                            <c:if test="${req.type == 'House'}"> selected </c:if>
                                    >Дім</option>
                                </select>
                                <label for="type_p" class="form-label">Тип</label>
                            </div>
                        </div>

                        <div class="form-floating mb-2">
                            <textarea class="form-control" rows="3" name="description" id="description_p"
                                      required >${req.description}</textarea>
                            <label for="description_p" class="form-label">Короткий опис</label>
                        </div>
                        <div class="form-floating mb-2">
                            <textarea class="form-control" rows="10" name="fullDescription" id="full_description_p"
                                      required>${req.fullDescription}</textarea>
                            <label for="full_description_p" class="form-label">Повний опис</label>
                        </div>
                        <div class="text-center">
                            <input type="submit" value="change" class="btn btn-success btn-lg"/>
                        </div>
                    </form>
                </div>
            </div>
        </c:if>
    </div>

</div>
</body>
</html>
