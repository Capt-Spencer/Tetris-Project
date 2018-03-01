/*
 * TCSS 305 Programming Practicum, Spring 2017
 * Assignment 6 - Tetris.
 */
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Board;

/**
 * TetrisGUI is the GUI for the Tetris game.
 * 
 * @author Norris Spencer nisj@uw.edu
 * 
 * @version Tetris_B, 2 June 2017
 */
public class TetrisGUI implements Observer {
    
    /**INITIAL_TIMER_DELAY is the initial delay for the game piece to begin dropping.*/
    private static final int INITIAL_DELAY = 500;
    
    /**START_SPEED is the default delay (in milliseconds) for the move timer. */
    private static final int START_SPEED = 1_000;
    
    /**BLOCK_SIZE stores the size of a Tetris block.*/
    private static final int BLOCK_SIZE = 20;
    
    /**DIM_X stores the x dimension for the screen size.*/
    private static final int DIM_X = 350;
    
    /**DIM_Y stores the x dimension for the screen size.*/
    private static final int DIM_Y = 450;
    
    /**GAME_OVER stores the string for the JOptionPane message when the game is over.*/
    private static final String GAME_OVER = "Game Over!";
    
    /**LIME creates the lime color for a Tetris piece.*/
    private static final Color LIME = new Color(0, 255, 0);
    
    /**PURPLE creates the purple color for a Tetris piece.*/
    private static final Color PURPLE = new Color(128, 0, 128);
    
    /**
     * ICON is the icon string for the directory to establish the icon.
     * Image obtained from:
     * https://www.iconfinder.com/icons/52626/game_games_play_tetris_icon
     */
    private static final ImageIcon ICON = new ImageIcon("./images/tetris_icon.png");
    
    /**myFrame is the main frame for the program.*/
    private final JFrame myFrame;
    
    /**myBoard stores the instantiation of a new Tetris board.*/
    private final Board myBoard;
    
    /**myGameBoard instantiates a new instance of the GameBoard.*/
    private final GameBoard myGameBoard;
    
    /**myNextPiece instantiates a new instance of the NextPiece class.*/
    private final NextPiece myNextPiece;
    
    /**myGameScore instantiates a new instance of the GameScore class.*/
    private final GameScore myGameScore;
    
    /**myInstructionPanel instantiates a new instance of the InstructionPanel class.*/
    private final InstructionPanel myInstructionPanel;
    
    /**myTimer is the timer for the game updates.*/
    private final Timer myTimer;
    
    /**myGameOver sets a boolean if the game is over to true.*/
    private boolean myGameOver;

    /**
     * myPieceMovable stores the boolean of the game status: false if the piece
     * is still playable and true if not.
     */
    private boolean myPieceNotMoveable;
    
    /**TetrisGUI is the constructor for the class that initializes all fields.*/
    public TetrisGUI() {
        myGameOver = true;
        myTimer = new Timer(START_SPEED, new TimerListener());
        myFrame = new JFrame();
        myBoard = new Board();
        myGameBoard = new GameBoard(myBoard, BLOCK_SIZE, populateColorMap());
        myNextPiece = new NextPiece(BLOCK_SIZE, populateColorMap());
        myGameScore = new GameScore(myTimer);
        myInstructionPanel = new InstructionPanel();
    }

    /**start displays a JFrame with the created components.*/
    public void start() {
        keyListenHelper();
        final JPanel gamePanel = new JPanel(new BorderLayout());
        final JPanel infoPanel = new JPanel(new BorderLayout());
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setIconImage(((ImageIcon) ICON).getImage());
        gamePanel.add(myGameBoard, BorderLayout.CENTER);
        gamePanel.setMinimumSize(myGameBoard.getBoardSize());
        infoPanel.add(myNextPiece, BorderLayout.NORTH);
        infoPanel.add(myGameScore, BorderLayout.CENTER);
        infoPanel.add(myInstructionPanel, BorderLayout.SOUTH);
        myFrame.add(gamePanel, BorderLayout.CENTER);
        myFrame.add(infoPanel, BorderLayout.EAST);
        myBoard.addObserver(myGameBoard);
        myBoard.addObserver(myNextPiece);
        myBoard.addObserver(myGameScore);        
        myBoard.addObserver(this);
        myFrame.setResizable(false);
        myFrame.setMinimumSize(new Dimension(DIM_X, DIM_Y));
        myFrame.setVisible(true);
        myFrame.setLocationRelativeTo(null);
        myFrame.pack();
        myGameScore.resetScore();
        JOptionPane.showMessageDialog(null, "Scores are factored by current level with"
                        + "\nthe exception of the placed piece:\n\nPlaced Piece: 4"
                        + "\n1 Line: 40\n2 Lines: 100\n3 Lines: 300\nTetris: 1200",
                        "Point System", JOptionPane.OK_OPTION, ICON);
    }

    /**KeyListenHelper is a helper method to set all the keyListeners.*/
    private void keyListenHelper() {
        myFrame.addKeyListener(new KeySelectedMovesASD());
        myFrame.addKeyListener(new KeySelectedMovesWSpace());
        myFrame.addKeyListener(new KeySelectedGrid());
        myFrame.addKeyListener(new KeySelectedNewGame());
        myFrame.addKeyListener(new KeySelectedEndGame());
        myFrame.addKeyListener(new KeySelectedPauseGame());
    }

    /**
     * populateColorMap builds the map list of the colors according to the Tetris piece.
     * 
     * @return the populated map of colors.
     */
    private Map<String, Color> populateColorMap() {
        final Map<String, Color> tempMap = new TreeMap<String, Color>();
        tempMap.put("I", Color.CYAN);
        tempMap.put("J", Color.BLUE);
        tempMap.put("L", Color.ORANGE);
        tempMap.put("O", Color.YELLOW);
        tempMap.put("S", LIME);
        tempMap.put("T", PURPLE);
        tempMap.put("Z", Color.RED);
        return tempMap;
    }

