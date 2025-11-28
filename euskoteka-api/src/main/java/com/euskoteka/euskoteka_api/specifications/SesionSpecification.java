package com.euskoteka.euskoteka_api.specifications;

import com.euskoteka.euskoteka_api.entity.Sesione;
import com.euskoteka.euskoteka_api.entity.SesionDj;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class SesionSpecification {

    //filtra por años
    public static Specification<Sesione> hasYears(List<Integer> anios) {
        return (root, query, cb) -> {
            if (anios == null || anios.isEmpty()) return cb.conjunction();
            return root.get("fechaSesionYear").in(anios);
        };
    }

    //filtra por discotecas
    public static Specification<Sesione> hasDiscotecas(List<String> discotecas) {
        return (root, query, cb) -> {
            if (discotecas == null || discotecas.isEmpty()) return cb.conjunction();
            return root.get("idDiscoteca").get("nombreDisco").in(discotecas);
        };
    }

    //filtra por DJs y se asegura de mostrar el nombre y no el id
    public static Specification<Sesione> hasDjs(List<String> djs) {
        return (root, query, cb) -> {
            if (djs == null || djs.isEmpty()) return cb.conjunction();

            query.distinct(true); //evita duplicados al hacer join
            Join<Sesione, SesionDj> join = root.join("sesionDjs");
            return join.get("idDj").get("nombreDj").in(djs);
        };
    }
}
