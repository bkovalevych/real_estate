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
    <title>Requests to buy</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<c:choose>
    <c:when test="${isMy}">
        <h3 class="text-center alert alert-success">Мої пропозиції купівлі</h3>
    </c:when>
    <c:otherwise>
        <h3 class="text-center alert alert-success">Всі пропозції купівлі</h3>
    </c:otherwise>
</c:choose>


<div class="container">
    <div class="text-center mb-2">
        <c:if test="${msgAdded != null}">
            <p class="alert alert-success">${msgAdded}</p>
        </c:if>
        <c:if test="${msgNotAdded}">
            <p class="alert alert-danger">${msgNotAdded}</p>
        </c:if>
    </div>
</div>
<%--Filter --%>
<ul class="nav nav-tabs justify-content-around">
    <li class="nav-item">
        <button class="text-center btn btn-secondary" type="button" data-bs-toggle="collapse"
                data-bs-target="#form_filter" aria-expanded="false"
                aria-controls="form_filter"
                id="btn_filter">
            Фільтрувати
        </button>
    </li>

    <c:if test="${isMy}">
        <li class="nav-item">
            <button class="text-center btn btn-secondary" type="button" data-bs-toggle="collapse"
                    data-bs-target="#form_add" aria-expanded="false"
                    aria-controls="form_add"
                    id="btn_add">
                Додати
            </button>
        </li>
    </c:if>

</ul>

<div class="container w-50" id="main_forms">
    <form id="form_filter" class="collapse"  aria-labelledby="btn_filter"
          data-bs-parent="#main_forms"
          action="${pageContext.request.contextPath}/requestToBuy/" method="get">
        <div>
            <input type="hidden" name="isMy" value="${isMy}">
            <label for="district">Район</label>
            <select id="district" name="district">
                <option value="">Не обрано</option>
                <c:if test="${not empty districts}">
                    <c:forEach var="districtVar" items="${districts}">
                        <c:if test="${not districtVar.hidden}">
                            <option
                                    <c:if test="${district.id == districtVar.id}">
                                        selected
                                    </c:if>
                                    value="${districtVar.id}"
                            >${districtVar.name}</option>
                        </c:if>
                    </c:forEach>
                </c:if>
            </select>
        </div>
        <div>
            <label for="price">Ціна, $</label>
            <input id="price" type="number" name="price" min="1" max="99999999"
                   value="${price}"
            />
        </div>
        <div>
            <label for="area">Площа</label>
            <input id="area" name="area" type="number" step="0.1" min="1" max="999999"
                   value="${area}"/>
        </div>
        <div>
            <label for="numberOfRooms">Кількість кімнат</label>
            <input type="number" name="numberOfRooms" id="numberOfRooms" min="1" max="999"
                   value="${numberOfRooms}"
            />
        </div>
        <div>
            <label for="type">Тип</label>
            <select name="type" id="type">
                <option value="">Не обрано</option>
                <option value="Apartment"
                        <c:if test="${ type eq 'Apartment'}">
                            selected
                        </c:if>
                >Квартира</option>
                <option value="House"
                        <c:if test="${ type eq 'House'}">
                            selected
                        </c:if>
                >Будинок</option>
            </select>
        </div>
        <div>
            <input type="submit" value="пошук"/>
        </div>
    </form>
    <c:if test="${isMy}">
        <form id="form_add" class="collapse" aria-labelledby="btn_add" data-bs-parent="#main_forms"
              action="${pageContext.request.contextPath}/requestToBuy/" method="post">
            <input type="hidden" name="isMy" value="true">
            <div class="input-group mb-2">
                <label class="form-label" for="selected_districts">Район</label>
                <select class="form-control" multiple name="selectedDistricts" id="selected_districts">
                    <c:if test="${not empty districts}">
                        <c:forEach var="districtVar" items="${districts}">
                            <c:if test="${not districtVar.hidden}">
                                <option value="${districtVar.id}"
                                >${districtVar.name}</option>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </select>
            </div>
            <div class="form-group row">
                <div class="col col-xs-2 form-floating">
                    <input id="minPrice" type="number" name="minPrice" min="1" max="99999999"
                           class="form-control" placeholder="min price"/>
                    <label for="minPrice" class="form-label">Мін ціна, $</label>
                </div>
                <div class="col col-xs-2 form-floating">
                    <input id="maxPrice" type="number" name="maxPrice" min="1" max="99999999"
                           class="form-control"
                           placeholder="max price"
                    />
                    <label for="maxPrice" class="form-label">Макс ціна, $</label>
                </div>
            </div>
            <div class="form-group row mb-2">
                <div class="col col-xs-2 form-floating">
                    <input id="minArea" name="minArea" type="number" step="0.1" min="1" max="999999"
                           class="form-control" placeholder="min area"
                    />
                    <label for="minArea" class="form-label">Мін площа, кв. м</label>
                </div>
                <div class="col col-xs-2 form-floating">
                    <input id="maxArea" name="maxArea" type="number" step="0.1" min="1" max="999999"
                           class="form-control"
                           placeholder="max area"
                    />
                    <label for="maxArea" class="form-label">Макс площа, кв. м</label>
                </div>
            </div>
            <div class="form-group row mb-2">
                <div class="col col-xs-2 form-floating">
                    <input type="number" name="numberOfRooms" id="numberOfRooms_add" min="1" max="999"
                           class="form-control"
                           placeholder="number of rooms"
                    />
                    <label for="numberOfRooms_add" class="form-label">Кількість кімнат</label>
                </div>
                <div class="col col-xs-2 form-floating">
                    <select class="form-control" name="type" placeholder="smth" id="type_add" required>
                        <option value="Apartment">Квартира</option>
                        <option value="House">Будинок</option>
                    </select>
                    <label for="type_add" class="form-label">Тип</label>
                </div>
            </div>
            <div class="form-floating mb-2">
                <textarea class="form-control" rows="3" name="description" id="description_p" required ></textarea>
                <label for="description_p" class="form-label">Короткий опис</label>
            </div>
            <div class="form-floating mb-2">
                <textarea class="form-control" rows="10" name="fullDescription" id="full_description_p"
                          required></textarea>
                <label for="full_description_p" class="form-label">Повний опис</label>
            </div>
            <div class="text-center">
                <input type="submit" value="add" class="btn btn-success btn-lg"/>
            </div>
        </form>
    </c:if>
