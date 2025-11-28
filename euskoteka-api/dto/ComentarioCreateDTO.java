package com.euskoteka.euskoteka_api.dto;

public class ComentarioCreateDTO {

    private String contenido;
    private String tipoObjetivo;
    private Integer idObjetivo;
    private Long idComentarioPadre;

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getTipoObjetivo() {
        return tipoObjetivo;
    }

    public void setTipoObjetivo(String tipoObjetivo) {
        this.tipoObjetivo = tipoObjetivo;
    }

    public Integer getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(Integer idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public Long getIdComentarioPadre() {
        return idComentarioPadre;
    }

    public void setIdComentarioPadre(Long idComentarioPadre) {
        this.idComentarioPadre = idComentarioPadre;
    }
}
