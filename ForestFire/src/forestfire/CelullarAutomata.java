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
 * @description this contains the methods which are needed for the CA
 *
 */
public class CelullarAutomata {

    private List<String> land; // land is used to store actual forest status
    private List<ForestStats> forestStatsList; // forestStatsList is the population of the GA

    // Different states of a forest cell
    private static final char BURNING = 'w'; // w represents a burning cell in the forest
    private static final char TREE = 'T'; // T represents a tree cell in the forest
    private static final char EMPTY = '.'; // . represents an empty cell in the forest
    private static final char RESCUED = 'r'; // r represents a cell which is just rescued by a fire fighter, this is actually a tree in the forest
    private static final char FIRE_FIGHTER_WORKING = 'f'; // f is an intermidiate state to allocate the fire fighters and is not visible during GA or CA iteration

    private ForestStats topForestStats; // topForestStats holds the best individual statistics after the GA

    /* 
    * initPopulation Initializs the individuals of initial population
     */
    void initPopulation() {
        forestStatsList = new ArrayList<ForestStats>();
        for (int i = 0; i < Defs.NUMBER_OF_INDIVIDUAL_IN_POPULATION; i++) {
            ForestStats forestStats = new ForestStats();
            forestStatsList.add(forestStats);
        }
    }

    /* 
    * initForest Initializs the forest with empty cells
     */
    void initForest() {
        land = new LinkedList<String>();
        for (int i = 0; i < Defs.GRID_HEIGHT; i++) {//height is just a copy anyway
            StringBuilder line = new StringBuilder(Defs.GRID_WIDTH);
            for (int j = Defs.GRID_WIDTH; j > 0; j--) {
                line.append(EMPTY);
            }
            land.add(line.toString());
        }
    }

