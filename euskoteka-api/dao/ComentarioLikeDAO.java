package com.euskoteka.euskoteka_api.dao;

import com.euskoteka.euskoteka_api.entity.Comentario;
import com.euskoteka.euskoteka_api.entity.ComentarioLike;
import com.euskoteka.euskoteka_api.entity.UsuarioLite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComentarioLikeDAO extends JpaRepository <ComentarioLike, Long> {

    //consulta que devuelve la información de un comentario concrecto
    Optional<ComentarioLike> findByComentarioAndUsuario(Comentario comentario, UsuarioLite usuario);
}
