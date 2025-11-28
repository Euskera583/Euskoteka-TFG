package com.euskoteka.euskoteka_api.dto;

public class TracklistDTO {
    private Long numeroPista;
    private Long idCancion;
    private String artista;
    private String nombreTema;

    public TracklistDTO(Long numeroPista, Long idCancion, String artista, String nombreTema) {
        this.numeroPista = numeroPista;
        this.idCancion = idCancion;
        this.artista = artista;
        this.nombreTema = nombreTema;
    }

    public TracklistDTO() {}

    public Long getNumeroPista() {
        return numeroPista;
    }

    public void setNumeroPista(Long numeroPista) {
        this.numeroPista = numeroPista;
    }

    public Long getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(Long idCancion) {
        this.idCancion = idCancion;
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
}
