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
            <base href="<%=basePath%>" />
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
            <title>Drop User Does Not Exist</title>
        <style type="text/css" media="all">@import "html/layout1.css";</style>
        </head>
        <body>

              <f:view>
    <jsp:include page="/WEB-INF/html/header.html" />
    <h1>Drop User Does Not Exist</h1>

    <c:if test="${adminUserBean.loggedIn != 1}">
        <p>You are not logged in. Please go to the <a href="jsp/loginForm.faces">login page</a>.</p>
    </c:if> <!-- if user is not logged in -->

    <c:if test="${adminUserBean.loggedIn == 1}">

        <p>It's sooo nice to see that <h:outputText id="mainPage1"
        value="#{adminUserBean.userName}" />
        decided to come to church this week.</p>
<br />
        <p>The user you tried to delete (<h:outputText id="dropUserDoesNotExist1"
        value="#{userManagerBean.userNameToDrop}" />) does not exist in the database.
            Please try again <a href="jsp/dropUserForm.faces">here</a>.
        </p>

    </c:if> <!-- if user is logged in  -->

<jsp:include page="/WEB-INF/html/footer.html" />

    </f:view>

        </body>
    </html>


