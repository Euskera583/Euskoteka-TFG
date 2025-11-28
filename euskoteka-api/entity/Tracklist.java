package com.euskoteka.euskoteka_api.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tracklists", schema = "euskoteka")
public class Tracklist {
    @Id
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_sesion", nullable = false)
    private Sesione idSesion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cancion", nullable = true)
    private Cancione idCancion;

    @Column(name = "numero_pista", columnDefinition = "int UNSIGNED")
    private Long numeroPista;

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

    public Cancione getIdCancion() {
        return idCancion;
    }

    public void setIdCancion(Cancione idCancion) {
        this.idCancion = idCancion;
    }

    public Long getNumeroPista() {
        return numeroPista;
    }

    public void setNumeroPista(Long numeroPista) {
        this.numeroPista = numeroPista;
    }

}