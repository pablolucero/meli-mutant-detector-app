package com.meli.mutantdetector.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class DnaResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String dna;

    private Boolean isMutant;

    public DnaResult() {
    }

    public DnaResult(String dna, Boolean isMutant) {
        this.dna = dna;
        this.isMutant = isMutant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDna() {
        return dna;
    }

    public void setDna(String dna) {
        this.dna = dna;
    }

    public Boolean isMutant() {
        return isMutant;
    }

    public void setIsMutant(Boolean mutant) {
        isMutant = mutant;
    }

    @Override
    public String toString() {
        return "DnaResult{" +
                "id=" + id +
                ", dna='" + dna + '\'' +
                ", isMutant=" + isMutant +
                '}';
    }
}

