/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forestfire;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author humayra
 * @description this class is the main class in this project. The structure of
 * the GUI was taken from: http://rosettacode.org/wiki/Forest_fire
 *
 */
public class Fire extends JFrame {

    // Different states of a forest cell
    private static final char BURNING = 'w'; // w represents a burning cell in the forest
    private static final char TREE = 'T'; // T represents a tree cell in the forest
    private static final char EMPTY = '.'; // . represents an empty cell in the forest
    private static final char RESCUED = 'r'; // r represents a cell which is just rescued by a fire fighter, this is actually a tree in the forest
    private static final char FIRE_FIGHTER_WORKING = 'f'; // f is an intermidiate state to allocate the fire fighters and is not visible during GA or CA iteration

    private Integer longivity = -1; // holds the longivity of the forest
    private double totalBiomass = 0; // holds the total biomass of the forest
    private List<String> land; // land is used to store actual forest status

    // Properties needed for the GUI
    private JPanel landPanel;

    /* 
    * Fire is used to show the current forest shape in the GUI
     */
    public Fire(List<String> land) {
        this.land = land;
        landPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                for (int y = 0; y < Fire.this.land.size(); y++) {
                    String row = Fire.this.land.get(y);
                    for (int x = 0; x < row.length(); x++) {
                        switch (row.charAt(x)) {
                            case BURNING:
                                g.setColor(Color.ORANGE);
                                break;
                            case TREE:
                                g.setColor(Color.GREEN);
                                break;
                            case RESCUED:
                                g.setColor(Color.BLUE);
                                break;
                            default: //will catch EMPTY
                                g.setColor(Color.BLACK);
                        }
                        g.fillRect(x * Defs.CELL_WIDTH, y * Defs.CELL_HEIGHT, Defs.CELL_WIDTH, Defs.CELL_HEIGHT);
                    }
                }
            }
        };

        //each block in the land is a nxn square
        landPanel.setSize(this.land.get(0).length() * Defs.CELL_WIDTH, this.land.size() * Defs.CELL_HEIGHT);
        add(landPanel);
        setSize(Defs.WINDOW_WIDTH, Defs.WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        repaint();
    }

    /*
    *
    * process makes the necessary change of the forest for a timestep. The modified forest
    * is stored in land.
    * @param land is the current forest state
    * output modified foerst state after the iteration
    *
     */
    private List<String> process(List<String> land) {
        List<String> newLand = new LinkedList<String>();
        for (int i = 0; i < land.size(); i++) {
            String rowAbove, thisRow = land.get(i), rowBelow;
            if (i == 0) { //first row
                rowAbove = null;
                rowBelow = land.get(i + 1);
            } else if (i == land.size() - 1) { //last row
                rowBelow = null;
                rowAbove = land.get(i - 1);
            } else { //middle row
                rowBelow = land.get(i + 1);
                rowAbove = land.get(i - 1);
            }
            newLand.add(processRows(rowAbove, thisRow, rowBelow));
        }
        return newLand;
    }

    /* 
    * processRows makes the necessary changes in a single (target) row of a forest for each
    * time iteration.
    * @param1 rowAbove is the row immediately above the target row
    * @param2 rowBelow is the row immediately below the target row
    * @output String is the modified version of the row after the iteration
    *
     */
    private String processRows(String rowAbove, String thisRow, String rowBelow) {
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
                        species1 = Utility.getRandomProbability() < Defs.PROBABILITY_P1 ? true : false;
                        species2 = Utility.getRandomProbability() < Defs.PROBABILITY_P2 ? true : false;
                        if (species1 == false && species2 == false) {
                            newRow += EMPTY;
                        } else {
                            newRow += TREE;
                        }
                    } else {
                        newRow += Utility.getRandomProbability() < Defs.PROBABILITY_P1 ? TREE : EMPTY;
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
    * populate Initializs the forest with empty cells
     */
    public static List<String> populate(int width, int height) {
        List<String> land = new LinkedList<String>();
        for (; height > 0; height--) { //height is just a copy anyway
            StringBuilder line = new StringBuilder(width);
            for (int i = width; i > 0; i--) {
                line.append(EMPTY);
            }
            land.add(line.toString());
        }
        return land;
    }

    /*
    *
    * isEmptyForest is helper function which finds if the forest is empty at any timestep
    * @output boolean: true if the forest is currently empty, false otherwise
    *
     */
    boolean isEmptyForest(List<String> land) {
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

    public ForestStats processN(int n) {
        totalBiomass = 0;
        for (int i = 0; i < n; i++) {
            List<CellPosition> burningTreePositionList = getBurningTreeList();
            Collections.shuffle(burningTreePositionList);
            int k = 0;
            for (CellPosition burningTree : burningTreePositionList) {
                k++;
                if (k > Defs.NUMBER_FIRE_FIGHTER) {
                    break;
                }
                String newRow = land.get(burningTree.getX()).substring(0, burningTree.getY()) + FIRE_FIGHTER_WORKING + land.get(burningTree.getX()).substring(burningTree.getY() + 1);
                land.set(burningTree.getX(), newRow);
            }
            land = process(land);
            if (isEmptyForest(land)) {
                break;
            }

            try {
                Thread.sleep(Defs.TIME_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }

        // Set the statistics for the given simulation
        ForestStats forestStats = new ForestStats();
        forestStats.setLongivity(longivity);
        forestStats.setBiomass(totalBiomass
                / (double) longivity
        );
        return forestStats;
    }

    // This main function is the entry point of this project
    public static void main(String[] args) {

        if (Defs.SHOW_GUI == true) { // this block has the code for the CA shown in GUI. Does not produce any statistics

            List<String> land = populate(Defs.GRID_WIDTH, Defs.GRID_HEIGHT);
            Fire fire = new Fire(land);
            ForestStats forestStats = fire.processN(Defs.NUMBER_OF_TIME_STEPS);

        } else { // this block has the code simulation of CA and GA and show all the statistics

            CelullarAutomata CA = new CelullarAutomata();
            CA.run();
        }
    }
}
