$(document).ready(function() {
    //------------------
    // Google Maps
    //------------------
    let infoWindow = new google.maps.InfoWindow;
    let directionsService = new google.maps.DirectionsService();
    let directionsDisplay = new google.maps.DirectionsRenderer();
    let address = $('#location').val();

    function googleMaps() {
        let mapOptions ={
            center: {
                lat: 29.397,
                lng: -98.5
            },
            zoom: 10
        };
        let map = new google.maps.Map(document.getElementById('map'), mapOptions);

        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                let pos = {
                    lat: position.coords.latitude,
                    lng: position.coords.longitude
                };

                directionsDisplay.setMap(map);
                directionsDisplay.setPanel(document.getElementById('directionsPanel'));
                calcRoute(pos);
            }, function() {
                handleLocationError(true, infoWindow, map.getCenter(), map);
            });
        } else {
            // Browser doesn't support Geolocation
            handleLocationError(false, infoWindow, map.getCenter(), map);
        }
    }

    function calcRoute(pos) {
        let start = new google.maps.LatLng(pos.lat, pos.lng);
        let request = {
            origin: start,
            destination: address,
            travelMode: 'DRIVING'
        };
        directionsService.route(request, function(result, status) {
            if (status == 'OK') {
                directionsDisplay.setDirections(result);
            }
        });
    }

    function handleLocationError(browserHasGeolocation, infoWindow, pos, map) {
        infoWindow.setPosition(pos);
        infoWindow.setContent(browserHasGeolocation ?
            'Error: The Geolocation service failed.' :
            'Error: Your browser doesn\'t support geolocation.');
        infoWindow.open(map);
    }

    googleMaps();
});