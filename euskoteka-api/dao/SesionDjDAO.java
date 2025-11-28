package com.euskoteka.euskoteka_api.dao;

import com.euskoteka.euskoteka_api.entity.SesionDj;
import com.euskoteka.euskoteka_api.entity.Sesione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionDjDAO extends JpaRepository <SesionDj, Long> {

    //consulta que borra todas las relaciones entre un DJ y una sesión concreta
    @Modifying
    @Query("DELETE FROM SesionDj sd WHERE sd.idSesion = :sesion")
    void deleteAllByIdSesion(@Param("sesion") Sesione sesion);

}
