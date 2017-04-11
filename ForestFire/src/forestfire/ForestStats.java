/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forestfire;

/**
 *
 * @author humayra
 */
public class ForestStats {

    private Double growthRate1;
    private Double growthRate2;
    private Integer longivity;
    private Double biomass;

    public ForestStats() {
        this.growthRate1 = Utility.getRandomProbability();
        this.growthRate2 = Utility.getRandomProbability();
        this.longivity = 0;
        this.biomass = 0.0;
    }

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
