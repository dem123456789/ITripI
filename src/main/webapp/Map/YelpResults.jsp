<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="edu.gatech.cs2340.ITripCS2340.Controller.JSPStringConstants"%>
<%@page import="edu.gatech.cs2340.ITripCS2340.Controller.SharedServletMethods"%>
<%String existingItineraries= (String)SharedServletMethods.getItinerary(request); %>
<!DOCTYPE html>
<html>
    <head>
        <title>Yelp results</title>
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
        <link href="/ITripCS2340/<%=JSPStringConstants.YELP_CSS%>" rel="stylesheet" type="text/css">
        <link href="/ITripCS2340/jquery-ui/jquery-ui.min.css" rel="stylesheet">
        <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=<%=JSPStringConstants.GOOGLE_API_KEY%>"></script>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places"></script>
        <script src="/ITripCS2340/jquery-ui/external/jquery/jquery.js"></script>
        <script src="/ITripCS2340/jquery-ui/jquery-ui.min.js"></script>
        <script type="text/javascript">
            var selectedUI, geocoder, selectedMarker;
            function initialize() 
            {
                geocoder=new google.maps.Geocoder();
                var existingItineraries = <%=existingItineraries%>;
                for(var i =0;i<existingItineraries.length;i++)
                {
                    var item = existingItineraries[i];
                    var newDiv = $("<div/>");
                    var newDescription = $("<div/>");
                    var currentBusiness = JSON.parse(item.<%=JSPStringConstants.BUSINESS_PARAM%>);
                    newDescription.text(currentBusiness.name+" Start Time: " + getTimeFormat(item.<%=JSPStringConstants.STARTTIME%>)+
                        " End Time: "+ getTimeFormat(item.<%=JSPStringConstants.ENDTIME%>));
                    var newPlace = $("<div type = hidden/>");
                    newPlace.val(item);
                    newDiv.append(newDescription);
                    newDiv.append(newPlace);
                    $("#ItinerariesBox").append(newDiv);
                }
                jQuery(document).ready(function()
                {    
                    $("#ItinerariesBox").sortable({
                        start: function(event, ui) 
                        {
                            selectedUI = ui.item[0];
                        }
                    });  
                    $("#ResulstsBox").selectable({
                        selected: function( event, ui ) 
                        {
                            selectedMarker = ui.selected;
                        }
                    });
                });
                $("#SaveHomeBase").click(saveItineraries);
                $("#SavePlaces").click(saveItineraries);
                $("#DeletePoint").click(function()
                {
                    selectedUI.remove();
                });
                $("#AddPoint").click(function()
                {
                    if(selectedMarker!=undefined)
                    {
                        var currentBusiness = selectedMarker.children[1].value;
                        var address = getAddress(currentBusiness);
                        geocoder.geocode({'address': address},
                        function(results, status)
                        {
                            if (status == google.maps.GeocoderStatus.OK)
                            {
                                currentBusiness=adaptYelpToGoogle(currentBusiness, address, results);
                                var newDiv = $("<div/>");
                                var newDescription = $("<div/>");
                                newDescription.text(currentBusiness.name+" Start Time: " + getTimeFormat(document.getElementById('StartTimeEvent').value) +
                                        " End Time: " + getTimeFormat(document.getElementById('EndTimeEvent').value));
                                    var newPlace = $("<div type = hidden/>");
                                    newPlace.val({
                                          "<%=JSPStringConstants.STARTTIME%>": document.getElementById('StartTimeEvent').value,
                                          "<%=JSPStringConstants.ENDTIME%>": document.getElementById('EndTimeEvent').value,
                                        "<%=JSPStringConstants.INTEREST_PLACE%>": "${InterestPlace}",
                                        "<%=JSPStringConstants.BUSINESS_PARAM%>": JSON.stringify(currentBusiness)
                                    });
                                newDiv.append(newDescription);
                                newDiv.append(newPlace);

                                $("#ItinerariesBox").append(newDiv);
                            }
                        });
                    }
                });
                FB.getLoginStatus(function(response) 
                {
                    if (response.status === 'connected')
                    {
                        FB.login(function()
                        {
                            FB.api('/me/friends', 'get', 
                            function(result)
                            {
                                var list=result.data;
                                if(list.length===0)
                                    markersWithoutFriends();
                                else 
                                    markersWithFriends(list);
                            });
                        }, {scope: 'user_friends '});
                    }
                    else
                        markersWithoutFriends(map, infowindow);
                });
            }
            
            function markersWithoutFriends()
            {                
                var business, description;
                var businesses=${Businesses};
                for(i=0; i<businesses.length;i++)
                {
                    business = businesses[i];
                    if(business.location.display_address.length>=3)
                    {
                        description= business.name + " <br>" + getAddress(business) + " " +
                              business.display_phone + " <br><a href=\"" + business.url + "\">" + business.url + "</a> <br>";
                        var newDiv = $("<div/>");
                        var newDescription = "<div>"+description+"</div>";
                        var newPlace = $("<div type = hidden/>");
                        newPlace.val(business);
                        newDiv.append(newDescription);
                        newDiv.append(newPlace);
                        $("#ResulstsBox").append(newDiv);
                    }
                }
            }
            
            function markersWithFriends(list)
            {                
                var business, description, friendString;
                var businesses=${Businesses};
                for(i=0; i<businesses.length;i++)
                {
                    business = businesses[i];
                    friendString=checkReviews(list, business);
                    if(business.location.display_address.length>=3)
                    {
                        description= business.name + " <br>" + getAddress(business) + " " +
                                business.display_phone + " <br><a href=\"" + business.url + "\">" + business.url + "</a> <br>"+friendString;
                        var newDiv = $("<div/>");
                        var newDescription = "<div>"+description+"</div>";
                        var newPlace = $("<div type = hidden/>");
                        newPlace.val(business);
                        newDiv.append(newDescription);
                        newDiv.append(newPlace);
                        $("#ResulstsBox").append(newDiv);
                    }
                }
            }
            
            function checkReviews(list, business)
            {
                var friend="", friendsList="";
                var reviews=business.reviews;
                for(var f=0;f<list.length;f++)
                {
                    friend=list[f].name;
                    if(reviews.user.name === friend)
                        friendsList=friend+" wrote a review";
                }
                return friendsList;
            }
            
            function adaptYelpToGoogle(yelpResult, address, results)
            {                
                var googleVersion={
                    "geometry" : { "location" : { "lat" : results[0].geometry.location.k, "lng" : results[0].geometry.location.B } },
                    "name" : yelpResult.name,
                    "formatted_address" : address,
                    "formatted_phone_number" : yelpResult.display_phone,
                    "url" : yelpResult.url,
                    "rating" : "NA"
                }
                return googleVersion;
            }
            
            function getAddress(currentBusiness)
            {
                var address="";
                for(var i=0; i<currentBusiness.location.address.length;i++)
                    address+=currentBusiness.location.address[i]+", ";
                address+=currentBusiness.location.city+", ";
                address+=currentBusiness.location.state_code+", ";
                address+=currentBusiness.location.country_code+", ";
                address+=currentBusiness.location.postal_code;
                return address;
            }
            
            function getTimeFormat(time)
            {
                var hours=Math.floor(time/100);
                var minutes=Math.floor(time%100);
                if(minutes<10)
                    minutes="0"+minutes;
                return hours+":"+minutes;
            }
            
            function saveItineraries()
            {
                var existingItineraries = [];
                var itinerary = $("#ItinerariesBox").children();
                for (var i = 0; i < itinerary.length; i++) 
                {
                    existingItineraries.push((itinerary[i].children)[1].value);
                };
                $('#itinerary').val(JSON.stringify(existingItineraries));
                $('#InterestPlace').val("${InterestPlace}");
                $('#Businesses').val(JSON.stringify(${Businesses}));
            }   
           </script>
    </head>
    <body onload="initialize()">
        <!--facebook wants this here so don't move it-->
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
        <div id="Frame">
            <div id="PreferenceArea">
            </div>
        </div>
        <div id="Legend">Enter the Time frame <br>for this event<br></div>
        <div>
            <div id="StartTimeTitleEvent">Start Time</div>
            <select  id = "StartTimeEvent" name = "<%=JSPStringConstants.STARTTIME%>">
                <option value="0000">00:00</option>
                <option value="0100">01:00</option>
                <option value="0200">02:00</option>
                <option value="0300">03:00</option>
                <option value="0400">04:00</option>
                <option value="0500">05:00</option>
                <option value="0600">06:00</option>
                <option value="0700">07:00</option>
                <option value="0800">08:00</option>
                <option value="0900">09:00</option>
                <option value="1000">10:00</option>
                <option value="1100">11:00</option>
                <option value="1200">12:00</option>
                <option value="1300">13:00</option>
                <option value="1400">14:00</option>
                <option value="1500">15:00</option>
                <option value="1600">16:00</option>
                <option value="1700">17:00</option>
                <option value="1800">18:00</option>
                <option value="1900">19:00</option>
                <option value="2000">20:00</option>
                <option value="2100">21:00</option>
                <option value="2200">22:00</option>
                <option value="2300">23:00</option>
            </select>
        </div>
        <div >
            <div id="EndTimeTitleEvent">End Time</div>
            <select  id = "EndTimeEvent" name = "<%=JSPStringConstants.ENDTIME%>">
                <option value="0000">00:00</option>
                <option value="0100">01:00</option>
                <option value="0200">02:00</option>
                <option value="0300">03:00</option>
                <option value="0400">04:00</option>
                <option value="0500">05:00</option>
                <option value="0600">06:00</option>
                <option value="0700">07:00</option>
                <option value="0800">08:00</option>
                <option value="0900">09:00</option>
                <option value="1000">10:00</option>
                <option value="1100">11:00</option>
                <option value="1200">12:00</option>
                <option value="1300">13:00</option>
                <option value="1400">14:00</option>
                <option value="1500">15:00</option>
                <option value="1600">16:00</option>
                <option value="1700">17:00</option>
                <option value="1800">18:00</option>
                <option value="1900">19:00</option>
                <option value="2000">20:00</option>
                <option value="2100">21:00</option>
                <option value="2200">22:00</option>
                <option value="2300" selected="selected">23:00</option>
            </select>
        </div>
        <form action="/ITripCS2340/YelpResults/" method="post">
            <input type="submit" value="Go back" name="<%=JSPStringConstants.GO_TO_MAP_FLAG%>" class="clickButton"/>
            <input type="submit" value="Go to Main" name="<%=JSPStringConstants.GO_BACK_FLAG%>" class="clickButton2"/>
        </form>
        <form action="/ITripCS2340/YelpResults/" method="post">              
            <input type="submit" id = "SavePlaces" name="<%=JSPStringConstants.SAVEPLACES%>" value="Save" />
            <input type="hidden" id = "itinerary" name="<%=JSPStringConstants.ITINERARY_PARAM%>" value="" />
            <input type="hidden" id = "InterestPlace" name="<%=JSPStringConstants.INTEREST_PLACE%>" value="" />
            <input type="hidden" id = "Businesses" name="<%=JSPStringConstants.BUSINESSES%>" value="" />
        </form>
        <input type="button" value="Add" name = "<%=JSPStringConstants.ADDPOINT%>" id="AddPoint"/>
        <input type="button" value="Delete" name = "<%=JSPStringConstants.DELETEPOINT%>" id="DeletePoint">
        <div id="itineraryTitle">Itinerary</div>
        <div class="table-responsive" id = "ItinerariesBox"></div>
        <div id="ResultsTitle">Results</div>
        <div class="table-responsive" id = "ResulstsBox"></div>
    </body>
</html>