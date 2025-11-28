package com.euskoteka.euskoteka_api.dao;
import com.euskoteka.euskoteka_api.entity.UsuarioLite;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersDAO extends JpaRepository <UsuarioLite, Long> {

    //consulta que devuelve un usuario segun su nombre, comprueba si existe o no
    Optional<UsuarioLite> findByUsername (String username);

    //consulta que devuelve un usuario segun su email, comprueba si existe o no
    Optional<UsuarioLite> findByEmail (String email);

    //consulta que devuelve un usuario segun su token, comprueba si existe o no
    Optional<UsuarioLite> findByToken (String token);



}
