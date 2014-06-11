<%@ page import="edu.gatech.cs2340.ITripCS2340.Controller.JSPStringConstants"%>
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
                            <%if(request.getAttribute(JSPStringConstants.USERNAME_TAKEN_ERROR)!=null){%>
                                <I>Error Username Taken</I><%}%>
                            <%String s=(String)request.getAttribute(JSPStringConstants.USERNAME_PARAM);
                                if(s==null)
                                    s="";%>
                            <input  <%if(s==""){%>
                                         placeholder = "Account"<%}
                                      else{%>
                                        value=<%=s%>
                                    <%}%> maxlength="40" size="25" name=<%=JSPStringConstants.USERNAME_PARAM%> type="text"/>
                        </td>
                    </tr>
                    <!-- <tr>
                        <td><input  placeholder = "E-mail address" maxlength="40" size="25" name="emailaddress" type="text"/> </td>
                    </tr> -->
                    <tr>
                        <td>
                            <%s=(String)request.getAttribute(JSPStringConstants.PASSWORD_PARAM);
                                if(s==null)
                                    s="";%>
                            <input  <%if(s==""){%>
                                        placeholder = "Password"<%}
                                      else{%>
                                        value=<%=s%>
                                    <%}%> id="password" size="25" name=<%=JSPStringConstants.PASSWORD_PARAM%> type="password"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <%if(request.getAttribute(JSPStringConstants.PASSWORDS_NOT_SAME_ERROR)!=null){%>
                                <I>Error passwords must be the same</I><%}%>
                            <%s=(String)request.getAttribute(JSPStringConstants.CONFIRM_PASSWORD_PARAM);
                                if(s==null)
                                    s="";%>
                            <input  <%if(s==""){%>
                                        placeholder = "Confirm password"<%}
                                      else{%>
                                        value="<%=s%>"
                                    <%}%> id="ConformationPass" size="25" name=<%=JSPStringConstants.CONFIRM_PASSWORD_PARAM%> type="password"/>
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