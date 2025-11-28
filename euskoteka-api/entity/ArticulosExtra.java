package com.euskoteka.euskoteka_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "articulos_extra", schema = "euskoteka")
public class ArticulosExtra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_art", columnDefinition = "int UNSIGNED not null")
    private Long idArt;

    @Column(name = "nombre_art", nullable = false, length = 45)
    private String nombreArt;

    @Column(name = "subtitulo", nullable = false, length = 255)
    private String subtitulo;

    @Column(name = "tipo_art", length = 45)
    private String tipoArt;

    @Lob
    @Column(name = "foto", length = 255)
    private String foto;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String texto;

    //getters y setters

    public Long getIdArt() {
        return idArt;
    }

    public void setIdArt(Long idArt) {
        this.idArt = idArt;
    }

    public String getNombreArt() {
        return nombreArt;
    }

    public void setNombreArt(String nombreArt) {
        this.nombreArt = nombreArt;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getTipoArt() {
        return tipoArt;
    }

    public void setTipoArt(String tipoArt) {
        this.tipoArt = tipoArt;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}
