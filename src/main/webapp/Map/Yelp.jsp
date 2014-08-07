<%@ page import="edu.gatech.cs2340.ITripCS2340.Controller.JSPStringConstants"%>
<%@ page import="edu.gatech.cs2340.ITripCS2340.Controller.SharedServletMethods"%>
<%String existingItineraries= (String)SharedServletMethods.getItinerary(request); %>
<!DOCTYPE html>
<html>
    <head>
        <title>Yelp Search</title>
        <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
        <link href="/ITripCS2340/<%=JSPStringConstants.YELP_CSS%>" rel="stylesheet" type="text/css">
        <link href="/ITripCS2340/jquery-ui/jquery-ui.min.css" rel="stylesheet">
        <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=<%=JSPStringConstants.GOOGLE_API_KEY%>"></script>
        <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places"></script>
        <script src="/ITripCS2340/jquery-ui/external/jquery/jquery.js"></script>
        <script src="/ITripCS2340/jquery-ui/jquery-ui.min.js"></script>
        <script type="text/javascript">
            var pointOfInterets, selectedUI, geocoder, map, infowindow, autocompleteCentralLocation, centralLocationMarker;
            function initialize() 
            {
                geocoder=new google.maps.Geocoder();
                var existingItineraries = <%=existingItineraries%>;
                pointOfInterets = new Array;
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
                createMap();
                $("#SavePlaces").click(saveItineraries);
                $("#DeletePoint").click(function()
                {
                    selectedUI.remove();
                });
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
                geocoder.geocode({'address': "Atlanta, GA, United States"}, 
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
                                createListeners();
                        }
                    }
                );
            }
            
            function createListeners()
            {
                autocompleteCentralLocation = new google.maps.places.Autocomplete(document.getElementById('CentralLocation'), { types: ['geocode'] });
                google.maps.event.addListener(autocompleteCentralLocation, 'place_changed', 
                    function() 
                    {
                        fillInAddress();
                    });
                var input = document.getElementById('InterestPlace');
                var searchBox = new google.maps.places.SearchBox(input);
                // Listen for the event fired when the user selects an item from the
                // pick list. Retrieve the matching places for that item.
                google.maps.event.addListener(searchBox, 'places_changed',
                        function()
                        {
                            updatePointOfInterest(searchBox, autocompleteCentralLocation.getPlace());
                        });
                google.maps.event.addListener(map, 'bounds_changed', 
                    function() 
                    {
                        var bounds = map.getBounds();
                        searchBox.setBounds(bounds);
                    });
            }
            
            function updatePointOfInterest(searchBox,centralLocation) 
            {
                var searchArea = new google.maps.Circle({
                   center:  centralLocation.geometry.location,
                   radius:  getDistance()
                });
                searchBox.setBounds(searchArea.getBounds());
                var places = searchBox.getPlaces();     
                hideMarkers();
                pointOfInterets = [];
                var place;
                var bounds = new google.maps.LatLngBounds();
                for (var i = 0; i < places.length; i++) 
                {
                    place = places[i];
                    createIndividualMarker(i,place, map, infowindow)
                    bounds.extend(place.geometry.location);
                }
                map.fitBounds(bounds);
             }
             
             function createIndividualMarker(i,place, map, infowindow)
             {
                var image = {
                    url: place.icon,
                    size: new google.maps.Size(71, 71),
                    origin: new google.maps.Point(0, 0),
                    anchor: new google.maps.Point(17, 34),
                    scaledSize: new google.maps.Size(25, 25)
                };
                pointOfInterets[i] = new google.maps.Marker(
                {
                    map: map,
                    icon: image,
                    title: place.name,
                    position: place.geometry.location
                });
                google.maps.event.addListener(pointOfInterets[i], 'click', 
                    function() 
                    {
                        infowindow.setContent(place.name);
                        infowindow.open(map,pointOfInterets[i]);
                    }
                );
            }
            
            function hideMarkers()
            {
                for (var i = 0; i < pointOfInterets.length; i++) 
                {
                    pointOfInterets[i].setMap(null);
                }
            }

            function getDistance()
            {
              var distance = parseFloat(document.getElementById('Distance').value);
              if(isNaN(distance))
              {
                  return 20;
              }
              else
              {
                  return distance;
              }
            }

            function fillInAddress() 
            {
                // Get the place details from the autocomplete object.
                var place = autocompleteCentralLocation.getPlace();
                if(centralLocationMarker==null)
                {
                    centralLocationMarker = new google.maps.Marker({
                        position: place.geometry.location,
                        map: map,
                        title: autocompleteCentralLocation.getPlace().short_name
                    });
                    google.maps.event.addListener(centralLocationMarker, 'click', 
                        function() 
                        {
                            infowindow.setContent(autocompleteCentralLocation.getPlace().short_name);
                            infowindow.open(map,centralLocationMarker);
                        });
                }
                else
                {
                    centralLocationMarker.setPosition(place.geometry.location);
                }
                map.setCenter(place.geometry.location);
                map.setZoom(12);
            }
            
            function geolocate() 
            {
                if (navigator.geolocation) 
                {
                    navigator.geolocation.getCurrentPosition(
                            function(position) 
                            {
                                var geolocation = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                                autocompleteCentralLocation.setBounds(new google.maps.LatLngBounds(geolocation, geolocation));
                            });
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
        </script>
    </head>
    <body onload="initialize()">
        <div id="YelpTitle">Yelp results can't be sorted by price, rating, or hours</div>
        <div id="Frame">
            <div id="map_canvas"></div>
            <form action="/ITripCS2340/Yelp/" method="POST">
                <div id="PreferenceArea">
                    <input id="CentralLocation"
                           name="<%=JSPStringConstants.CENTRAL_LOCATION%>" placeholder="Enter your address"
                           onFocus="geolocate()" type="text"/>
                    <input id="InterestPlace"
                           name="<%=JSPStringConstants.INTEREST_PLACE%>" placeholder="Point of interest"
                           type="text"/>
                    <input id="Distance"
                           name="<%=JSPStringConstants.DISTANCE%>" placeholder="Enter search radius(miles)"
                           onFocus="geolocate()" type="text"/>
                </div>
                <input type="submit" value="Search" name="Preferences" id="Preferences" class="clickButton"/>
                <input type="submit" value="Return to Main" name = "<%=JSPStringConstants.GO_BACK_FLAG%>" id="Preference" class="clickButton2"/>
                <input type="button" value="Delete" name = "<%=JSPStringConstants.DELETEPOINT%>" id="DeletePoint"/>
                <input type="submit" id = "SavePlaces" name="<%=JSPStringConstants.SAVEPLACES%>" value="Save" />
                <input type="hidden" id = "itinerary" name="<%=JSPStringConstants.ITINERARY_PARAM%>" value="" />
            </form>
        </div>
        <div id="itineraryTitle">Itinerary</div>
        <div class="table-responsive" id = "ItinerariesBox"></div>
    </body>
</html>