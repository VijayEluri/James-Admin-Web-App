<!--
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
-->

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

    <html>
        <head>
            <base href="<%=basePath%>" />
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Admin Main Page</title>
        <style type="text/css" media="all">@import "html/layout1.css";</style>
        </head>
        <body>

              <f:view>
    <jsp:include page="/WEB-INF/html/header.html" />
    <h1>James Web App Admin Main Page</h1>

    <c:if test="${adminUserBean.loggedIn != 1}">
        <p>You are not logged in. Please go to the
        <a href="jsp/loginForm.faces">login page</a>.</p>
    </c:if> <!-- if user is not logged in -->

    <c:if test="${adminUserBean.loggedIn == 1}">

        <p>It's sooo nice to see that <h:outputText id="mainPage1"
        value="#{adminUserBean.userName}" />
        decided to come to church this week.</p>
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
        <p>To add a user, click <a href="jsp/addUserForm.faces">here</a>.</p>
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
        <p>To drop a user, click <a href="jsp/dropUserForm.faces">here</a>.</p>

    </c:if> <!-- if user is logged in  -->

<jsp:include page="/WEB-INF/html/footer.html" />

    </f:view>

        </body>
    </html>

