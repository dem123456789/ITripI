<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="edu.gatech.cs2340.ITripCS2340.Controller.JSPStringConstants"%>
<%@page import="edu.gatech.cs2340.ITripCS2340.Controller.SharedServletMethods"%>
<%String existingItineraries= (String)SharedServletMethods.getItinerary(request); %>
<%String homeBase= (String)SharedServletMethods.getCentralLocation(request); %>
<!DOCTYPE html>
<html>
    <head>
        <title>Your Itinerary</title>
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
        <link href="/ITripCS2340/<%=JSPStringConstants.ROUTE_CSS%>" rel="stylesheet" type="text/css">
        <link href="/ITripCS2340/jquery-ui/jquery-ui.min.css" rel="stylesheet">
        <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=<%=JSPStringConstants.GOOGLE_API_KEY%>"></script>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places"></script>
        <script src="/ITripCS2340/jquery-ui/external/jquery/jquery.js"></script>
        <script src="/ITripCS2340/jquery-ui/jquery-ui.min.js"></script>
        <script type="text/javascript">
            var markers, selectedUI, HomeBase, directionsService, directionsDisplay, geocoder, map, infowindow;
            function initialize() 
            {
                geocoder=new google.maps.Geocoder();
                directionsService = new google.maps.DirectionsService();
                directionsDisplay = new google.maps.DirectionsRenderer();
                var existingItineraries = <%=existingItineraries%>;
                HomeBase = "<%=homeBase%>";
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
                        stop: function(event, ui) 
                        {
                            selectedUI = ui.item[0];
                        }
                    });  
                });
                $("#SavePlaces").click(saveItineraries);
                $("#DeletePoint").click(function()
                {
                    selectedUI.remove();
                    for (var i = 0; i < markers.length; i++) 
                    {
                        markers[i].setMap(null);
                    }
                    makeMarkers(map, infowindow)
                });
                $("#SharePoint").click(Login);
                $("#DisplayRoute").click(function()
                {
                    if($("#ItinerariesBox").children().length!=0)
                    {
                        directionsDisplay.setMap(map) 
                        hideMarkers();
                        displayRoutes();
                    }
                });
                $("#HideRoute").click(function()
                {
                    if($("#ItinerariesBox").children().length!=0)
                    {
                        showMarkers();
                        directionsDisplay.setMap(null)                     
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
            
            function Login ()
            {
                FB.getLoginStatus(function(response) 
                {
                    if (response.status === 'connected') {
                        FB.login(function()
                        {
                            FB.api('/me/feed', 'post', {message: Message()});
                        }, {scope: 'publish_actions'});
                    }
                });
            }
           
            function Message ()
            {
                var itinerary=$("#ItinerariesBox").children();
                var message="";
                var event={};
                var business={};
                message="Itrip Itinerary for "+HomeBase+": ";
                for(var i=0; i<itinerary.length;i++)
                {
                        event=itinerary[i].children[1].value;
                        business = JSON.parse(event.<%=JSPStringConstants.BUSINESS_PARAM%>);
                        var a=getTimeFormat(event.<%=JSPStringConstants.STARTTIME%>);
                        message = message + '\n' + business.name +" from " + getTimeFormat(event.<%=JSPStringConstants.STARTTIME%>) + 
                        " to " + getTimeFormat(event.<%=JSPStringConstants.ENDTIME%>);
                }
                return message;
            }
           
            function createMap()
            {
                geocoder.geocode({'address': HomeBase}, 
                    function (results, status)
                    {
                        if (status == google.maps.GeocoderStatus.OK)
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
                            directionsDisplay.setMap(map);
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
                    title: HomeBase,
                    icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
                });
                google.maps.event.addListener(marker, 'click', 
                    function() 
                    {
                        infowindow.setContent(HomeBase);
                        infowindow.open(map,marker);
                    }
                );
            }
            
            function makeMarkers(map, infowindow)
            {
                var business, description;
                var itinerary=$("#ItinerariesBox").children();
                markers=new Array;
                for(var i=0; i<itinerary.length;i++)
                {
                    business = JSON.parse(itinerary[i].children[1].value.<%=JSPStringConstants.BUSINESS_PARAM%>);
                    description= business.name + " <br>" + business.formatted_address + " <br>" +
                          business.formatted_phone_number + " <br><a href=\"" + business.url + "\">" + business.url + "</a> <br>Rating: " + business.rating;
                    createIndividualMarker(i,business, description, map, infowindow);
                }
            }
            
            function createIndividualMarker(i,business, description, map, infowindow){
                markers[i] = new google.maps.Marker(
                {
                    map: map,
                    position: business.geometry.location,
                    title: business.name
                });
                google.maps.event.addListener(markers[i], 'click', 
                    function() 
                    {
                        infowindow.setContent(description);
                        infowindow.open(map,markers[i]);
                    }
                );
            }
            
            function hideMarkers()
            {
                for (var i = 0; i < markers.length; i++) 
                {
                    markers[i].setMap(null);
                }
            }
            
            function showMarkers()
            {
                for (var i = 0; i < markers.length; i++) 
                {
                    markers[i].setMap(map);
                }
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
            }          

            function displayRoutes()
            {  
                geocoder.geocode({'address': HomeBase},
                    function(results, status)
                    {
                        if (status == google.maps.GeocoderStatus.OK)
                        {
                            var start = results[0].geometry.location;
                            var itinerary = getPlacesToAddToRoute();
                            if(itinerary.length==0)
                            {
                                directionsDisplay.setMap(null)
                                return;
                            }
                            var waypts = [];
                            var selectedMode = document.getElementById('mode').value;
                            for (var i = 0; i < itinerary.length-1; i++)
                            {
                                var business = itinerary[i];
                                waypts.push(
                                {
                                    location: new google.maps.LatLng(business.geometry.location.lat, business.geometry.location.lng),
                                    stopover:true
                                });
                            }
                            var request = {
                                origin: start,
                                destination: new google.maps.LatLng(itinerary[itinerary.length-1].geometry.location.lat, itinerary[itinerary.length-1].geometry.location.lng),
                                waypoints: waypts,
                                optimizeWaypoints: true,
                                travelMode: google.maps.TravelMode[selectedMode]
                            };

                            // Route the directions and pass the response to a
                            // function to create markers for each step.
                            directionsService.route(request, function(response, status)
                            {
                                if (status == google.maps.DirectionsStatus.OK) 
                                {
                                    directionsDisplay.setDirections(response);
                                }
                            });
                        }
                    }
                );
            }
            
            function getPlacesToAddToRoute()
            {
                var startTime, endTime;
                var itinerary=$("#ItinerariesBox").children();
                var arr=[];
                for(var i=0; i<itinerary.length;i++)
                {
                    startTime = itinerary[i].children[1].value.<%=JSPStringConstants.STARTTIME%>;
                    endTime = itinerary[i].children[1].value.<%=JSPStringConstants.ENDTIME%>;
                    if(document.getElementById('StartTime').value<=endTime&&document.getElementById('EndTime').value>=startTime)
                        arr.push(JSON.parse(itinerary[i].children[1].value.<%=JSPStringConstants.BUSINESS_PARAM%>));
                }
                return arr;
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
                    <div id="TravelModePanel">
                        <div>Mode of Travel</div>
                        <select id="mode">
                            <option value="DRIVING">Driving</option>
                            <option value="WALKING">Walking</option>
                            <option value="BICYCLING">Bicycling</option>
                            <option value="TRANSIT">Transit</option>
                        </select>
                    </div>
            </div>
        </div>
        <div>
            <div id="Legend">Enter the Time frame <br>for your route<br></div>
            <div id="StartTimeTitle">Start Time</div>
            <select  id = "StartTime" name = "<%=JSPStringConstants.STARTTIME%>">
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
            <div id="EndTimeTitle">End Time</div>
            <select  id = "EndTime" name = "<%=JSPStringConstants.ENDTIME%>">
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
        <form action="/ITripCS2340/DisplayItinerary/" method="post">
            <input type="submit" value="Go to Main" name="<%=JSPStringConstants.GO_BACK_FLAG%>" class="clickButton2"/>
            <input type="submit" id = "SavePlaces" name="<%=JSPStringConstants.SAVEPLACES%>" value="Save" />
            <input type="hidden" id = "itinerary" name="<%=JSPStringConstants.ITINERARY_PARAM%>" value="" />
        </form>
        <input type="button" value="Hide Route" name = "HideRoute" id="HideRoute">
        <input type="button" value="Display Route" name = "DisplayRoute" id="DisplayRoute">
        <input type="button" value="Delete" name = "<%=JSPStringConstants.DELETEPOINT%>" id="DeletePoint">
        <input type="button" value="Share" name = "<%=JSPStringConstants.SHAREPOINT%>" id="SharePoint">
        <div id="itineraryTitle">Itinerary</div>
        <div class="table-responsive" id = "ItinerariesBox"></div>
    </body>
</html>
