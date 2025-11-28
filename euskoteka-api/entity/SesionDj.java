package com.euskoteka.euskoteka_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sesion_dj", schema = "euskoteka")
public class SesionDj {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_sesion", nullable = false)
    private Sesione idSesion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_dj", nullable = false)
    private DiscJockey idDj;

    //getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sesione getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(Sesione idSesion) {
        this.idSesion = idSesion;
    }

    public DiscJockey getIdDj() {
        return idDj;
    }

    public void setIdDj(DiscJockey idDj) {
        this.idDj = idDj;
    }

}