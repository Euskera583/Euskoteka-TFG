package com.euskoteka.euskoteka_api.dao;

import com.euskoteka.euskoteka_api.entity.DiscJockey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiscJockeyDAO extends JpaRepository <DiscJockey, Long> {

    //consulta que devuelve el id del DJ segun su nombre, comprueba si existe o no
    Optional<DiscJockey> findByNombreDjIgnoreCase(String nombreDj);
}
