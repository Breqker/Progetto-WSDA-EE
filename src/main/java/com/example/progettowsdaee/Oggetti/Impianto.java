package com.example.progettowsdaee.Oggetti;

public class Impianto {
    private String idImpianto;
    private String idPalinsesto;
    private double latitudine;
    private double longitudine;


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



    @Override
    public String toString() {
        return "Impianto{" +
                "id_impianto='" + idImpianto + '\'' +
                ", ref_palinsesto='" + idPalinsesto + '\'' +
                ", latitudine=" + latitudine +
                ", longitudine=" + longitudine +
                '}';
    }

}
