package com.euskoteka.euskoteka_api.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.euskoteka.euskoteka_api.entity.Comentario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface ComentariosDAO extends JpaRepository <Comentario, Long>{

    //consulta que devuelve todos lso comentarios padre, fecha descendiente
    @EntityGraph(attributePaths = {"user"})
    Page<Comentario> findByTipoObjetivoAndIdObjetivoAndComentarioPadreIsNullOrderByCreatedAtDesc(
            String tipoObjetivo,
            Integer idObjetivo,
            Pageable pageable
    );

    //consulta que devuelve todos los comentarios hijos de un padre, ordenados por fecha ascendente
    @EntityGraph(attributePaths = {"user"})
    List<Comentario> findByComentarioPadreIdComentOrderByCreatedAtAsc(Long padreId);

}
