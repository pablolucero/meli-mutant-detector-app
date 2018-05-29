package com.meli.mutantdetector.model;

public class Stats {

    public long mutants;
    public long notMutants;
    public double ratio;

    public Stats(long mutants, long notMutants, double ratio) {
        this.mutants = mutants;
        this.notMutants = notMutants;
        this.ratio = ratio;
    }

    public long getMutants() {
        return mutants;
    }

    public void setMutants(long mutants) {
        this.mutants = mutants;
    }

    public long getNotMutants() {
        return notMutants;
    }

    public void setNotMutants(long notMutants) {
        this.notMutants = notMutants;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    @Override
    public String toString() {
        return "Stats{" +
                "mutants=" + mutants +
                ", notMutants=" + notMutants +
                ", ratio=" + ratio +
                '}';
    }
}
