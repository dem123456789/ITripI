<%@ page import="edu.gatech.cs2340.ITripCS2340.Controller.JSPStringConstants"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Login</title>
        <meta charset="UTF-8">
        <link href="/ITripCS2340/<%=JSPStringConstants.LOGINREGISTER_CSS%>" rel="stylesheet" type="text/css">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    </head>
    <body>
        <div id="LoginComponentsContainer">
            <form action="/ITripCS2340/Login/" method="POST">
                <table cellspacing="1" cellpadding="3" border="0" >
                    <tr>
                        <td>
                            <%if(request.getAttribute(JSPStringConstants.USERNAME_NOT_FOUND_ERROR)!=null){%>
                                                    <I>Error Username Incorrect</I><%}%>
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

                    <tr>
                        <td>
                            <%if(request.getAttribute(JSPStringConstants.PASSWORD_INCORRECT_ERROR)!=null){%>
                                                    <I>Error Password incorrect</I><%}%>
                            <%s=(String)request.getAttribute(JSPStringConstants.PASSWORD_PARAM);
                              if(s==null)
                                s="";%>
                            <input  <%if(s==""){%>
                                             placeholder = "Password"<%}
                                      else{%>
                                             value=<%=s%>
                                    <%}%> id="password" type="password" size="25" name=<%=JSPStringConstants.PASSWORD_PARAM%> />
                        </td>
                    </tr>
                    <tr align="left">
                        <td colspan="2">
                                <input type="submit" value="Login" name="LogIn" id="LogIn" class="clickButton"/>
                                <!--<form action="" id="forgotPasswordLink">forgot password</form>-->
                            <input type="button" onclick="location.href='/ITripCS2340/<%=JSPStringConstants.REGISTER_JSP%>';" value="Register" name="registerButton" id="registerButton" class="clickButton"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div id="status">
        </div>
    </body>
</html>