package com.euskoteka.euskoteka_api.dto;

import java.util.List;

public class FiltrosDispDiscotecaDTO {

    private List<FiltroOptionDTO> anios;
    private List<FiltroOptionDTO> provincias;

    public FiltrosDispDiscotecaDTO(List<FiltroOptionDTO> anios,
                                   List<FiltroOptionDTO> provincias) {
        this.anios = anios;
        this.provincias = provincias;
    }

    public List<FiltroOptionDTO> getAnios() { return anios; }
    public List<FiltroOptionDTO> getProvincias() { return provincias; }
}
