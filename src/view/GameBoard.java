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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import model.Block;
import model.Board;

/**
 * GameBoard is the GUI for the power paint program.
 * 
 * @author Norris Spencer nisj@uw.edu
 * 
 * @version Tetris_B, 2 June 2017
 */
public class GameBoard extends JPanel implements Observer {

    /**A generated serial version UID for object serialization.*/
    private static final long serialVersionUID = -1406077166444402338L;
    
    /**FLOW_CORRECTION stores the constant value to adjust the flow of the Tetris pieces.*/
    private static final int FLOW_CORRECTION = 20;
    
    /**mySize stores the size of a Tetris block.*/
    private final int mySize;
    
    /**myBoardSize stores the dimensional size of the game board.*/
    private final Dimension myBoardSize;
    
    /**myPosition stores the current position of the Tetris pieces.*/
    private List<Block[]> myPosition;
    
    /**myColorMap stores the passed map of Tetris piece colors.*/
    private final Map<String, Color> myColorMap;
    
    /**myGrid stores the current boolean value for the grid helper.*/
    private Boolean myGrid;
    
    /**
     * GameBoard is the constructor for this class and initializes all fields
     * with the passed values.
     * 
     * @param theMap passes the map of Tetris piece colors.
     * @param theBoard passes in the active game board.
     * @param theSize passes the current square size factor.
     */
    public GameBoard(final Board theBoard, final int theSize,
                     final Map<String, Color> theMap) {
        super();
        myColorMap = theMap;
        myGrid = false;
        mySize = theSize;
        myBoardSize = new Dimension(mySize * theBoard.getWidth(),
                                    mySize * theBoard.getHeight());
        setMinimumSize(myBoardSize);
        setBackground(Color.LIGHT_GRAY);
        setVisible(true);
    }
    
    /**
     * paintComponent creates the visual images of the shapes in the drawing area.
     * 
     * @param theGraphics passes the graphics context to use for painting.
     */
    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);
        if (myPosition != null) {
//            for (int i = myPosition.size() - 1; i >= 0; i--) {
            for (int i = 0; i < myPosition.size(); i++) {
                final Block[] temp = myPosition.get(i);
                final int row = i;
                for (int j = 0; j <= temp.length - 1; j++) {
                    final int col = j;
                    if (temp[j] != null) {
                        g2d.setColor(myColorMap.get(temp[j].name()));
                        g2d.fillRect(col * mySize, (FLOW_CORRECTION - row)
                                     * mySize, mySize, mySize);
                        g2d.setStroke(new BasicStroke(1));
                        g2d.setColor(Color.BLACK);
                        g2d.drawRect(col * mySize, (FLOW_CORRECTION - row)
                                     * mySize, mySize, mySize);
                    }
                    if (myGrid) {
                        g2d.setStroke(new BasicStroke(1));
                        g2d.setColor(Color.WHITE);
                        g2d.drawRect(col * mySize, (FLOW_CORRECTION - row)
                                     * mySize, mySize, mySize);
                    }
                }
            }
        }
    }

//    /**
//     * colorHelper this.
//     * 
//     * @param thePiece this.
//     * 
//     * @return this.
//     */
//    private Color colorHelper(final Block thePiece) {
////        Color tempColor = null;
////        if (thePiece != null) {
////            if (thePiece == Block.I) {
////                tempColor = myColorMap.get("I");
////            }
////        }
////        return tempColor;
//        return myColorMap.get(thePiece.name());
//    }

    /**
     * getGrid returns the boolean value of myGrid.
     * 
     * @return the boolean value of myGrid.
     */
    public boolean isGrid() {
        return myGrid;
    }
    
    /**
     * setGrid sets the boolean value for myGrid..
     * 
     * @param theGrid passes in the boolean value for myGrid.
     */
    public void setGrid(final boolean theGrid) {
        myGrid = theGrid;
    }
    
    /**
     * getBoardSize returns the size of the game board.
     * 
     * @return the size of the game board.
     */
    public Dimension getBoardSize() {
        return myBoardSize;
    }
    
    /**update listens for changes in the game code..
     * 
     * @param theObservable passes the observed class.
     * @param theObject passes the object being observed.
     */
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        if (theObject instanceof List<?>) {
            final List<Block[]> tempList = new ArrayList<Block[]>();
            for (int i = 0; i < ((List<?>) theObject).size(); i++) {
                tempList.add((Block[]) (((List<?>) theObject).get(i)));
            }
            myPosition = tempList;
            repaint();
        }
    }
}