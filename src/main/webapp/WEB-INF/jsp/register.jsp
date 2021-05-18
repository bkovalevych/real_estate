<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/WEB-INF/jspf/taglib.jspf" %>

<html>
<head>
    <title>Register</title>
</head>
<body>
<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="container">
    <h3 class="text-center">User register</h3>
    <form style="width: 100%; max-width: 420px; padding: 15px; margin: auto"
            action="${pageContext.request.contextPath}/register" method="post">
        <div class="form-label-group">
            <label for="username">Логін</label>
            <input id="username" required class="form-control" type="text" name="username" placeholder="Enter username"/>
        </div>
        <div class="form-label-group">
            <label for="password">Пароль</label>
            <input id="password" required class="form-control" type="password" name="password" placeholder="Enter password"/>
        </div>
        <div class="form-label-group">
            <label for="firstName">Ім'я</label>
            <input id="firstName" required class="form-control" type="text" name="firstName" placeholder="Enter first name"/>
        </div>
        <div class="form-label-group">
            <label for="lastName">Прізвище</label>
            <input id="lastName" required class="form-control" type="text" name="lastName" placeholder="Enter last name"/>
        </div>
        <div class="form-label-group">
            <label for="phoneNumber">Номер телефону</label>
            <input id="phoneNumber" required class="form-control" type="text" name="phoneNumber" placeholder="Enter phone number"/>
        </div>
        <br>
        <button class="btn btn-primary">Реєстрація</button>
        <p>${successRegister}</p>
    </form>
</div>
</body>
</html>
