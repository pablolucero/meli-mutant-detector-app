package com.meli.mutantdetector.model;

public class Stats {

    private long mutants;
    private long humans;

    public Stats(long mutants, long humans) {
        this.mutants = mutants;
        this.humans = humans;
    }

    public long getMutantsCount() {
        return mutants;
    }

    public long getHumansCount() {
        return humans;
    }

    public double getRatio() {
        if (humans <= 0 && mutants <= 0) return 0;
        if (humans == 0) return 1;
        return ((double) mutants / humans);
    }

    @Override
    public String toString() {
        return "Stats{" +
                "mutants=" + mutants +
                ", humans=" + humans +
                ", ratio=" + getRatio() +
                '}';
    }
}
