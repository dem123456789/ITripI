<%@page import="edu.gatech.cs2340.ITripCS2340.Controller.SharedServletMethods"%>
<%@page import="edu.gatech.cs2340.ITripCS2340.Controller.JSPStringConstants"%>
<!DOCTYPE html>
<%String user= (String)SharedServletMethods.getUsername(request); %>
<html>
    <head>
        <title>Logged in</title>
    </head>
    <body>
        <div id="fb-root"></div>
        <script>
            (function(d, s, id) 
            {
                var js, fjs = d.getElementsByTagName(s)[0];
                if (d.getElementById(id)) 
                    return;
                js = d.createElement(s); 
                js.id = id;
                js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&appId=1483543211862886&version=v2.0";
                fjs.parentNode.insertBefore(js, fjs);
            }(document, 'script', 'facebook-jssdk'));
        </script>
        <h1>
            You are logged in as <%= user %>.
        </h1>
        <form action="/ITripCS2340/Main/" method="POST">
            <button type="submit" name="<%=JSPStringConstants.MANAGE_ACCOUNT_FLAG%>" value="true">Manage Account</button>
            <button type="submit" name="<%=JSPStringConstants.LOGOUT_FLAG%>" value="true">Log out</button>
            <button type="submit" name="<%=JSPStringConstants.GO_TO_MAP_FLAG%>" value="true">Search for Businesses</button>
            <button type="submit" name="<%=JSPStringConstants.GO_TO_YELP_FLAG%>" value="true">Search for Businesses via yelp</button>
            <button type="submit" name="<%=JSPStringConstants.SHOW_ROUTE_FLAG%>" value="true">Show Itinerary</button>
        </form>
        <div class="fb-login-button" data-max-rows="1" data-size="medium" data-show-faces="false" data-auto-logout-link="true"></div>
    </body>
</html>
