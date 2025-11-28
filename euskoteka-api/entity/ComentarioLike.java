package com.euskoteka.euskoteka_api.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "comentarios_likes", schema = "euskoteka",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_coment", "id_user"}))
public class ComentarioLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_like", columnDefinition = "int unsigned not null")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_coment", nullable = false)
    private Comentario comentario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    private UsuarioLite usuario;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    // getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }

    public UsuarioLite getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioLite usuario) {
        this.usuario = usuario;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}