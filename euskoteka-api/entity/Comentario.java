package com.euskoteka.euskoteka_api.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comentarios", schema = "euskoteka")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_coment", columnDefinition = "int not null")
    private Long idComent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true, referencedColumnName = "id_user",
            foreignKey = @ForeignKey(name = "fk_comentarios_user"))
    private UsuarioLite user;

    @Column(name = "id_objetivo", nullable = false)
    private Integer idObjetivo;

    @Column(name = "tipo_objetivo", nullable = false, length = 45)
    private String tipoObjetivo;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String contenido;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "likes", columnDefinition = "int UNSIGNED default 0")
    private Integer likes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_coment_padre", nullable = true)
    private Comentario comentarioPadre;

    @OneToMany(mappedBy = "comentarioPadre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> respuestas = new ArrayList<>();

    @Column(name = "eliminado", columnDefinition = "TINYINT(1) DEFAULT 0")
    private boolean eliminado;

    //getters y setters

    public Long getIdComent() {
        return idComent;
    }

    public void setIdComent(Long idComent) {
        this.idComent = idComent;
    }

    public UsuarioLite getUser() {
        return user;
    }

    public void setUser(UsuarioLite user) {
        this.user = user;
    }

    public Integer getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(Integer idObjetivo) {
        this.idObjetivo = idObjetivo;
    }

    public String getTipoObjetivo() {
        return tipoObjetivo;
    }

    public void setTipoObjetivo(String tipoObjetivo) {
        this.tipoObjetivo = tipoObjetivo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Comentario getComentarioPadre() {
        return comentarioPadre;
    }

    public void setComentarioPadre(Comentario comentarioPadre) {
        this.comentarioPadre = comentarioPadre;
    }

    public List<Comentario> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Comentario> respuestas) {
        this.respuestas = respuestas;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
}