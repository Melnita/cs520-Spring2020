import controller.RowGameController;
import controller.ThreeInARowController;
import controller.TicTacToeController;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import model.RowBlockModel;
import model.RowGameModel;
import view.RowGameGUI;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * An example test class, which merely shows how to write JUnit tests.
 */
public class TestExample {
    private RowGameModel ticTacToeGameModel;
    private RowGameController ticTacToeGame;
    private RowGameGUI ticTacToeGameGUI;

    private RowGameModel threeInARowGameModel;
    private RowGameController threeInARowGame;
    private RowGameGUI threeInARowGameGUI;

    @Before
    public void setUp() {
        threeInARowGame = new ThreeInARowController();
        threeInARowGameModel = threeInARowGame.gameModel;
        threeInARowGameGUI = new RowGameGUI(threeInARowGame);

        ticTacToeGame = new TicTacToeController();
        ticTacToeGameModel = ticTacToeGame.gameModel;
        ticTacToeGameGUI = new RowGameGUI(ticTacToeGame);
    }

    @After
    public void tearDown() {
        threeInARowGameModel = null;
        threeInARowGame = null;
        threeInARowGameGUI = null;

        ticTacToeGame = null;
        ticTacToeGameModel = null;
        ticTacToeGameGUI = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void verifyThreeInARowIllegalMove(){
        threeInARowGame.move(threeInARowGameGUI.gameBoardView.blocks[0][0]); // top left block is illegal
        threeInARowGame.move(threeInARowGameGUI.gameBoardView.blocks[4][4]); // outside dimensions of the game
    }

    @Test(expected = IllegalArgumentException.class)
    public void verifyTicTacToeIllegalMove(){
        ticTacToeGame.move(ticTacToeGameGUI.gameBoardView.blocks[0][0]); // legal move
        ticTacToeGame.move(ticTacToeGameGUI.gameBoardView.blocks[0][0]); // illegal move
        ticTacToeGame.move(ticTacToeGameGUI.gameBoardView.blocks[4][4]); // outside dimensions of the game
    }

    /**
     * Check for legal moves in 3-in-a-row game.
     * []   []  []
     * [L]  [L] []
     * [X]  [0] [L]
     * L -> Legal
     */
    @Test
    public void verifyThreeInARowLegalMove(){
        threeInARowGame.move(threeInARowGameGUI.gameBoardView.blocks[2][0]);
        threeInARowGame.move(threeInARowGameGUI.gameBoardView.blocks[2][1]);
        assertTrue(threeInARowGameModel.getBlock(1, 0).getIsLegalMove());
        assertTrue(threeInARowGameModel.getBlock(1, 1).getIsLegalMove());
        assertTrue(threeInARowGameModel.getBlock(2, 2).getIsLegalMove());
        assertFalse(threeInARowGameModel.getBlock(2, 0).getIsLegalMove());
        assertFalse(threeInARowGameModel.getBlock(2, 1).getIsLegalMove());
        assertFalse(threeInARowGameModel.getBlock(0, 0).getIsLegalMove());
        assertFalse(threeInARowGameModel.getBlock(0, 1).getIsLegalMove());
        assertFalse(threeInARowGameModel.getBlock(0, 2).getIsLegalMove());
        assertFalse(threeInARowGameModel.getBlock(1, 2).getIsLegalMove());
    }

    /**
     * Check for legal moves in Tic Tac Toe -row game.
     * [L]   [L]  [L]
     * [L]   [L]  [L]
     * [X]   [0]  [X]
     * L -> Legal
     */
    @Test
    public void verifyTicTacToeMove(){
        ticTacToeGame.move(ticTacToeGameGUI.gameBoardView.blocks[2][0]);
        ticTacToeGame.move(ticTacToeGameGUI.gameBoardView.blocks[2][1]);
        ticTacToeGame.move(ticTacToeGameGUI.gameBoardView.blocks[2][2]);
        assertTrue(ticTacToeGameModel.getBlock(0, 0).getIsLegalMove());
        assertTrue(ticTacToeGameModel.getBlock(0, 1).getIsLegalMove());
        assertTrue(ticTacToeGameModel.getBlock(0, 2).getIsLegalMove());
        assertTrue(ticTacToeGameModel.getBlock(1, 0).getIsLegalMove());
        assertTrue(ticTacToeGameModel.getBlock(1, 1).getIsLegalMove());
        assertTrue(ticTacToeGameModel.getBlock(1, 2).getIsLegalMove());
        assertFalse(ticTacToeGameModel.getBlock(2, 0).getIsLegalMove());
        assertFalse(ticTacToeGameModel.getBlock(2, 1).getIsLegalMove());
        assertFalse(ticTacToeGameModel.getBlock(2, 2).getIsLegalMove());
    }

    private List<JButton> getJButtonsfromMoves(JButton[][] blocks, int[][] moves){
        return Arrays.stream(moves)
                .map(move -> getJButtonFromMove(blocks, move))
                .collect(Collectors.toList());
    }

    private JButton getJButtonFromMove(JButton[][] blocks, int[] move){
        return blocks[move[0]][move[1]];
    }


}
