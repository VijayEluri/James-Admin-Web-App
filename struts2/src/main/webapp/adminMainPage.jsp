<?xml version='1.0' encoding='UTF-8' ?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://java.sun.com/jsf/html"
      xmlns:f="http://java.sun.com/jsf/core"
      xmlns:c="http://java.sun.com/jsp/jstl/core"
      xmlns:ui="http://java.sun.com/jsf/facelets">
    <h:head>
        <title>Facelet Title</title>
    </h:head>
    
    <h:body>
        <f:view>
    <!-- ui:include src="/WEB-INF/html/header.html" / -->
        
        <a href="#{request.contextPath}/jsp/loginForm.faces">Login</a> <br />
<a href="#{request.contextPath}/jsp/adminMainPage.faces">Admin Main Page</a><br />
<a href="#{request.contextPath}/jsp/listUsers.faces">List Users</a> <br />
<a href="#{request.contextPath}/jsp/addUserForm.faces">Add A User</a><br />
<a href="#{request.contextPath}/jsp/dropUserForm.faces">Drop A User</a><br />
<a href="#{request.contextPath}/jsp/listInboxMessages.faces">List Inbox Messages</a><br />
<a href="#{request.contextPath}/jsp/logOutForm.faces">Log out</a> <br />
    <h1>James Web App Admin Main Page</h1>

    <c:if test="${adminUserBean.loggedIn != 1}">
        <p>You are not logged in. Please go to the
        <a href="#{request.contextPath}/jsp/loginForm.faces">login page</a>.</p>
    </c:if> <!-- if user is not logged in -->

    <c:if test="${adminUserBean.loggedIn == 1}">

        <p>It's sooo nice to see that <h:outputText id="mainPage1"
        value="#{adminUserBean.userName}" />
        decided to come to church this week.</p>
        
        <p>someVar: <h:outputText id="mainPage22" value="#{adminUserBean.someVar}" /></p>
        
        <h:form>
        <p>To list the users in the database, click
            <h:commandLink value="here" action="#{adminUserBean.getListOfUsers}" />
        </p>
        </h:form>
        
        <h:form>
        <p>
            To list inbox messages, click
            <h:commandLink value="here" action="#{adminUserBean.getListOfInboxMessages}" />
        </p>
        </h:form>
        
        <h:form>
        <p>Number of messages in Deadletter table: <h:outputText id="deadletter1"
        value="#{adminUserBean.deadLetterCount}" /> <br />
            To delete the contents of the dead letter table, click
            <h:commandLink value="here" action="#{adminUserBean.deleteDeadLetterContents}" />
        </p>
        </h:form>
        
        <p>To add a user, click <a href="#{request.contextPath}/jsp/addUserForm.faces">here</a>.</p>
        <!--
<a href="dropUserForm.jsp"> leads to
http://localhost:8080/JamesWebFacesHibernate001/jsp/dropUserForm.jsp
Which gives an error
<a href="dropUserForm.faces"> seems to work
2010-05-01_17.45.37: But with the <base href="<%=basePath%>" /> tag in,
it does not work.
Trying jsp/dropUserForm.jsp - error
Trying /jsp/dropUserForm.jsp - error
Trying jsp/dropUserForm.faces - works
-->
        <p>To drop a user, click <a href="#{request.contextPath}/jsp/dropUserForm.faces">here</a>.</p>

    </c:if> <!-- if user is logged in -->

    <!-- ui:include src="/WEB-INF/html/footer.html" / -->

    </f:view>

    </h:body>
    
</html>