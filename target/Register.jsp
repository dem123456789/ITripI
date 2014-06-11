<!DOCTYPE html>
<html>
    <head>
        <title>Register</title>
        <meta charset="UTF-8">
        <link href="style.css" rel="stylesheet" type="text/css">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    </head>
    <body>
        <div id="LoginComponentsContainer">
            <form action="/ITripCS2340/Register/" method="POST">
                <table cellspacing="1" cellpadding="3" border="0" >
                    <tr>
                        <td>
                            <%if(request.getAttribute("UserIncorrect")!=null){%>
                                <I>Error Username Taken</I><%}%>
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
                    <!-- <tr>
                        <td><input  placeholder = "E-mail address" maxlength="40" size="25" name="emailaddress" type="text"/> </td>
                    </tr> -->
                    <tr>
                        <td>
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
                    <tr>
                        <td>
                            <%if(request.getAttribute("PassDifferent")!=null){%>
                                <I>Error passwords must be the same</I><%}%>
                            <input id="ConformationPass" placeholder = "Confirm password" type="password" size="25" name="ConformationPass" />
                        </td>
                    </tr>
                    <tr align="left">
                        <td >
                                <input type="submit" value="Register" name="registerButton" id="registerButton" class="clickButton"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </body>
</html>