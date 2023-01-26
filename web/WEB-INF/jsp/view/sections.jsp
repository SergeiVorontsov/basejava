<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.basejava.webapp.model.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="resume" scope="request" type="com.basejava.webapp.model.Resume"/>
<c:forEach var="sectionEntry" items="${resume.sections}">
    <jsp:useBean id="sectionEntry"
                 type="java.util.Map.Entry<com.basejava.webapp.model.SectionType, com.basejava.webapp.model.Section>"/>
    <c:set var="section" value="${sectionEntry.value}"/>
    <c:set var="type" value="${sectionEntry.key}"/>
    <jsp:useBean id="section" type="com.basejava.webapp.model.Section"/>
    <c:set var="person" value="<%=SectionType.PERSONAL%>"/>
    <c:set var="objectiv" value="<%=SectionType.OBJECTIVE%>"/>
    <c:set var="qulific" value="<%=SectionType.QUALIFICATIONS%>"/>
    <c:set var="achiev" value="<%=SectionType.ACHIEVEMENT%>"/>
    <c:set var="expeience" value="<%=SectionType.EXPERIENCE%>"/>
    <c:set var="educat" value="<%=SectionType.EDUCATION%>"/>
    <div class="list-group">
        <div class="d-flex w-100 justify-content-between mt-3">
            <h5 class="m-2">${sectionEntry.key.title}</h5>
        </div>
        <c:choose>
            <c:when test="${type==person || type==objectiv}">
                <p class="m-2"><c:out value="<%= ((TextSection) section).getContent()%>"/></p>
            </c:when>
            <c:when test="${type==achiev || type==qulific}">
                <ul>
                    <c:forEach var="item" items="<%=((ListSection) section).getItems()%>">
                        <li class="m-2">${item}</li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:when test="${type==expeience || type==educat}">
                <c:forEach var="company" items="<%=((CompanySection) section).getCompanies()%>">
                    <jsp:useBean id="company" type="com.basejava.webapp.model.Company"/>
                    <div class="container">
                        <div class="row align-items-start">
                            <div class="col-3">
                            </div>
                            <div class="col-sm-9">
                                <c:if test="${company.website==null}">
                                    <h5>${company.title}</h5>
                                </c:if>
                                <c:if test="${company.website!=null}">
                                    <a href="${company.website}"><h5>${company.title}</h5></a>
                                </c:if>
                            </div>
                        </div>
                        <c:forEach var="period" items="<%=company.getPeriods()%>">
                            <jsp:useBean id="period" type="com.basejava.webapp.model.Company.Period"/>
                            <div class="row align-items-start">
                                <div class="col-3">
                                    <p>${period.startDate.format(DateTimeFormatter.ofPattern("MM/yyyy"))}
                                        - ${period.endDate.format(DateTimeFormatter.ofPattern("MM/yyyy"))}
                                    </p>
                                </div>
                                <div class="col-sm-9">
                                    <h6>${period.title}</h6>
                                    <p>${period.description}</p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:forEach>
            </c:when>
        </c:choose>
    </div>
</c:forEach>