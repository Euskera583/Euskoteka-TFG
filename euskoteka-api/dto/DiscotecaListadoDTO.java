package com.euskoteka.euskoteka_api.dto;

import com.euskoteka.euskoteka_api.entity.Discoteca;

public class DiscotecaListadoDTO {

    private Long id;
    private String nombre;
    private String foto;
    private String localidad;
    private String periodoActividad;

    public DiscotecaListadoDTO() {};

    public DiscotecaListadoDTO(Long id, String nombre, String foto,
                               String localidad, String periodoActividad) {

        this.id = id;
        this.nombre = nombre;
        this.foto = foto;
        this.localidad = localidad;
        this.periodoActividad = periodoActividad;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getPeriodoActividad() {
        return periodoActividad;
    }

    public void setPeriodoActividad(String periodoActividad) {
        this.periodoActividad = periodoActividad;
    }
}
