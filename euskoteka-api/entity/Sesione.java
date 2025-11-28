package com.euskoteka.euskoteka_api.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "sesiones", schema = "euskoteka")
public class Sesione {

    public Sesione() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int UNSIGNED not null")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "id_discoteca", nullable = true)
    private Discoteca idDiscoteca;

    @Column(name = "nombre_sesion", nullable = false)
    private String nombreSesion;

    @Lob
    @Column(name = "info_sesion", columnDefinition = "MEDIUMTEXT", nullable = false)
    private String infoSesion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genero", nullable = true)
    private GenerosMusicale genero;

    @Column(name = "caratula_foto", nullable = false)
    private String caratulaFoto;

    @Column(name = "caratula_foto_calidad", nullable = false)
    private String caratulaFotoCalidad;

    @Column(name = "url_sesion")
    private String urlSesion;

    @Column(name = "conservacion_sesion")
    private String conservacionSesion;

    @Column(name = "anio", nullable = false)
    private Integer fechaSesionYear;

    @Column(name = "mes", columnDefinition = "SMALLINT UNSIGNED")
    private Short fechaSesionMonth;

    @Column(name = "dia", columnDefinition = "SMALLINT UNSIGNED")
    private Short fechaSesionDay;

    @Column(name = "medio_sesion", length = 20)
    private String medioSesion;

    @Column(name = "duenno_medio", length = 50)
    private String duennoMedio;

    @Column(name = "inedita_sesion", columnDefinition = "SMALLINT UNSIGNED NOT NULL")
    private Short ineditaSesion;

    @Column(name = "digitalizado_sesion")
    private Boolean digitalizadoSesion;

    // dentro de la clase Sesione...
    @OneToMany(mappedBy = "idSesion", fetch = FetchType.LAZY)
    private List<SesionDj> sesionDjs;

    //getters y setters

    public List<SesionDj> getSesionDjs() {
        return sesionDjs;
    }

    public void setSesionDjs(List<SesionDj> sesionDjs) {
        this.sesionDjs = sesionDjs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Discoteca getIdDiscoteca() {
        return idDiscoteca;
    }

    public void setIdDiscoteca(Discoteca idDiscoteca) {
        this.idDiscoteca = idDiscoteca;
    }

    public String getNombreSesion() {
        return nombreSesion;
    }

    public void setNombreSesion(String nombreSesion) {
        this.nombreSesion = nombreSesion;
    }

    public String getInfoSesion() {
        return infoSesion;
    }

    public void setInfoSesion(String infoSesion) {
        this.infoSesion = infoSesion;
    }

    public GenerosMusicale getGenero() {
        return genero;
    }

    public void setGenero(GenerosMusicale genero) {
        this.genero = genero;
    }

    public String getCaratulaFoto() {
        return caratulaFoto;
    }

    public void setCaratulaFoto(String caratulaFoto) {
        this.caratulaFoto = caratulaFoto;
    }

    public String getCaratulaFotoCalidad() {
        return caratulaFotoCalidad;
    }

    public void setCaratulaFotoCalidad(String caratulaFotoCalidad) {
        this.caratulaFotoCalidad = caratulaFotoCalidad;
    }

    public String getUrlSesion() {
        return urlSesion;
    }

    public void setUrlSesion(String urlSesion) {
        this.urlSesion = urlSesion;
    }

    public String getConservacionSesion() {
        return conservacionSesion;
    }

    public void setConservacionSesion(String conservacionSesion) {
        this.conservacionSesion = conservacionSesion;
    }

    public Integer getFechaSesionYear() {
        return fechaSesionYear;
    }

    public void setFechaSesionYear(Integer fechaSesionYear) {
        this.fechaSesionYear = fechaSesionYear;
    }

    public Short getFechaSesionMonth() {
        return fechaSesionMonth;
    }

    public void setFechaSesionMonth(Short fechaSesionMonth) {
        this.fechaSesionMonth = fechaSesionMonth;
    }

    public Short getFechaSesionDay() {
        return fechaSesionDay;
    }

    public void setFechaSesionDay(Short fechaSesionDay) {
        this.fechaSesionDay = fechaSesionDay;
    }

    public String getMedioSesion() {
        return medioSesion;
    }

    public void setMedioSesion(String medioSesion) {
        this.medioSesion = medioSesion;
    }

    public String getDuennoMedio() {
        return duennoMedio;
    }

    public void setDuennoMedio(String duennoMedio) {
        this.duennoMedio = duennoMedio;
    }

    public Short getIneditaSesion() {
        return ineditaSesion;
    }

    public void setIneditaSesion(Short ineditaSesion) {
        this.ineditaSesion = ineditaSesion;
    }

    public Boolean getDigitalizadoSesion() {
        return digitalizadoSesion;
    }

    public void setDigitalizadoSesion(Boolean digitalizadoSesion) {
        this.digitalizadoSesion = digitalizadoSesion;
    }

    public String getFechaSesionFormateada() {
        if (fechaSesionDay != null && fechaSesionMonth != null) {
            return String.format("%02d/%02d/%04d", fechaSesionDay, fechaSesionMonth, fechaSesionYear);
        } else if (fechaSesionMonth != null) {
            return String.format("%02d/%04d", fechaSesionMonth, fechaSesionYear);
        } else {
            return String.valueOf(fechaSesionYear);
        }
    }

}