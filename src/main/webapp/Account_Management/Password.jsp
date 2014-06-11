<%@ page import="edu.gatech.cs2340.ITripCS2340.Controller.JSPStringConstants"%>
<html>
    <body>
        <form action="/ITripCS2340/ChangePassword/">
            <%String s=(String)request.getAttribute(JSPStringConstants.USERNAME_PARAM);%>
            <input type="hidden" name=<%=JSPStringConstants.USERNAME_PARAM%> value=<%=s%> ></input>
            <%if(request.getAttribute(JSPStringConstants.PASSWORD_INCORRECT_ERROR)!=null){%>
                <I>Error Password incorrect</I><%}%>
            Password: <input type="password" name=<%=JSPStringConstants.PASSWORD_PARAM%> ><br>
            <%s=(String)request.getAttribute(JSPStringConstants.CONFIRM_PASSWORD_PARAM);
                if(s==null)
                    s="";%>
            New Password: <input type="password" name=<%=JSPStringConstants.CONFIRM_PASSWORD_PARAM%> value=<%=s%> ><br>
            <button type="submit" name="changedPass" value="true">Change Password</button>
            <button type="submit" name="Back" value="true">Back</button>
        </form>
    </body>
</html>