/*
 * TCSS 305 Programming Practicum, Spring 2017
 * Assignment 6 - Tetris.
 */
package view;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * InstructionPanel creates a JLabel for the instructions to play the game.
 * 
 * @author Norris Spencer nisj@uw.edu
 * 
 * @version Tetris_B, 2 June 2017
 */
public class InstructionPanel extends JPanel {
    
    /**A generated serial version UID for object serialization.*/
    private static final long serialVersionUID = -6432212008724788255L;

    /**InstructionPanel is the constructor for the class that initializes all fields.*/
    public InstructionPanel() {
        super();
        add(new JLabel("<html>Left: \"A\" or Left<br>"
                        + "Right: \"D\" or Right<br>"
                        + "Down: \"S\" or Down<br>"
                        + "Rotate CW: \"W\" or Up<br>"
                        + "Rotate CCW: \"E\" or END<br>"
                        + "Drop: Space Bar<br>"
                        + "New Game: F1<br>"
                        + "End Game: F5<br>"
                        + "Pause Game: Pause<br>"
                        + "Grid: \"G\"<html>"));
    }
}