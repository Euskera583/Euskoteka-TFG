package com.euskoteka.euskoteka_api.dto;

import com.euskoteka.euskoteka_api.entity.Comentario;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComentarioDTO {

    private Long id;
    private String contenido;
    private String fecha;
    private String username;
    private Integer likes;
    private Long idComentarioPadre;
    private List<ComentarioDTO> respuestas;
    private boolean eliminado;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm:ss");

    public ComentarioDTO() {};

    public ComentarioDTO(Comentario c) {
        this.id = c.getIdComent();
        this.contenido = c.getContenido();

        Timestamp ts = c.getCreatedAt();
        if (ts != null) {
            LocalDateTime ldt = ts.toLocalDateTime();
            this.fecha = ldt.format(FORMATTER);
        } else {
            this.fecha = "";
        }

        this.likes = c.getLikes();

        this.idComentarioPadre = c.getComentarioPadre() != null
                ? c.getComentarioPadre().getIdComent()
                : null;

        this.username = (c.getUser() != null && c.getUser().getUsername() != null)
                ? c.getUser().getUsername()
                : "Anónimo";

        // MUY IMPORTANTE - NO construir aquí el árbol
        this.respuestas = c.getRespuestas() != null
                ? c.getRespuestas().stream().map(ComentarioDTO::new).toList()
                : new ArrayList<>();

        this.eliminado = c.isEliminado();
    }

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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Long getIdComentarioPadre() {
        return idComentarioPadre;
    }

    public void setIdComentarioPadre(Long idComentarioPadre) {
        this.idComentarioPadre = idComentarioPadre;
    }

    public List<ComentarioDTO> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<ComentarioDTO> respuestas) {
        this.respuestas = respuestas;
    }

    public boolean isEliminado() {
        return eliminado;
    }

    public void setEliminado(boolean eliminado) {
        this.eliminado = eliminado;
    }
}
