<%@ page import="edu.gatech.cs2340.ITripCS2340.Controller.JSPStringConstants"%>
       <!DOCTYPE html>
       <html>
       <head>
           <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
           <link href="/ITripCS2340/<%=JSPStringConstants.MAP_CSS%>" rel="stylesheet" type="text/css">
           <script type="text/javascript"
                   src="http://maps.googleapis.com/maps/api/js?key=<%=JSPStringConstants.GOOGLE_API_KEY%>">
           </script>
           <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places"></script>
           <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
           <script type="text/javascript">
           var placeSearch, autocompleteCentralLocation,map, directionsService,
               directionsDisplay,centralLocationMarker,centralLocationLngLat;


             function initialize() {
               var pointOfInterets = ${Businesses};
               var centralLocation = ${CentralLocation};
               directionsService = new google.maps.DirectionsService();
               directionsDisplay = new google.maps.DirectionsRenderer();
               var mapOptions = {
                 center: new google.maps.LatLng(33.748995,-84.387982),
                 zoom: 8,
                 mapTypeId: google.maps.MapTypeId.ROADMAP
                <!--ROADMAP SATELLITE HYBRID TERRAIN-->
               };
               var infowindow = new google.maps.InfoWindow();
               map = new google.maps.Map(document.getElementById("map_canvas"),
                   mapOptions);
               directionsDisplay.setMap(map);


                    geocoder = new google.maps.Geocoder();
                    (function(centralLocation) {
                        geocoder.geocode(
                        { 'address': centralLocation[0].location},
                        function(results, status) {
                            if (status == google.maps.GeocoderStatus.OK) {
                              var marker = new google.maps.Marker({
                                  map: map,
                                  position: results[0].geometry.location,
                                  title: centralLocation[0].location,
                                  icon: 'http://maps.google.com/mapfiles/ms/icons/blue-dot.png'
                              });
                            google.maps.event.addListener(marker, 'click', function() {
                                     infowindow.setContent(centralLocation[0].location);
                                     infowindow.open(map,marker);
                                   });
                              centralLocationLngLat = results[0].geometry.location;
                            }
                        });

                    })(centralLocation);


                  // For each place, get the icon, place name, and location.
                  for (var i = 0, place; place = pointOfInterets[i]; i++) {


                    var businessName = place.name;
                    var description = place.name + "\n" + place.formatted_address + "\n" +
                        place.formatted_phone_number + "\n" + place.url + "\n" + place.rating;
                    (function(businessName) {
                    
                              var marker = new google.maps.Marker({
                                  map: map,
                                  position: place.geometry.location,
                                  title: businessName
                              });
                              google.maps.event.addListener(marker, 'click', function(place) {
                                   infowindow.setContent(description);
                                   infowindow.open(map,marker);
                                  var start = centralLocationLngLat;
                                  var end = place.latLng;
                                  var selectedMode = document.getElementById('mode').value;
                                  var request = {
                                        origin: start,
                                        destination: end,
                                        travelMode: google.maps.TravelMode[selectedMode]
                                    };

                                    // Route the directions and pass the response to a
                                    // function to create markers for each step.
                                    directionsService.route(request, function(response, status) {
                                      if (status == google.maps.DirectionsStatus.OK) {
                                        directionsDisplay.setDirections(response);
                                      }
                                    });
                              });



                              //pointOfInterets.push(marker);
                         
                    
                    })(businessName);
                   }
             }

             function geolocate() {
               if (navigator.geolocation) {
                 navigator.geolocation.getCurrentPosition(function(position) {
                   var geolocation = new google.maps.LatLng(
                       position.coords.latitude, position.coords.longitude);
                   autocompleteCentralLocation.setBounds(new google.maps.LatLngBounds(geolocation,
                       geolocation));
                 });
               }
             }


           </script>
       </head>
       <body onload="initialize()">
           <div id="Frame">
               <div id="map_canvas"></div>
               <div id="PreferenceArea">
                   <div id="TravelModePanel">
                       <div>Mode of Travel: </div>
                       <select id="mode">
                         <option value="DRIVING">Driving</option>
                         <option value="WALKING">Walking</option>
                         <option value="BICYCLING">Bicycling</option>
                         <option value="TRANSIT">Transit</option>
                       </select>
                   </div>
               </div>
           </div>
           <form action="/ITripCS2340/Map/CentralLocation.jsp">
                <input type="submit" value="Go back!" name="Preferences" id="Preferences" class="clickButton"/>
           </form>
            <form action="/ITripCS2340/Main.jsp">
                <input type="submit" value="Acoount" name="Preferences" id="Preferences" class="clickButton2"/>
           </form>
       </body>
       </html>