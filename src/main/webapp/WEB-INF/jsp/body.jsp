<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<html:html>
    <head>

    </head>
    <body>
    <div class="rq">
        <html:textarea property="request"/>
        <html:submit onclick="">SEND</html:submit>
    </div>
    <div class="rq">
        <html:textarea property="vars"/>
    </div>
    <div class="rs">
        <html:textarea property="response"/>
    </div>
    </body>
</html:html>
