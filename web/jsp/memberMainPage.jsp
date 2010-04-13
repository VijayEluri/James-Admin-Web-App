<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <base href="<%=basePath%>" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <style type="text/css" media="all">@import "html/layout1.css";</style>
        <title>JSP Page</title>
    </head>
    <body>

    
    
    <f:view>
    <jsp:include page="/WEB-INF/html/header.html" />
    <h1>Member Main Page</h1>

    <c:if test="${userBean.loggedIn != 1}">
        <p>You are not logged in. Please go to the 
        <a href="jsp/loginForm.faces">login page</a>.</p>
    </c:if>

    <c:if test="${userBean.loggedIn == 1}">

        <p>It's sooo nice to see that <h:outputText id="mainPage1" 
        value="#{userBean.sessionUserName}" />
        decided to come to church this week.</p>

        <ul>
             <li>User Number: <h:outputText id="mainPage7" 
            value="#{userBean.sessionUserNumber}" />
            </li>
            <li>Browser: <h:outputText id="mainPage2" value="#{userBean.sessionBrowser}" />
                <h:outputText id="mainPage3" value="#{userBean.sessionBrowserNum}" />
            </li>
            <li>OS: <h:outputText id="mainPage4" value="#{userBean.sessionOS}" />
                <h:outputText id="mainPage5" value="#{userBean.sessionOSNum}" />
            </li>
            <li>Email Address: <h:outputText id="mainPage8" 
            value="#{userBean.sessionUserEmail}" />
            </li>
            <li>Profile Number: <h:outputText id="mainPage6" 
            value="#{userBean.sessionUserProfileNum}" />
            </li>
        </ul>
    </c:if>

<jsp:include page="/WEB-INF/html/footer.html" />

    </f:view>


     
    </body>
</html>
