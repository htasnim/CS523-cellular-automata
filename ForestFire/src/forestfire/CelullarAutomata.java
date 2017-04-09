/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forestfire;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author humayra
 */
public class CelullarAutomata {

    private List<String> land;
    private List<ForestStats> forestStatsList;
    private static final char BURNING = 'w'; //w looks like fire, right?
    private static final char TREE = 'T';
    private static final char EMPTY = '.';
    private static final char RESCUED = 'r';
    private static final char FIRE_FIGHTER_WORKING = 'f';
    private ForestStats topForestStats;

    void init() {

        // Initialize the gnome of initial population
        forestStatsList = new ArrayList<ForestStats>();
        for (int i = 0; i < Defs.NUMBER_OF_INDIVIDUAL_IN_POPULATION; i++) {
            ForestStats forestStats = new ForestStats();
            forestStatsList.add(forestStats);
        }

        // Initialize the forest in the begining
        land = new LinkedList<String>();
        for (int i = 0; i < Defs.GRID_HEIGHT; i++) {//height is just a copy anyway
            StringBuilder line = new StringBuilder(Defs.GRID_WIDTH);
            for (int j = Defs.GRID_WIDTH; j > 0; j--) {
                line.append(EMPTY);
            }
            land.add(line.toString());
        }
    }

    private String processRows(String rowAbove, String thisRow, String rowBelow, ForestStats forestStats) {
        String newRow = "";
        for (int i = 0; i < thisRow.length(); i++) {
            switch (thisRow.charAt(i)) {
                case BURNING:
                    newRow += EMPTY;
                    break;
                case EMPTY:
                    if (Defs.USE_MULTIPLE_SPECIES == true) {
                        boolean species1 = false;
                        boolean species2 = false;
                        species1 = Math.random() < forestStats.getGrowthRate1().doubleValue() ? true : false;
                        species2 = Math.random() < forestStats.getGrowthRate2().doubleValue() ? true : false;
                        if (species1 == false && species2 == false) {
                            newRow += EMPTY;
                        } else {
                            newRow += TREE;
                        }
                    } else {
                        newRow += Math.random() < forestStats.getLongivity() ? TREE : EMPTY;
                    }
                    break;
                case FIRE_FIGHTER_WORKING:
                    newRow += RESCUED;
                    break;
                case RESCUED:
                    newRow += TREE;
                    break;
                case TREE:
                    String neighbors = "";
                    if (i == 0) {//first char
                        neighbors += rowAbove == null ? "" : rowAbove.substring(i, i + 2);
                        neighbors += thisRow.charAt(i + 1);
                        neighbors += rowBelow == null ? "" : rowBelow.substring(i, i + 2);
                        if (neighbors.contains(Character.toString(BURNING))) {
                            newRow += BURNING;
                            break;
                        }
                    } else if (i == thisRow.length() - 1) {//last char
                        neighbors += rowAbove == null ? "" : rowAbove.substring(i - 1, i + 1);
                        neighbors += thisRow.charAt(i - 1);
                        neighbors += rowBelow == null ? "" : rowBelow.substring(i - 1, i + 1);
                        if (neighbors.contains(Character.toString(BURNING))) {
                            newRow += BURNING;
                            break;
                        }
                    } else {//middle
                        neighbors += rowAbove == null ? "" : rowAbove.substring(i - 1, i + 2);
                        neighbors += thisRow.charAt(i + 1);
                        neighbors += thisRow.charAt(i - 1);
                        neighbors += rowBelow == null ? "" : rowBelow.substring(i - 1, i + 2);
                        if (neighbors.contains(Character.toString(BURNING))) {
                            newRow += BURNING;
                            break;
                        }
                    }
                    newRow += Math.random() < Defs.PROBABILITY_F ? BURNING : TREE;
            }
        }
        return newRow;
    }

    private void runCAIteration(ForestStats forestStats) {
        List<String> newLand = new LinkedList<String>();
        for (int i = 0; i < land.size(); i++) {
            String rowAbove, thisRow = land.get(i), rowBelow;
            if (i == 0) {//first row
                rowAbove = null;
                rowBelow = land.get(i + 1);
            } else if (i == land.size() - 1) {//last row
                rowBelow = null;
                rowAbove = land.get(i - 1);
            } else {//middle
                rowBelow = land.get(i + 1);
                rowAbove = land.get(i - 1);
            }
            newLand.add(processRows(rowAbove, thisRow, rowBelow, forestStats));
        }
        land = newLand;
    }

