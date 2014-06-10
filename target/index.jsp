<html>
    <head>
        <title>Logged in</title>
    <head>
    <body>
        <form action="">
            <%if(request.getAttribute("UserError")!=null){%>
                        <I>Error Username Incorrect</I><%}%>
            <%if(request.getAttribute("UserIncorrect")!=null){%>
                        <I>Error Username taken</I><%}%>
            <%String s=(String)request.getAttribute("NameString");%>
            Username: <input type="text" name="username" value="<%=s%>"><br>
            <%if(request.getAttribute("PassError")!=null){%>
                        <I>Error Password incorrect</I><%}%>
            <%s=(String)request.getAttribute("PassString");%>
            Password: <input type="password" name="password" value="<%=s%>"><br>
            <p><button type="submit" name="SignIn" value="true">Sign in</button></a>
               <button type="submit" name="SignIn" value="false">Register</button></a></p>
        </form>
    </body>
<html>