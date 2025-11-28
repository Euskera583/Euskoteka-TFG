package com.euskoteka.euskoteka_api.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.euskoteka.euskoteka_api.entity.Cancione;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CancionesDAO extends JpaRepository<Cancione, Long>, JpaSpecificationExecutor<Cancione> {

    //consulta que devuelve todas las canciones con el nombre del genero
    @Query("SELECT c FROM Cancione c JOIN FETCH c.genero ORDER BY c.id")
    List<Cancione> findAllWithGenero();

    //consulta que devuelve los años disponibles para filtros
    @Query("SELECT DISTINCT c.anio FROM Cancione c " +
            "WHERE (:generos IS NULL OR c.genero.genero IN :generos) " +
            "ORDER BY c.anio ASC")
    List<Integer> findDistinctYearsFiltered(List<String> generos);

    //consulta que devuelve los generos disponibles para filtros
    @Query("SELECT DISTINCT c.genero.genero FROM Cancione c " +
            "WHERE (:anios IS NULL OR c.anio IN :anios) " +
            "ORDER BY c.genero.genero ASC")
    List<String> findDistinctGenerosFiltered(List<Integer> anios);
}
