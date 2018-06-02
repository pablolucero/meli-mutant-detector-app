package com.meli.mutantdetector.dto;

import java.util.List;

public class DnaDTO {

    private List<String> dna;

    public DnaDTO(List<String> dna) {
        this.dna = dna;
    }

    public List<String> getDna() {
        return dna;
    }

    public void setDna(List<String> dna) {
        this.dna = dna;
    }

    @Override
    public String toString() {
        return "DnaDTO{" +
                "dna=" + dna +
                '}';
    }
}
