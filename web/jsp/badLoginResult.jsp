<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>


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
        <title>Bad Login result for James</title>
    </head>
    <body>
<jsp:include page="/WEB-INF/html/header.html" />
    <h1>Login Result</h1>
    <p>Oops. Try <a href="jsp/loginForm.faces">again</a>.</p>
<jsp:include page="/WEB-INF/html/footer.html" />
    
    </body>
    
</html>
