package com.euskoteka.euskoteka_api.service;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import com.euskoteka.euskoteka_api.dao.*;
import com.euskoteka.euskoteka_api.dto.*;

import com.euskoteka.euskoteka_api.entity.*;

import com.euskoteka.euskoteka_api.exception.EuskotekaException;
import com.euskoteka.euskoteka_api.specifications.CancionSpecification;
import com.euskoteka.euskoteka_api.specifications.DiscotecaSpecification;
import com.euskoteka.euskoteka_api.specifications.SesionSpecification;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static com.euskoteka.euskoteka_api.dto.EntidadesMapper.*;
import static org.springframework.data.jpa.domain.Specification.where;
//import static java.lang.ScopedValue.where;

@Service
public class EuskotekaServiceImpl implements EuskotekaService {
    //Inmyección de los objetos de acceso a datos
    @Autowired
    private ArticulosDAO articulosDAO;

    @Autowired
    private CancionesDAO cancionesDAO;

    @Autowired
    private ComentariosDAO comentariosDAO;

    @Autowired
    private ComentarioLikeDAO comentarioLikeDAO;

    @Autowired
    private DiscJockeyDAO discJockeyDAO;

    @Autowired
    private DiscotecasDAO discotecasDAO;

    @Autowired
    private MultimediaDao multimediaDao;

    @Autowired
    private SesionesDAO sesionesDAO;

    @Autowired
    private TracklistDAO tracklistDAO;

    @Autowired
    private UsersDAO usersDAO;
    @Autowired
    private EmailService emailService;

    @Autowired
    private SesionDjDAO sesionDjDAO;

    @Autowired
    private ResidenciaDAO residenciaDAO;

    @Autowired
    private LocalidadeDAO localidadeDAO;

    @Autowired
    private ProvinciaDAO provinciaDAO;

    @Autowired
    GenerosMusicalesDAO generosMusicalesDAO;

    //Declaro los metodos para obtener los datos

    //METODOS PARA ARTICULOS

    //obtiene el listado paginado de articulos
    @Override
    public Page<ArticuloExtraListadoDTO> getTodosArticulos(Pageable pageable) {
        Page<ArticulosExtra> art = articulosDAO.findAll(pageable);
        return art.map(EntidadesMapper::toListadoDTO);
    }

    //obtiene un articulo concreto por ID
    @Override
    public ArticuloExtraDTO getArticuloById(Long id){
        Optional<ArticulosExtra> articulOpt = articulosDAO.findById(id);
        return articulOpt.map(EntidadesMapper::toArticulosListadoDTO).orElse(null);
    }

    //edita un articulo concreto por ID
    @Override
    @Transactional
    public ArticuloExtraDTO actualizarArticulo(Long id, ArticuloExtraDTO dto) {

        if (dto == null) {
            throw new EuskotekaException("Datos del artículo no proporcionados", HttpStatus.BAD_REQUEST);
        }

        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new EuskotekaException("El nombre del artículo es obligatorio", HttpStatus.BAD_REQUEST);
        }

        ArticulosExtra art = articulosDAO.findById(id)
                .orElseThrow(() -> new EuskotekaException("Artículo no encontrado", HttpStatus.NOT_FOUND));


        art.setNombreArt(dto.getNombre());
        art.setSubtitulo(dto.getSubtitulo());
        art.setTipoArt(dto.getTipo());
        art.setFoto(dto.getFoto());
        art.setTexto(dto.getTexto());

        ArticulosExtra guardado = articulosDAO.save(art);

