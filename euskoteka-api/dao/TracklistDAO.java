    package com.euskoteka.euskoteka_api.dao;
    
    import com.euskoteka.euskoteka_api.dto.TracklistDTO;
    import com.euskoteka.euskoteka_api.entity.Sesione;
    import com.euskoteka.euskoteka_api.entity.Tracklist;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;
    
    import java.util.List;

    //consulta que devuelve una tracklist segun el id de la sesión
    public interface TracklistDAO extends JpaRepository<Tracklist, Long> {
        @Query("SELECT new com.euskoteka.euskoteka_api.dto.TracklistDTO(" +
                "t.numeroPista, " +
                "c.id, " +
                "c.artista, " +
                "c.nombreTema) " +
                "FROM Tracklist t " +
                "LEFT JOIN t.idCancion c " +
                "WHERE t.idSesion.id = :idSesion " +
                "ORDER BY t.numeroPista ASC")
        List<TracklistDTO> findBySesionId(@Param("idSesion") Long idSesion);
    
        //consulta que devuelve una lista con los IDs de las sesiones donde suena una canción concreta
        @Query("SELECT DISTINCT t.idSesion.id FROM Tracklist t WHERE t.idCancion.id = :idCancion")
        List<Long> findSesionesIdsByCancionId(@Param("idCancion") Long idCancion);
    }