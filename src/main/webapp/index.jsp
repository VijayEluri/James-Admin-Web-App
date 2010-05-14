<!--
< %@ page session="false"%>
< %
response.sendRedirect("helloWorld.jsf");
%>

-->

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%--
    This file is an entry point for JavaServer Faces application.
--%>

    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>JSP Page</title>
        </head>
        <body>
            <h1>JavaServer Faces</h1>
            <br />
    <a href="loginForm.faces">Login</a> faces<br />
    <a href="loginForm.jsp">Login</a> jsp<br />
    <a href="loginForm.jsf">Login</a> jsf<br />
    <p>Try this one:</p>
    <a href="jsp/loginForm.faces">Login</a><br />
        </body>
    </html>

    </body>
</html>
