package com.euskoteka.euskoteka_api.dto;

import java.util.List;

public class SesionResumenDTO {
    private Long idSesion;
    private String nombreSesion;
    private String fechaSesion;
    private List<String> djs;
    private String caratula;

    public SesionResumenDTO(Long idSesion, String nombreSesion, String fechaSesion, List<String> djs, String caratula) {
        this.idSesion = idSesion;
        this.nombreSesion = nombreSesion;
        this.fechaSesion = fechaSesion;
        this.djs = djs;
    }

    public Long getIdSesion() {
        return idSesion;
    }

    public String getNombreSesion() {
        return nombreSesion;
    }

    public String getFechaSesion() {
        return fechaSesion;
    }

    public List<String> getDjs() {
        return djs;
    }

    public String getCaratula() {
        return caratula;
    }
}