    /**
     * update listens for changes in the game code..
     * 
     * @param theObservable passes the observed class.
     * @param theObject passes the object being observed.
     */
    @Override
    public void update(final Observable theObservable, final Object theObject) {
        if (theObservable instanceof Board && theObject instanceof Boolean) {
            myTimer.stop();
            myPieceNotMoveable = true;
            myGameOver = true;
            JOptionPane.showMessageDialog(null, GAME_OVER, GAME_OVER,
                                          JOptionPane.OK_OPTION, ICON);
        }
    }

    /**
     * TimerListener is a listener for the program to update the game by a single step.
     * 
     * @author Norris Spencer nisj@uw.edu
     * 
     * @version Tetris_B 2 June 2017
     */
    private class TimerListener implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            myBoard.step();
        }
    }
    
    /**
     * KeySelectedMovesASD listens for the key selected by
     *  the user to change the position of the Tetris piece for
     *  the left, right and down commands.
     * 
     * @author Norris Spencer nisj@uw.edu
     * 
     * @version Tetris_B, 2 June 2017
     */
    private class KeySelectedMovesASD extends KeyAdapter {
        
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            final int location = theEvent.getKeyCode();
            if (!myGameOver && myPieceNotMoveable) {
                switch (location) {
                    case KeyEvent.VK_A:
                        myBoard.left();
                        break;
                    case KeyEvent.VK_LEFT:
                        myBoard.left();
                        break;
                    case KeyEvent.VK_S:
                        myBoard.down();
                        break;
                    case KeyEvent.VK_DOWN:
                        myBoard.down();
                        break;
                    case KeyEvent.VK_D:
                        myBoard.right();
                        break;
                    case KeyEvent.VK_RIGHT:
                        myBoard.right();
                        break;
                    default:
                        break;
                }
            }
        }
    }
    
    /**
     * KeySelectedMovesWSpace listens for the key selected by
     *  the user to change the position of the Tetris piece for
     *  the rotate CW/CCW and drop commands.
     * 
     * @author Norris Spencer nisj@uw.edu
     * 
     * @version Tetris_B, 2 June 2017
     */
    private class KeySelectedMovesWSpace extends KeyAdapter {
        
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            final int location = theEvent.getKeyCode();
            if (!myGameOver && myPieceNotMoveable) {
                switch (location) {
                    case KeyEvent.VK_W:
                        myBoard.rotateCW();
                        break;
                    case KeyEvent.VK_UP:
                        myBoard.rotateCW();
                        break;
                    case KeyEvent.VK_E:
                        myBoard.rotateCCW();
                        break;
                    case KeyEvent.VK_END:
                        myBoard.rotateCCW();
                        break;
                    case KeyEvent.VK_SPACE:
                        myBoard.drop();
                        break;
                    default:
                        break;
                }
            }
        }
    }
    
    /**
     * KeySelectedNewGame listens for the key selected by the user to turn
     * on the grid assister.
     * 
     * @author Norris Spencer nisj@uw.edu
     * 
     * @version Tetris_B, 2 June 2017
     */
    private class KeySelectedGrid extends KeyAdapter {
     
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            final int location = theEvent.getKeyCode();
            if (location == KeyEvent.VK_G && myGameBoard.isGrid()) {
                myGameBoard.setGrid(false);
            } else if (location == KeyEvent.VK_G && !myGameBoard.isGrid()) {
                myGameBoard.setGrid(true);
            }
        }
    }
    
    /**
     * KeySelectedNewGame listens for the key selected by the user to start a new game.
     * 
     * @author Norris Spencer nisj@uw.edu
     * 
     * @version Tetris_B, 2 June 2017
     */
    private class KeySelectedNewGame extends KeyAdapter {
     
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            final int location = theEvent.getKeyCode();
            if (location == KeyEvent.VK_F1) {
                myTimer.setInitialDelay(INITIAL_DELAY);
                myTimer.setDelay(START_SPEED);
                myTimer.start();
                myBoard.newGame();
                myGameScore.resetScore();
                myGameOver = false;
                myPieceNotMoveable = true;
            }
        }
    }
    
    /**
     * KeySelectedEndGame listens for the key selected by the user to end the current game.
     * 
     * @author Norris Spencer nisj@uw.edu
     * 
     * @version Tetris_B, 2 June 2017
     */
    private class KeySelectedEndGame extends KeyAdapter {
     
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            final int location = theEvent.getKeyCode();
            if (!myGameOver && location == KeyEvent.VK_F5) {
                myTimer.stop();
                myPieceNotMoveable = false;
                myGameOver = true;
                JOptionPane.showMessageDialog(null, GAME_OVER, GAME_OVER,
                                              JOptionPane.OK_OPTION, ICON);
            }
        }
    }
    
    /**
     * KeySelectedEndGame listens for the key selected by the user to end the current game.
     * 
     * @author Norris Spencer nisj@uw.edu
     * 
     * @version Tetris_B, 2 June 2017
     */
    private class KeySelectedPauseGame extends KeyAdapter {
     
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            final int location = theEvent.getKeyCode();
            if (!myGameOver && location == KeyEvent.VK_PAUSE && myTimer.isRunning()) {
                myTimer.stop();
                myPieceNotMoveable = false;
            } else if (!myGameOver && location == KeyEvent.VK_PAUSE && !myTimer.isRunning()) {
                myTimer.start();
                myPieceNotMoveable = true;
            }
        }
    }
}