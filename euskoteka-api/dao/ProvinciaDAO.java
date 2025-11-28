package com.euskoteka.euskoteka_api.dao;

import com.euskoteka.euskoteka_api.entity.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProvinciaDAO extends JpaRepository <Provincia, Long> {

    //consulta que devuelve el id de la provincia segun su nombre, comprueba si existe o no
    Optional<Provincia> findByProvincia(String provincia);
}