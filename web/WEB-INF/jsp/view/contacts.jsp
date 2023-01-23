<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.basejava.webapp.util.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${resume.contacts.values().size() ne 0}">
<div class="list-group">
    <div class="d-flex w-100 justify-content-between mt-3">
        <h5 class="m-2">Контакты</h5>
    </div>
    <jsp:useBean id="resume" scope="request" type="com.basejava.webapp.model.Resume"/>
    <c:forEach var="contactEntry" items="${resume.contacts}">
        <jsp:useBean id="contactEntry"
                     type="java.util.Map.Entry<com.basejava.webapp.model.ContactType, java.lang.String>"/>
        <p class="m-2"><%= HtmlUtil.formatContactView(contactEntry.getKey(), contactEntry.getValue()) %>
        </p>
    </c:forEach>
</div>
</c:if>