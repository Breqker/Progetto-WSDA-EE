document.addEventListener("DOMContentLoaded", function() {
    var map = L.map('map').setView([38.1157, 13.3615], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    console.log("ImpiantiWithStatus JSON:", impiantiWithStatusJson);

    impiantiWithStatusJson.forEach(function(impianto_ws) {
        var iconUrl = impianto_ws.isActive ? 'immagini/switch-on.png' : 'immagini/switch-off.png';
        var icon = L.icon({
            iconUrl: iconUrl,
            iconSize: [70, 70],
            iconAnchor: [32, 64],
            popupAnchor: [0, -16]
        });

        var marker = L.marker([impianto_ws.latitudine, impianto_ws.longitudine], { icon: icon })
            .addTo(map);

        function getAddress(lat, lon, callback) {
            var url = `https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=${lat}&lon=${lon}`;

            fetch(url)
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! Status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    callback(data);
                })
                .catch(error => console.error('Errore:', error));
        }

        getAddress(impianto_ws.latitudine, impianto_ws.longitudine, function(addressData) {
            var address = addressData.display_name || 'Indirizzo non disponibile';
            var popupContent = '<p>ID Impianto: ' + impianto_ws.idImpianto + '</p>' +
                '<p>ID Palinsesto: ' + impianto_ws.idPalinsesto + '</p>' +
                '<p>Indirizzo: ' + address + '</p>';
            marker.bindPopup(popupContent);
        });
    });
});
