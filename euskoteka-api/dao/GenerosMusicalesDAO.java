package com.euskoteka.euskoteka_api.dao;

import com.euskoteka.euskoteka_api.entity.GenerosMusicale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenerosMusicalesDAO extends JpaRepository <GenerosMusicale, Long> {
    Optional<GenerosMusicale> findByGenero(String genero);
}