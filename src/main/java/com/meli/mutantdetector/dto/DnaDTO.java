package com.meli.mutantdetector.dto;

import java.util.List;

public class DnaDTO {

    List<String> dna;

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
