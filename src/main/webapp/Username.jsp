<%@ page import="edu.gatech.cs2340.ITripCS2340.Controller.JSPStringConstants"%>
<html>
    <body>
        <form action="/ITripCS2340/ChangeUserName/">
            <%String s=(String)request.getAttribute(JSPStringConstants.USERNAME_PARAM);%>
            <input type="hidden" name=<%=JSPStringConstants.USERNAME_PARAM%> value=<%=s%> ></input>
            <%if(request.getAttribute(JSPStringConstants.PASSWORD_INCORRECT_ERROR)!=null){%>
                <I>Error Password incorrect</I><%}%>
            <%s=(String)request.getAttribute(JSPStringConstants.PASSWORD_PARAM);
                if(s==null)
                    s="";%>
            Password: <input type="password" name=<%=JSPStringConstants.PASSWORD_PARAM%> value=<%=s%> ><br>
            <%if(request.getAttribute(JSPStringConstants.USERNAME_TAKEN_ERROR)!=null){%>
                <I>Error Username taken</I><%}%>
            <%s=(String)request.getAttribute(JSPStringConstants.NEW_USERNAME_PARAM);
                if(s==null)
                    s="";%>
            New User: <input type="text" name=<%=JSPStringConstants.NEW_USERNAME_PARAM%> value=<%=s%> ><br>
            <p><button type="submit" name="ChangedUser" value="true">Change Username</button></p>
            <button type="submit" name="Back" value="true">Back</button>
        </form>
    </body>
</html>