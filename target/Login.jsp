<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <meta charset="UTF-8">
        <link href="style.css" rel="stylesheet" type="text/css">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    </head>
    <body>
        <div id="LoginComponentsContainer">
            <form action="/ITripCS2340/Login/" method="POST">
                <table cellspacing="1" cellpadding="3" border="0" >
                    <tr>
                        <td>
                            <%if(request.getAttribute("UserError")!=null){%>
                                                    <I>Error Username Incorrect</I><%}%>
                            <%String s=(String)request.getAttribute("NameString");
                              if(s==null)
                                s="";%>
                            <input  <%if(s==""){%>
                                            placeholder = "Account"<%}
                                      else{%>
                                            value="<%=s%>"
                                    <%}%> maxlength="40" size="25" name="username" type="text"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <%if(request.getAttribute("PassError")!=null){%>
                                                    <I>Error Password incorrect</I><%}%>
                            <%s=(String)request.getAttribute("PassString");
                              if(s==null)
                                s="";%>
                            <input  <%if(s==""){%>
                                             placeholder = "Password"<%}
                                      else{%>
                                             value="<%=s%>"
                                    <%}%> id="password" type="password" size="25" name="password" />
                        </td>
                    </tr>
                    <tr align="left">
                        <td colspan="2">
                                <input type="submit" value="Login" name="LogIn" id="LogIn" class="clickButton"/>
                                <!--<form action="" id="forgotPasswordLink">forgot password</form>-->
                            <input type="button" onclick="location.href='/ITripCS2340/Register.jsp';" value="Register" name="registerButton" id="registerButton" class="clickButton"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div id="status">
        </div>
    </body>
</html>