<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html:html>
    <head>
    </head>
    <body>
    <table class="bodyTable">
        <tr>
            <td colspan="2" align="center">
                <html:textarea styleClass="code" property="request"/>
            </td>
        <tr height="7%">
            <td colspan="2" align="center">
                <html:submit styleClass="button">SEND</html:submit>
            </td>
        </tr>
        <tr>
            <td align="right" width="25%">
                <html:textarea styleClass="vars" property="vars"/>
            </td>
            <td align="left" width="75%">
                <html:textarea styleClass="response" disabled="true" property="response"/>
            </td>
        </tr>
    </table>
    </body>
</html:html>
