package com.euskoteka.euskoteka_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "multimedia", schema = "euskoteka")
public class Multimedia {
    @Id
    @Column(name = "id_flyer", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_discoteca")
    private Discoteca idDiscoteca;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "anio_flyer")
    private Integer anioFlyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_contenido")
    private TipoContenido tipoContenido;

    @Column(name = "url_flyer")
    private String urlFlyer;

    @Column(name = "url_flyer_calidad")
    private String urlFlyerCalidad;

    //getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Discoteca getIdDiscoteca() {
        return idDiscoteca;
    }

    public void setIdDiscoteca(Discoteca idDiscoteca) {
        this.idDiscoteca = idDiscoteca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getAnioFlyer() {
        return anioFlyer;
    }

    public void setAnioFlyer(Integer anioFlyer) {
        this.anioFlyer = anioFlyer;
    }

    public TipoContenido getTipoContenido() {
        return tipoContenido;
    }

    public void setTipoContenido(TipoContenido tipoContenido) {
        this.tipoContenido = tipoContenido;
    }

    public String getUrlFlyer() {
        return urlFlyer;
    }

    public void setUrlFlyer(String urlFlyer) {
        this.urlFlyer = urlFlyer;
    }

    public String getUrlFlyerCalidad() {
        return urlFlyerCalidad;
    }

    public void setUrlFlyerCalidad(String urlFlyerCalidad) {
        this.urlFlyerCalidad = urlFlyerCalidad;
    }
}


