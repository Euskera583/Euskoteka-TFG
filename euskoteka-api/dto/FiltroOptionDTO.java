package com.euskoteka.euskoteka_api.dto;

//este DTO representa la opción que se elige en los filtros del front
public class FiltroOptionDTO {
    private String value;
    private boolean compatible;

    public FiltroOptionDTO(String value, boolean compatible) {
        this.value = value;
        this.compatible = compatible;
    }

    // Nuevo constructor solo con value
    public FiltroOptionDTO(String value) {
        this.value = value;
        this.compatible = true;
    }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    public boolean isCompatible() { return compatible; }
    public void setCompatible(boolean compatible) { this.compatible = compatible; }
}
