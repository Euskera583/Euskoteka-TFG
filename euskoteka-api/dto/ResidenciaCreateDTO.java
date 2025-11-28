package com.euskoteka.euskoteka_api.dto;

public class ResidenciaCreateDTO {

    private String nombreDj;
    private String fechaInicio;
    private String fechaFin;
    private String comentariosResi;

    public String getNombreDj() {
        return nombreDj;
    }

    public void setNombreDj(String nombreDj) {
        this.nombreDj = nombreDj;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getComentariosResi() {
        return comentariosResi;
    }

    public void setComentariosResi(String comentariosResi) {
        this.comentariosResi = comentariosResi;
    }
}
