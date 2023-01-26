<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
    <jsp:useBean id="resume" scope="request" type="com.basejava.webapp.model.Resume"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<div class="container" style="margin-top: 30px">
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
            <c:if test="<%=!resume.getContacts().values().isEmpty() || !resume.getSections().values().isEmpty()%>">
                <div class="card-body">
                    <c:import url="contacts.jsp"/>
                    <c:import url="sections.jsp"/>
                </div>
            </c:if>
        </div>
    </div>
</div>
</div>
</body>
</html>