</div>

<div class="container">
    <table class="table table-striped mb-3 text-center">
        <thead class="thead-light">
        <tr>
            <th>
                Id
            </th>

            <c:if test="${not isMy}">
                <th>
                    Ім'я користувача
                </th>
            </c:if>
            <th>
                Тип
            </th>
            <th>
                Площа
            </th>
            <th>
                Райони
            </th>
            <th>
                Кількість кімнат
            </th>
            <th>
                Ціна
            </th>
            <th>Дата</th>
            <th>
                Опис
            </th>
            <c:if test="${isMy}">
                <th>
                    Статус
                </th>
            </c:if>
            <th>Даталі</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="request" items="${requests}">
            <tr>
                <td>${request.id}</td>
                <c:if test="${not isMy}">
                    <td>${request.user.username}</td>
                </c:if>
                <td>${request.type}</td>
                <td><%
                    RequestToBuy req = (RequestToBuy) pageContext.getAttribute("request");
                    if (req.isAreaNoMatter() || req.getMinArea() == null && req.getMaxArea() == null) {
                        out.print("ну важливо");
                    } else {
                        if (req.getMinArea() != null) {
                            out.print("мін " + req.getMinArea() + " кв. м ");
                        }
                        if (req.getMaxArea() != null) {
                            out.print("макс " + req.getMaxArea() + " кв м.");
                        }
                    }
                %></td>
                <td>
                    <c:if test="${empty request.district}">
                        не важливо
                    </c:if>
                    <c:forEach var="districtItem" items="${request.district}">
                        <p>${districtItem.name}</p>
                    </c:forEach>
                </td>
                <td><%
                    if (req.isNumberOfRoomsNoMatter() || req.getNumberOfRooms() == 0) {
                        out.print("не важливо");
                    } else {
                        out.print(req.getNumberOfRooms());
                    }
                %></td>
                <td><%
                    if (req.isPriceNoMatter() || req.getMaxPrice() == null && req.getMinPrice() == null) {
                        out.print("не важливо");
                    } else {
                        if (req.getMinPrice() != null) {
                            out.print("мін $" + req.getMinPrice() + " ");
                        }
                        if (req.getMaxPrice() != null) {
                            out.print("макс $" + req.getMaxPrice() + " ");
                        }
                    }
                %></td>
                <td> <%= sdf.format(req.getDate()) %></td>
                <td>
                    <%= req.getDescription().substring(0, Math.min(30, req.getDescription().length())) %>
                </td>
                <c:if test="${isMy}">
                    <td>
                            ${request.status}
                    </td>
                </c:if>
                <td>
                    <c:choose>
                        <c:when test="${isMy}">
                            <a href="${pageContext.request.contextPath}/requestToBuy/${request.id}?isMy=true"> Info</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/requestToBuy/${request.id}"> Info</a>
                        </c:otherwise>
                    </c:choose>

                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%--My requests--%>
<%--content all requests --%>
</body>
</html>
