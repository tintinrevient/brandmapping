package io.github.tintinrevient.brandmapping.domain;

public class MappingRate {
    private double levenshteinRate;
    private double lcsRate;
    private double containRate;

    private double averageRate;

    public MappingRate(double levenshteinRate, double lcsRate, double containRate){
        this.levenshteinRate = levenshteinRate;
        this.lcsRate = lcsRate;
        this.containRate = containRate;
    }

    public double getLevenshteinRate() {
        return levenshteinRate;
    }

    public void setLevenshteinRate(double levenshteinRate) {
        this.levenshteinRate = levenshteinRate;
    }

    public double getLcsRate() {
        return lcsRate;
    }

    public void setLcsRate(double lcsRate) {
        this.lcsRate = lcsRate;
    }

    public double getContainRate() {
        return containRate;
    }

    public void setContainRate(double containRate) {
        this.containRate = containRate;
    }

    public double getAverageRate() {
        return this.levenshteinRate * 0.15 + this.lcsRate * 0.15 + this.containRate * 0.7;
    }

    public void setAverageRate(double averageRate) {
        this.averageRate = averageRate;
    }
}
