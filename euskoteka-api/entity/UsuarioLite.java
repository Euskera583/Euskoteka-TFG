package com.euskoteka.euskoteka_api.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "usuarios_lite", schema = "euskoteka")
public class UsuarioLite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", columnDefinition = "int UNSIGNED not null")
    private Long idUser;

    @Column(name = "username", nullable = false, length = 50)
    private String username;

    @Column(name = "rol", nullable = false, length = 20)
    private String rol;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "token", length = 64, unique = true)
    private String token;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    //getters y setters

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}