<%@ page import="edu.gatech.cs2340.ITripCS2340.Model.Username"%>
<%Username user=(Username) request.getAttribute("Username"); %>
<html>
    <head>
        <title>ManageAccount</title>
    <head>
    <body>
        <form action="">
            <input type="hidden" name="Username" value="<%=user.getUsername()%>"></input>
            <input type="hidden" name="ManageAccount" value="true"></input>
            <button type="submit" name="ChangeUser" value="true">Change Username</button>
            <button type="submit" name="changePass" value="true">Change Password</button>
        </form>
        <form action="">
            <input type="hidden" name="ManageAccount" value="true"></input>
            <input type="hidden" name="Username" value="<%=user.getUsername()%>"></input>
            <button type="submit" name="returnToMain" value="true">Back</button>
        </form>
    </body>
<html>