<%@ page import="edu.gatech.cs2340.ITripCS2340.Controller.JSPStringConstants"%>
<%String user=(String) request.getAttribute(JSPStringConstants.USERNAME_PARAM); %>
<html>
    <head>
        <title>Logged in</title>
    <head>
    <body>
        <h1>
            You are logged in as <%= user %>.
        </h1>
        <form action="/ITripCS2340/AccountManagement/">
            <input type="hidden" name=<%=JSPStringConstants.USERNAME_PARAM%> value=<%=user%> ></input>
            <button type="submit" name=<%=JSPStringConstants.MANAGE_ACCOUNT_FLAG%> value="true">Manage Account</button>
        </form>
        <a href="/ITripCS2340">Log out</a>
    </body>
<html>