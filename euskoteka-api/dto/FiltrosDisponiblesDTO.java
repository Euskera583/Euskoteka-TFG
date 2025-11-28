package com.euskoteka.euskoteka_api.dto;

import java.util.List;

public class FiltrosDisponiblesDTO {
    private List<FiltroOptionDTO> anios;
    private List<FiltroOptionDTO> djs;
    private List<FiltroOptionDTO> discotecas;

    public FiltrosDisponiblesDTO(List<FiltroOptionDTO> anios,
                                 List<FiltroOptionDTO> djs,
                                 List<FiltroOptionDTO> discotecas) {
        this.anios = anios;
        this.djs = djs;
        this.discotecas = discotecas;
    }

    public List<FiltroOptionDTO> getAnios() { return anios; }
    public void setAnios(List<FiltroOptionDTO> anios) { this.anios = anios; }

    public List<FiltroOptionDTO> getDjs() { return djs; }
    public void setDjs(List<FiltroOptionDTO> djs) { this.djs = djs; }

    public List<FiltroOptionDTO> getDiscotecas() { return discotecas; }
    public void setDiscotecas(List<FiltroOptionDTO> discotecas) { this.discotecas = discotecas; }



}