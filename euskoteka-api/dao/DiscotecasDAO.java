package com.euskoteka.euskoteka_api.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.euskoteka.euskoteka_api.entity.Discoteca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscotecasDAO extends JpaRepository <Discoteca, Long>, JpaSpecificationExecutor<Discoteca> {

    //consulta que devuelve una discoteca concreta
    List<Discoteca> findById(Integer id);

    //consulta que devuelve los años donde hay almenos una discoteca abierta (para filtrar)
    @Query("SELECT DISTINCT d.fechaAperturaDisco FROM Discoteca d ORDER BY d.fechaAperturaDisco ASC")
    List<Integer> findAllDistinctYears();

    //consulta que devuelve las provincias donde hay almenos una discoteca (para filtrar)
    @Query("SELECT DISTINCT p.provincia FROM Discoteca d JOIN d.localidadDisco l JOIN l.idProvincia p ORDER BY p.provincia ASC")
    List<String> findAllDistinctProvincias();

    //consulta que devuelve las localidades donde hay almenos una discoteca (para filtrar)
    @Query("SELECT DISTINCT l.nombreLocalidad FROM Discoteca d JOIN d.localidadDisco l ORDER BY l.nombreLocalidad ASC")
    List<String> findAllDistinctLocalidades();

    //consulta que devuelve el nombre de una discoteca, para crear disco nueva y comprobar si ya existe
    Optional<Discoteca> findByNombreDiscoIgnoreCase(String nombreDisco);
}
