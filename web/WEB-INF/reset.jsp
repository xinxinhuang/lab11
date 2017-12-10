<%-- 
    Document   : reset
    Created on : Nov 20, 2017, 1:45:39 PM
    Author     : 721292
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset password Page</title>
    </head>
    <body>
        <h1>Reset Password</h1>
        <p>Enter email below to reset password.</p>
        <form action="/rest?action=passEmail" method="post">
        <p>Email Address:<input type="text" name="passResetEmail"/></p>
        <input type="submit" value="Submit">
        </form>
        ${message}
    </body>
</html>
