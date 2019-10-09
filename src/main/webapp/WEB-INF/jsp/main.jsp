<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>--%>
<html>
<head>
    <link href="/css/style.css" rel="stylesheet">
    <title><tiles:getAsString name="title"/></title>
</head>
<body>
<table class="page">
    <tr>
        <td><tiles:insert attribute="header"/></td>
    </tr>
    <tr>
        <td><tiles:insert attribute="body"/></td>
    </tr>
    <tr>
        <td><tiles:insert attribute="footer"/></td>
    </tr>
</table>
</body>
</html>