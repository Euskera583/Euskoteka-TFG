package com.euskoteka.euskoteka_api.dto;

public class CancionDTO {
    private Long id;
    private String artista;
    private String nombreTema;
    private Integer anio;
    private String genero; //solo el nombre del genro
    private String urlYt;

    public CancionDTO(Long id, String artista, String nombreTema, Integer anio, String genero, String urlYt) {
        this.id = id;
        this.artista = artista;
        this.nombreTema = nombreTema;
        this.anio = anio;
        this.genero = genero;
        this.urlYt = urlYt;
    }

    public CancionDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getNombreTema() {
        return nombreTema;
    }

    public void setNombreTema(String nombreTema) {
        this.nombreTema = nombreTema;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getUrlYt() {
        return urlYt;
    }

    public void setUrlYt(String urlYt) {
        this.urlYt = urlYt;
    }
}
