package com.euskoteka.euskoteka_api.dto;

import java.util.List;

public class FiltrosDispCancionDTO {

    private List<FiltroOptionDTO> anios;
    private List<FiltroOptionDTO> generos;

    public FiltrosDispCancionDTO(List<FiltroOptionDTO> anios, List<FiltroOptionDTO> generos) {
        this.anios = anios;
        this.generos = generos;
    }

    public List<FiltroOptionDTO> getAnios() { return anios; }
    public void setAnios(List<FiltroOptionDTO> anios) { this.anios = anios; }

    public List<FiltroOptionDTO> getGeneros() { return generos; }
    public void setGeneros(List<FiltroOptionDTO> generos) { this.generos = generos; }

}
