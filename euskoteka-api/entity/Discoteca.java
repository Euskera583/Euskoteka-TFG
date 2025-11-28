package com.euskoteka.euskoteka_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "discotecas", schema = "euskoteka")
public class Discoteca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Column(name = "nombre_disco", length = 100)
    private String nombreDisco;

    @Lob
    @Column(name = "historia_disco", columnDefinition = "LONGTEXT")
    private String historiaDisco;

    @Column(name = "fecha_apertura_disco")
    private Integer fechaAperturaDisco;

    @Column(name = "fecha_cierre_disco")
    private Integer fechaCierreDisco;

    @Column(name = "duenno_disco", length = 200)
    private String duennoDisco;

    @Column(name = "foto_disco")
    private String fotoDisco;

    @Column(name = "foto_disco_calidad")
    private String fotoDiscoCalidad;

    @Column(name ="logo_disco")
    private String logoDisco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "localidad_disco")
    private Localidade localidadDisco;

    //getters y setters 

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreDisco() {
        return nombreDisco;
    }

    public void setNombreDisco(String nombreDisco) {
        this.nombreDisco = nombreDisco;
    }

    public String getHistoriaDisco() {
        return historiaDisco;
    }

    public void setHistoriaDisco(String historiaDisco) {
        this.historiaDisco = historiaDisco;
    }

    public Integer getFechaAperturaDisco() {
        return fechaAperturaDisco;
    }

    public void setFechaAperturaDisco(Integer fechaAperturaDisco) {
        this.fechaAperturaDisco = fechaAperturaDisco;
    }

    public Integer getFechaCierreDisco() {
        return fechaCierreDisco;
    }

    public void setFechaCierreDisco(Integer fechaCierreDisco) {
        this.fechaCierreDisco = fechaCierreDisco;
    }

    public String getDuennoDisco() {
        return duennoDisco;
    }

    public void setDuennoDisco(String duennoDisco) {
        this.duennoDisco = duennoDisco;
    }

    public String getFotoDisco() {
        return fotoDisco;
    }

    public void setFotoDisco(String fotoDisco) {
        this.fotoDisco = fotoDisco;
    }

    public String getFotoDiscoCalidad() {
        return fotoDiscoCalidad;
    }

    public void setFotoDiscoCalidad(String fotoDiscoCalidad) {
        this.fotoDiscoCalidad = fotoDiscoCalidad;
    }

    public String getLogoDisco() {
        return logoDisco;
    }

    public void setLogoDisco(String logoDisco) {
        this.logoDisco = logoDisco;
    }

    public Localidade getLocalidadDisco() {
        return localidadDisco;
    }

    public void setLocalidadDisco(Localidade localidadDisco) {
        this.localidadDisco = localidadDisco;
    }

}