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
 * default values, constant values and parameters of the project.
 *
 */
public class Defs {

    // Define the dimension of the forest
    public static final int GRID_HEIGHT = 250; // height of the grid (or forest)
    public static final int GRID_WIDTH = 250; // width of the grid (or forest)

    // Define  total number of time steps
    public static final int NUMBER_OF_TIME_STEPS = 5000; // total number of iterations

    // Define the propability values
    public static final double PROBABILITY_F = 0.001; // lightning strike probability

    // Define the parameters for GA
    public static final Integer NUMBER_OF_GENERATIONS = 10; // number of generations in GA
    public static final Integer NUMBER_OF_INDIVIDUAL_IN_POPULATION = 20; // number of individual in a population in GA
    public static final Double MUTATION_RATE = 0.5; // mutation rate to be used in GA
    public static final Integer NUMBER_OF_ELITE_INDIVIDUAL = 2; // number of elite individual in GA

    public static final boolean USE_FIRE_FIGHTER = true; // true means use fire fighter in CA
    public static final boolean USE_MULTIPLE_SPECIES = false; // true means use 2 species version for CA

    //Parameters for fire fighter
    public static final Integer MAX_FIRE_FIGHTERS = 1000; // maximum number of fire fighter to be used in CA
    public static final Integer FIRE_FIGHTERS_INCREMENT_RATE = 50; // increment rate of fire fighter to observe CA
    public static final Integer MIN_FIRE_FIGHTERS = 0; // minimum number of fire fighter to be used in CA

    //Define running mode
    public static final boolean DEBUG_MODE = false; // enabling debug mode produces lots of traces during simulation of CA

    /**
     * ******** These parameters are only for GUI version **************
     */
    // Define the parameters for GUI
    public static final boolean SHOW_GUI = true; //true means run GUI. false means run the CA and show text output
    public static double PROBABILITY_P1 = 0.50; // probability for growing single species tree in GUI
    public static double PROBABILITY_P2 = 0.80; // probability for growing second species (used only if USE_MULTIPLE_SPECIES = true) tree in GUI
    public static int NUMBER_FIRE_FIGHTER = 50; // number of fire fighter in GUI version
    public static final int TIME_INTERVAL = 750; // time interval (in miliseconds) between two steps in GUI

    // Define the dimension of each cell in forest
    public static final int CELL_HEIGHT = 2; // height of each cell (in pixel) in GUI window
    public static final int CELL_WIDTH = 2; // width of each cell (in pixel) in GUI window

    // Define the window size for the forest
    public static final int WINDOW_HEIGHT = 500; // height of GUI window (in pixel)
    public static final int WINDOW_WIDTH = 500; // width of GUI window (in pixel)
}
