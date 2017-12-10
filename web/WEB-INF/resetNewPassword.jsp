<%-- 
    Document   : resetNewPassword.jsp
    Created on : Nov 23, 2017, 9:44:35 AM
    Author     : 721292
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Set New Password</title>
    </head>
    <body>
        
        <h1>Enter New Password:</h1>
        <form action="rest?ACTION=SETNEWPASSWORD" method="post">
            <input type="text" name="newpassword">
            <input type="hidden" name="uuid" value="${uuid}">
            <input type="submit" value="sumbit">
        </form>
            ${message}
        
    </body>
</html>
