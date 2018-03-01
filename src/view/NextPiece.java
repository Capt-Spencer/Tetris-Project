/*
 * TCSS 305 Programming Practicum, Spring 2017
 * Assignment 6 - Tetris.
 */
package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import model.Point;
import model.TetrisPiece;

/**
 * NextPiece is a helper class to create the display panel for the next Tetris piece.
 * 
 * @author Norris Spencer nisj@uw.edu
 * 
 * @version Tetris_B, 2 June 2017
 */
public class NextPiece extends JPanel implements Observer {

    /**A generated serial version UID for object serialization.*/
    private static final long serialVersionUID = 8082798712118734225L;
    
    /**ALIGN_FACTOR adjust the image of the Tetris.*/
    private static final int ALIGN_FACTOR = 5;
    
    /**
     * DEFAULT_RESOLUTION_X_AND_Y stores the default x and y resolution settings
     * for the next piece viewing window.
     */
    private static final int DIMENSION_X_AND_Y = 130;
    
    /**myColorMap stores the passed map of Tetris piece colors.*/
    private final Map<String, Color> myColorMap;
    
    /**mySize stores the size of a Tetris block.*/
    private final int mySize;
    
    /**myPiece stores the next Tetris piece.*/
    private TetrisPiece myPiece;
    
    /**
     * NextPiece is the constructor for this class and initializes all fields
     * with the passed values.
     * 
     * @param theMap passes the map of Tetris piece colors.
     * @param theSize passes the current square size factor.
     */
    public NextPiece(final int theSize, final Map<String, Color> theMap) {
        super();
        myColorMap = theMap;
        mySize = theSize;
        setPreferredSize(new Dimension(DIMENSION_X_AND_Y, DIMENSION_X_AND_Y));
        setBackground(Color.BLACK);
        setVisible(true);
    }
    
    /**
     * paintComponent creates the visual images of the shapes in the next piece viewing window.
     * 
     * @param theGraphics passes the graphics context to use for painting.
     */
    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        if (myPiece != null) {
            for (int i = myPiece.getPoints().length - 1; i >= 0; i--) {
                final Point point = myPiece.getPoints()[i];
                g2d.setStroke(new BasicStroke(1));
                if (myPiece.getBlock().toString().equals("O")) {
                    g2d.setColor(myColorMap.get(myPiece.getBlock().toString()));
                    g2d.fillRect((point.x() * mySize) + ((ALIGN_FACTOR + 1) * ALIGN_FACTOR),
                                 getHeight() - (point.y() * mySize)
                                 - (((ALIGN_FACTOR * 2) * ALIGN_FACTOR) - 1),
                                 mySize, mySize);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect((point.x() * mySize) + ((ALIGN_FACTOR + 1) * ALIGN_FACTOR),
                                 getHeight() - (point.y() * mySize)
                                 - ((ALIGN_FACTOR * 2) * ALIGN_FACTOR),
                                 mySize, mySize);
                } else if (myPiece.getBlock().toString().equals("I")) {
                    g2d.setColor(myColorMap.get(myPiece.getBlock().toString()));
                    g2d.fillRect((point.x() * mySize) + ((ALIGN_FACTOR + 1) * ALIGN_FACTOR),
                                 getHeight() - (point.y() * mySize)
                                 - (((ALIGN_FACTOR + 2) * ALIGN_FACTOR) - 1),
                                 mySize, mySize);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect((point.x() * mySize) + ((ALIGN_FACTOR + 1) * ALIGN_FACTOR),
                                 getHeight() - (point.y() * mySize)
                                 - ((ALIGN_FACTOR + 2) * ALIGN_FACTOR),
                                 mySize, mySize);
                } else {
                    g2d.setColor(myColorMap.get(myPiece.getBlock().toString()));
                    g2d.fillRect((point.x() * mySize)
                                 + (((ALIGN_FACTOR - 1) * 2) * ALIGN_FACTOR),
                                 getHeight() - (point.y() * mySize)
                                 - (((ALIGN_FACTOR * 2) * ALIGN_FACTOR) - 1),
                                 mySize, mySize);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect((point.x() * mySize)
                                 + (((ALIGN_FACTOR - 1) * 2) * ALIGN_FACTOR),
                                 getHeight() - (point.y() * mySize)
                                 - ((ALIGN_FACTOR * 2) * ALIGN_FACTOR),
                                 mySize, mySize);
                }
            }
        }
    }
    
    /**
     * update listens for changes in the game code.
     * 
     * @param theObservable passes the observed class.
     * @param theObject passes the object being observed.
     */
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        if (theObject instanceof TetrisPiece) {
            myPiece = (TetrisPiece) theObject;
            repaint();
        }
    }
}