package com.example.progettowsdaee.Oggetti;

public class Impianto {
    private String idImpianto;
    private String idPalinsesto;
    private double latitudine;
    private double longitudine;
    private boolean stato; // Nuovo campo

    // Getter e Setter
    public String getIdImpianto() {
        return idImpianto;
    }

    public void setIdImpianto(String idImpianto) {
        this.idImpianto = idImpianto;
    }

    public String getIdPalinsesto() {
        return idPalinsesto;
    }

    public void setIdPalinsesto(String idPalinsesto) {
        this.idPalinsesto = idPalinsesto;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    public boolean getStato() {
        return stato;
    }

    public void setStato(boolean stato) {
        this.stato = stato;
    }

    @Override
    public String toString() {
        return "Impianto{" +
                "idImpianto='" + idImpianto + '\'' +
                ", idPalinsesto='" + idPalinsesto + '\'' +
                ", latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                ", stato=" + stato +
                '}';
    }
}
