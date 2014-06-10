<%@ page import="edu.gatech.cs2340.ITripCS2340.Model.Username"%>
<%Username user=(Username) request.getAttribute("Username"); %>
<html>
    <head>
        <title>Logged in</title>
    <head>
    <body>
        <h1>
            You are logged in as <%= user.getUsername() %>.
        </h1>
        <form action="">
            <input type="hidden" name="Username" value="<%=user.getUsername()%>"></input>
            <button type="submit" name="ManageAccount" value="true">Manage Account</button>
        </form>
        <a href="/ITripCS2340">Log out</a>
    </body>
<html>