    /* 
    * processRows makes the necessary changes in a single (target) row of a forest for each
    * time iteration.
    * @param1 rowAbove is the row immediately above the target row
    * @param2 rowBelow is the row immediately below the target row
    * @param3 forestStats has the probabilities for which the current CA is running
    * @output String is the modified version of the row after the iteration
    *
     */
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
                        species1 = Utility.getRandomProbability() < forestStats.getGrowthRate1().doubleValue() ? true : false;
                        species2 = Utility.getRandomProbability() < forestStats.getGrowthRate2().doubleValue() ? true : false;
                        if (species1 == false && species2 == false) {
                            newRow += EMPTY;
                        } else {
                            newRow += TREE;
                        }
                    } else {
                        newRow += Utility.getRandomProbability() < forestStats.getGrowthRate1() ? TREE : EMPTY;
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
                    if (i == 0) { //first char
                        neighbors += rowAbove == null ? "" : rowAbove.substring(i, i + 2);
                        neighbors += thisRow.charAt(i + 1);
                        neighbors += rowBelow == null ? "" : rowBelow.substring(i, i + 2);
                        if (neighbors.contains(Character.toString(BURNING))) {
                            newRow += BURNING;
                            break;
                        }
                    } else if (i == thisRow.length() - 1) { //last char
                        neighbors += rowAbove == null ? "" : rowAbove.substring(i - 1, i + 1);
                        neighbors += thisRow.charAt(i - 1);
                        neighbors += rowBelow == null ? "" : rowBelow.substring(i - 1, i + 1);
                        if (neighbors.contains(Character.toString(BURNING))) {
                            newRow += BURNING;
                            break;
                        }
                    } else { //middle char
                        neighbors += rowAbove == null ? "" : rowAbove.substring(i - 1, i + 2);
                        neighbors += thisRow.charAt(i + 1);
                        neighbors += thisRow.charAt(i - 1);
                        neighbors += rowBelow == null ? "" : rowBelow.substring(i - 1, i + 2);
                        if (neighbors.contains(Character.toString(BURNING))) {
                            newRow += BURNING;
                            break;
                        }
                    }
                    newRow += Utility.getRandomProbability() < Defs.PROBABILITY_F ? BURNING : TREE;
            }
        }
        return newRow;
    }

    /*
    *
    * runCAIteration makes the necessary change of the forest for a timestep. The modified forest
    * is stored in land.
    * @param forestStats has the probabilities for which the current CA is running
    *
     */
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

    /*
    *
    * getBurningTreeList is helper function which finds the list of burning tree at any timestep
    * @output list of CellPosition of burning trees
    *
     */
    List<CellPosition> getBurningTreeList() {
        List<CellPosition> positionList = new ArrayList<CellPosition>();
        int i = 0;
        for (String currString : land) {
            for (int j = 0; j < currString.length(); j++) {
                if (currString.charAt(j) == BURNING) {
                    positionList.add(new CellPosition(i, j));
                }
            }
            i++;
        }
        return positionList;
    }

    /*
    *
    * isEmptyForest is helper function which finds if the forest is empty at any timestep
    * @output boolean: true if the forest is currently empty, false otherwise
    *
     */
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

    /*
    *
    * claculatenumberOfTrees is helper function which finds the number of trees in the forest at any timestep
    * this is needed to calcualte the biomass of the forest
    * @output int: the number of trees
    *
     */
    int claculatenumberOfTrees() {
        int treeCount = 0;
        for (String currString : land) {
            for (int i = 0; i < currString.length(); i++) {
                if (currString.charAt(i) == TREE || currString.charAt(i) == RESCUED || currString.charAt(i) == FIRE_FIGHTER_WORKING) {
                    treeCount++;
                }
            }
        }
        return treeCount;
    }

    /*
    *
    * selection is GA helper function which makes the necessary change of selection 
    * operator in the population
    *
     */
    void selection() {
        List<ForestStats> newForestStatsList = new ArrayList<ForestStats>();
        for (int i = 0; i < Defs.NUMBER_OF_INDIVIDUAL_IN_POPULATION / 2; i++) {
            newForestStatsList.add(new ForestStats(forestStatsList.get(i).getGrowthRate1(), forestStatsList.get(i).getGrowthRate2()));
        }

        for (int i = 0; i < Defs.NUMBER_OF_INDIVIDUAL_IN_POPULATION / 2; i++) {
            int randomInt = Utility.getRandomInteger(0, (Defs.NUMBER_OF_INDIVIDUAL_IN_POPULATION / 2) - 1);
            newForestStatsList.add(new ForestStats(forestStatsList.get(randomInt).getGrowthRate1(), forestStatsList.get(randomInt).getGrowthRate2()));
        }

        forestStatsList = newForestStatsList;
    }

    /*
    *
    * mutation is GA helper function which makes the necessary change of mutation 
    * operator in the population
    *
     */
    void mutation() {
        List<ForestStats> newForestStatsList = new ArrayList<ForestStats>();
        for (int i = 0; i < Defs.NUMBER_OF_ELITE_INDIVIDUAL; i++) {
            newForestStatsList.add(new ForestStats(forestStatsList.get(i).getGrowthRate1(), forestStatsList.get(i).getGrowthRate2()));
        }

        for (int i = Defs.NUMBER_OF_ELITE_INDIVIDUAL; i < Defs.NUMBER_OF_INDIVIDUAL_IN_POPULATION; i++) {
            if (Utility.getRandomProbability() < Defs.MUTATION_RATE) {
                newForestStatsList.add(new ForestStats());
            } else {
                newForestStatsList.add(new ForestStats(forestStatsList.get(i).getGrowthRate1(), forestStatsList.get(i).getGrowthRate2()));
            }
        }

        forestStatsList = newForestStatsList;
    }

    /*
    *
    * run is the entry point of this cellular automata
    * this method is public and called from outside. this method runs the CA ang the GA inside.
    * this method uses the necessary parameter values and constant values from Defs. java class
    *
     */
    public void run() {
        initPopulation();

        // iteration of the total number of generations of the GA
        for (int i = 1; i <= Defs.NUMBER_OF_GENERATIONS.intValue(); i++) {

            // iteration of each individual in population in the GA
            for (ForestStats currForestStats : forestStatsList) {
                int longivity = 0;
                double biomass = 0.0;
                initForest();

                // iteration of the each time step of the CA
                for (int j = 1; j <= Defs.NUMBER_OF_TIME_STEPS; j++) {
                    runCAIteration(currForestStats);
                    longivity = j;
                    biomass += (double) claculatenumberOfTrees() / (double) (Defs.GRID_WIDTH * Defs.GRID_HEIGHT);
                    if (claculatenumberOfTrees() == 0) {
                        break;
                    }
                    if (Defs.DEBUG_MODE) {
                        System.out.println("After iteration " + j + ":");
                    }
                    for (String str : land) {
                        if (Defs.DEBUG_MODE) {
                            System.out.println(str);
                        }
                    }
                    if (Defs.DEBUG_MODE) {
                        System.out.println("Trees :" + claculatenumberOfTrees() + ", Biomass: " + biomass);
                    }
                }
                biomass /= (double) longivity;
                if (Defs.DEBUG_MODE) {
                    System.out.println("Biomass after all iterations: " + biomass);
                }
                currForestStats.setBiomass(biomass);
                currForestStats.setLongivity(longivity);
            }

            // Sort the individuals according to their fitness function (biomass values)
            Utility.sortForestStats(forestStatsList);

            // Show the statistics after each generation
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

            // Apply the GA operators after each generation
            selection();
            mutation();
        }

        // Get the individual with highest fitness after the GA
        topForestStats = forestStatsList.get(0);

        // Introduce fire fighters in the forest to observe the behavioral change
        if (Defs.USE_FIRE_FIGHTER == true) {
            if (Defs.DEBUG_MODE) {
                System.out.println("***************** FIRE FIGHTER STARTS *****************");
            }

            // Apply the firefighters ranging from 0 to 1000 in number
            for (int numberOfFireFighter = Defs.MIN_FIRE_FIGHTERS; numberOfFireFighter <= Defs.MAX_FIRE_FIGHTERS; numberOfFireFighter += Defs.FIRE_FIGHTERS_INCREMENT_RATE) {
                int longivity = 0;
                double biomass = 0.0;
                initForest();
                for (int j = 1; j <= Defs.NUMBER_OF_TIME_STEPS; j++) {
                    List<CellPosition> burningTreePositionList = getBurningTreeList();
                    if (Defs.DEBUG_MODE) {
                        for (CellPosition cp : burningTreePositionList) {
                            System.out.println("X: " + cp.getX() + ", Y: " + cp.getY());
                        }
                    }
                    Collections.shuffle(burningTreePositionList);
                    if (Defs.DEBUG_MODE) {
                        System.out.println("After shuffle: ");
                        for (CellPosition cp : burningTreePositionList) {
                            System.out.println("X: " + cp.getX() + ", Y: " + cp.getY());
                        }
                    }
                    int k = 0;
                    for (CellPosition burningTree : burningTreePositionList) {
                        k++;
                        if (k > numberOfFireFighter) {
                            break;
                        }
                        String newRow = land.get(burningTree.getX()).substring(0, burningTree.getY()) + FIRE_FIGHTER_WORKING + land.get(burningTree.getX()).substring(burningTree.getY() + 1);
                        if (Defs.DEBUG_MODE) {
                            System.out.println("New string: " + newRow);
                        }
                        land.set(burningTree.getX(), newRow);
                    }
                    runCAIteration(topForestStats);
                    longivity = j;
                    biomass += (double) claculatenumberOfTrees() / (double) (Defs.GRID_WIDTH * Defs.GRID_HEIGHT);
                    if (Defs.DEBUG_MODE) {
                        System.out.println("After iteration " + j + ":");
                    }
                    for (String str : land) {
                        if (Defs.DEBUG_MODE) {
                            System.out.println(str);
                        }
                    }
                    if (Defs.DEBUG_MODE) {
                        System.out.println("Trees :" + claculatenumberOfTrees() + ", Biomass: " + biomass);
                    }
                    if (claculatenumberOfTrees() == 0) {
                        break;
                    }
                }
                biomass /= (double) longivity;

                // Show the results with fire fighters
                System.out.println("Results after using fire fighter " + numberOfFireFighter + ":");
                System.out.println("Biomass results   Longivity results       GR1              GR2");
                DecimalFormat df = new DecimalFormat("#.###");
                System.out.println(" " + df.format(biomass)
                        + "                  " + longivity
                        + "             " + df.format(topForestStats.getGrowthRate1())
                        + "             " + df.format(topForestStats.getGrowthRate2()));
            }
        }
    }
}
