package com.euskoteka.euskoteka_api.dto;

public class ResidenciaDTO {
    private Long id;
    private String nombreDj;
    private String fechaInicio;
    private String fechaFin;
    private String comentariosResi;

    public ResidenciaDTO(){}

    public ResidenciaDTO(Long id, String nombreDj, String fechaInicio, String fechaFin, String comentariosResi) {
        this.id = id;
        this.nombreDj = nombreDj;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.comentariosResi = comentariosResi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
