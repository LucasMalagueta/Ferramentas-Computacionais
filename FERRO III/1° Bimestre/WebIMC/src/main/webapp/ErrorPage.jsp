<%@page isErrorPage="true"%>

<%--
  Created by IntelliJ IDEA.
  User: Aluno
  Date: 13/08/2025
  Time: 20:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Página de Erros</title>
</head>
<body style="background: red; color: white">
    <p>Erro Inesperado. Contacte o desenvolvedor</p>
    <p>
        <%
            out.print(exception.getMessage());
        %>
    </p>
</body>
</html>
