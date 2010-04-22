
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsf/core"      prefix="f" %>
<%@ taglib uri="http://java.sun.com/jsf/html"      prefix="h" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">


    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>List Users</title>
        </head>
        <body>

              <f:view>
    <jsp:include page="/WEB-INF/html/header.html" />
    <h1>List Users Page</h1>

    <c:if test="${adminUserBean.loggedIn != 1}">
        <p>You are not logged in. Please go to the
        <a href="jsp/loginForm.faces">login page</a>.</p>
    </c:if> <!-- if user is not logged in -->

    <c:if test="${adminUserBean.loggedIn == 1}">

        <h:dataTable id="boogabooga" border="2"
 value="#{adminUserBean.userList}" var="sr123">
     <h:column>
         <f:facet name="header"> <h:outputText value="Name" /> </f:facet>
         <h:outputText value="#{sr123.username}" />
     </h:column>
     <h:column>
         <f:facet name="header"> <h:outputText value="Hash" /> </f:facet>
         <h:outputText value="#{sr123.pwdHash}" />
     </h:column>
     <h:column>
         <f:facet name="header"> <h:outputText value="Algorithm" /> </f:facet>
         <h:outputText value="#{sr123.pwdAlgorithm}" />
     </h:column>
     <h:column>
         <f:facet name="header"> <h:outputText value="Forwarding" /> </f:facet>
         <h:outputText value="#{sr123.useForwarding}" />
     </h:column>
     <h:column>
         <f:facet name="header"> <h:outputText value="Destination" /> </f:facet>
         <h:outputText value="#{sr123.forwardDestination}" />
     </h:column>
     <h:column>
         <f:facet name="header"> <h:outputText value="UAlias" /> </f:facet>
         <h:outputText value="#{sr123.useAlias}" />
     </h:column>
     <h:column>
         <f:facet name="header"> <h:outputText value="Alist" /> </f:facet>
         <h:outputText value="#{sr123.alias}" />
     </h:column>
     <h:column>
         <f:facet name="header"> <h:outputText value="Delete" /> </f:facet>
<h:form>
         <h:commandLink action="#{userManagerBean.dropUser}">
         <h:outputText value="Click here to delete" />
         <f:param name="userNameToDrop" value="#{sr123.username}" />  </h:commandLink>
</h:form>
     </h:column>
     </h:dataTable>

        <p>It's sooo nice to see that <h:outputText id="mainPage1"
        value="#{adminUserBean.userName}" />
        decided to come to church this week.</p>


    </c:if> <!-- if user is logged in  -->

<jsp:include page="/WEB-INF/html/footer.html" />

    </f:view>

        </body>
    </html>
