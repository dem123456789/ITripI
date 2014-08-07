<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="edu.gatech.cs2340.ITripCS2340.Controller.JSPStringConstants"%>
<%@page import="edu.gatech.cs2340.ITripCS2340.Controller.SharedServletMethods"%>
<%String existingItineraries= (String)SharedServletMethods.getItinerary(request); %>
<!DOCTYPE html>
<html>
    <head>
        <title>Map</title>
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
        <link href="/ITripCS2340/<%=JSPStringConstants.MAP_CSS%>" rel="stylesheet" type="text/css">
        <link href="/ITripCS2340/jquery-ui/jquery-ui.min.css" rel="stylesheet">
        <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=<%=JSPStringConstants.GOOGLE_API_KEY%>"></script>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places"></script>
        <script src="/ITripCS2340/jquery-ui/external/jquery/jquery.js"></script>
        <script src="/ITripCS2340/jquery-ui/jquery-ui.min.js"></script>
        <script type="text/javascript">
            var selectedUI, geocoder, map, infowindow, selectedMarker;
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
                });
                $("#SaveHomeBase").click(saveItineraries);
                $("#SavePlaces").click(saveItineraries);
                $("#DeletePoint").click(function()
                {
                    selectedUI.remove();
                });
                $("#AddPoint").click(function()
                {
                    if(selectedMarker!==undefined)
                    {
                        var newDiv = $("<div/>");
                        var newDescription = $("<div/>");
                        var currentBusiness = selectedMarker;
                        newDescription.text(currentBusiness.name+" Start Time: " + getTimeFormat(document.getElementById('StartTimeEvent').value) +
                            " End Time: " + getTimeFormat(document.getElementById('EndTimeEvent').value));
                        var newPlace = $("<div type = hidden/>");
                        newPlace.val({
                              "<%=JSPStringConstants.STARTTIME%>": document.getElementById('StartTimeEvent').value,
                              "<%=JSPStringConstants.ENDTIME%>": document.getElementById('EndTimeEvent').value,
                              "<%=JSPStringConstants.INTEREST_PLACE%>": "${InterestPlace}",
                              "<%=JSPStringConstants.BUSINESS_PARAM%>": JSON.stringify(selectedMarker)
                            });
                        newDiv.append(newDescription);
                        newDiv.append(newPlace);
                        $("#ItinerariesBox").append(newDiv);
                    }
                });
                createMap();
            }
            
            function getTimeFormat(time)
            {
                var hours=Math.floor(time/100);
                var minutes=Math.floor(time%100);
                if(minutes<10)
                    minutes="0"+minutes;
                return hours+":"+minutes;
            }
            
            function createMap()
            {
                geocoder.geocode({'address': "${CentralLocation}"}, 
                    function (results, status)
                    {
                        if (status === google.maps.GeocoderStatus.OK)
                        {
                            var centralLatLng=results[0].geometry.location;
                            infowindow = new google.maps.InfoWindow();
                            var mapOptions = {
                                center: centralLatLng,
                                zoom: 11,
                                mapTypeId: google.maps.MapTypeId.ROADMAP
                            };
                            map = new google.maps.Map(document.getElementById("map_canvas"), mapOptions);
                            makeCentralMarker(centralLatLng, map, infowindow);
                            makeMarkers(map, infowindow);
                        }
                    }
                );
            }
            function makeCentralMarker(centralLatLng, map, infowindow)
            {
                var marker = new google.maps.Marker(
                {
                    map: map,
                    position: centralLatLng,
                    title: "${CentralLocation}",
                    icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
                });
                google.maps.event.addListener(marker, 'click', 
                    function() 
                    {
                        infowindow.setContent("${CentralLocation}");
                        infowindow.open(map,marker);
                    }
                );
            }
            
            function makeMarkers(map, infowindow)
            {
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
                                    markersWithoutFriends(map, infowindow);
                                else 
                                    markersWithFriends(map, infowindow, list);
                            });
                        }, {scope: 'user_friends '});
                    }
                    else
                        markersWithoutFriends(map, infowindow);
                });
            }
            
            function markersWithoutFriends(map, infowindow)
            {
                var business, description;
                var businesses=${Businesses};
                for(var i=0; i<businesses.length;i++)
                {
                    business = businesses[i];
                    description= business.name + " <br>" + business.formatted_address + " <br>" +
                          business.formatted_phone_number + " <br><a href=\"" + business.url + "\">" + business.url + "</a> <br>Rating: " + business.rating;
                    createIndividualMarker(business, description, map, infowindow);
                }
            }
            
            function markersWithFriends(map, infowindow, list)
            {
                
                var business, description, friendString;
                var businesses=${Businesses};
                for(var i=0; i<businesses.length;i++)
                {
                    business = businesses[i];
                    friendString=checkReviews(list, business);
                    if(friendString==="")
                    {
                        description= business.name + " <br>" + business.formatted_address + " <br>" +
                            business.formatted_phone_number + " <br><a href=\"" + business.url + "\">" + business.url + "</a> <br>Rating: " + business.rating;
                    }
                    else
                    {
                        description= business.name + " <br>" + business.formatted_address + " <br>" +
                            business.formatted_phone_number + " <br><a href=\"" + business.url + "\">" + business.url + "</a> <br>Rating: " + business.rating+
                            "<br>"+friendString;
                    }
                    createIndividualMarker(business, description, map, infowindow);
                }
            }
            
            function checkReviews(list, business)
            {
                var friend="", friendsList="";
                var reviews=business.reviews;
                for(var f=0;f<list.length;f++)
                {
                    friend=list[f].name;
                    for (var r=0;r<reviews.length;r++)
                    {
                        if(reviews[r].author_name === friend)
                            friendsList+=friend+", ";
                    }
                }
                if(friendsList!=="")
                    return ("Friends who wrote reviews: "+friendsList);
                return friendsList;
            }
            
            function createIndividualMarker(business, description, map, infowindow){
                var marker = new google.maps.Marker(
                {
                    map: map,
                    position: business.geometry.location,
                    title: business.name
                });
                google.maps.event.addListener(marker, 'click', 
                    function() 
                    {
                        infowindow.setContent(description);
                        infowindow.open(map,marker);
                        selectedMarker=business;
                    }
                );
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
                $('#CentralLocation').val("${CentralLocation}");
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
            <div id="map_canvas"></div>
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
        <form action="/ITripCS2340/Map/" method="post">
            <input type="submit" value="Go back" name="<%=JSPStringConstants.GO_TO_MAP_FLAG%>" class="clickButton"/>
            <input type="submit" value="Go to Main" name="<%=JSPStringConstants.GO_BACK_FLAG%>" class="clickButton2"/>
        </form>
        <form action="/ITripCS2340/Map/" method="post">              
            <input type="submit" id = "SaveHomeBase" name="<%=JSPStringConstants.SAVE_HOME_BASE_FLAG%>" value="Save Home Base" />
            <input type="submit" id = "SavePlaces" name="<%=JSPStringConstants.SAVEPLACES%>" value="Save" />
            <input type="hidden" id = "itinerary" name="<%=JSPStringConstants.ITINERARY_PARAM%>" value="" />
            <input type="hidden" id = "InterestPlace" name="<%=JSPStringConstants.INTEREST_PLACE%>" value="" />
            <input type="hidden" id = "CentralLocation" name="<%=JSPStringConstants.CENTRAL_LOCATION%>" value="" />
            <input type="hidden" id = "Businesses" name="<%=JSPStringConstants.BUSINESSES%>" value="" />
        </form>
        <input type="button" value="Add" name = "<%=JSPStringConstants.ADDPOINT%>" id="AddPoint"/>
        <input type="button" value="Delete" name = "<%=JSPStringConstants.DELETEPOINT%>" id="DeletePoint"/>
        <div id="itineraryTitle">Itinerary</div>
        <div class="table-responsive" id = "ItinerariesBox"></div>
    </body>
</html>