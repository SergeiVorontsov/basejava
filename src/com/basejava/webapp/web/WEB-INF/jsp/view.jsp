<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.basejava.webapp.util.HtmlUtil" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
    <jsp:useBean id="resume" scope="request" type="com.basejava.webapp.model.Resume"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<jsp:include page="header.jsp"/>
<body>
<div class="container" style="margin-top: 130px">
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header">
                    <div class="d-flex align-items-center highlight-toolbar ps-2 pe-2 py-1">
                        <h2 class="">${resume.fullName} <a href="resume?uuid=${resume.uuid}&action=edit"></a></h2>
                        <div class="d-flex ms-auto">
                            <button type="button" class="btn btn btn-outline-success"
                                    onclick="location.href='resume?uuid=${resume.uuid}&action=edit'">
                                <i class="bi bi-pencil-square"></i>
                            </button>
                        </div>
                    </div>
                </div>
                <div class="card-body">
                    <div class="list-group">
                        <div class="d-flex w-100 justify-content-between mt-3">
                            <h5 class="m-2">Контакты</h5>
                        </div>
                        <c:forEach var="contactEntry" items="${resume.contacts}">
                            <jsp:useBean id="contactEntry"
                                         type="java.util.Map.Entry<com.basejava.webapp.model.ContactType, java.lang.String>"/>
                            <p class="m-2"><%= HtmlUtil.formatContactView(contactEntry.getKey(), contactEntry.getValue()) %>
                            </p>
                        </c:forEach>
                    </div>
                    <c:forEach var="sectionEntry" items="${resume.sections}">
                        <jsp:useBean id="sectionEntry"
                                     type="java.util.Map.Entry<com.basejava.webapp.model.SectionType, com.basejava.webapp.model.Section>"/>
                        <div class="list-group">
                            <div class="d-flex w-100 justify-content-between mt-3">
                                <h5 class="m-2">${sectionEntry.key.title}</h5>
                            </div>
                            <p class="m-2">${sectionEntry.value}</p>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>