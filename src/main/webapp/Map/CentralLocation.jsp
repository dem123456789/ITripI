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
        directionsDisplay,centralLocationMarker,pointOfInterets = [];
        var infowindow = new google.maps.InfoWindow();

      function initialize() {
        directionsService = new google.maps.DirectionsService();
        directionsDisplay = new google.maps.DirectionsRenderer();
        var mapOptions = {
          center: new google.maps.LatLng(33.748995,-84.387982),
          zoom: 8,
          mapTypeId: google.maps.MapTypeId.ROADMAP
         <!--ROADMAP SATELLITE HYBRID TERRAIN-->
        };

        map = new google.maps.Map(document.getElementById("map_canvas"),
            mapOptions);
        directionsDisplay.setMap(map);

        autocompleteCentralLocation = new google.maps.places.Autocomplete(
              (document.getElementById('CentralLocation')),
              { types: ['geocode'] });
          google.maps.event.addListener(autocompleteCentralLocation, 'place_changed', function() {
            fillInAddress();
          });



         var input = /** @type {HTMLInputElement} */(
               document.getElementById('InterestPlace'));

         var searchBox = new google.maps.places.SearchBox(input);

           // [START region_getplaces]
           // Listen for the event fired when the user selects an item from the
           // pick list. Retrieve the matching places for that item.
           google.maps.event.addListener(searchBox, 'places_changed',function(){updatePointOfInterest(searchBox,
            autocompleteCentralLocation.getPlace())});


         // [END region_getplaces]

         // Bias the SearchBox results towards places that are within the bounds of the
         // current map's viewport.
         google.maps.event.addListener(map, 'bounds_changed', function() {
           var bounds = map.getBounds();
           searchBox.setBounds(bounds);
         });
      }
      function updatePointOfInterest(searchBox,centralLocation) {
       var searchArea = new google.maps.Circle({
          center:  centralLocation.geometry.location,
          radius:  getDistance()
       });
       searchBox.setBounds(searchArea.getBounds());
       var places = searchBox.getPlaces();     
//       var placeDetails={
//           results: []
//       };
//       service = new google.maps.places.PlacesService(map); 
//       var delay=1000;//1 seconds
//       for (var i = 0, place; place = places[i]; i++) {
//           var request = {
//                placeId: place.place_id
//            };
//            setTimeout(function(){
//
//            //your code to be executed after 1 seconds
//            },delay);
//            service.getDetails(request, addToArray)
//        }
//        function addToArray(place, status) {
//            if (status == google.maps.places.PlacesServiceStatus.OK) {
//            }
//            placeDetails.results.push(place);
//            placeDetails.results.push(status);
//        }
       $('#PlacesCentralLocation').val(JSON.stringify(places));

       for (var i = 0, marker; marker = pointOfInterets[i]; i++) {
         marker.setMap(null);
       }

       // For each place, get the icon, place name, and location.
       pointOfInterets = [];
       var bounds = new google.maps.LatLngBounds();
       for (var i = 0, place; place = places[i]; i++) {
         var image = {
           url: place.icon,
           size: new google.maps.Size(71, 71),
           origin: new google.maps.Point(0, 0),
           anchor: new google.maps.Point(17, 34),
           scaledSize: new google.maps.Size(25, 25)
         };

         // Create a marker for each place.
         var marker = new google.maps.Marker({
           map: map,
           icon: image,
           title: place.name,
           position: place.geometry.location
         });
         google.maps.event.addListener(marker, 'click', function(place) {
             var start = centralLocation.formatted_address;
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
                infowindow.setContent(place.name);
                infowindow.open(map,marker);
         });

         pointOfInterets.push(marker);

         bounds.extend(place.geometry.location);
        }
       }

      function getDistance(){
        var distance = parseFloat(document.getElementById('Distance').value);
        if(isNaN(distance)){
            return 20;
        }
        else{
            return distance;
        }
      }

      function fillInAddress() {
        // Get the place details from the autocomplete object.
        var place = autocompleteCentralLocation.getPlace();
        if(centralLocationMarker==null){
            centralLocationMarker = new google.maps.Marker({
                  position: place.geometry.location,
                  map: map,
                  title: autocompleteCentralLocation.getPlace().short_name
              });
              google.maps.event.addListener(centralLocationMarker, 'click', function() {
                       infowindow.setContent(autocompleteCentralLocation.getPlace().short_name);
                       infowindow.open(map,centralLocationMarker);
                     });
        }
        else{
            centralLocationMarker.setPosition(place.geometry.location);
        }
        map.setCenter(place.geometry.location);
        map.setZoom(12);
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
                <form action="/ITripCS2340/Preferences/" method="POST">
                <div id="PreferenceArea">
                    <input id=<%=JSPStringConstants.CENTRAL_LOCATION%>
                        name=<%=JSPStringConstants.CENTRAL_LOCATION%> placeholder="Enter your address"
                         onFocus="geolocate()" type="text"/>
                    <input id=<%=JSPStringConstants.INTEREST_PLACE%>
                        name=<%=JSPStringConstants.INTEREST_PLACE%> placeholder="Point of interest"
                                  type="text"/>
                    <input id=<%=JSPStringConstants.DISTANCE%>
                        name=<%=JSPStringConstants.DISTANCE%> placeholder="Enter search radius(miles)"
                                 onFocus="geolocate()" type="text"/>
                    <input type="hidden" id = <%=JSPStringConstants.PlacesFoundInCentralLocation%>
                     name=<%=JSPStringConstants.PlacesFoundInCentralLocation%> value="" >
                    <div >
                       <div id="PriceTitle">Price Range: </div>
                       <select  id=<%=JSPStringConstants.PRICE%> name = <%=JSPStringConstants.PRICE%>>
                         <option value="0">0</option>
                         <option value="1">1</option>
                         <option value="2">2</option>
                         <option value="3">3</option>
                         <option value="4">4</option>
                       </select>
                   </div>
                   <div >
                      <div id="RatingTitle">Rating: </div>
                      <select  id=<%=JSPStringConstants.RATING%> name = <%=JSPStringConstants.RATING%>>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5">5</option>
                      </select>
                    </div>

                    <div >
                      <div id="StartTimeTitle">Start Time: </div>
                      <select  id=<%=JSPStringConstants.STARTTIME%> name = <%=JSPStringConstants.STARTTIME%>>
                        <option value="0">00:00</option>
                        <option value="1">01:00</option>
                        <option value="2">02:00</option>
                        <option value="3">03:00</option>
                        <option value="4">04:00</option>
                        <option value="5">05:00</option>
                        <option value="6">06:00</option>
                        <option value="7">07:00</option>
                        <option value="8">08:00</option>
                        <option value="9">09:00</option>
                        <option value="10">10:00</option>
                        <option value="11">11:00</option>
                        <option value="12">12:00</option>
                        <option value="13">13:00</option>
                        <option value="14">14:00</option>
                        <option value="15">15:00</option>
                        <option value="16">16:00</option>
                        <option value="17">17:00</option>
                        <option value="18">18:00</option>
                        <option value="19">19:00</option>
                        <option value="20">20:00</option>
                        <option value="21">21:00</option>
                        <option value="22">22:00</option>
                        <option value="23">23:00</option>
                      </select>
                    </div>
                    <div >
                      <div id="EndTimeTitle">End Time: </div>
                      <select  id=<%=JSPStringConstants.ENDTIME%> name = <%=JSPStringConstants.ENDTIME%>>
                        <option value="0">00:00</option>
                        <option value="1">01:00</option>
                        <option value="2">02:00</option>
                        <option value="3">03:00</option>
                        <option value="4">04:00</option>
                        <option value="5">05:00</option>
                        <option value="6">06:00</option>
                        <option value="7">07:00</option>
                        <option value="8">08:00</option>
                        <option value="9">09:00</option>
                        <option value="10">10:00</option>
                        <option value="11">11:00</option>
                        <option value="12">12:00</option>
                        <option value="13">13:00</option>
                        <option value="14">14:00</option>
                        <option value="15">15:00</option>
                        <option value="16">16:00</option>
                        <option value="17">17:00</option>
                        <option value="18">18:00</option>
                        <option value="19">19:00</option>
                        <option value="20">20:00</option>
                        <option value="21">21:00</option>
                        <option value="22">22:00</option>
                        <option value="23">23:00</option>
                      </select>
                    </div>

                </div>
                    <input type="submit" value="Go!" name="Preferences" id="Preferences" class="clickButton"/>
                </form>

    </div>
</body>
</html>