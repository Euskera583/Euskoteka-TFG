package com.euskoteka.euskoteka_api.dto;

public class MultimediaDTO {
    private Long id;
    private String discoteca;
    private String descripcion;
    private String anio;
    private String url;
    private String urlCalidad;

    public MultimediaDTO() {}

    public MultimediaDTO(Long id, String discoteca, String descripcion,
                         String anio, String url, String urlCalidad){
        this.id=id;
        this.discoteca=discoteca;
        this.descripcion=descripcion;
        this.anio=anio;
        this.url=url;
        this.urlCalidad=urlCalidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiscoteca() {
        return discoteca;
    }

    public void setDiscoteca(String discoteca) {
        this.discoteca = discoteca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlCalidad() {
        return urlCalidad;
    }

    public void setUrlCalidad(String urlCalidad) {
        this.urlCalidad = urlCalidad;
    }
}
