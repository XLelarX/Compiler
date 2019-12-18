<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/compiler" acceptCharset="UTF-8">
    <head>
        <link href="https://fonts.googleapis.com/css?family=Ubuntu+Mono&display=swap" rel="stylesheet">
        <link href="<c:url value="/css/style.css"/>" rel="stylesheet">
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5/jquery.min.js"></script>
        <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>
            <%--        <script src="dist/set-number.min.js"></script>--%>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/script.js"></script>
        <script src="http://codemirror.net/lib/codemirror.js"></script>
        <script src="http://codemirror.net/mode/javascript/javascript.js"></script>
        <link href="https://fonts.googleapis.com/css?family=Staatliches&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="<c:url value="/css/codemirror.css"/>">
    </head>
    <div id="container">
        <div id="menu">
            <div id="logo">ONLINE COMPILER</div>
            <div class="center">
                <html:submit style="background-image: url('${pageContext.request.contextPath}/img/run.jpg')" styleClass="button" value=" "/>
            </div>
            <div align="center" id="cr">©2019 Lelar</div>
        </div>
        <div class="code">
            <html:textarea style="height: 100% !important" property="request" styleId="code"/>
        </div>
        <div id="bottom">
            <div id="horizontalResize"></div>
            <div id="vars">
                <html:textarea onkeydown="insertTab(this, event);" styleId="varsInner" property="vars"/>
                <div id="resize"></div>
            </div>
            <div id="out"><html:textarea style="border-top:none;border-left:none" disabled="true" property="response"/></div>
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

        function insertTab(o, e)
        {
            var kC = e.keyCode ? e.keyCode : e.charCode ? e.charCode : e.which;
            if (kC == 9 && !e.shiftKey && !e.ctrlKey && !e.altKey)
            {
                var oS = o.scrollTop;
                if (o.setSelectionRange)
                {
                    var sS = o.selectionStart;
                    var sE = o.selectionEnd;
                    o.value = o.value.substring(0, sS) + "  " + o.value.substr(sE);
                    o.setSelectionRange(sS + 2, sS + 2);
                    o.focus();
                }
                else if (o.createTextRange)
                {
                    document.selection.createRange().text = "  ";
                    e.returnValue = false;
                }
                o.scrollTop = oS;
                if (e.preventDefault)
                {
                    e.preventDefault();
                }
                return false;
            }
            return true;
        }

        function getWidthForVars() {
            return $("#vars").width();
        }
    </script>
</html:form>