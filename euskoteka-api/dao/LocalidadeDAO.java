package com.euskoteka.euskoteka_api.dao;

import com.euskoteka.euskoteka_api.entity.Localidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalidadeDAO extends JpaRepository <Localidade, Long> {

    //consulta que devuelve el id de la localidad segun su nombre, comprueba si existe o no
    Optional<Localidade> findByNombreLocalidad(String nombreLocalidad);
}
