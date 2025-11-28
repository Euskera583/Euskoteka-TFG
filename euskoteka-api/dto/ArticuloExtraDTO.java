package com.euskoteka.euskoteka_api.dto;

public class ArticuloExtraDTO {

    private Long id;
    private String nombre;
    private String tipo;
    private String foto;
    private String texto;
    private String subtitulo;

    public ArticuloExtraDTO() {}

    public ArticuloExtraDTO(Long id, String nombre, String tipo, String foto, String texto, String subtitulo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.foto = foto;
        this.texto = texto;
        this.subtitulo = subtitulo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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