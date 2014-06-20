<%@ page import="edu.gatech.cs2340.ITripCS2340.Controller.JSPStringConstants"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <link href="/ITripCS2340/<%=JSPStringConstants.MAP_CSS%>" rel="stylesheet" type="text/css">
    <script type="text/javascript"
            src="http://maps.googleapis.com/maps/api/js?key=AIzaSyCUwf97HWBebDwzBoqYvDZ1qirdFcl_yJY">
    </script>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&libraries=places"></script>
    <script type="text/javascript">
    var placeSearch, autocomplete,map;
      function initialize() {
        var mapOptions = {
          center: new google.maps.LatLng(33.748995,-84.387982),
          zoom: 8,
          mapTypeId: google.maps.MapTypeId.ROADMAP
         <!--ROADMAP SATELLITE HYBRID TERRAIN-->
        };
        map = new google.maps.Map(document.getElementById("map_canvas"),
            mapOptions);

        autocomplete = new google.maps.places.Autocomplete(
              (document.getElementById('CentralLocation')),
              { types: ['geocode'] });
          google.maps.event.addListener(autocomplete, 'place_changed', function() {
            fillInAddress();
          });
      }
      function fillInAddress() {
        // Get the place details from the autocomplete object.
        var place = autocomplete.getPlace();
        var marker = new google.maps.Marker({
              position: autocomplete.getBounds().getCenter(),
              map: map,
              title: autocomplete.getPlace().short_name
          });
        map.setZoom(12);
      }
      function geolocate() {
        if (navigator.geolocation) {
          navigator.geolocation.getCurrentPosition(function(position) {
            var geolocation = new google.maps.LatLng(
                position.coords.latitude, position.coords.longitude);
            autocomplete.setBounds(new google.maps.LatLngBounds(geolocation,
                geolocation));
          });
        }
      }
    </script>
</head>
<body onload="initialize()">
    <div id="Frame">
        <div id="map_canvas"></div>
        <div id="locationField">
            <input id="CentralLocation" placeholder="Enter your address"
                 onFocus="geolocate()" type="text"></input>
        </div>
    </div>

    <div id="PreferencesContainer">

    </div>
</body>
</html>