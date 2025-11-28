package com.euskoteka.euskoteka_api.dao;

import com.euskoteka.euskoteka_api.entity.ArticulosExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticulosDAO extends JpaRepository<ArticulosExtra, Long> {
}
