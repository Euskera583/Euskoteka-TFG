package com.euskoteka.euskoteka_api.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import com.euskoteka.euskoteka_api.entity.Sesione;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface SesionesDAO extends JpaRepository <Sesione, Long>, JpaSpecificationExecutor<Sesione> {

    //get todas las sesiones, incluyendo DJ's, ordenado por fecha
    //este es el metodo que trae toas las seisones, y que tiene los filtros
    @EntityGraph(attributePaths = {"sesionDjs.idDj", "idDiscoteca"})
    Page<Sesione> findAll(Pageable pageable);


    //consulta que devuelve una discoteca segun un ID concreto
    List<Sesione> findById(Integer id);


    //consulta que devuelve todos los años disponibles tras aplicar filtros
    @Query("SELECT DISTINCT s.fechaSesionYear " +
            "FROM Sesione s " +
            "JOIN s.sesionDjs sd " +
            "JOIN sd.idDj dj " +
            "WHERE (:djs IS NULL OR dj.nombreDj IN :djs) " +
            "AND (:discotecas IS NULL OR s.idDiscoteca.nombreDisco IN :discotecas) " +
            "ORDER BY s.fechaSesionYear ASC")
    List<Integer> findDistinctYearsFiltered(List<Integer> anios, List<String> discotecas, List<String> djs);



    //consulta que devuelve todos los DJs disponibles tras aplicar filtros
    @Query("SELECT DISTINCT dj.nombreDj " +
            "FROM Sesione s " +
            "JOIN s.sesionDjs sd " +
            "JOIN sd.idDj dj " +
            "WHERE (:anios IS NULL OR s.fechaSesionYear IN :anios) " +
            "AND (:discotecas IS NULL OR s.idDiscoteca.nombreDisco IN :discotecas) " +
            "ORDER BY dj.nombreDj ASC")
    List<String> findDistinctDjsFiltered(List<Integer> anios, List<String> discotecas, List<String> djsIgnorados);



    //consulta que devuelve todas las discotecas disponibles tras aplicar filtros
    @Query("SELECT DISTINCT s.idDiscoteca.nombreDisco " +
            "FROM Sesione s " +
            "JOIN s.sesionDjs sd " +
            "JOIN sd.idDj dj " +
            "WHERE (:anios IS NULL OR s.fechaSesionYear IN :anios) " +
            "AND (:djs IS NULL OR dj.nombreDj IN :djs) " +
            "ORDER BY s.idDiscoteca.nombreDisco ASC")
    List<String> findDistinctDiscotecasFiltered(List<Integer> anios, List<String> djs, List<String> discotecasIgnoradas);


    //consulta que devuelve todas las sesiones donde suena una canción en concreto
    //se le pasa como parametro una lista con IDs de varias sesiones
    @EntityGraph(attributePaths = {"sesionDjs.idDj", "idDiscoteca"})
    Page<Sesione> findByIdIn(List<Long> ids, Pageable pageable);


    //consulta que trae todos los años que hay en la tabla sesiones, para usarlo en filtros
    @Query("SELECT DISTINCT s.fechaSesionYear FROM Sesione s " +
            "ORDER BY s.fechaSesionYear ASC")
    List<Integer> findAllDistinctYears();

    //consulta que trae todos los DJs que tienen sesion en la tabla sesionDJ
    @Query("SELECT DISTINCT dj.nombreDj FROM Sesione s " +
            "JOIN s.sesionDjs sd " +
            "JOIN sd.idDj dj " +
            "ORDER BY dj.nombreDj ASC")
    List<String> findAllDistinctDjNames();

    //consulta que trae todas las discotecas que tienen sesiones en la tabla sesiones
    @Query("SELECT DISTINCT s.idDiscoteca.nombreDisco FROM Sesione s " +
            "ORDER BY s.idDiscoteca.nombreDisco ASC")
    List<String> findAllDistinctDiscotecas();

}
