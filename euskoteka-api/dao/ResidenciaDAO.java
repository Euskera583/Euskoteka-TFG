package com.euskoteka.euskoteka_api.dao;

import com.euskoteka.euskoteka_api.entity.Residencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResidenciaDAO  extends JpaRepository <Residencia, Long> {

    //cosnulta que devuelve las residencias de un dj con discoteca segun el id de la discoteca
    List<Residencia> findByIdDiscoteca_Id(Long idDiscoteca);

    //consulta que devuelve la residencia segun si id, comprueba si existe o no
    Optional<Residencia> findById(Long id);
}
