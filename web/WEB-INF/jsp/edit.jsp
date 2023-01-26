<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.model.SectionType" %>
<%@ page import="com.basejava.webapp.model.CompanySection" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <jsp:useBean id="resume" scope="request" type="com.basejava.webapp.model.Resume"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<div class="container" style="margin-top: 30px">
    <div class="row">
        <div class="col-md-12">
            <div class="card">
                <div class="card-header"><h2>${resume.fullName}</h2></div>
                <div class="card-body">
                    <form class="form-floating mb-3 " method="post" action="resume"
                          enctype="application/x-www-form-urlencoded">
                        <input type="hidden" name="uuid" value="${resume.uuid}">
                        <div class="row mb-3">
                            <label class="col-sm-2 col-form-label">Имя</label>
                            <div class="col-sm-10">
                                <input type="text" pattern="([А-Яа-яЁёA-Za-z]{1,15}\s*){1,5}" required
                                       class="form-control" name="fullName"
                                       value="<c:out value="${resume.fullName}" />">
                            </div>
                        </div>
                        <div class="list-group">
                            <div class="d-flex w-100 justify-content-between">
                                <h5 class="mb-1">Контакты</h5>
                            </div>
                            <c:forEach var="type" items="<%=ContactType.values()%>">
                                <div class="row mb-3">
                                    <label class="col-sm-3 col-form-label">${type.title}</label>
                                    <div class="col-sm-6">
                                        <input type="text" class="form-control" name="${type.name()}"
                                               value="${resume.contacts.get(type)}">
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <c:forEach var="type" items="<%=SectionType.values()%>">
                            <c:set var="section" value="${resume.sections.get(type)}"/>
                            <jsp:useBean id="section" type="com.basejava.webapp.model.Section"/>
                            <c:set var="person" value="<%=SectionType.PERSONAL%>"/>
                            <c:set var="qulific" value="<%=SectionType.QUALIFICATIONS%>"/>
                            <c:set var="objectiv" value="<%=SectionType.OBJECTIVE%>"/>
                            <c:set var="achiev" value="<%=SectionType.ACHIEVEMENT%>"/>
                            <c:set var="expeience" value="<%=SectionType.EXPERIENCE%>"/>
                            <c:set var="educat" value="<%=SectionType.EDUCATION%>"/>
                            <h5 class="mb-3">${type.title}</h5>
                            <div class="row">
                                <c:choose>
                                    <c:when test="${type==person || type==objectiv}">
                                        <div class="col-sm-6 mb-3">
                                            <input type="text" class="form-control" name="${type.name()}"
                                                   value="${resume.sections.get(type)}">
                                        </div>
                                    </c:when>
                                    <c:when test="${type==achiev || type==qulific}">
                                        <div class="col-sm-6 mb-3">
                                                <textarea type="text" rows="3" class="form-control"
                                                          name="${type.name()}">${resume.sections.get(type)}</textarea>
                                        </div>
                                    </c:when>
                                    <c:when test="${type==expeience || type==educat}">
                                        <c:forEach var="company"
                                                   items="<%=((CompanySection) section).getCompanies()%>"
                                                   varStatus="counter">
                                            <jsp:useBean id="company" type="com.basejava.webapp.model.Company"/>
                                            <div class="mb-5">
                                                <div class="list-group">
                                                    <div class="w-100 justify-content-between">
                                                        <div class="row mb-3">
                                                            <label class="col-sm-3 col-form-label b">Наименование
                                                                организации</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control"
                                                                       name="${type}${counter.index}name"
                                                                       value="${company.title}">
                                                            </div>
                                                        </div>
                                                        <div class="row mb-3">
                                                            <label class="col-sm-3 col-form-label">Веб-сайт
                                                                организации</label>
                                                            <div class="col-sm-6">
                                                                <input type="text" class="form-control"
                                                                       name="${type}${counter.index}website"
                                                                       value="${company.website}">
                                                            </div>
                                                        </div>
                                                        <c:forEach var="period"
                                                                   items="<%=company.getPeriods()%>">
                                                            <jsp:useBean id="period"
                                                                         type="com.basejava.webapp.model.Company.Period"/>
                                                            <div class="list-group">
                                                                <div class="">
                                                                    <div class="row mb-3">
                                                                        <label class="col-sm-3 col-form-label">Дата
                                                                            начала</label>
                                                                        <div class="col-sm-3">
                                                                            <input type="text" class="form-control"
                                                                                   name="${type}${counter.index}startDate"
                                                                                   value="${period.startDate.format(DateTimeFormatter.ofPattern("MM/yyyy"))}"
                                                                                   pattern="(0[1-9]|1[012])[/](19|20)\d\d"
                                                                                   placeholder="ММ/ГГГГ">
                                                                        </div>
                                                                    </div>
                                                                    <div class="row mb-3">
                                                                        <label class="col-sm-3 col-form-label">Дата
                                                                            окончания</label>
                                                                        <div class="col-sm-3">
                                                                            <input type="text"
                                                                                   class="form-control"
                                                                                   name="${type}${counter.index}endDate"
                                                                                   value="${period.endDate.format(DateTimeFormatter.ofPattern("MM/yyyy"))}"
                                                                                   pattern="(0[1-9]|1[012])[/](19|20)\d\d"
                                                                                   placeholder="ММ/ГГГГ">
                                                                        </div>
                                                                    </div>
                                                                    <div class="row mb-3">
                                                                        <label class="col-sm-3 col-form-label">Название
                                                                            позиции</label>
                                                                        <div class="col-sm-6">
                                                                            <input type="text"
                                                                                   class="form-control"
                                                                                   name="${type}${counter.index}title"
                                                                                   value="${period.title}">
                                                                        </div>
                                                                    </div>
                                                                    <div class="row mb-3">
                                                                        <label class="col-sm-3 col-form-label">Описание
                                                                            позиции</label>
                                                                        <div class="col-sm-6">
                                                                            <input type="text"
                                                                                   class="form-control"
                                                                                   name="${type}${counter.index}description"
                                                                                   value="${period.description}">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:forEach>
                                    </c:when>
                                    <c:otherwise>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:forEach>
                        <button type="submit" class="btn btn-primary">Сохранить</button>
                        <button type="reset" onclick="window.history.back()" class="btn btn-primary">Отменить</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>