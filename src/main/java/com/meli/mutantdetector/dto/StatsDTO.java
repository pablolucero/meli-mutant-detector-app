package com.meli.mutantdetector.dto;

public class StatsDTO {

    public long mutants;
    public long notMutants;
    public double ratio;

    public StatsDTO(long mutants, long notMutants, double ratio) {
        this.mutants = mutants;
        this.notMutants = notMutants;
        this.ratio = ratio;
    }

    public long getMutants() {
        return mutants;
    }

    public long getNotMutants() {
        return notMutants;
    }

    public double getRatio() {
        return ratio;
    }
}
