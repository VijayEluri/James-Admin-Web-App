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
        <title>Add User Form</title>
    </head>
    <body>

    <f:view>
    <jsp:include page="/WEB-INF/html/header.html" />
    <h1>Add User Form Page</h1>

      <c:if test="${adminUserBean.loggedIn != 1}">
        <p>You are not logged in. Please go to the
        <a href="jsp/loginForm.faces">login page</a>.</p>
    </c:if> <!-- if user is not logged in -->

    <c:if test="${adminUserBean.loggedIn == 1}">

        <p>It's sooo nice to see that <h:outputText id="mainPage1"
        value="#{adminUserBean.userName}" />
        decided to come to church this week.</p>

                <h:form>
            <p>Enter your name: <h:inputText value="#{userManagerBean.username}"
                                                 id="username" required="true"/>
            <h:message for="name" /></p>

            <p>Enter your password: <h:inputText value="#{userManagerBean.password}"
                                                     id="password" required="true">
                </h:inputText>
            </p>

            <p>

                    <h:outputLabel rendered="true" for="algorithm2">
			<h:outputText value="Algorithm:" />
	</h:outputLabel>
	<h:selectOneMenu id="algorithm" value="#{userManagerBean.algorithm}">
            <f:selectItem itemLabel="SHA-256"       itemValue="SHA-256" />
            <f:selectItem itemLabel="SHA-384"   itemValue="SHA-384" />
            <f:selectItem itemLabel="SHA-512"      itemValue="SHA-512" />
	</h:selectOneMenu>
    </p>

            <p><h:commandButton value="Submit" rendered="true"
                                action="#{userManagerBean.addUser}" /></p>

        </h:form>

    </c:if> <!-- if user is logged in  -->
<jsp:include page="/WEB-INF/html/footer.html" />

    </f:view>



    </body>
</html>

