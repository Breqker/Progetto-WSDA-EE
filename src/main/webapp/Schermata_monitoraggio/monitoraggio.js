document.addEventListener("DOMContentLoaded", function() {
    var map = L.map('map').setView([38.1157, 13.3615], 13); // Imposta la vista della mappa con le nuove coordinate

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    // Logging per verificare il JSON
    console.log("ImpiantiWithStatus JSON:", impiantiWithStatusJson);

    impiantiWithStatusJson.forEach(function(impianto_ws) {
        // Crea un'icona per il marker
        var iconUrl = impianto_ws.active ? 'immagini/switch-on.png' : 'immagini/switch-off.png';
        var icon = L.icon({
            iconUrl: iconUrl,
            iconSize: [70, 70],
            iconAnchor: [32, 64],
            popupAnchor: [0, -16]
        });

        // Crea il marker e aggiungilo alla mappa
        var marker = L.marker([impianto_ws.latitudine, impianto_ws.longitudine], { icon: icon })
            .addTo(map);

        // Crea il contenuto del popup manualmente
        var popupContent = '<p>ID Impianto: ' + impianto_ws.idImpianto + '</p>' +
            '<p>ID Palinsesto: ' + impianto_ws.idPalinsesto + '</p>' +
            '<p>Latitudine: ' + impianto_ws.latitudine + '</p>' +
            '<p>Longitudine: ' + impianto_ws.longitudine + '</p>' +
            '<p>Attivo: ' + impianto_ws.isActive + '</p>';

        // Aggiungi il popup al marker
        marker.bindPopup(popupContent);
    });
});
