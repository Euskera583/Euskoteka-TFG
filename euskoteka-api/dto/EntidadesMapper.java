package com.euskoteka.euskoteka_api.dto;

import com.euskoteka.euskoteka_api.dto.DiscotecaDTO;
import com.euskoteka.euskoteka_api.entity.*;

import java.util.Collections;
import java.util.stream.Collectors;
import java.util.List;

public class EntidadesMapper {

    public static DiscotecaDTO DTODiscoteca(Discoteca d) {
        //si algun campo es null, devuelve null para evitar nullpointerException
        if(d == null) return null;

        Long id = d.getId();
        String nombre = d.getNombreDisco();
        String historia = d.getHistoriaDisco();
        Integer apertura = d.getFechaAperturaDisco();
        Integer cierre = d.getFechaCierreDisco();
        String duenno = d.getDuennoDisco();
        String foto = d.getFotoDisco();
        String fotoCalidad = d.getFotoDiscoCalidad();
        String logo = d.getLogoDisco();

        String localidadNombre = null;
        String provinciaNombre = null;

        //manejo de si la localidad es null
        Localidade loc = d.getLocalidadDisco();
        if(loc != null) {
            localidadNombre = loc.getNombreLocalidad();
            Provincia prov = loc.getIdProvincia();
            if(prov != null) {
                provinciaNombre = prov.getProvincia();
            }
        }

        return new DiscotecaDTO(id, nombre, historia, apertura, cierre,
                duenno, foto, fotoCalidad, logo,  localidadNombre, provinciaNombre);
    }

    public static DiscotecaListadoDTO toListadoDTO(Discoteca d) {
        if (d == null) return null;

        Long id = d.getId();
        String nombre = d.getNombreDisco();
        String foto = d.getFotoDisco();

        String localidadNombre = null;
        String provinciaNombre = null;
        Localidade loc = d.getLocalidadDisco();
        if (loc != null) {
            localidadNombre = loc.getNombreLocalidad();
            Provincia prov = loc.getIdProvincia();
            if (prov != null) {
                provinciaNombre = prov.getProvincia();
            }
        }

        Integer apertura = d.getFechaAperturaDisco();
        Integer cierre = d.getFechaCierreDisco();
        String periodo;

        //manejo de periodo de actividad segun los datos en la base

        //si la apartura y cierre son nulos, devolver 'Desconocido'
        if (apertura == null && cierre == null) {
            periodo = "Desconocido";
        //si la apertura y cierre existen, devolverlos con un separador '-'
        } else if (apertura != null && cierre != null) {
            periodo = apertura + " - " + cierre;
        //si la apertura existe pero el cierre no, la discoteca sigue abierta
        } else if (apertura != null) {
            periodo = apertura + " - Actualidad";
        } else {
        //si la apertura no existe pero el cierre si, devolver con desconocido al principio y un separador
            periodo = "Desconocido - " + cierre;
        }

        return new DiscotecaListadoDTO(id, nombre, foto, localidadNombre, periodo);
    }

    public static SesionDTO DTOSesion(Sesione s) {
        if(s == null) return null; //si sesiones es null, deovlvemos null

        //cogemos los campos de la tabla sesiones
        Long id = s.getId();
        String nombreSesion = s.getNombreSesion();
        String infoSesion = s.getInfoSesion();
        String caratulaFoto = s.getCaratulaFoto();
        String caratulaFotoCalidad = s.getCaratulaFotoCalidad();
        String urlSesion = s.getUrlSesion();
        String conservacionSesion = s.getConservacionSesion();
        String fechaSesion = s.getFechaSesionFormateada();
        String medioSesion = s.getMedioSesion();
        String duennoMedio = s.getDuennoMedio();
        Short ineditaSesion = s.getIneditaSesion();
        Boolean digitalizado =  s.getDigitalizadoSesion();

        //cogemos el nombre de la discoteca asociada a la sesion
        String nombreDiscoteca = null;
        if (s.getIdDiscoteca() !=null){
            nombreDiscoteca = s.getIdDiscoteca().getNombreDisco();
        }

        //extraer el nombre de los DJs que pinchan las sesiones
        //primero cogemos la entidad JPA
        List<SesionDj> listaSesionDjs = s.getSesionDjs();
        List<String> nombreDjs;
        if(listaSesionDjs == null || listaSesionDjs.isEmpty()){
            nombreDjs = Collections.emptyList();
            //si la sesion no tiene DJ asociado devolvemos una lista vacia
        }else{
            nombreDjs = listaSesionDjs.stream()
                    .map(SesionDj::getIdDj) //obetener el objeto DiscJocker
                    .filter(dj -> dj !=null) //filtrar por si hay nulos
                    .map(DiscJockey::getNombreDj)// de cada DiscJockey saco solo el nombre
                    .collect(Collectors.toList()); //convertimos en lista
        }

        return new SesionDTO(id, nombreSesion, infoSesion, caratulaFoto, caratulaFotoCalidad,
                urlSesion, conservacionSesion, fechaSesion, medioSesion,
                duennoMedio, ineditaSesion, digitalizado, nombreDiscoteca, nombreDjs);
    }

    public static CancionDTO cancionDTO(Cancione c) {
        if (c == null) return null;

        String genero = null;
        if (c.getGenero() != null) {
            genero = c.getGenero().getGenero(); //cojo soloel nombe
        }

        return new CancionDTO(
                c.getId(),
                c.getArtista(),
                c.getNombreTema(),
                c.getAnio(),
                genero,
                c.getUrlYt()
        );
    }

    public static MultimediaDTO DTOMultimedia(Multimedia m){
        if (m==null) return null;

        String discotecaNombre = (m.getIdDiscoteca() != null && m.getIdDiscoteca().getNombreDisco() != null)
                ? m.getIdDiscoteca().getNombreDisco()
                : "Desconocido";

        String anioTexto = (m.getAnioFlyer() != null)
                ? m.getAnioFlyer().toString()
                : "Desconocido";

        return new MultimediaDTO(
                m.getId(),
                discotecaNombre,
                m.getDescripcion(),
                anioTexto,
                m.getUrlFlyer(),
                m.getUrlFlyerCalidad()
        );
    };

    public static ArticuloExtraListadoDTO toListadoDTO(ArticulosExtra a) {
        if (a == null) return null;

        return new ArticuloExtraListadoDTO(
                a.getIdArt(),
                a.getNombreArt(),
                a.getTipoArt(),
                a.getFoto()
        );
    }

    public static ArticuloExtraDTO toArticulosListadoDTO(ArticulosExtra a) {
        if (a == null) return null;

        return new ArticuloExtraDTO(
                a.getIdArt(),
                a.getNombreArt(),
                a.getTipoArt(),
                a.getFoto(),
                a.getTexto(),
                a.getSubtitulo()
        );
    }
}
