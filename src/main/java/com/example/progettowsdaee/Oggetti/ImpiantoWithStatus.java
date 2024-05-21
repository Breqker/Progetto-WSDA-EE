package com.example.progettowsdaee.Oggetti;


public class ImpiantoWithStatus extends Impianto {
    private boolean isActive;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
