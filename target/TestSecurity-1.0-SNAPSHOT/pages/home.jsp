<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    
    <% if(session.getAttribute("id") == null){
        
        session.setAttribute("requestMessage", "Try to access within login!");
        response.sendRedirect("login.jsp");

    } %>
    
    <body>
        <h1>Hello! ${user}</h1>
        <h2><a href="../register/getAuthorizedUser">Get a List of Authorized User</a></h2>
    </body>
</html>
