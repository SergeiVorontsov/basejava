<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.basejava.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="resume" scope="request" type="com.basejava.webapp.model.Resume"/>
<c:forEach var="sectionEntry" items="${resume.sections}">
    <jsp:useBean id="sectionEntry"
                 type="java.util.Map.Entry<com.basejava.webapp.model.SectionType, com.basejava.webapp.model.Section>"/>
    <div class="list-group">
        <div class="d-flex w-100 justify-content-between mt-3">
            <h5 class="m-2">${sectionEntry.key.title}</h5>
        </div>
        <c:set var="string" value="${sectionEntry.value}"/>
        <c:set var="person" value="<%=SectionType.PERSONAL%>"/>
        <c:set var="qulific" value="<%=SectionType.QUALIFICATIONS%>"/>
        <c:set var="objectiv" value="<%=SectionType.OBJECTIVE%>"/>
        <c:set var="achiev" value="<%=SectionType.ACHIEVEMENT%>"/>
        <c:set var="expeience" value="<%=SectionType.EXPERIENCE%>"/>
        <c:set var="educat" value="<%=SectionType.EDUCATION%>"/>
        <c:choose>
            <c:when test="${sectionEntry.key eq achiev}">
                <ul>
                    <c:forTokens items="${string}" delims='<%="\n"%>' var="token" begin="0"
                                 varStatus="tokenStatus" step="1">
                    <li class="m-2"><c:out value="${token}"/>
                        </c:forTokens>
                    </li>
                </ul>
            </c:when>
            <c:when test="${sectionEntry.key eq qulific}">
                <ul>
                    <c:forTokens items="${string}" delims='<%="\n"%>' var="token" begin="0"
                                 varStatus="tokenStatus"
                                 step="1">
                    <li class="m-2"><c:out value="${token}"/>
                        </c:forTokens>
                    </li>
                </ul>
            </c:when>
            <c:otherwise>
                <c:forTokens items="${string}" delims='<%="\n"%>' var="token" begin="0"
                             varStatus="tokenStatus"
                             step="1">

                    <p class="m-2"><c:out value="${token}"/></p>
                </c:forTokens>
            </c:otherwise>
        </c:choose>
    </div>
</c:forEach>