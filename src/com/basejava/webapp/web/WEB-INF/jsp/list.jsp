<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.basejava.webapp.model.ContactType" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
    <title>Список всех резюме</title>
</head>
<jsp:include page="header.jsp"/>
<body>
<div class="container" style="margin-top: 130px">
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header">
                    <H2 class="m-2">База данных резюме</H2>
                </div>
                    <div class="card-body">

                    <table class="table m-2">
                        <thead>
                        <tr>
                            <th>Имя</th>
                            <th>Еmail</th>
                            <th>Телефон</th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <jsp:useBean id="resumes" scope="request" type="java.util.List"/>
                        <c:forEach items="${resumes}" var="resume">
                            <tr>
                                <td ><a class="mt-3" href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                                <td>${resume.getContacts().get(ContactType.EMAIL)}</td>
                                <td>${resume.getContacts().get(ContactType.PHONE)}</td>
                                <td>
                                    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                        <button type="button" class="btn btn-outline-danger"
                                                onclick="location.href='resume?uuid=${resume.uuid}&action=delete'">
                                            <i class="bi bi-trash3-fill"></i>
                                        </button>
                                        <button type="button" class="btn btn btn-outline-success"
                                                onclick="location.href='resume?uuid=${resume.uuid}&action=edit'">
                                            <i class="bi bi-pencil-square"></i>
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</body>
<jsp:include page="footer.jsp"/>
</html>

