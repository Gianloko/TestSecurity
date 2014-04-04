<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <h1>Login Form</h1>
        
        <form action="../security/login" method="POST">
            <table border = "1">
                <tr>
                    <td>Username</td>
                    <td><input type="text" name="login" /></td>
                </tr>
                <tr>
                    <td>Password</td>
                    <td><input type="password" name="password" /></td>
                </tr>
                <tr>
                    <td colspan="2"><input type="submit" value="Login" /></td>
                </tr>
            </table>
        </form>
        
    </body>
</html>
