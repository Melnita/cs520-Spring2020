package controller;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import model.RowBlockModel;
import model.RowGameModel;
import model.RowGamePlayer;
import view.RowGameGUI;


/**
 * Java implementation of the 3 in a row game, using the Swing framework.
 *
 * This quick-and-dirty implementation violates a number of software engineering
 * principles and needs a thorough overhaul to improve readability,
 * extensibility, and testability.
 */
public class RowGameController {

    public RowGameModel gameModel;
    public RowGameGUI gameView;


    /**
     * Creates a new game initializing the GUI.
     */
    public RowGameController() {
	gameModel = new RowGameModel();
	gameView = new RowGameGUI(this);
	
	resetGame();
    }

    public RowGameModel getModel() {
	return this.gameModel;
    }

    public RowGameGUI getView() {
	return this.gameView;
    }

    public void startUp() {
	gameView.makeVisible();
    }

    /**
     * Moves the current player into the given block.
     *
     * @param block The block to be moved to by the current player
     */
    public void move(JButton block) {
	gameModel.movesLeft = gameModel.movesLeft - 1;

	RowGamePlayer player = gameModel.player;
	int movesLeft = gameModel.movesLeft;
	AtomicInteger currentPosition = new AtomicInteger(-1);
	List<JButton> flatbuttonsList = Arrays.stream(gameView.gameBoardView.blocks).flatMap(Arrays::stream).collect(Collectors.toList());
	JButton currentButton = flatbuttonsList.stream()
			.peek(x -> currentPosition.incrementAndGet())
			.filter(block::equals)
			.findFirst()
			.get();
	List<RowBlockModel> flatBlocks = Arrays.stream(gameModel.blocksData)
			.flatMap(Arrays::stream)
			.collect(Collectors.toList());
	RowBlockModel currentBlock = flatBlocks.get(currentPosition.get());
	currentBlock.setContents(player == RowGamePlayer.PLAYER_1 ? "X" : "0");
	gameModel.player = togglePlayer(gameModel.player);
	RowBlockModel aboveBlock = flatBlocks.get(currentPosition.get() - 3 >= 0 ? currentPosition.get() - 3: currentPosition.get());
	aboveBlock.setIsLegalMove(true); // TODO: aboveBlock to be calculated for PLAYER_2 also ?
	currentBlock.setIsLegalMove(false);
	if(this.isWin()){
		this.endGame();
		gameModel.setFinalResult(RowGameMessage.PLAYER_1_WINS);
	}
	gameView.update(gameModel);
    }

    /**
     * Ends the game disallowing further player turns.
     */
    public void endGame() {
	for(int row = 0;row<3;row++) {
	    for(int column = 0;column<3;column++) {
		this.gameModel.blocksData[row][column].setIsLegalMove(false);
	    }
	}

	gameView.update(gameModel);
    }

    /**
     * Resets the game to be able to start playing again.
     */
    public void resetGame() {
        for(int row = 0;row<3;row++) {
            for(int column = 0;column<3;column++) {
                gameModel.blocksData[row][column].reset();
		// Enable the bottom row
	        gameModel.blocksData[row][column].setIsLegalMove(row == 2);
            }
        }gameModel.player = RowGamePlayer.PLAYER_1;
	gameModel.movesLeft = 9;
	gameModel.setFinalResult(null);
	gameView.update(gameModel);
    }

    public RowGamePlayer togglePlayer(RowGamePlayer player){
    	return player == RowGamePlayer.PLAYER_1 ? RowGamePlayer.PLAYER_2 : RowGamePlayer.PLAYER_1 ;
	}

	static class RowGameMessage {
		public static final String GAME_END_NOWINNER = "Game ends in a draw";
		public static final String PLAYER_1_WINS = "Player 1 wins!";
		public static final String PLAYER_2_WINS = "Player 2 wins!";
	}

	public boolean isWin(){
		System.out.println(Arrays.deepToString(gameModel.blocksData));
		/*
		* Rule #1 : if all columns are the same
		* TODO : Remove 3x3 hardcoding, make generic for all columns
		* */
		List<String> filteredBlockContent = Arrays.stream(gameModel.blocksData)
				.map(row -> row[0].getContents())
				.filter(blockContent -> !blockContent.equals(""))
				.collect(Collectors.toList());
		return filteredBlockContent.stream().distinct().count() == 1 && filteredBlockContent.size() == 3;
//    	return true;
	}

}
