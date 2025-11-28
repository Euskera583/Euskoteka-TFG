package com.euskoteka.euskoteka_api.rest;
import com.euskoteka.euskoteka_api.dao.UsersDAO;
import com.euskoteka.euskoteka_api.dto.*;
import com.euskoteka.euskoteka_api.entity.*;
import com.euskoteka.euskoteka_api.exception.EuskotekaException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.euskoteka.euskoteka_api.service.EuskotekaService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
// @CrossOrigin(origins = "http://localhost:5173")
public class EuskoRestController {
    //Inyección de la capa de servicio
    @Autowired
    EuskotekaService euskotekaService;
    @Autowired
    UsersDAO usersdao;

    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        // Devuelve una respuesta exitosa para indicar que el servicio está en línea
        return new ResponseEntity<>("Servicio en línea", HttpStatus.OK);
    }

    //METODOS PARA ARTICULOS

    //get todos los articulos (paginado)
    @GetMapping("/articulos")
    public Page<ArticuloExtraListadoDTO> getArticulosExtra(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return euskotekaService.getTodosArticulos(pageable);
    }

    //get articulos por id
    @GetMapping("/articulos/{id}")
    public ResponseEntity<ArticuloExtraDTO> obtenerPorId(@PathVariable Long id) {
        ArticuloExtraDTO art = euskotekaService.getArticuloById(id);
        if (art == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(art);
    }

    //actualizar articulos
    @PutMapping("/articulos/{id}")
    public ResponseEntity<ArticuloExtraDTO> actualizarArticulo(
            @PathVariable Long id,
            @RequestBody ArticuloExtraDTO dto
    ) {
        return ResponseEntity.ok(euskotekaService.actualizarArticulo(id, dto));
    }

    //----------------------------







    //METODOS PARA CANCIONES

    //get canciones (paginado)
    @GetMapping("/canciones")
    public Page<CancionDTO> getCancionesFiltradas(
            @RequestParam(required = false) List<Integer> anios,
            @RequestParam(required = false) List<String> generos,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("anio").ascending());
        return euskotekaService.getCancionesFiltradas(anios, generos, search, pageable);
    }

    //crear canciones
    @PostMapping("/canciones")
    public ResponseEntity<CancionDTO> crearCancion(@RequestBody CancionDTO dto) {
        return ResponseEntity.ok(euskotekaService.crearCancion(dto));
    }

    //editar canciones
    @PutMapping("/canciones/{id}")
    public ResponseEntity<CancionDTO> actualizarCancion(
            @PathVariable Long id,
            @RequestBody CancionDTO dto
    ) {
        return ResponseEntity.ok(euskotekaService.actualizarCancion(id, dto));
    }

    //traer los filtros disponibles de canciones
    @GetMapping("/canciones/filtros")
    public FiltrosDispCancionDTO getFiltrosCanciones(
            @RequestParam(required = false) List<Integer> anios,
            @RequestParam(required = false) List<String> generos
    ) {
        return euskotekaService.getFiltrosCancionesDisponibles(anios, generos);
    }

    //----------------------------







    //METODOS PARA COMENTARIOS

    //eliminar comentarios por id
    @DeleteMapping("/comentarios/{id}")
    public ResponseEntity<Void> eliminarComentario(
            @PathVariable Long id,
            @CookieValue(value = "auth_token", required = false) String token
    ) {
        boolean ok = euskotekaService.eliminarComentario(id, token);
        return ok ? ResponseEntity.ok().build() : ResponseEntity.status(401).build();
    }

    //coger todos los comentarios por fecha
    @GetMapping("/comentarios")
    public ResponseEntity<Page<ComentarioDTO>> obtenerComentariosArbol(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "15") int size,
            @RequestParam(name = "tipo") String tipoObjetivo,
            @RequestParam(name = "id") Integer idObjetivo
    ) {
        return ResponseEntity.ok(
                euskotekaService.obtenerComentariosArbol(tipoObjetivo, idObjetivo, page, size)
        );
    }

    //publicar comentarios
    @PostMapping("/comentarios")
    public ResponseEntity<ComentarioDTO> crearComentario(
            @RequestBody ComentarioCreateDTO dto,
            @CookieValue(value = "auth_token", required = false) String token
    ) {
        return ResponseEntity.ok(euskotekaService.crearComentario(dto, token));
    }

    //DAR LIKE
    @PostMapping("/comentarios/{id}/like")
    public ResponseEntity<?> toggleLikeComentario(
            @PathVariable("id") Long idComentario,
            @CookieValue(value = "auth_token", required = false) String token
    ) {
        return ResponseEntity.ok(
                euskotekaService.toggleLikeComentario(idComentario, token)
        );
    }

    //----------------------------







    //METODOS PARA RESIDENCIAS

    //get residencias por id discoteca
    @GetMapping("/residencias/{idDiscoteca}")
    public List<ResidenciaDTO> getResidenciasPorDiscoteca(@PathVariable Long idDiscoteca) {
        return euskotekaService.getResidenciasPorDiscoteca(idDiscoteca);
    }

    //crear una nueva residencia
    @PostMapping("/residencias/{idDiscoteca}")
    public ResponseEntity<ResidenciaDTO> crearResidencia(
            @PathVariable Long idDiscoteca,
            @RequestBody ResidenciaCreateDTO dto
    ) {
        return ResponseEntity.ok(euskotekaService.crearResidencia(idDiscoteca, dto));
    }

    //editar las residencias
    @PutMapping("/residencias/{id}")
    public ResponseEntity<ResidenciaDTO> editarResidencia(
            @PathVariable Long id,
            @RequestBody ResidenciaCreateDTO dto
    ) {
        return ResponseEntity.ok(euskotekaService.editarResidencia(id, dto));
    }

    //borrar la residencia
    @DeleteMapping("/residencias/{id}")
    public ResponseEntity<Void> eliminarResidencia(@PathVariable Long id) {
        euskotekaService.eliminarResidencia(id);
        return ResponseEntity.ok().build();
    }

    //----------------------------







    //METODOS PARA DISCOTECAS

    //get discotecas (paginado)
    @GetMapping("/discotecas")
    public Page<DiscotecaListadoDTO> getDiscotecasFiltradas(
            @RequestParam(required = false) List<Integer> anios,
            @RequestParam(required = false) List<String> provincias,
            @RequestParam(required = false) List<String> localidades,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombreDisco").ascending());
        return euskotekaService.getDiscotecasFiltradas(anios, provincias, localidades, pageable);
    }

    //get filtros disponibles de las discotecas
    @GetMapping("/discotecas/filtros")
    public FiltrosDispDiscotecaDTO getFiltrosDisponiblesDiscotecas() {
        return euskotekaService.getFiltrosDisponiblesDiscotecas();
    }

    //get discoteca concreta por id
    @GetMapping("/discotecas/{id}")
    public List<DiscotecaDTO>  getDiscotecasById(@PathVariable Integer id){
        return euskotekaService.getDiscotecasById(id);
    }

    //crear discoteca nueva
    @PostMapping("/discotecasCrear")
    public ResponseEntity<DiscotecaDTO> crearDiscoteca(@RequestBody DiscotecaDTO dto) {
        DiscotecaDTO nueva = euskotekaService.crearDiscoteca(dto);
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    //editr una discoteca
    @PutMapping("/discotecas/{id}")
    public ResponseEntity<DiscotecaDTO> actualizarDiscoteca(
            @PathVariable Long id,
            @RequestBody DiscotecaDTO dto
    ) {
        try {
            DiscotecaDTO actualizada = euskotekaService.actualizarDiscoteca(id, dto);
            return ResponseEntity.ok(actualizada);
        } catch (EuskotekaException e) {
            return ResponseEntity.status(e.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //----------------------------







    //METODOS PARA MULTIMEDIA

    //get material multimedia (paginado)
    @GetMapping("/multimedia")
    public Page<MultimediaDTO>getAllMultimedia(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ){
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("anioFlyer").ascending().and(Sort.by("id").ascending())
        );
        return euskotekaService.getMultimedia(pageable);
    }

    //----------------------------







    //METODOS PARA SESIONES

    //get sesiones (paginado)
    @GetMapping("/sesiones")
    public Page<SesionDTO> getSesionesFiltradas(
            @RequestParam(required = false) List<Integer> anios,
            @RequestParam(required = false) List<String> discotecas,
            @RequestParam(required = false) List<String> djs,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("fechaSesionYear").ascending()
                        .and(Sort.by("fechaSesionMonth").ascending())
                        .and(Sort.by("fechaSesionDay").ascending())
                        .and(Sort.by("id").ascending())
        );
        return euskotekaService.getSesionesFiltradas(anios, discotecas, djs, pageable);
    }

    //get sesion por id
    @GetMapping("/sesiones/{id}")
    public List<SesionDTO> getSesionesById(@PathVariable Integer id){
        return euskotekaService.getSesionesById(id);
    }

    //get filtros disponibles para sesiones
    @GetMapping("/sesiones/filtros")
    public FiltrosDisponiblesDTO getFiltrosDisponibles(
            @RequestParam(required = false) List<Integer> anios,
            @RequestParam(required = false) List<String> djs,
            @RequestParam(required = false) List<String> discotecas
    ) {
        return euskotekaService.getFiltrosDisponibles(anios, djs, discotecas);
    }

    //get sesiones segun una canción
    @GetMapping("/canciones/{idCancion}/sesiones")
    public Page<SesionDTO> getSesionesByCancion(
            @PathVariable Long idCancion,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "30") int size
    ) {
        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("fechaSesionYear").ascending()
                        .and(Sort.by("fechaSesionMonth").ascending())
                        .and(Sort.by("fechaSesionDay").ascending())
                        .and(Sort.by("id").ascending())
        );

        return euskotekaService.getSesionesByCancionId(idCancion, pageable);
    }

    //editar una sesion concreta
    @PutMapping("/sesiones/{id}")
    public ResponseEntity<SesionDTO> actualizarSesion(
            @PathVariable Long id,
            @RequestBody SesionDTO sesionDTO
    ) {
        try {
            SesionDTO actualizada = euskotekaService.actualizarSesion(id, sesionDTO);
            return ResponseEntity.ok(actualizada);
        } catch (EuskotekaException e) {
            return ResponseEntity.status(e.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //editar los DJ's de una sesion concreta
    @PutMapping("/sesiones/{id}/djs")
    public ResponseEntity<SesionDTO> actualizarDjsSesion(
            @PathVariable Long id,
            @RequestBody List<String> nuevosDjs
    ) {
        try {
            SesionDTO actualizada = euskotekaService.actualizarDjsSesion(id, nuevosDjs);
            return ResponseEntity.ok(actualizada);
        } catch (EuskotekaException e) {
            return ResponseEntity.status(e.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //crear una sesion nueva
    @PostMapping("/sesiones")
    public ResponseEntity<SesionDTO> crearSesion(@RequestBody SesionDTO dto) {
        try {
            SesionDTO nueva = euskotekaService.crearSesion(dto);
            return ResponseEntity.ok(nueva);
        } catch (EuskotekaException e) {
            return ResponseEntity.status(e.getStatus()).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    //----------------------------







    //METODOS DE TRACKLIST

    //get listado de tracklist segun id de sesion
    @GetMapping("/tracklist/{idSesion}")
    public List<TracklistDTO> getTracklistBySesion(@PathVariable Long idSesion){
        return euskotekaService.getTracklistBySesion(idSesion);
    }

    //----------------------------







    //METODOS DE LOGIN Y REGISTRO

    //registro
    @PostMapping("/register")
    public ResponseEntity<UsuarioLiteDTO> registrarUsuario(
            @RequestParam String username,
            @RequestParam(required = false) String email
    ) {
        UsuarioLiteDTO usuario = euskotekaService.registrarUsuario(username, email);
        return ResponseEntity.ok(usuario);
    }

    //verificar registro
    @GetMapping("/verificar-registro")
    public ResponseEntity<Void> verificarCuenta(
            @RequestParam String token,
            HttpServletResponse response
    ) throws IOException {
        return euskotekaService.verificarCuenta(token, response);
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<String> loginUsuario(@RequestParam String email) {
        try{
            euskotekaService.iniciarSesion(email);
            return ResponseEntity.ok("Se ha enviado un enlace de verificación");
        }catch (EuskotekaException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno al procesar el inicio de sesión.");
        }
    }

    //verificar login
    @GetMapping("/verificar-login")
    public ResponseEntity<UsuarioLiteDTO> verificarLogin(
            @RequestParam String token,
            HttpServletResponse response
    ) {
        return euskotekaService.verificarLogin(token, response);
    }

    //inicia sesión de forma automatica
    @GetMapping("/auto-login")
    public ResponseEntity<UsuarioLiteDTO> autoLogin(@CookieValue(value = "auth_token", required = false) String token) {
        if (token == null || token.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        //compreuba que el usuario exist
        Optional<UsuarioLite> usuarioOpt = usersdao.findByToken(token);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(new UsuarioLiteDTO(usuarioOpt.get()));
    }

    //cerrar sesión
    @PostMapping("/logout")
    public ResponseEntity<Void> logout (HttpServletResponse response) {
        Cookie cookie = new Cookie("auth_token","");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0); //elimina la cookie
        response.addCookie(cookie);
        return ResponseEntity.ok().build();
    }

}

