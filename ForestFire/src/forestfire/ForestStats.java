/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forestfire;

/**
 *
 * @author humayra
 * @description this class is a container for a specific forest statistics
 *
 */
public class ForestStats {

    private Double growthRate1; // growth rate (probability) for species 1 in a forest
    private Double growthRate2; // growth rate (probability, used only in multiple species version) for species 2 in a forest
    private Integer longivity; // longivity of the forest (initially 0)
    private Double biomass; // average biomass of the forest (initially 0)

    /**
     *
     * @name default constructor of the class
     * @description initializes with random growth rates
     *
     */
    public ForestStats() {
        this.growthRate1 = Utility.getRandomProbability();
        this.growthRate2 = Utility.getRandomProbability();
        this.longivity = 0;
        this.biomass = 0.0;
    }

    /**
     *
     * @name constructor of the class
     * @description initializes with given growth rates
     * @param1 growth rate (double) for species 1
     * @param2 growth rate (double) for species 2
     *
     */
    public ForestStats(double growthRate1, double growthRate2) {
        this.growthRate1 = growthRate1;
        this.growthRate2 = growthRate2;
        this.longivity = 0;
        this.biomass = 0.0;
    }

    /**
     * @return the longivity
     */
    public Integer getLongivity() {
        return longivity;
    }

    /**
     * @param longivity the longivity to set
     */
    public void setLongivity(Integer longivity) {
        this.longivity = longivity;
    }

    /**
     * @return the biomass
     */
    public Double getBiomass() {
        return biomass;
    }

    /**
     * @param biomass the biomass to set
     */
    public void setBiomass(Double biomass) {
        this.biomass = biomass;
    }

    /**
     * @return the growthRate1
     */
    public Double getGrowthRate1() {
        return growthRate1;
    }

    /**
     * @param growthRate1 the growthRate1 to set
     */
    public void setGrowthRate1(Double growthRate1) {
        this.growthRate1 = growthRate1;
    }

    /**
     * @return the growthRate2
     */
    public Double getGrowthRate2() {
        return growthRate2;
    }

    /**
     * @param growthRate2 the growthRate2 to set
     */
    public void setGrowthRate2(Double growthRate2) {
        this.growthRate2 = growthRate2;
    }
}
