/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forestfire;

/**
 *
 * @author humayra
 * @description this class is used to store the coordinate of a cell in the
 * forest
 *
 */
public class CellPosition {

    private int x; // holds row value of the forest grid
    private int y; // holds column value of the forest grid

    /*
    *
    * constructor of the class
    * initializes with the given row and column value
    * @param1 row value of the grid
    * @param2 column value of the grid
    *
     */
    public CellPosition(int i, int j) {
        this.x = i;
        this.y = j;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

}
