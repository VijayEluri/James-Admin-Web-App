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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsf/core"      prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsf/html"      prefix="h"%>
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
            <title>List Users</title>
            <style type="text/css" media="all">@import "html/layout1.css";</style>
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
         <!-- f:param name="userNameToDrop" value=" # { sr123.username }" / -->
         <!-- f:param name="userNameToDrop" value="Name" / -->
         <f:param name="dropName" value="#{sr123.username}" />
         </h:commandLink>
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
