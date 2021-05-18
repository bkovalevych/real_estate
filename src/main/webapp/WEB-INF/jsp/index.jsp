<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>
<head>
    <title>Main</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>

<div class="container">
    <c:if test="${empty user}">
        <c:choose>
            <c:when test="${prefix == '/admin'}">
                <h3 class="text-center">
                    Зайти (Адмін)
                </h3>
            </c:when>
            <c:otherwise>
                <h3 class="text-center">
                    Зайти (Користувач)
                </h3>
            </c:otherwise>
        </c:choose>

        <form style="width: 100%; max-width: 420px; padding: 15px; margin: auto"
              action="${pageContext.request.contextPath}${prefix}/login" method="post">
            <div class="form-label-group">
                <label for="username">Логін</label>
                <input class="form-control" type="text" name="username" placeholder="Enter username" required autofocus
                id="username"/>

            </div>
            <div class="form-group">
                <label for="password">Пароль</label>
                <input class="form-control" type="password" name="password" placeholder="Enter password"
                       required autofocus id="password"/>

            </div>
            <button class="btn btn-primary">Зайти</button>
        </form>
        <p class="text-danger">${incorrectCredentials}</p>
    </c:if>
    <c:if test="${not empty user}">
        <h4>${successRegister}</h4>
        <div class="card text-center">
            <div class="card-header">
                ${user.firstName} - ${role}
            </div>
            <div class="card-body">
                <c:choose>
                    <c:when test="${role == 'user'}">
                        <h5 class="card-title">Нікнейм - ${user.username}</h5>
                        <h5 class="card-title">Ім'я - ${user.firstName}</h5>
                        <h5 class="card-title">Прізвище - ${user.lastName}</h5>
                        <h5 class="card-title">Номер телефону - ${user.phoneNumber}</h5>

                    </c:when>
                    <c:otherwise>
                        <h5>Сторінка адміністратора</h5>
                    </c:otherwise>
                </c:choose>

            </div>
        </div>
    </c:if>
</div>
</body>
</html>
