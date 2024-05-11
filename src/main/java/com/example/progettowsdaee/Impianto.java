package com.example.progettowsdaee;

public class Impianto {
    private int idImpianto;
    private boolean descrizione;
    private double latitudine;
    private double longitudine;

    // Costruttore
    public Impianto(int idImpianto, boolean descrizione, double latitudine, double longitudine) {
        this.idImpianto = idImpianto;
        this.descrizione = descrizione;
        this.latitudine = latitudine;
        this.longitudine = longitudine;
    }

    // Metodi getter e setter
    public int getIdImpianto() {
        return idImpianto;
    }

    public void setIdImpianto(int idImpianto) {
        this.idImpianto = idImpianto;
    }

    public boolean getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(boolean descrizione) {
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
                ", descrizione_='" + descrizione + '\'' +
                ", latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                '}';
    }

}
