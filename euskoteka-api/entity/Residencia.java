package com.euskoteka.euskoteka_api.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "residencias", schema = "euskoteka")
public class Residencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_resi", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_dj", nullable = false)
    private DiscJockey idDj;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_discoteca", nullable = false)
    private Discoteca idDiscoteca;

    @Column(name = "fecha_inicio", length = 50)
    private String fechaInicio;

    @Column(name = "fecha_fin", length = 50)
    private String fechaFin;

    @Column(name = "comentarios_resi")
    private String comentariosResi;

    //getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiscJockey getIdDj() {
        return idDj;
    }

    public void setIdDj(DiscJockey idDj) {
        this.idDj = idDj;
    }

    public Discoteca getIdDiscoteca() {
        return idDiscoteca;
    }

    public void setIdDiscoteca(Discoteca idDiscoteca) {
        this.idDiscoteca = idDiscoteca;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getComentariosResi() {
        return comentariosResi;
    }

    public void setComentariosResi(String comentariosResi) {
        this.comentariosResi = comentariosResi;
    }

}