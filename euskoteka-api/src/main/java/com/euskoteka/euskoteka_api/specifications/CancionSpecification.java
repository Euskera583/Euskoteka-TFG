package com.euskoteka.euskoteka_api.specifications;

import com.euskoteka.euskoteka_api.entity.Cancione;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class CancionSpecification {

    //filtra por los años  si la lista tiene valores
    //si es vacia o null no se aplica ningun filtro
    public static Specification<Cancione> hasYears(List<Integer> anios) {
        return (root, query, cb) -> {
            if (anios == null || anios.isEmpty()) return cb.conjunction();
            return root.get("anio").in(anios);
        };
    }

    //filtra por genero
    public static Specification<Cancione> hasGeneros(List<String> generos) {
        return (root, query, cb) -> {
            if (generos == null || generos.isEmpty()) return cb.conjunction();

            //accede a la entidad de genero para sacar el nombre
            return root.get("genero").get("genero").in(generos);
        };
    }

    //filtra por coincidencias de busqueda en 'nombre del tema', 'artista', 'artist - tema'
    public static Specification<Cancione> hasSearch(String search) {
        return (root, query, cb) -> {
            if (search == null || search.trim().isEmpty()) return cb.conjunction();

            String likePattern = "%" + search.toLowerCase() + "%";

            return cb.or(
                    cb.like(cb.lower(root.get("nombreTema")), likePattern),
                    cb.like(cb.lower(root.get("artista")), likePattern),
                    cb.like(
                            cb.lower(cb.concat(
                                    cb.concat(root.get("artista"), " - "),
                                    root.get("nombreTema")
                            )),
                            likePattern
                    )
            );
        };
    }
}
