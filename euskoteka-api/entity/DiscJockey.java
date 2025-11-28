package com.euskoteka.euskoteka_api.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "`disc-jockeys`", schema = "euskoteka")
public class DiscJockey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dj", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Column(name = "nombre_dj", length = 40)
    private String nombreDj;

    @Column(name = "nombre_real_dj", length = 100)
    private String nombreRealDj;

    @Column(name = "nacimiento_dj")
    private LocalDate nacimientoDj;

    @Column(name = "muerte_dj")
    private LocalDate muerteDj;

    @Lob
    @Column(name = "biografia_dj", columnDefinition = "LONGTEXT")
    private String biografiaDj;

    @Column(name = "foto_dj")
    private String fotoDj;

    //getter y settters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreDj() {
        return nombreDj;
    }

    public void setNombreDj(String nombreDj) {
        this.nombreDj = nombreDj;
    }

    public String getNombreRealDj() {
        return nombreRealDj;
    }

    public void setNombreRealDj(String nombreRealDj) {
        this.nombreRealDj = nombreRealDj;
    }

    public LocalDate getNacimientoDj() {
        return nacimientoDj;
    }

    public void setNacimientoDj(LocalDate nacimientoDj) {
        this.nacimientoDj = nacimientoDj;
    }

    public LocalDate getMuerteDj() {
        return muerteDj;
    }

    public void setMuerteDj(LocalDate muerteDj) {
        this.muerteDj = muerteDj;
    }

    public String getBiografiaDj() {
        return biografiaDj;
    }

    public void setBiografiaDj(String biografiaDj) {
        this.biografiaDj = biografiaDj;
    }

    public String getFotoDj() {
        return fotoDj;
    }

    public void setFotoDj(String fotoDj) {
        this.fotoDj = fotoDj;
    }

}