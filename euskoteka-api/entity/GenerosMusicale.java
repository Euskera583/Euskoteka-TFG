    package com.euskoteka.euskoteka_api.entity;

    import jakarta.persistence.*;

    @Entity
    @Table(name = "generos_musicales", schema = "euskoteka")
    public class GenerosMusicale {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", columnDefinition = "int UNSIGNED not null")
        private Long id;

        @Column(name = "genero", nullable = false, length = 45)
        private String genero;

        //getters y setters

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getGenero() {
            return genero;
        }

        public void setGenero(String genero) {
            this.genero = genero;
        }

    }