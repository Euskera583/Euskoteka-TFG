package com.euskoteka.euskoteka_api.dto;

public class ArticuloExtraListadoDTO {

    private Long id;
    private String nombre;
    private String tipo;
    private String foto;

    public ArticuloExtraListadoDTO() {}

    public ArticuloExtraListadoDTO(Long id, String nombre, String tipo, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.foto = foto;
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
}
