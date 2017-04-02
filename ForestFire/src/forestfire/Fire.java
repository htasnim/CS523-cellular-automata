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
import java.awt.Color;
import java.awt.Graphics;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Fire extends JFrame {

    private static final char BURNING = 'w'; //w looks like fire, right?
    private static final char TREE = 'T';
    private static final char EMPTY = '.';
    private List<String> land;
    private JPanel landPanel;

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
                            default: //will catch EMPTY
                                g.setColor(Color.BLACK);
                        }
                        g.fillRect(x * Defs.CELL_WIDTH, y * Defs.CELL_HEIGHT, Defs.CELL_WIDTH, Defs.CELL_HEIGHT);
                    }
                }
            }
        };
        //each block in the land is a 2x2 square
        landPanel.setSize(this.land.get(0).length() * Defs.CELL_WIDTH, this.land.size() * Defs.CELL_HEIGHT);
        add(landPanel);
        setSize(Defs.WINDOW_WIDTH, Defs.WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private List<String> process(List<String> land) {
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
            newLand.add(processRows(rowAbove, thisRow, rowBelow));
        }
        return newLand;
    }

    private String processRows(String rowAbove, String thisRow,
            String rowBelow) {
        String newRow = "";
        for (int i = 0; i < thisRow.length(); i++) {
            switch (thisRow.charAt(i)) {
                case BURNING:
                    newRow += EMPTY;
                    break;
                case EMPTY:
                    newRow += Math.random() < Defs.PROBABILITY_P ? TREE : EMPTY;
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

    public static List<String> populate(int width, int height) {
        List<String> land = new LinkedList<String>();
        for (; height > 0; height--) {//height is just a copy anyway
            StringBuilder line = new StringBuilder(width);
            for (int i = width; i > 0; i--) {
//                line.append((Math.random() < TREE_PROB) ? TREE : EMPTY);
                line.append(EMPTY);
            }
            land.add(line.toString());
        }
        return land;
    }

    public void processN(int n) {
        for (int i = 0; i < n; i++) {
            land = process(land);
            try {
                Thread.sleep(750);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
    }

    public static void main(String[] args) {
        List<String> land = populate(Defs.GRID_WIDTH, Defs.GRID_HEIGHT);
        Fire fire = new Fire(land);
        fire.processN(Defs.NUMBER_OF_TIME_STEPS);
    }
}
