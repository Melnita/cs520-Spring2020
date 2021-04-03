import controller.RowGameController;
import controller.ThreeInARowController;
import controller.TicTacToeController;
import model.RowGamePlayer;
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
import java.util.stream.IntStream;


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
        threeInARowGame = new ThreeInARowController(3, 3);
        threeInARowGameModel = threeInARowGame.gameModel;
        threeInARowGameGUI = new RowGameGUI(threeInARowGame);

        ticTacToeGame = new TicTacToeController(3, 3);
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
        threeInARowGame.move(0,0); // top left block is illegal
        threeInARowGame.move(4,4); // outside dimensions of the game
    }

    @Test(expected = IllegalArgumentException.class)
    public void verifyTicTacToeIllegalMove(){
        ticTacToeGame.move(0,0); // legal move
        ticTacToeGame.move(0,0); // illegal move
        ticTacToeGame.move(4,4); // outside dimensions of the game
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
        threeInARowGame.move(2,0);
        threeInARowGame.move(2,1);
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
        ticTacToeGame.move(2,0);
        ticTacToeGame.move(2,1);
        ticTacToeGame.move(2,2);
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

    @Test
    public void verifyThreeInARowPlayerWin(){
        /*
        * [ ] [ ] [X]
        * [X] [X] [0]
        * [X] [0] [0]
        * */
        threeInARowGame.move(2,0);
        threeInARowGame.move(2,1);
        threeInARowGame.move(1,1);
        threeInARowGame.move(2,2);
        threeInARowGame.move(1,0);
        threeInARowGame.move(1,2);
        threeInARowGame.move(0,2);
        assertEquals(threeInARowGame.isWin(), RowGamePlayer.PLAYER_1); // winner should be player 1
    }

    @Test
    public void verifyTicTacToePlayerWin(){
        /*
        * [ ] [X] [ ]
        * [X] [X] [ ]
        * [0] [0] [0]
        * */
        ticTacToeGame.move(1,1);
        ticTacToeGame.move(2,1);
        ticTacToeGame.move(1,0);
        ticTacToeGame.move(2,2);
        ticTacToeGame.move(0,1);
        ticTacToeGame.move(2,0);
        assertEquals(ticTacToeGame.isWin(), RowGamePlayer.PLAYER_2); // winner should be player 2
    }

    @Test
    public void verifyTicTacToeTie(){
        /*
        * [X] [X] [0]
        * [0] [0] [X]
        * [X] [0] [X]
        * */
        ticTacToeGame.move(2,2);
        ticTacToeGame.move(1,0);
        ticTacToeGame.move(1,2);
        ticTacToeGame.move(2,1);
        ticTacToeGame.move(2,0);
        ticTacToeGame.move(1,1);
        ticTacToeGame.move(0,1);
        ticTacToeGame.move(0,2);
        ticTacToeGame.move(0,0);
        assertTrue(ticTacToeGame.isWin() == null && ticTacToeGameModel.movesLeft == 0); // null if Tie
    }

    @Test
    public void verifyThreeInARowTie(){
        /*
        * [X] [X] [O]
        * [O] [O] [X]
        * [X] [X] [O]
        * */
        threeInARowGame.move(2,1);
        threeInARowGame.move(1,1);
        threeInARowGame.move(0,1);
        threeInARowGame.move(2,2);
        threeInARowGame.move(2,0);
        threeInARowGame.move(1,0);
        threeInARowGame.move(1,2);
        threeInARowGame.move(0,2);
        threeInARowGame.move(0,0);
        assertTrue(threeInARowGame.isWin() == null && threeInARowGameModel.movesLeft == 0); // null if Tie
    }

    @Test
    public void verifyResetThreeInARow(){
        threeInARowGame.move(2,0);
        threeInARowGame.resetGame();
        assertTrue(threeInARowGameModel.getBlock(2, 0).getIsLegalMove());
    }

    @Test
    public void verifyResetTicTacToe(){
        ticTacToeGame.move(0,0);
        ticTacToeGame.resetGame();
        assertTrue(ticTacToeGameModel.getBlock(0, 0).getIsLegalMove());
    }

    @Test
    public void verifyGameModelInitialState(){
        Arrays.asList(threeInARowGameModel, ticTacToeGameModel).forEach(model -> {
            assertEquals(9, model.movesLeft);
            assertEquals(RowGamePlayer.PLAYER_1, model.player);
        });
    }

    @Test
    public void verifyTicTacToeInitialBlocks(){
        assertTrue(Arrays.stream(ticTacToeGameModel.blocksData)
                .flatMap(Arrays::stream)
                .map(RowBlockModel::getIsLegalMove)
                .reduce(true, (subtotal, element) -> subtotal && element));
    }

    @Test
    public void verifyThreeInARowInitialBlocks(){
        assertTrue(Arrays.stream(threeInARowGameModel.blocksData[2])
                .map(RowBlockModel::getIsLegalMove)
                .reduce(true, (subtotal, element) -> subtotal && element));
        IntStream.range(0, 2).forEach(i -> {
            assertFalse(Arrays.stream(threeInARowGameModel.blocksData[i])
                    .peek(System.out::println)
                    .map(RowBlockModel::getIsLegalMove)
                    .reduce(Boolean::logicalAnd).get());
        });
    }

    @Test
    public void verifyGameGUI(){
        threeInARowGame.move(2,0);
        assertEquals("Player 2 to play 'O'", ((JTextArea) threeInARowGameGUI.gameStatusView.messages.getComponent(0)).getText());
        ticTacToeGame.move(0,0);
        ticTacToeGame.move(0,1);
        assertEquals("Player 1 to play 'X'", ((JTextArea) ticTacToeGameGUI.gameStatusView.messages.getComponent(0)).getText());
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
