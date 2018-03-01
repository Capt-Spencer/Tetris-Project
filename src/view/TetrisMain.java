/*
 * TCSS 305 Programming Practicum, Spring 2017
 * Assignment 6 - Tetris.
 */
package view;

import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * TetrisMain starts the Tetris game.
 * 
 * @author Norris Spencer nisj@uw.edu
 * 
 * @version Tetris_B, 2 June 2017
 */
public final class TetrisMain {
    
    /**Private constructor, to prevent instantiation of this class.*/
    private TetrisMain() {
        throw new IllegalStateException();
    }

    /**
     * The main method invokes the PowerPaintGUI. Command line arguments are ignored.
     * 
     * @param theArgs Command Line arguments.
     */
    public static void main(final String[] theArgs) {
        /* 
         * Copied from professor provided example in Swing GUI Examples (TooldBar,
         * ColorChooser, File Menu) ColorChooserMenuExample.java.  
         * https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (final UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (final IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (final InstantiationException ex) {
            ex.printStackTrace();
        } catch (final ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TetrisGUI().start();
            }
        });
    }
}
