<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet">
    <title><tiles:getAsString name="title"/></title>
</head>
<body style="overflow: hidden">
<table class="page">
    <tr class="mainTr">
        <td class="mainHeader"><tiles:insert attribute="header"/></td>
    </tr>
    <tr>
        <td align="center"><tiles:insert attribute="body"/></td>
    </tr>
    <tr class="mainTr">
        <td class="mainFooter"><tiles:insert attribute="footer"/></td>
    </tr>
</table>
</body>
</html>