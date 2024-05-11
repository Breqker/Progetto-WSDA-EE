package com.example.progettowsdaee;

public class Impianto {
    private String idImpianto;
    private String descrizione;
    private double latitudine;
    private double longitudine;

    // Costruttore
    public Impianto(String idImpianto, String descrizione, double latitudine, double longitudine) {
        this.idImpianto = idImpianto;
        this.descrizione = descrizione;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    // Metodi getter e setter
    public String getIdImpianto() {
        return idImpianto;
    }

    public void setIdImpianto(String idImpianto) {
        this.idImpianto = idImpianto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
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

    // Metodo toString per la rappresentazione testuale dell'oggetto
    @Override
    public String toString() {
        return "Impianto{" +
                "idImpianto='" + idImpianto + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                '}';
    }

}
