package com.euskoteka.euskoteka_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tipo_contenidos", schema = "euskoteka")
public class TipoContenido {
    @Id
    @Column(name = "id_contenido", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @Column(name = "contenido", nullable = false, length = 45)
    private String contenido;

    //getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

}