<!DOCTYPE html>
<html>
<head>
    <title>Facebook Login JavaScript Example</title>
    <meta charset="UTF-8">
    <link href="style.css" rel="stylesheet" type="text/css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
</head>
<body>

<div id="LoginComponentsContainer">
    <table cellspacing="1" cellpadding="3" border="0" >
        <tr>
            <td><input placeholder = "Account name" maxlength="40" size="25" name="username" type="text"/> </td>
        </tr>

        <tr>
            <td><input  placeholder = "E-mail address" maxlength="40" size="25" name="emailaddress" type="text"/> </td>
        </tr>

        <tr>
            <td><input id="password" placeholder = "Password" type="password" size="25" name="password" /> </td>
        </tr>
        <tr>
            <td><input id="password" placeholder = "Confirm password" type="password" size="25" name="password" /> </td>
        </tr>

        <tr align="left">
            <td >
                <form action="Login/" method="POST">
                    <input type="button" value="Register" name="registerButton" id="registerButton" class="clickButton"/>
                </form>
            </td>
        </tr>
    </table>
</div>

</body>
</html>