    List<CellPosition> getBurningTreeList() {
        List<CellPosition> positionList = new ArrayList<CellPosition>();
        List<CellPosition> allTreePosition = new ArrayList<CellPosition>();
        int i = 0;
        for (String currString : land) {
            for (int j = 0; j < currString.length(); j++) {
                if (currString.charAt(i) != BURNING) {
                    allTreePosition.add(new CellPosition(i, j));
                }
            }
            i++;
        }
        return positionList;
    }

//    private void runCAIteration(ForestStats forestStats, int numberOfFireFighter) {
//        List<String> newLand = new LinkedList<String>();
//        for (int i = 0; i < land.size(); i++) {
//            String rowAbove, thisRow = land.get(i), rowBelow;
//            if (i == 0) {//first row
//                rowAbove = null;
//                rowBelow = land.get(i + 1);
//            } else if (i == land.size() - 1) {//last row
//                rowBelow = null;
//                rowAbove = land.get(i - 1);
//            } else {//middle
//                rowBelow = land.get(i + 1);
//                rowAbove = land.get(i - 1);
//            }
//            newLand.add(processRows(rowAbove, thisRow, rowBelow, forestStats, numberOfFireFighter));
//        }
//        land = newLand;
//    }
    boolean isEmptyForest() {
        for (String currString : land) {
            for (int i = 0; i < currString.length(); i++) {
                if (currString.charAt(i) != EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    int claculatenumberOfTrees() {
        int biomass = 0;
        for (String currString : land) {
            for (int i = 0; i < currString.length(); i++) {
                if (currString.charAt(i) != TREE) {
                    biomass++;
                }
            }
        }
        return biomass;
    }

    void selection() {
        List<ForestStats> newForestStatsList = new ArrayList<ForestStats>();
        for (int i = 0; i < Defs.NUMBER_OF_INDIVIDUAL_IN_POPULATION / 2; i++) {
            newForestStatsList.add(new ForestStats(forestStatsList.get(i).getGrowthRate1(), forestStatsList.get(i).getGrowthRate2()));
        }

        for (int i = 0; i < Defs.NUMBER_OF_INDIVIDUAL_IN_POPULATION / 2; i++) {
            newForestStatsList.add(new ForestStats(forestStatsList.get(i).getGrowthRate1(), forestStatsList.get(i).getGrowthRate2()));
        }

        forestStatsList = newForestStatsList;
    }

    public void run() {
        for (int i = 1; i <= Defs.NUMBER_OF_GENERATIONS.intValue(); i++) {
            init();
            for (ForestStats currForestStats : forestStatsList) {
                int longivity = 0;
                double biomass = 0.0;
                for (int j = 1; j <= Defs.NUMBER_OF_TIME_STEPS; j++) {
                    runCAIteration(currForestStats);
                    longivity = j;
                    biomass += (double) claculatenumberOfTrees() / (double) (Defs.GRID_WIDTH * Defs.GRID_HEIGHT);
                    if (claculatenumberOfTrees() == 0) {
                        break;
                    }
                }
                biomass /= (double) longivity;
                currForestStats.setBiomass(biomass);
                currForestStats.setLongivity(longivity);
            }
            Utility.sortForestStats(forestStatsList);
            System.out.println("Results after generation " + i + ":");
            System.out.println("Biomass results   Longivity results       GR1              GR2");
            DecimalFormat df = new DecimalFormat("#.###");
            for (ForestStats fs : forestStatsList) {
                System.out.println(" " + df.format(fs.getBiomass())
                        + "                  " + fs.getLongivity()
                        + "             " + df.format(fs.getGrowthRate1())
                        + "             " + df.format(fs.getGrowthRate2()));
            }

            System.out.println("");
            selection();
        }

        topForestStats = forestStatsList.get(0);
        if (Defs.USE_FIRE_FIGHTER == true) {
            for (int numberOfFireFighter = 50; numberOfFireFighter <= 1000; numberOfFireFighter += 50) {
                for (ForestStats currForestStats : forestStatsList) {
                    int longivity = 0;
                    double biomass = 0.0;
                    for (int j = 1; j <= Defs.NUMBER_OF_TIME_STEPS; j++) {
                        List<CellPosition> burningTreePositionList = getBurningTreeList();
                        Collections.shuffle(burningTreePositionList);
                        int k = 0;
                        for (CellPosition burningTree : burningTreePositionList) {
                            k++;
                            if (k > numberOfFireFighter) {
                                break;
                            }
                            String currRow = land.get(burningTree.getX());
                            StringBuilder sb = new StringBuilder(currRow);
                            sb.setCharAt(burningTree.getY(), FIRE_FIGHTER_WORKING);
                            land.set(burningTree.getX(), sb.toString());
                        }
                        runCAIteration(currForestStats);
                        longivity = j;
                        biomass += (double) claculatenumberOfTrees() / (double) (Defs.GRID_WIDTH * Defs.GRID_HEIGHT);
                        if (claculatenumberOfTrees() == 0) {
                            break;
                        }
                    }
                    biomass /= (double) longivity;
                    currForestStats.setBiomass(biomass);
                    currForestStats.setLongivity(longivity);
                }
                System.out.println("Results after using fire fighter " + numberOfFireFighter + ":");
                System.out.println("Biomass results   Longivity results       GR1              GR2");
                DecimalFormat df = new DecimalFormat("#.###");
                for (ForestStats fs : forestStatsList) {
                    System.out.println(" " + df.format(fs.getBiomass())
                            + "                  " + fs.getLongivity()
                            + "             " + df.format(fs.getGrowthRate1())
                            + "             " + df.format(fs.getGrowthRate2()));
                }
            }
        }

    }

}
