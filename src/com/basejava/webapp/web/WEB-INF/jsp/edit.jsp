<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.basejava.webapp.model.ContactType" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <jsp:useBean id="resume" scope="request" type="com.basejava.webapp.model.Resume"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<jsp:include page="header.jsp"/>
<body>
<div class="container" style="margin-top: 30px">
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header"><h2>${resume.fullName}</h2></div>
                <div class="card-body">
                    <form class="form-floating mb-3" method="post" action="resume"
                          enctype="application/x-www-form-urlencoded">
                        <input type="hidden" name="uuid" value="${resume.uuid}">
                        <div class="row mb-3">
                            <label class="col-sm-2 col-form-label">Имя</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" name="fullName" value="${resume.fullName}">
                            </div>
                        </div>
                        <div class="list-group">
                            <div class="d-flex w-100 justify-content-between">
                                <h5 class="mb-1">Контакты</h5>
                                <small>3 days ago</small>
                            </div>
                            <c:forEach var="type" items="<%=ContactType.values()%>">
                                <div class="row mb-3">
                                    <label class="col-sm-3 col-form-label">${type.title}</label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" name="${type.name()}" value="${resume.contacts.get(type)}">
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                            <button type="submit" class="btn btn-primary">Сохранить</button>
                            <button onclick="window.history.back()" class="btn btn-primary">Отменить</button>
                    </form>


                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>