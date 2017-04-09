/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forestfire;

/**
 *
 * @author humayra
 * @description this is the definition class. This class contains all the
 * default values of the project.
 */
public class Defs {

    // Define the window size for the forest
    public static final int WINDOW_HEIGHT = 500;
    public static final int WINDOW_WIDTH = 500;

    // Define the dimension of the forest
    public static final int GRID_HEIGHT = 50;
    public static final int GRID_WIDTH = 50;

    // Define the dimension of each cell in forest
    public static final int CELL_HEIGHT = 2;
    public static final int CELL_WIDTH = 2;

    // Define  total number of time steps
    public static final int NUMBER_OF_TIME_STEPS = 100;

    // Define the propability values
    public static final double PROBABILITY_F = 0.85;
    public static double PROBABILITY_P = 0.00;

    // Define the parameters for GA
    public static final Integer NUMBER_OF_GENERATIONS = 10;
    public static final Integer NUMBER_OF_INDIVIDUAL_IN_POPULATION = 10;

    // Define wheather to check for empty forrest
    public static final boolean CHECK_FOR_EMPTY_FOREST = true;
    public static final boolean USE_FIRE_FIGHTER = true;
    public static final boolean USE_MULTIPLE_SPECIES = true;
}
