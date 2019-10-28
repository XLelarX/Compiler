<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Ubuntu+Mono&display=swap" rel="stylesheet">
    <link href="<c:url value="/css/style.css"/>" rel="stylesheet">
    <title><tiles:getAsString name="title"/></title>
</head>
<body>
<div id="container">
    <div id="menu">
        <tiles:insert attribute="header"/>
    </div>

    <div id="code">
        <tiles:insert attribute="body"/>
    </div>
    <div id="bottom">
        <div id="vars">s</div>
        <div id="out">s</div>
    </div>
</div>
</body>
</html>