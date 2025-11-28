package com.euskoteka.euskoteka_api.dto;

import com.euskoteka.euskoteka_api.entity.UsuarioLite;

public class UsuarioLiteDTO {
    private Long idUser;
    private String username;
    //private String email;
    private String rol;
    //private String token;

    public UsuarioLiteDTO() {}

    public UsuarioLiteDTO(UsuarioLite u) {
        this.idUser = u.getIdUser();
        this.username = u.getUsername();
        //this.email = u.getEmail();
        this.rol = u.getRol();
        //this.token = u.getToken();
    }

    // Getters y setters
    public Long getIdUser() { return idUser; }
    public void setIdUser(Long idUser) { this.idUser = idUser; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    //public String getEmail() { return email; }
    //public void setEmail(String email) { this.email = email; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    //public String getToken() { return token; }
    //public void setToken(String token) { this.token = token; }
}
