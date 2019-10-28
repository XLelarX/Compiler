<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/compiler">
    <head>
        <link href="https://fonts.googleapis.com/css?family=Ubuntu+Mono&display=swap" rel="stylesheet">
        <link href="<c:url value="/css/style.css"/>" rel="stylesheet">
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
            <%--        <script src="dist/set-number.min.js"></script>--%>
        <script type="text/javascript" src="/js/script.js"></script>
        <script src="http://codemirror.net/lib/codemirror.js"></script>
        <script src="http://codemirror.net/mode/javascript/javascript.js"></script>
        <link href="https://fonts.googleapis.com/css?family=Staatliches&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="<c:url value="/css/codemirror.css"/>">
    </head>
    <div id="container">
        <div id="menu">
            <div id="logo">ONLINE COMPILER</div>
            <div class="center">

                <html:submit styleClass="button" value=" "/>

            </div>
            <div align="center" id="cr">©2019 Lelar</div>

        </div>
        <div class="code">
            <html:textarea style="height: 100% !important" styleId="code" property="request"/>
        </div>
        <div id="bottom">
            <div id="vars">
                <html:textarea styleId="varsInner" property="vars"/>
<%--                <div id="resize"></div>--%>
            </div>

            <div id="out"><html:textarea style="border-left:none" disabled="true" property="response"/></div>
        </div>
    </div>

    <script>
        var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
            lineNumbers: true,
            lineWrapping: true,
            autofocus: true
        });

        editor.setCursor(2, 2);     // это значит поместить курсор на 3 строку (отсчёт от 0), символ 3

        var pos = editor.posFromIndex(3);  //получить координаты 3-ей позиции (строку и символ)
        editor.setCursor(pos.line, pos.ch);
    </script>
</html:form>