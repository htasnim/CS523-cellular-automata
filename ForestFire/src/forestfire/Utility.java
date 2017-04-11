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
 */
public class Utility {

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

    public static Double getRandomProbability() {
        return new Random().nextDouble();
    }
}
