<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>

<html:form action="/compiler">
<%--    <div class="footer">--%>
        <tiles:insert definition="main"><%--page="/WEB-INF/jsp/main.jsp">--%>
            <tiles:put name="title"/>
            <tiles:put name="header" value="/WEB-INF/jsp/header.jsp"/>
            <tiles:put name="body" value="/WEB-INF/jsp/body.jsp"/>
            <tiles:put name="footer" value="/WEB-INF/jsp/footer.jsp"/>
        </tiles:insert>
<%--    </div>--%>
</html:form>