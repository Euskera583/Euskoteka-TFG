package com.euskoteka.euskoteka_api.dto;

import java.time.LocalTime;
import java.util.List;

public class SesionDTO {
    private Long id;
    private String nombreSesion;
    private String infoSesion;
    private String caratulaFoto;
    private String caratulaFotoCalidad;
    private String urlSesion;
    private String conservacionSesion;
    private String fechaSesion;
    private String medioSesion;
    private String duracionSesion;
    private String duennoMedio;
    private Short ineditaSesion;
    private Boolean digitalizadoSesion;

    //Información extra
    private String nombreDiscoteca;
    private List<String> djs; //solo nombres de Djs

    public SesionDTO() {}

    public SesionDTO(Long id, String nombreSesion, String infoSesion, String caratulaFoto,
                     String caratulaFotoCalidad, String urlSesion, String conservacionSesion,
                     String fechaSesion, String medioSesion,
                     String duennoMedio, Short ineditaSesion, Boolean digitalizadoSesion,
                     String nombreDiscoteca, List<String> djs) {
        this.id = id;
        this.nombreSesion = nombreSesion;
        this.infoSesion = infoSesion;
        this.caratulaFoto = caratulaFoto;
        this.caratulaFotoCalidad = caratulaFotoCalidad;
        this.urlSesion = urlSesion;
        this.conservacionSesion = conservacionSesion;
        this.fechaSesion = fechaSesion;
        this.medioSesion = medioSesion;
        this.duennoMedio = duennoMedio;
        this.ineditaSesion = ineditaSesion;
        this.digitalizadoSesion = digitalizadoSesion;
        this.nombreDiscoteca = nombreDiscoteca;
        this.djs = djs;
    }
    //getters y setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreSesion() {
        return nombreSesion;
    }

    public void setNombreSesion(String nombreSesion) {
        this.nombreSesion = nombreSesion;
    }

    public String getInfoSesion() {
        return infoSesion;
    }

    public void setInfoSesion(String infoSesion) {
        this.infoSesion = infoSesion;
    }

    public String getCaratulaFoto() {
        return caratulaFoto;
    }

    public void setCaratulaFoto(String caratulaFoto) {
        this.caratulaFoto = caratulaFoto;
    }

    public String getCaratulaFotoCalidad() {
        return caratulaFotoCalidad;
    }

    public void setCaratulaFotoCalidad(String caratulaFotoCalidad) {
        this.caratulaFotoCalidad = caratulaFotoCalidad;
    }

    public String getUrlSesion() {
        return urlSesion;
    }

    public void setUrlSesion(String urlSesion) {
        this.urlSesion = urlSesion;
    }

    public String getConservacionSesion() {
        return conservacionSesion;
    }

    public void setConservacionSesion(String conservacionSesion) {
        this.conservacionSesion = conservacionSesion;
    }

    public String getFechaSesion() {
        return fechaSesion;
    }

    public void setFechaSesion(String fechaSesion) {
        this.fechaSesion = fechaSesion;
    }

    public String getMedioSesion() {
        return medioSesion;
    }

    public void setMedioSesion(String medioSesion) {
        this.medioSesion = medioSesion;
    }

    public String getDuracionSesion() {
        return duracionSesion;
    }

    public void setDuracionSesion(String duracionSesion) {
        this.duracionSesion = duracionSesion;
    }

    public String getDuennoMedio() {
        return duennoMedio;
    }

    public void setDuennoMedio(String duennoMedio) {
        this.duennoMedio = duennoMedio;
    }

    public Short getIneditaSesion() {
        return ineditaSesion;
    }

    public void setIneditaSesion(Short ineditaSesion) {
        this.ineditaSesion = ineditaSesion;
    }

    public Boolean getDigitalizadoSesion() {
        return digitalizadoSesion;
    }

    public void setDigitalizadoSesion(Boolean digitalizadoSesion) {
        this.digitalizadoSesion = digitalizadoSesion;
    }

    public String getNombreDiscoteca() {
        return nombreDiscoteca;
    }

    public void setNombreDiscoteca(String nombreDiscoteca) {
        this.nombreDiscoteca = nombreDiscoteca;
    }

    public List<String> getDjs() {
        return djs;
    }

    public void setDjs(List<String> djs) {
        this.djs = djs;
    }
}
