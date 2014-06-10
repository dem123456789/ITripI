<%@ page import="edu.gatech.cs2340.ITripCS2340.Model.Username"%>
<%Username user=(Username) request.getAttribute("Username"); %>
<html>
    <body>
        <form action="">
            <input type="hidden" name="Username" value="<%=user.getUsername()%>"></input>
            <input type="hidden" name="ManageAccount" value="true"></input>
            <%if(request.getAttribute("PassError")!=null){%>
                <I>Error Password incorrect</I><%}%>
            Old Password: <input type="password" name="oldPass"><br>
            New Password: <input type="password" name="newPass"><br>
            <p><button type="submit" name="changedPass" value="true">Change Password</button></p>
        </form>
        <form action="">
            <input type="hidden" name="ManageAccount" value="true"></input>
            <input type="hidden" name="Username" value="<%=user.getUsername()%>"></input>
            <button type="submit" name="Back" value="true">Back</button>
        </form>
    </body>
</html>