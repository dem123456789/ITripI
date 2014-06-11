<%@ page import="edu.gatech.cs2340.ITripCS2340.Controller.JSPStringConstants"%>
<html>
    <head>
        <title>ManageAccount</title>
    <head>
    <body>
        <form action="/ITripCS2340/AccountManagement/">
            <%String s=(String)request.getAttribute(JSPStringConstants.USERNAME_PARAM);%>
            <input type="hidden" name=<%=JSPStringConstants.USERNAME_PARAM%> value=<%=s%> ></input>
            <button type="submit" name=<%=JSPStringConstants.CHANGE_USERNAME_FLAG%> value="true">Change Username</button>
            <button type="submit" name=<%=JSPStringConstants.CHANGE_PASSWORD_FLAG%> value="true">Change Password</button>
            <button type="submit" name="GoBack" value="true">Back</button>
        </form>
    </body>
<html>