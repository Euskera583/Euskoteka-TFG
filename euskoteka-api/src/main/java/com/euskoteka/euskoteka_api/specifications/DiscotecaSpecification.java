package com.euskoteka.euskoteka_api.specifications;

import com.euskoteka.euskoteka_api.entity.Discoteca;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class DiscotecaSpecification {

    //filtra por año de actividad, se considera año de actividad cualquiere entre el año de apertura y cierre, estos incluidos
    public static Specification<Discoteca> isActiveInYear(List<Integer> years) {
        return (root, query, cb) -> {
            if (years == null || years.isEmpty()) return cb.conjunction();

            List<Predicate> orPreds = new ArrayList<>();

            for (Integer year : years) {
                //pred: apertura <= year AND (cierre >= year OR cierre IS NULL)
                Predicate aperturaLEYear = cb.lessThanOrEqualTo(root.get("fechaAperturaDisco").as(Integer.class), year);
                Predicate cierreGEYear = cb.greaterThanOrEqualTo(root.get("fechaCierreDisco").as(Integer.class), year);
                Predicate cierreEsNull = cb.isNull(root.get("fechaCierreDisco"));

                Predicate cierreCond = cb.or(cierreGEYear, cierreEsNull);
                Predicate añoActivo = cb.and(aperturaLEYear, cierreCond);

                orPreds.add(añoActivo);
            }

            return cb.or(orPreds.toArray(new Predicate[0]));
        };
    }

    //filtra por provincia
    public static Specification<Discoteca> hasProvince(List<String> provincias) {
        return (root, query, cb) -> {
            if (provincias == null || provincias.isEmpty()) return cb.conjunction();

            Join<Object, Object> loc = root.join("localidadDisco");
            Join<Object, Object> prov = loc.join("idProvincia");

            return prov.get("provincia").in(provincias);
        };
    }

    //filtra por localidad (no implementado)
    public static Specification<Discoteca> hasLocalidad(List<String> localidades) {
        return (root, query, cb) -> {
            if (localidades == null || localidades.isEmpty()) return cb.conjunction();

            Join<Object, Object> loc = root.join("localidadDisco");
            return loc.get("nombreLocalidad").in(localidades);
        };
    }

}
