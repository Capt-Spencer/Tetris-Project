/*
 * TCSS 305 Programming Practicum, Spring 2017
 * Assignment 6 - Tetris.
 */
package view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Board;
import model.TetrisPiece;

/**
 * GameScore is a helper class to calculate the running score for a game.
 * 
 * @author Norris Spencer nisj@uw.edu
 * 
 * @version Tetris_B, 2 June 2017
 */
public class GameScore extends JPanel implements Observer {
    
    /**A generated serial version UID for object serialization.*/
    private static final long serialVersionUID = 2760959334609385910L;
    
    /**INITIAL_SCORE_OFFSET sets the initial score to -4. */
    private static final int INITIAL_SCORE_OFFSET = -4;
    
    /**LEVEL_UP_LINES is the number of lines required to be cleared to go to the next level.*/
    private static final int LEVEL_UP_LINES = 5;
    
    /**PLACED_P stores the number of points for setting a Tetris piece on the board.*/
    private static final int PLACED_P = 4;
    
    /**GRID_ROWS stores the number of rows for the score panel.*/
    private static final int GRID_ROWS = 4;

    /**LEVEL_UP_TIME is the amount decremented from the timer for each level gained.*/
    private static final int HIGH_LEVEL_TIME = 300;

    /**LEVEL_UP_TIME is the amount decremented from the timer for each level gained.*/
    private static final int LEVEL_UP_TIME = 100;

    /**HIGH_LEVEL_UP_TIME is the amount decremented from the timer for each level gained.*/
    private static final int HIGH_LEVEL_UP_TIME = 50;
    
    /**SCORE stores the constant string for the score panel.*/
    private static final String SCORE = "Score:  ";
    
    /**LINES stores the constant string for the score panel.*/
    private static final String LINES = "Lines Cleared:  "; 
    
    /**CURRENT_LVL stores the constant string for the score panel.*/
    private static final String CURRENT_LVL = "Current Level:  "; 
    
    /**NEXT_LVL stores the constant string for the score panel.*/
    private static final String NEXT_LVL = "Next Level in:  "; 
    
    /**
     * DEFAULT_RESOLUTION_X_AND_Y stores the default x and y resolution settings
     * for the score viewing window.
     */
    private static final int DIMENSION_X_AND_Y = 130;
    
    /**SCORE_LIST stores the constant values for the scores.*/
    private static final List<Integer> SCORE_LIST = new ArrayList<Integer>(Arrays.
                    asList(40, 100, 300, 1200));
    
    /**myScoreMap stores the scores for the lines cleared.*/
    private final Map<Integer, Integer> myScoreMap;
    
    /**myTimer stores the passed value of the timer speed.*/
    private final Timer myTimer;
    
    /**myNextLevel stores the decremented lines counter to reach the next level.*/
    private int myNextLevel;
    
    /**myLinesCleared stores the number of lines cleared in the current game.*/
    private int myLinesCleared;
    
    /**myCurrentLevel stores the level of the current game.*/ 
    private int myCurrentLevel;
    
    /**myScore stores the score of the current game.*/
    private int myScore;
    
    /**myScoreLbl stores the information for the current score.*/
    private JLabel myScoreLbl;
    
    /**myClearLbl stores the information for the number of lines cleared.*/
    private JLabel myClearLbl;
    
    /**myCurrLvlLbl stores the information for the current level.*/
    private JLabel myCurrLvlLbl;
    
    /**myNextLvlLbl stores the information for the number of lines to reach the next level.*/
    private JLabel myNextLvlLbl;
    
    /**
     * GameScore is the constructor for this class and initializes all fields
     * with the passed values.
     * 
     * @param theTimer passes the value of the current timer speed.
     */
    public GameScore(final Timer theTimer) {
        super();
        myScore = INITIAL_SCORE_OFFSET;
        myLinesCleared = 0;
        myCurrentLevel = 1;
        myNextLevel = LEVEL_UP_LINES;
        myTimer = theTimer;
        myScoreMap = new TreeMap<Integer, Integer>();
        createScorePanel();
    }
    
    /**createScorePanel creates the score panel for the GUI.*/
    private void createScorePanel() {
        setLayout(new GridLayout(GRID_ROWS, 0));
        populateScoreMap();
        myScoreLbl = new JLabel(SCORE + myScore);
        myClearLbl = new JLabel(LINES + myLinesCleared);
        myCurrLvlLbl = new JLabel(CURRENT_LVL + myCurrentLevel);
        myNextLvlLbl = new JLabel(NEXT_LVL + myNextLevel);
        add(myScoreLbl);
        add(myClearLbl);
        add(myCurrLvlLbl);
        add(myNextLvlLbl);
        setMinimumSize(new Dimension(DIMENSION_X_AND_Y, DIMENSION_X_AND_Y));
    }
    
    /**
     * currentScore calculates the current score.
     * 
     * @param theNumCleared passes this.
     */
    private void currentScore(final int theNumCleared) {
        myScore += (myScoreMap.get(theNumCleared)) * myCurrentLevel;
        myLinesCleared += theNumCleared;
        myScoreLbl.setText(SCORE + myScore);
        myClearLbl.setText(LINES + myLinesCleared);
    }
    
    /**currentLevel calculates the current level.*/
    private void currentLevel() {
        if (myNextLevel <= 0) {
            myNextLevel += LEVEL_UP_LINES;
            myCurrentLevel++;
            myCurrLvlLbl.setText(CURRENT_LVL + myCurrentLevel);
            myNextLvlLbl.setText(NEXT_LVL + myNextLevel);
            if (myTimer.getDelay() <= HIGH_LEVEL_TIME) {
                myTimer.setDelay(myTimer.getDelay() - HIGH_LEVEL_UP_TIME);
            } else {
                myTimer.setDelay(myTimer.getDelay() - LEVEL_UP_TIME);
            }
        }
    }

    /**populateScoreMap builds the score map.*/
    private void populateScoreMap() {
        for (int i = 0; i < SCORE_LIST.size(); i++) {
            myScoreMap.put(i + 1, SCORE_LIST.get(i));
        }
    }

    /**resetScore sets the scores to default settings.*/
    public void resetScore() {
        myScore = 0;
        myLinesCleared = 0;
        myCurrentLevel = 1;
        myNextLevel = LEVEL_UP_LINES;
        myScoreLbl.setText(SCORE + myScore);
        myClearLbl.setText(LINES + myLinesCleared);
        myCurrLvlLbl.setText(CURRENT_LVL + myCurrentLevel);
        myNextLvlLbl.setText(NEXT_LVL + myNextLevel);
    }

    /**
     * update listens for changes in the game code.
     * 
     * @param theObservable passes the observed class.
     * @param theObject passes the object being observed.
     */
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        if (theObservable instanceof Board && theObject instanceof Integer[]) {
            final int curLinesCleared = ((Integer[]) theObject).length; 
            currentScore(curLinesCleared);
            myNextLevel -= curLinesCleared;
            currentLevel();
            myNextLvlLbl.setText(NEXT_LVL + myNextLevel);
            repaint();
        }
        if (theObject instanceof TetrisPiece) {
            myScore += PLACED_P;
            myScoreLbl.setText(SCORE + myScore);
        }
    }
}