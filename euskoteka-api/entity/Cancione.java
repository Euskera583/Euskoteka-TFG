package com.euskoteka.euskoteka_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "canciones", schema = "euskoteka")
public class Cancione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cancion", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Column(name = "artista", nullable = false, length = 100)
    private String artista;

    @Column(name = "nombre_tema", nullable = false, length = 100)
    private String nombreTema;

    @Column(name = "anio")
    private Integer anio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genero", nullable = true)
    private GenerosMusicale genero;

    @Column(name = "url_yt")
    private String urlYt;

    //getter y setters

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

    public GenerosMusicale getGenero() {
        return genero;
    }

    public void setGenero(GenerosMusicale genero) {
        this.genero = genero;
    }

    public String getUrlYt() {
        return urlYt;
    }

    public void setUrlYt(String urlYt) {
        this.urlYt = urlYt;
    }

}