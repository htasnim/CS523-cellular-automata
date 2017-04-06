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
    
    private Double growth_rate;
    private Integer longivity;
    private Double biomass;

    /**
     * @return the growth_rate
     */
    public Double getGrowth_rate() {
        return growth_rate;
    }

    /**
     * @param growth_rate the growth_rate to set
     */
    public void setGrowth_rate(Double growth_rate) {
        this.growth_rate = growth_rate;
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
    
}
