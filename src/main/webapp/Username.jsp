<%@ page import="edu.gatech.cs2340.ITripCS2340.Model.Username"%>
<%Username user=(Username) request.getAttribute("Username"); %>
<html>
    <body>
        <form action="">
            <input type="hidden" name="Username" value="<%=user.getUsername()%>"></input>
            <input type="hidden" name="ManageAccount" value="true"></input>
            <%if(request.getAttribute("PassError")!=null){%>
                <I>Error Password incorrect</I><%}%>
            Password: <input type="password" name="pass"><br>
            <%if(request.getAttribute("userError")!=null){%>
                <I>Error Username taken</I><%}%>
            New User: <input type="text" name="newUser"><br>
            <p><button type="submit" name="ChangedUser" value="true">Change Username</button></p>
        </form>
        <form action="">
            <input type="hidden" name="ManageAccount" value="true"></input>
            <input type="hidden" name="Username" value="<%=user.getUsername()%>"></input>
            <button type="submit" name="Back" value="true">Back</button>
        </form>
    </body>
</html>