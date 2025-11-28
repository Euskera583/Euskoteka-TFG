package com.euskoteka.euskoteka_api.service;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.euskoteka.euskoteka_api.dto.*;
import com.euskoteka.euskoteka_api.entity.*;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public interface EuskotekaService {

    //METODOS PARA ARTICULOS

    //get articulos extra por id
    ArticuloExtraDTO getArticuloById(Long id);

    //get listado de articulos
    Page<ArticuloExtraListadoDTO>getTodosArticulos(Pageable pageable);

    ArticuloExtraDTO actualizarArticulo(Long id, ArticuloExtraDTO dto);

    //---------------------------------





    //METODOS PARA CANCIONES


    Page<CancionDTO> getCancionesFiltradas(List<Integer> anios, List<String> generos, String search, Pageable pageable);

    CancionDTO crearCancion(CancionDTO dto);

    CancionDTO actualizarCancion(Long id, CancionDTO dto);

    FiltrosDispCancionDTO getFiltrosCancionesDisponibles(List<Integer> anios, List<String> generos);

    //----------------------------





    //METODOS PARA COMENTARIOS

    //eliminar comentario
    boolean eliminarComentario(Long id, String token);

    //GET todos los comentarios por objetivo paginado
    Page<ComentarioDTO> obtenerComentariosArbol(
            String tipoObjetivo,
            Integer idObjetivo,
            int page,
            int size
    );

    //Escribir un comentario
    ComentarioDTO crearComentario(ComentarioCreateDTO dto, String token);

    //dar like
    Map<String, Object> toggleLikeComentario(Long idComentario, String token);

    //----------------------------





    //METODOS PARA RESIDENCIAS

    //GET RESIDENCIAS
    List<ResidenciaDTO> getResidenciasPorDiscoteca(Long idDiscoteca);

    //crear residencia
    ResidenciaDTO crearResidencia(Long idDiscoteca, ResidenciaCreateDTO dto);

    //EDITAR RESIDENCIAS
    public ResidenciaDTO editarResidencia(Long id, ResidenciaCreateDTO dto);

    //BORRAR RESIDENCIAS
    void eliminarResidencia(Long id);

    //----------------------------





    //METODOS PARA DISCOTECAS

    //Get todas las discotecas paginado
    Page<DiscotecaListadoDTO> getDiscotecasFiltradas(
            List<Integer> anios,
            List<String> provincias,
            List<String> localidades,
            Pageable pageable
    );

    FiltrosDispDiscotecaDTO getFiltrosDisponiblesDiscotecas();

    //Get discoteca por ID
    List<DiscotecaDTO> getDiscotecasById(Integer id);

    //CREAR DISCOTECA
    DiscotecaDTO crearDiscoteca(DiscotecaDTO dto);

    //EDITAR DISCOTECA
    DiscotecaDTO actualizarDiscoteca(Long id, DiscotecaDTO dto);

    //----------------------------





    //METODOS PARA MULTIMEDIA

    Page<MultimediaDTO>getMultimedia(Pageable pageable);

    //----------------------------





    //METODOS PARA SESIONES

    //get sesiones con opción de filtro
    public Page<SesionDTO> getSesionesFiltradas(List<Integer> anios, List<String> discotecas, List<String> djs, Pageable pageable);

    //Get sesion por el id concreto
    List<SesionDTO> getSesionesById(Integer id);

    FiltrosDisponiblesDTO  getFiltrosDisponibles(
            List<Integer> anios,
            List<String> discotecas,
            List<String> djs
    );

    //llamadas a sesiones por id de canción
    Page<SesionDTO> getSesionesByCancionId(Long idCancion, Pageable pageable);

    //actualizar sesion
    SesionDTO actualizarSesion(Long id, SesionDTO sesionDTO);

    SesionDTO crearSesion(SesionDTO sesionDTO);

    //actualizar DJs
    SesionDTO actualizarDjsSesion(Long idSesion, List<String> nuevosDjs);

    //----------------------------


//se vee


    //METODOS DE TRACKLIST

    List<TracklistDTO> getTracklistBySesion(Long idSesion);

    //----------------------------





    //METODOS DE LOGIN Y REGISTRO

    UsuarioLiteDTO registrarUsuario(String username, String email);

    //iniciar sesión
    public void iniciarSesion(String email);

    //verificar registro
    public ResponseEntity<Void> verificarCuenta(String token, HttpServletResponse response);

    //verificar login
    public ResponseEntity<UsuarioLiteDTO> verificarLogin(String token, HttpServletResponse response);

}
