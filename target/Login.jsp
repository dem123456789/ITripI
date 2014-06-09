<!DOCTYPE html>
<html>
<head>
    <title>Facebook Login JavaScript Example</title>
    <meta charset="UTF-8">
    <link href="style.css" rel="stylesheet" type="text/css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
</head>
<body>

<!--
<div id="fb-root"></div>

<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/zh_CN/sdk.js#xfbml=1&appId=1483543211862886&version=v2.0";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

<script>
      window.fbAsyncInit = function() {
        FB.init({
          appId      : '{1483543211862886}',
          xfbml      : true,
          version    : 'v2.0'
        });
        FB.getLoginStatus(function(response) {
          if (response.status === 'connected') {
            console.log('Logged in.');
          }
          else {
            FB.login();
          }
        });
      };
      (function(d, s, id){
         var js, fjs = d.getElementsByTagName(s)[0];
         if (d.getElementById(id)) {return;}
         js = d.createElement(s); js.id = id;
         js.src = "//connect.facebook.net/en_US/sdk.js";
         fjs.parentNode.insertBefore(js, fjs);
       }(document, 'script', 'facebook-jssdk'));
</script>


<script>
  // This is called with the results from from FB.getLoginStatus().
  function statusChangeCallback(response) {
    console.log('statusChangeCallback');
    console.log(response);
    // The response object is returned with a status field that lets the
    // app know the current login status of the person.
    // Full docs on the response object can be found in the documentation
    // for FB.getLoginStatus().
    if (response.status === 'connected') {
      // Logged into your app and Facebook.
      //testAPI();
      window.location="Hello.html";
    } else if (response.status === 'not_authorized') {
      // The person is logged into Facebook, but not your app.
      document.getElementById('status').innerHTML = 'Please log ' +
        'into this app.';
    } else {
      // The person is not logged into Facebook, so we're not sure if
      // they are logged into this app or not.
      document.getElementById('status').innerHTML = 'Please log ' +
        'into Facebook.';
    }
  }

//At this point in the login flow, your app displays the Login dialog,
//which gives people the choice of whether to cancel or to enable the app to access their data.
//Whatever choice people make, the browser returns to the app and response data
//indicating whether they connected or cancelled is sent to your app. When your app uses the JavaScript SDK,
//it returns an authResponse object to the callback specified when you made the FB.login() call:
//This response can be detected and handled within the FB.login call, like this:
FB.login(function(response) {
  if (response.status === 'connected') {
    // Logged into your app and Facebook.
  } else if (response.status === 'not_authorized') {
    // The person is logged into Facebook, but not your app.
  } else {
    // The person is not logged into Facebook, so we're not sure if
    // they are logged into this app or not.
  }
});

  // This function is called when someone finishes with the Login
  // Button.  See the onlogin handler attached to it in the sample
  // code below.
  function checkLoginState() {
    FB.getLoginStatus(function(response) {
      statusChangeCallback(response);
    });
  }

  window.fbAsyncInit = function() {
  FB.init({
    appId      : '{1483543211862886}',
    cookie     : true,  // enable cookies to allow the server to access
                        // the session
    xfbml      : true,  // parse social plugins on this page
    version    : 'v2.0' // use version 2.0
  });

  // Now that we've initialized the JavaScript SDK, we call
  // FB.getLoginStatus().  This function gets the state of the
  // person visiting this page and can return one of three states to
  // the callback you provide.  They can be:
  //
  // 1. Logged into your app ('connected')
  // 2. Logged into Facebook, but not your app ('not_authorized')
  // 3. Not logged into Facebook and can't tell if they are logged into
  //    your app or not.
  //
  // These three cases are handled in the callback function.

  FB.getLoginStatus(function(response) {
    statusChangeCallback(response);
  });
  };

  // Load the SDK asynchronously
  (function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
  }(document, 'script', 'facebook-jssdk'));

  // Here we run a very simple test of the Graph API after login is
  // successful.  See statusChangeCallback() for when this call is made.
  function testAPI() {
    console.log('Welcome!  Fetching your information.... ');
    FB.api('/me', function(response) {
      console.log('Successful login for: ' + response.name);
      document.getElementById('status').innerHTML =
        'Thanks for logging in, ' + response.name + '!';

    });
  }
</script>
-->

<!--
  Below we include the Login Button social plugin. This button uses
  the JavaScript SDK to present a graphical Login button that triggers
  the FB.login() function when clicked.
-->


<div id="LoginComponentsContainer">
    <table cellspacing="1" cellpadding="3" border="0" >
        <tr>
            <td><input  placeholder = "Account" maxlength="40" size="25" name="username" type="text"/> </td>
        </tr>

        <tr>
            <td><input  id="password" placeholder = "Password" type="password" size="25" name="password" /> </td>
        </tr>
        <tr align="left">
            <td colspan="2">
                <form action="" method="POST">
                    <input type="submit" value="Login" name="loginButton" id="loginButton" class="clickButton"/>
                    <form action="" id="forgotPasswordLink">forgot password</form>
                </form>
                <form  action="location.href='Register.jsp';">
                    <input type="button" onclick="location.href='Register.jsp';" value="Register" name="registerButton" id="registerButton" class="clickButton"/>
                </form>
            </td>
        </tr>
    </table>
</div>

<!--
    <fb:login-button scope="public_profile,email" onlogin="checkLoginState();">
    </fb:login-button>
<div id="status">
</div>
-->

</body>
</html>