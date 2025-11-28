package com.euskoteka.euskoteka_api.dto;

public class DiscotecaDTO {
    private Long id;
    private String nombre;
    private String historia;
    private Integer fechaApertura;
    private Integer fechaCierre;
    private String duenno;
    private String foto;
    private String fotoCalidad;
    private String logo;
    private String localidad;
    private String provincia;

    public DiscotecaDTO() {}

    public DiscotecaDTO(Long id, String nombre, String historia, Integer fechaApertura, Integer fechaCierre,
                        String duenno, String foto, String fotoCalidad, String logo, String localidad, String provincia) {
        this.id = id;
        this.nombre = nombre;
        this.historia = historia;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
        this.duenno = duenno;
        this.foto = foto;
        this.fotoCalidad = fotoCalidad;
        this.logo = logo;
        this.localidad = localidad;
        this.provincia = provincia;
    }

//getter y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public Integer getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Integer fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Integer getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Integer fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getDuenno() {
        return duenno;
    }

    public void setDuenno(String duenno) {
        this.duenno = duenno;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFotoCalidad() {
        return fotoCalidad;
    }

    public void setFotoCalidad(String fotoCalidad) {
        this.fotoCalidad = fotoCalidad;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
