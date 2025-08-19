<%@ page import="java.util.Random" %>
<%@ page import="java.util.RandomAccess" %>
<%@ page import="java.io.RandomAccessFile" %>
<%@ page import="com.example.webimc.IMC" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page errorPage="ErrorPage.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="Style.css">
    <title>IMC Web Calculator</title>
</head>
<body>
    <%
        int peso;
        double altura;
        try {
            peso = Integer.parseInt(request.getParameter("peso"));
            altura = Double.parseDouble(request.getParameter("altura"))/100;
        }
        catch (Exception e){
            //out.print("<p>Dados não informados. Ultilizamos valores defaukt para o cálculo</p>");
            peso = 70;
            altura= 1.7;
            //response.sendRedirect("index.html");
        }

    %>
    <%!
        double imc;
        void calcIMC(double altura, double peso){
            imc = peso/Math.pow(altura,2);
        }

    %>
    <h3>IMC Calculator</h3>

    <div>
        <form action="">
            <label for="fpeso">Seu Peso</label>
            <input type="number" id="fpeso" name="peso" value="<%=peso%>">

            <label for="laltura">Sua Altura</label>
            <input type="text" id="laltura" name="altura" value="<%=altura*100%>">
            <input type="submit" value="Calcular IMC">
        </form>
    </div>

    <br/>

    <div>
        <p style="background: orange; font-size: 48px">Seu IMC: <%=String.format("%.2f",imc)%></p>
        <p><%=IMC.getCondicaoFisica(imc)%></p>
    </div>
</body>
</html>