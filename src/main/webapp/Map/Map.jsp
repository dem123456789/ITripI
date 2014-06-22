<%@ page import="edu.gatech.cs2340.ITripCS2340.Controller.JSPStringConstants"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <link href="/ITripCS2340/<%=JSPStringConstants.MAP_CSS%>" rel="stylesheet" type="text/css">
    <script type="text/javascript"
            src="http://maps.googleapis.com/maps/api/js?key=<%=JSPStringConstants.GOOGLE_APP_KEY%>">
    </script>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places"></script>
    <script type="text/javascript">
    var placeSearch, autocompleteCentralLocation,map, directionsService,
        directionsDisplay,centralLocationMarker,pointOfInterets = [];


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
      function calcRoute() {
        var selectedMode = document.getElementById('mode').value;
        var request = {
            origin: haight,
            destination: oceanBeach,
            // Note that Javascript allows us to access the constant
            // using square brackets and a string value as its
            // "property."
            travelMode: google.maps.TravelMode[selectedMode]
        };
        directionsService.route(request, function(response, status) {
          if (status == google.maps.DirectionsStatus.OK) {
            directionsDisplay.setDirections(response);
          }
        });
      }

    </script>
</head>
<body onload="initialize()">
    <div id="Frame">
        <div id="map_canvas"></div>
        <div id="PreferenceArea">
            <input id="CentralLocation" placeholder="Enter your address"
                 onFocus="geolocate()" type="text"></input>
            <input id="InterestPlace" placeholder="Point of interest"
                          type="text"></input>
            <div id="TravelModePanel">
                <div>Mode of Travel: </div>
                <select id="mode" onchange="calcRoute();">
                  <option value="DRIVING">Driving</option>
                  <option value="WALKING">Walking</option>
                  <option value="BICYCLING">Bicycling</option>
                  <option value="TRANSIT">Transit</option>
                </select>
            </div>
            <input id="Distance" placeholder="Enter search radius(miles)"
                         onFocus="geolocate()" type="text"></input>
        </div>
    </div>
</body>
</html>