        return toArticulosListadoDTO(guardado);
    }

    //---------------------------------







    //METODOS PARA CANCIONES

    //obitiene una lista de las canciones paginadas con filtros dinámicos
    @Override
    @Transactional
    public Page<CancionDTO> getCancionesFiltradas(List<Integer> anios, List<String> generos, String search, Pageable pageable) {

        //se construye especification que añade partes del where de la consulta
        Specification<Cancione> spec = Specification.where(CancionSpecification.hasYears(anios))
                .and(CancionSpecification.hasGeneros(generos))
                .and(CancionSpecification.hasSearch(search));

        //se hace la consulta
        Page<Cancione> canciones = cancionesDAO.findAll(spec, pageable);
        return canciones.map(EntidadesMapper::cancionDTO);
    }

    //crea un nuevo registro en canciones
    public CancionDTO crearCancion(CancionDTO dto) {

        if (dto == null)
            throw new EuskotekaException("Datos inválidos", HttpStatus.BAD_REQUEST);

        if (dto.getNombreTema() == null || dto.getNombreTema().isBlank()) {
            throw new EuskotekaException("El nombre del tema es obligatorio", HttpStatus.BAD_REQUEST);
        }

        if (dto.getArtista() == null || dto.getArtista().isBlank()) {
            throw new EuskotekaException("El artista es obligatorio", HttpStatus.BAD_REQUEST);
        }

        //buscar genero, si no existe crearlo
        GenerosMusicale genero = null;

        if (dto.getGenero() != null && !dto.getGenero().isBlank()) {

            genero = generosMusicalesDAO.findByGenero(dto.getGenero())
                    .orElseGet(() -> {
                        //crear nuevo género
                        GenerosMusicale nuevo = new GenerosMusicale();
                        nuevo.setGenero(dto.getGenero());
                        return generosMusicalesDAO.save(nuevo);
                    });
        }

        Cancione nueva = new Cancione();
        nueva.setArtista(dto.getArtista());
        nueva.setNombreTema(dto.getNombreTema());
        nueva.setAnio(dto.getAnio());
        nueva.setGenero(genero);
        nueva.setUrlYt(dto.getUrlYt());

        Cancione guardada = cancionesDAO.save(nueva);

        return EntidadesMapper.cancionDTO(guardada);
    }

    //edita las canciones segun un ID
    @Override
    @Transactional
    public CancionDTO actualizarCancion(Long id, CancionDTO dto) {

        if (dto == null) {
            throw new EuskotekaException("Datos inválidos", HttpStatus.BAD_REQUEST);
        }

        if (dto.getNombreTema() == null || dto.getNombreTema().isBlank()) {
            throw new EuskotekaException("El nombre de la canción es obligatorio", HttpStatus.BAD_REQUEST);
        }

        if (dto.getArtista() == null || dto.getArtista().isBlank()) {
            throw new EuskotekaException("El artista es obligatorio", HttpStatus.BAD_REQUEST);
        }

        Cancione cancion = cancionesDAO.findById(id)
                .orElseThrow(() -> new EuskotekaException("Canción no encontrada", HttpStatus.NOT_FOUND));

        cancion.setArtista(dto.getArtista());
        cancion.setNombreTema(dto.getNombreTema());
        cancion.setAnio(dto.getAnio());
        cancion.setUrlYt(dto.getUrlYt());

        //manejo del genero
        if (dto.getGenero() != null) {
            GenerosMusicale genero = generosMusicalesDAO.findByGenero(dto.getGenero())
                    .orElse(null);
            cancion.setGenero(genero);
        }

        Cancione guardada = cancionesDAO.save(cancion);
        return EntidadesMapper.cancionDTO(guardada);
    }

    //obtiene los filtros disponibles segun el seleccionado
    @Transactional
    public FiltrosDispCancionDTO getFiltrosCancionesDisponibles(List<Integer> anios, List<String> generos) {

        //obtiene lo años compatibles según los generos seleccionados
        List<Integer> aniosDisponibles = cancionesDAO.findDistinctYearsFiltered(generos);

        //obtiene los géneros compatibles según los años seleccionados
        List<String> generosDisponibles = cancionesDAO.findDistinctGenerosFiltered(anios);

        //transforma en DTO con compatible = true si está en la lista de disponibles
        List<FiltroOptionDTO> aniosDTO = aniosDisponibles.stream()
                .map(a -> new FiltroOptionDTO(a.toString(), true))
                .collect(Collectors.toList());

        List<FiltroOptionDTO> generosDTO = generosDisponibles.stream()
                .map(g -> new FiltroOptionDTO(g, true))
                .collect(Collectors.toList());

        return new FiltrosDispCancionDTO(aniosDTO, generosDTO);
    }


    //----------------------------







    //METODOS PARA COMENTARIOS

    //elimina un comentario segun su id
    public boolean eliminarComentario(Long id, String token) {

        if (token == null || token.isBlank()) return false;

        Optional<UsuarioLite> usuarioOpt = usersDAO.findByToken(token);
        if (usuarioOpt.isEmpty()) return false;

        Comentario comentario = comentariosDAO.findById(id).orElse(null);
        if (comentario == null) return false;

        comentario.setEliminado(true);
        comentario.setContenido("[Comentario eliminado]");

        comentariosDAO.save(comentario);
        return true;
    }

    //obtiene una lista páginada con todos los comentarios padre segun el id y tipo de objetivo
    public Page<ComentarioDTO> obtenerComentariosArbol(
            String tipoObjetivo,
            Integer idObjetivo,
            int page,
            int size
    ) {

        Page<Comentario> padres = comentariosDAO
                .findByTipoObjetivoAndIdObjetivoAndComentarioPadreIsNullOrderByCreatedAtDesc(
                        tipoObjetivo,
                        idObjetivo,
                        PageRequest.of(page, size)
                );

        //para cada padre, construye el árbol recursivo
        List<ComentarioDTO> dtos = padres.getContent()
                .stream()
                .map(this::construirArbol)
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, padres.getPageable(), padres.getTotalElements());
    }

    //construye de forma recursiva el arbol de comentrios segun el padre
    private ComentarioDTO construirArbol(Comentario c) {

        ComentarioDTO dto = new ComentarioDTO(c);

        //obtiene los comentarios que tienen como padre al actul
        List<Comentario> hijos = comentariosDAO
                .findByComentarioPadreIdComentOrderByCreatedAtAsc(c.getIdComent());

        //construye recursivamente el arbol por cada comentario hasta que ya no haya
        List<ComentarioDTO> hijosDTO = hijos.stream()
                .map(this::construirArbol)
                .collect(Collectors.toList());

        dto.setRespuestas(hijosDTO);

        return dto;
    }

    //escribe el comentario
    @Override
    public ComentarioDTO crearComentario(ComentarioCreateDTO dto, String token) {

        if (token == null || token.isBlank()) {
            throw new EuskotekaException("Debes iniciar sesión para comentar",
                    HttpStatus.UNAUTHORIZED);
        }

        UsuarioLite user = usersDAO.findByToken(token)
                .orElseThrow(() -> new EuskotekaException("Token inválido",
                        HttpStatus.UNAUTHORIZED));

        if (dto.getContenido() == null || dto.getContenido().isBlank()) {
            throw new EuskotekaException("El comentario no puede estar vacío",
                    HttpStatus.BAD_REQUEST);
        };

        Comentario nuevo = new Comentario();
        nuevo.setUser(user);
        nuevo.setContenido(dto.getContenido());
        nuevo.setTipoObjetivo(dto.getTipoObjetivo());
        nuevo.setIdObjetivo(dto.getIdObjetivo());
        nuevo.setLikes(0);
        nuevo.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        //asigna el comentario nuevo a un padre si es una respuesta
        if (dto.getIdComentarioPadre() != null) {
            Comentario padre = comentariosDAO.findById(dto.getIdComentarioPadre())
                    .orElseThrow(() -> new EuskotekaException(
                            "Comentario padre no encontrado", HttpStatus.NOT_FOUND));

            if (padre.isEliminado()) {
                throw new EuskotekaException(
                        "No puedes responder a un comentario eliminado",
                        HttpStatus.CONFLICT);
            }

            nuevo.setComentarioPadre(padre);
        }

        Comentario guardado = comentariosDAO.save(nuevo);
        return new ComentarioDTO(guardado);
    }

    //aumenta el valor de me gustas de un comentario segun su id
    @Transactional
    public Map<String, Object> toggleLikeComentario(Long idComentario, String token) {

        if (token == null || token.isBlank()) {
            throw new EuskotekaException("Inicia sesión para dar like",
                    HttpStatus.UNAUTHORIZED);
        }

        //busca al usuario
        UsuarioLite usuario = usersDAO.findByToken(token)
                .orElseThrow(() -> new EuskotekaException("Token inválido",
                        HttpStatus.UNAUTHORIZED));

        //busca el comentario
        Comentario comentario = comentariosDAO.findById(idComentario)
                .orElseThrow(() -> new EuskotekaException("Comentario no encontrado",
                        HttpStatus.NOT_FOUND));

        Optional<ComentarioLike> existing =
                comentarioLikeDAO.findByComentarioAndUsuario(comentario, usuario);

        boolean liked;

        //si usuario le da like a un comentario al que ya le ha dado like
        if (existing.isPresent()) {
            comentarioLikeDAO.delete(existing.get());
            comentario.setLikes(comentario.getLikes() - 1); //se resta
            liked = false;
        } else {
            ComentarioLike nuevo = new ComentarioLike();
            nuevo.setComentario(comentario);
            nuevo.setUsuario(usuario);
            comentarioLikeDAO.save(nuevo);
            comentario.setLikes(comentario.getLikes() + 1); //se aumenta el valor del like
            liked = true;
        }

        comentariosDAO.save(comentario);

        return Map.of(
                "liked", liked,
                "message", liked ? "👍 Like añadido" : "👎 Like eliminado"
        );
    }

    //----------------------------







    //METODOS PARA RESIDENCIAS

    //obtiene las residencias segun el id de discoteca
    public List<ResidenciaDTO> getResidenciasPorDiscoteca(Long idDiscoteca) {
        List<Residencia> residencias = residenciaDAO.findByIdDiscoteca_Id(idDiscoteca);

        return residencias.stream()
                .map(r -> new ResidenciaDTO(
                        r.getId(),
                        r.getIdDj().getNombreDj(),
                        r.getFechaInicio(),
                        r.getFechaFin(),
                        r.getComentariosResi()
                ))
                .collect(Collectors.toList());
    }

    //crea nueva residencia para la discoteca del id
    @Override
    @Transactional
    public ResidenciaDTO crearResidencia(Long idDiscoteca, ResidenciaCreateDTO dto) {

        if (dto.getNombreDj() == null || dto.getNombreDj().trim().isEmpty()) {
            throw new EuskotekaException("El nombre del DJ no puede estar vacío", HttpStatus.BAD_REQUEST);
        }

        Discoteca discoteca = discotecasDAO.findById(idDiscoteca)
                .orElseThrow(() -> new EuskotekaException("Discoteca no encontrada", HttpStatus.NOT_FOUND));

        //se busca al DJ, si no está se crea
        DiscJockey dj = discJockeyDAO.findByNombreDjIgnoreCase(dto.getNombreDj().trim())
                .orElseGet(() -> {
                    DiscJockey nuevo = new DiscJockey();
                    nuevo.setNombreDj(dto.getNombreDj().trim());
                    return discJockeyDAO.save(nuevo);
                });

        //crear la residencia
        Residencia res = new Residencia();
        res.setIdDj(dj);
        res.setIdDiscoteca(discoteca);
        res.setFechaInicio(dto.getFechaInicio());
        res.setFechaFin(dto.getFechaFin());
        res.setComentariosResi(dto.getComentariosResi());

        residenciaDAO.save(res);

        return new ResidenciaDTO(
                res.getId(), dj.getNombreDj(), res.getFechaInicio(),
                res.getFechaFin(), res.getComentariosResi()
        );
    }

    //edita la residencia
    @Override
    @Transactional
    public ResidenciaDTO editarResidencia(Long id, ResidenciaCreateDTO dto) {

        Residencia res = residenciaDAO.findById(id)
                .orElseThrow(() -> new EuskotekaException("Residencia no encontrada", HttpStatus.NOT_FOUND));

        if (dto.getNombreDj() == null || dto.getNombreDj().trim().isEmpty()) {
            throw new EuskotekaException("El nombre del DJ no puede estar vacío", HttpStatus.BAD_REQUEST);
        }

        //si cambia el dj se obtiene el nuevo o se crea
        if (dto.getNombreDj() != null) {
            DiscJockey dj = discJockeyDAO.findByNombreDjIgnoreCase(dto.getNombreDj().trim())
                    .orElseGet(() -> {
                        DiscJockey nuevo = new DiscJockey();
                        nuevo.setNombreDj(dto.getNombreDj().trim());
                        return discJockeyDAO.save(nuevo);
                    });
            res.setIdDj(dj);
        }

        res.setFechaInicio(dto.getFechaInicio());
        res.setFechaFin(dto.getFechaFin());
        res.setComentariosResi(dto.getComentariosResi());

        residenciaDAO.save(res);

        return new ResidenciaDTO(
                res.getId(),
                res.getIdDj().getNombreDj(),
                res.getFechaInicio(),
                res.getFechaFin(),
                res.getComentariosResi()
        );
    }

    //borra la residencia segun el id
    @Override
    @Transactional
    public void eliminarResidencia(Long id) {
        residenciaDAO.deleteById(id);
    }

    //----------------------------







    //METODOS PARA DISCOTECAS

    //obtiene un listas paginada con todas las discotecas y con sus filtros aplicados
    @Override
    @Transactional
    public Page<DiscotecaListadoDTO> getDiscotecasFiltradas(
            List<Integer> anios,
            List<String> provincias,
            List<String> localidades,
            Pageable pageable
    ){
        //se construye especification que añade partes del where de la consulta
        Specification<Discoteca> spec = Specification.where(
                        DiscotecaSpecification.isActiveInYear(anios)
                )
                .and(DiscotecaSpecification.hasProvince(provincias))
                .and(DiscotecaSpecification.hasLocalidad(localidades));

        Page<Discoteca> discotecas = discotecasDAO.findAll(spec, pageable);

        return discotecas.map(EntidadesMapper::toListadoDTO);
    }

    //obtiene los filtros disponibles de discotecas
    @Transactional
    public FiltrosDispDiscotecaDTO getFiltrosDisponiblesDiscotecas() {

        //provincias distintas de la tabla localidades
        List<String> provincias = localidadeDAO.findAll().stream()
                .map(l -> l.getIdProvincia().getProvincia())
                .distinct()
                .toList();

        //convertir a DTO
        List<FiltroOptionDTO> provinciasDTO = provincias.stream()
                .map(FiltroOptionDTO::new)  // Si tu constructor acepta solo el value
                .toList();

        return new FiltrosDispDiscotecaDTO(List.of(), provinciasDTO);
    }

    //obtiene una discoteca concreta segun el id
    @Override
    @Transactional
    public List<DiscotecaDTO> getDiscotecasById(Integer id){
        List<Discoteca> discoteca = discotecasDAO.findById(id);
        return discoteca.stream().map(EntidadesMapper::DTODiscoteca)
                .collect(Collectors.toList());
    }

    //crea una nueva discoteca
    @Transactional
    public DiscotecaDTO crearDiscoteca(DiscotecaDTO dto) {
        if (dto == null) {
            throw new EuskotekaException("Datos inválidos", HttpStatus.BAD_REQUEST);
        }

        Discoteca disco = new Discoteca();

        disco.setNombreDisco(dto.getNombre());
        disco.setHistoriaDisco(dto.getHistoria());
        disco.setFechaAperturaDisco(dto.getFechaApertura());
        disco.setFechaCierreDisco(dto.getFechaCierre());
        disco.setDuennoDisco(dto.getDuenno());
        disco.setFotoDisco(dto.getFoto());
        disco.setFotoDiscoCalidad(dto.getFotoCalidad());
        disco.setLogoDisco(dto.getLogo());

        //busca o crea la localidad o provincia
        Localidade localidad = obtenerOCrearLocalidadYProvincia(
                dto.getLocalidad(),
                dto.getProvincia()
        );

        disco.setLocalidadDisco(localidad);

        Discoteca guardada = discotecasDAO.save(disco);
        return EntidadesMapper.DTODiscoteca(guardada);
    }


    //comprueba la localidad y provincia que se pone al crear una discoteca
    private Localidade obtenerOCrearLocalidadYProvincia(String nombreLocalidad, String nombreProvincia) {

        if (nombreLocalidad == null || nombreLocalidad.isBlank() ||
                nombreProvincia == null || nombreProvincia.isBlank()) {

            throw new EuskotekaException(
                    "Localidad y provincia son obligatorias.",
                    HttpStatus.BAD_REQUEST
            );
        }

        //busca provincia por nombre, si no está la crea
        Provincia provincia = provinciaDAO.findByProvincia(nombreProvincia)
                .orElseGet(() -> {
                    Provincia nueva = new Provincia();
                    nueva.setProvincia(nombreProvincia);
                    return provinciaDAO.save(nueva);
                });

        //busca localidad por nombre, si no está la crea
        return localidadeDAO.findByNombreLocalidad(nombreLocalidad)
                .orElseGet(() -> {
                    Localidade nuevaLoc = new Localidade();
                    nuevaLoc.setNombreLocalidad(nombreLocalidad);
                    nuevaLoc.setIdProvincia(provincia);
                    return localidadeDAO.save(nuevaLoc);
                });
    }

    //edita la discoteca segun el ID
    @Override
    public DiscotecaDTO actualizarDiscoteca(Long id, DiscotecaDTO dto) {
        Discoteca disco = discotecasDAO.findById(id)
                .orElseThrow(() -> new EuskotekaException("Discoteca no encontrada", HttpStatus.NOT_FOUND));

        disco.setNombreDisco(dto.getNombre());
        disco.setHistoriaDisco(dto.getHistoria());
        disco.setFechaAperturaDisco(dto.getFechaApertura());
        disco.setFechaCierreDisco(dto.getFechaCierre());
        disco.setDuennoDisco(dto.getDuenno());
        disco.setFotoDisco(dto.getFoto());
        disco.setFotoDiscoCalidad(dto.getFotoCalidad());
        disco.setLogoDisco(dto.getLogo());

        //actualizar localidad
        if (dto.getLocalidad() != null && !dto.getLocalidad().isBlank()) {
            localidadeDAO.findByNombreLocalidad(dto.getLocalidad())
                    .ifPresentOrElse(
                            disco::setLocalidadDisco,
                            () -> {
                                throw new EuskotekaException(
                                        "La localidad '" + dto.getLocalidad() + "' no existe en la base de datos.",
                                        HttpStatus.BAD_REQUEST
                                );
                            }
                    );
        }
        Discoteca guardada = discotecasDAO.save(disco);
        return EntidadesMapper.DTODiscoteca(guardada);
    }

    //----------------------------







    //METODOS PARA MULTIMEDIA

    //obtiene un lista paginada con todos los materiales multimedia
    @Override
    @Transactional
    public Page<MultimediaDTO> getMultimedia(Pageable pageable){
        Page<Multimedia> multimedia = multimediaDao.findAll(pageable);
        return multimedia.map(EntidadesMapper::DTOMultimedia);
    }

    //----------------------------







    //METODOS PARA SESIONES


    //obtien una lista paginada con todas las sesiones, con opción de filtros
    @Override
    @Transactional
    public Page<SesionDTO> getSesionesFiltradas(List<Integer> anios, List<String> discotecas, List<String> djs, Pageable pageable) {
        //se construye especification que añade partes del where de la consulta
        Specification<Sesione> spec = Specification.where(SesionSpecification.hasYears(anios))
                .and(SesionSpecification.hasDiscotecas(discotecas))
                .and(SesionSpecification.hasDjs(djs));

        Page<Sesione> sesiones = sesionesDAO.findAll(spec, pageable);
        return sesiones.map(EntidadesMapper::DTOSesion);
    }

    //obtiene una sesion segun el id
    @Override
    @Transactional
    public List<SesionDTO> getSesionesById(Integer id){
        List<Sesione> sesiones = sesionesDAO.findById(id);
        return sesiones.stream().map(EntidadesMapper::DTOSesion)
                .collect(Collectors.toList());
    }

    //obtiene los valores disponibles para los filtros
    @Override
    @Transactional
    public FiltrosDisponiblesDTO getFiltrosDisponibles(List<Integer> anios, List<String> djs, List<String> discotecas) {

        //años compatibles: filtrar por DJs y discotecas seleccionadas
        List<Integer> aniosDisponibles = sesionesDAO.findDistinctYearsFiltered(null, discotecas, djs);

        //djs compatibles: NO filtrar por DJs seleccionados
        List<String> djsDisponibles = sesionesDAO.findDistinctDjsFiltered(anios, discotecas, null);

        //discotecas compatibles: NO filtrar por discotecas seleccionadas
        List<String> discotecasDisponibles = sesionesDAO.findDistinctDiscotecasFiltered(anios, djs, null);

        //transformar a DTO
        List<FiltroOptionDTO> aniosDTO = sesionesDAO.findAllDistinctYears().stream()
                .map(a -> new FiltroOptionDTO(a.toString(), aniosDisponibles.contains(a)))
                .collect(Collectors.toList());

        List<FiltroOptionDTO> djsDTO = sesionesDAO.findAllDistinctDjNames().stream()
                .map(dj -> new FiltroOptionDTO(dj, djsDisponibles.contains(dj)))
                .collect(Collectors.toList());

        List<FiltroOptionDTO> discotecasDTO = sesionesDAO.findAllDistinctDiscotecas().stream()
                .map(disco -> new FiltroOptionDTO(disco, discotecasDisponibles.contains(disco)))
                .collect(Collectors.toList());

        return new FiltrosDisponiblesDTO(aniosDTO, djsDTO, discotecasDTO);
    }

    //obtiene un lista paginada con todas las sesiones donde hay una cancion especifica
    @Override
    @Transactional//(readOnly = true)
    public Page<SesionDTO> getSesionesByCancionId(Long idCancion, Pageable pageable) {
        //obtiene los IDs de sesiones donde suena la canción
        List<Long> idsSesiones = tracklistDAO.findSesionesIdsByCancionId(idCancion);

        if (idsSesiones == null || idsSesiones.isEmpty()) {
            return Page.empty(pageable);
        }

        //con los ids, obtiene las sesiones completas, paginadas y ordenadas
        Page<Sesione> sesionesPage = sesionesDAO.findByIdIn(idsSesiones, pageable);

        return sesionesPage.map(EntidadesMapper::DTOSesion);
    }



    //edita una sesión segun su id
    @Override
    public SesionDTO actualizarSesion(Long id, SesionDTO sesionDTO) {
        Sesione sesion = sesionesDAO.findById(id)
                .orElseThrow(() -> new EuskotekaException("Sesión no encontrada", HttpStatus.NOT_FOUND));

        sesion.setNombreSesion(sesionDTO.getNombreSesion());
        sesion.setInfoSesion(sesionDTO.getInfoSesion());
        sesion.setCaratulaFoto(sesionDTO.getCaratulaFoto());
        sesion.setCaratulaFotoCalidad(sesionDTO.getCaratulaFotoCalidad());
        sesion.setUrlSesion(sesionDTO.getUrlSesion());
        sesion.setConservacionSesion(sesionDTO.getConservacionSesion());
        sesion.setMedioSesion(sesionDTO.getMedioSesion());
        sesion.setDuennoMedio(sesionDTO.getDuennoMedio());
        sesion.setIneditaSesion(sesionDTO.getIneditaSesion());
        sesion.setDigitalizadoSesion(sesionDTO.getDigitalizadoSesion());

        //actualizamos la fecha (año, mes y día) si viene formateada como "dd/MM/yyyy" o "MM/yyyy" o solo "yyyy"
        if (sesionDTO.getFechaSesion() != null && !sesionDTO.getFechaSesion().isEmpty()) {
            try {
                String[] partes = sesionDTO.getFechaSesion().split("/");

                if (partes.length == 3) { //dd/MM/yyyy
                    sesion.setFechaSesionDay(Short.parseShort(partes[0]));
                    sesion.setFechaSesionMonth(Short.parseShort(partes[1]));
                    sesion.setFechaSesionYear(Integer.parseInt(partes[2]));
                } else if (partes.length == 2) { //MM/yyyy
                    sesion.setFechaSesionDay(null);
                    sesion.setFechaSesionMonth(Short.parseShort(partes[0]));
                    sesion.setFechaSesionYear(Integer.parseInt(partes[1]));
                } else if (partes.length == 1) { //yyyy
                    sesion.setFechaSesionDay(null);
                    sesion.setFechaSesionMonth(null);
                    sesion.setFechaSesionYear(Integer.parseInt(partes[0]));
                }
            } catch (NumberFormatException e) {
                throw new EuskotekaException("Formato de fecha inválido. Usa dd/MM/yyyy, MM/yyyy o yyyy.", HttpStatus.BAD_REQUEST);
            }
        }

        Sesione guardada = sesionesDAO.save(sesion);

        return DTOSesion(guardada);
    }

    //crea una nueva sesion
    @Override
    @Transactional
    public SesionDTO crearSesion(SesionDTO dto) {

        Sesione s = new Sesione();

        s.setNombreSesion(dto.getNombreSesion());
        s.setInfoSesion(dto.getInfoSesion());
        s.setCaratulaFoto(dto.getCaratulaFoto());
        s.setCaratulaFotoCalidad(dto.getCaratulaFotoCalidad());
        s.setUrlSesion(dto.getUrlSesion());
        s.setConservacionSesion(dto.getConservacionSesion());
        s.setMedioSesion(dto.getMedioSesion());
        s.setDuennoMedio(dto.getDuennoMedio());
        s.setIneditaSesion(dto.getIneditaSesion());
        s.setDigitalizadoSesion(dto.getDigitalizadoSesion());

        //manejo de fecha segun que formato le llega
        if (dto.getFechaSesion() != null && !dto.getFechaSesion().isEmpty()) {
            String[] partes = dto.getFechaSesion().split("/");
            if (partes.length == 3) {
                s.setFechaSesionDay(Short.parseShort(partes[0]));
                s.setFechaSesionMonth(Short.parseShort(partes[1]));
                s.setFechaSesionYear(Integer.parseInt(partes[2]));
            } else if (partes.length == 2) {
                s.setFechaSesionDay(null);
                s.setFechaSesionMonth(Short.parseShort(partes[0]));
                s.setFechaSesionYear(Integer.parseInt(partes[1]));
            } else {
                s.setFechaSesionDay(null);
                s.setFechaSesionMonth(null);
                s.setFechaSesionYear(Integer.parseInt(partes[0]));
            }
        }

        //asigna una discoteca a la sesion
        if (dto.getNombreDiscoteca() != null && !dto.getNombreDiscoteca().isEmpty()) {
            Discoteca disco = discotecasDAO.findByNombreDiscoIgnoreCase(dto.getNombreDiscoteca())
                    .orElseThrow(() -> new EuskotekaException("Discoteca no encontrada", HttpStatus.NOT_FOUND));

            s.setIdDiscoteca(disco);
        }

        s.setId(null);
        Sesione guardada = sesionesDAO.save(s);

        //retilizamos el metodo de guardar DJs de una sesion
        if (dto.getDjs() != null) {
            actualizarDjsSesion(guardada.getId(), dto.getDjs());
        }

        return DTOSesion(guardada);
    }

    //edita los Djs que pinchan una sesion concret, segun el id
    @Override
    @Transactional
    public SesionDTO actualizarDjsSesion(Long idSesion, List<String> nuevosDjs) {
        Sesione sesion = sesionesDAO.findById(idSesion)
                .orElseThrow(() -> new EuskotekaException("Sesión no encontrada", HttpStatus.NOT_FOUND));

        //borra todas las relaciones actuales
        sesionDjDAO.deleteAllByIdSesion(sesion);

        //crea nuevas relaciones
        List<SesionDj> nuevasRelaciones = new ArrayList<>();

        for (String nombreDj : nuevosDjs) {
            if (nombreDj == null || nombreDj.trim().isEmpty()) continue;

            //busca DJ por nombre, si no lo crea
            DiscJockey dj = discJockeyDAO.findByNombreDjIgnoreCase(nombreDj.trim())
                    .orElseGet(() -> {
                        DiscJockey nuevoDj = new DiscJockey();
                        nuevoDj.setNombreDj(nombreDj.trim());
                        return discJockeyDAO.save(nuevoDj);
                    });

            SesionDj sesionDj = new SesionDj();
            sesionDj.setIdSesion(sesion);
            sesionDj.setIdDj(dj);
            nuevasRelaciones.add(sesionDj);
        }

        sesionDjDAO.saveAll(nuevasRelaciones);

        Sesione actualizada = sesionesDAO.findById(idSesion).get();
        return EntidadesMapper.DTOSesion(actualizada);
    }

    //----------------------------







    //METODOS DE TRACKLIST

    //obtiene una lista de una tracklist segun el id de sesion
    @Override
    public List<TracklistDTO> getTracklistBySesion(Long idSesion){
        return tracklistDAO.findBySesionId(idSesion);
    }

    //----------------------------







    //METODOS DE LOGIN Y REGISTRO

    //REGISTRAR USUARIO
    @Override
    public UsuarioLiteDTO registrarUsuario(String username, String email) {
        if (username == null || username.isBlank()) {
            throw new EuskotekaException("El nombre de usuario no puede estar vacío", HttpStatus.BAD_REQUEST);
        }

        if (email == null || email.isBlank()) {
            throw new EuskotekaException("El correo electrónico no puede estar vacío", HttpStatus.BAD_REQUEST);
        }

        Optional<UsuarioLite> userExistente = usersDAO.findByUsername(username);
        if (userExistente.isPresent()) {
            throw new EuskotekaException("El nombre de usuario ya existe", HttpStatus.CONFLICT);
        }

        Optional<UsuarioLite> emailExistente = usersDAO.findByEmail(email);
        if (emailExistente.isPresent()) {
            throw new EuskotekaException("La dirección de correo ya existe", HttpStatus.CONFLICT);
        }

        UsuarioLite nuevo = new UsuarioLite();
        nuevo.setUsername(username);
        nuevo.setEmail(email);
        nuevo.setRol("USER"); // Rol por defecto
        nuevo.setToken(UUID.randomUUID().toString().replace("-", ""));
        nuevo.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        UsuarioLite guardado = usersDAO.save(nuevo);

        if (email != null && !email.isEmpty()) {
            emailService.enviarCorreoVerificacion(email, username, guardado.getToken());
        }

        return new UsuarioLiteDTO(guardado);
    }

    //VERIFICAR REGISTRO
    @Override
    public ResponseEntity<Void> verificarCuenta(String token, HttpServletResponse response) {
        System.out.println("⌛ VERIFICANDO CUENTA TRAS REGISTRO");
            Optional<UsuarioLite> usuarioOpt = usersDAO.findByToken(token);

            // if (usuarioOpt.isEmpty()) {
            //     response.sendRedirect("https://euskoteka-front.vercel.app/verificacionFallida");
            //     return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            // }

            UsuarioLite usuario = usuarioOpt.get();

            usersDAO.save(usuario);

            Cookie cookie = new Cookie("auth_token", usuario.getToken());
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(30 * 24 * 60 * 60); // 30 días
            cookie.setAttribute("SameSite" , "None");
            cookie.setDomain("https://eukoteka-api.onrender.com");
            response.addCookie(cookie);

            //response.sendRedirect("https://euskoteka-front.vercel.app/verificacionRegistroExitosa");
            System.out.println("✅ Registro verificado y cookie creada para: " + usuario.getUsername());
            return ResponseEntity.ok().build();
    }

    //INICIAR SESION
    @Override
    public void iniciarSesion(String email) {
        if (email == null || email.isBlank()) {
            throw new EuskotekaException("El correo electornico es obligatorio", HttpStatus.BAD_REQUEST);
        }

        Optional<UsuarioLite> usuarioOpt = usersDAO.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            throw new EuskotekaException("No existe ninguna cuenta con ese correo.", HttpStatus.NOT_FOUND);
        }

        UsuarioLite usuario = usuarioOpt.get();

        //Generar nuevo token para este login
        String nuevoToken = UUID.randomUUID().toString().replace("-", "");
        usuario.setToken(nuevoToken);
        usersDAO.save(usuario);

        //Enviar correo de verificación de inicio de sesión
        emailService.enviarCorreoLogin(usuario.getEmail(), usuario.getUsername(), nuevoToken);
    }

    //VERIFICAR LOGIN
    @Override
    public ResponseEntity<UsuarioLiteDTO> verificarLogin(String token, HttpServletResponse response) {
        System.out.println("⌛ VERIFICANDO TRAS CAMBIO DEL CORS: ");
        
            Optional<UsuarioLite> usuarioOpt = usersDAO.findByToken(token);

            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            UsuarioLite usuario = usuarioOpt.get();

            //Crear cookie con el nuevo token
            Cookie cookie = new Cookie("auth_token", usuario.getToken());
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge(30 * 24 * 60 * 60); // 30 días
            cookie.setAttribute("SameSite", "None");
            //lo de arriba obligatorio si el front está en otro dominio
            cookie.setDomain("eukoteka-api.onrender.com");
            response.addCookie(cookie);

            // response.sendRedirect("https://euskoteka-front.vercel.app/verificarLogin");

            //response.sendRedirect(null);
            System.out.println("✅ Login verificado y cookie creada para: " + usuario.getUsername());
            return ResponseEntity.ok(new UsuarioLiteDTO(usuario));
        
    }

}



