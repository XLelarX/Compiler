<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html:html>
    <head>
    </head>
    <body>
    <html:textarea styleClass="code" property="request"/>
    <html:textarea styleClass="vars" property="vars"/>
    <html:textarea styleClass="response" disabled="true" property="response"/>
    </body>
</html:html>
