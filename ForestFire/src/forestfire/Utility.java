/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forestfire;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author humayra
 * @description the utility class contains some utility methods like sorting,
 * generating random number etc.
 *
 */
public class Utility {

    /**
     *
     * @name sortForestStats
     * @description this method sorts a list of ForestStats class according to
     * Biomass value (descending)
     * @param1 List of ForestStats class
     * @output sorted list of ForestStats class
     *
     */
    public static List<ForestStats> sortForestStats(List<ForestStats> forestStatsList) {

        System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
        Collections.sort(forestStatsList, new Comparator<ForestStats>() {
            @Override
            public int compare(ForestStats o1, ForestStats o2) {
                return o1.getBiomass() > o2.getBiomass() ? -1 : 1;
            }
        });
        return forestStatsList;
    }

    /**
     *
     * @name getRandomProbability
     * @description this methods generates random probability from 0.0 to 1.0
     * (inclusive)
     * @output probability double value
     *
     */
    public static Double getRandomProbability() {
        return new Random().nextDouble();
    }

    /**
     *
     * @name getRandomInteger
     * @description this methods generates random integer from a given range
     * (inclusive) param1 int start range param1 int end range
     * @output random int from the range
     *
     */
    public static Integer getRandomInteger(int startRange, int endRange) {
        return startRange + (int) (Math.random() * ((endRange - startRange) + 1));
    }
}
