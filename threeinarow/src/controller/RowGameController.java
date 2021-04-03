package controller;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
public abstract class RowGameController {

    public RowGameModel gameModel;
    public int rows;
    public int cols;
    /**
     * Creates a new game initializing the GUI.
     */
    public RowGameController(int rows, int cols) {
    	this.rows = rows;
    	this.cols = cols;
	gameModel = new RowGameModel(rows, cols);
	
	resetGame();
    }

    public RowGameModel getModel() {
	return this.gameModel;
    }

    /**
     * Moves the current player into the given block.
     *
     * @param row row position which was clicked
	 * @param column col position which was clicked
     */
    public void move(int row, int column) {
	RowGamePlayer player = gameModel.player;
	this.gameModel.moveCompleted();
	System.out.println("ticTacToeGame.move(ticTacToeGameGUI.gameBoardView.blocks[" + row + "][" + column + "]);");
	RowBlockModel currentBlock = this.gameModel.blocksData[row][column];
	if(!currentBlock.getIsLegalMove())
		throw new IllegalArgumentException("Not a legal move");
	if(row >= gameModel.rows || column >= gameModel.cols)
		throw new IllegalArgumentException("Outside board dimensions");
	currentBlock.setContents(player == RowGamePlayer.PLAYER_1 ? "X" : "0");
	gameModel.player = togglePlayer(gameModel.player);
	List<RowBlockModel> legalBlocks = this.getLegalBlocks(row * gameModel.cols + column);
	legalBlocks.forEach(legalblock -> legalblock.setIsLegalMove(true));
	currentBlock.setIsLegalMove(false);
	RowGamePlayer winner = this.isWin();
	gameModel.setFinalResult(null);
	if(winner == null){
		if(gameModel.movesLeft == 0){
			this.endGame();
			gameModel.setFinalResult(RowGameMessage.GAME_END_NOWINNER);
		}
	}
	else {
		switch (winner) {
			case PLAYER_1:
				this.endGame();
				gameModel.setFinalResult(RowGameMessage.PLAYER_1_WINS);
				break;
			case PLAYER_2:
				this.endGame();
				gameModel.setFinalResult(RowGameMessage.PLAYER_2_WINS);
				break;
		}
	}
    }

    /**
     * Ends the game disallowing further player turns.
     */
    public void endGame() {
	for(int row = 0;row< gameModel.rows;row++) {
	    for(int column = 0;column< gameModel.cols;column++) {
		this.gameModel.blocksData[row][column].setIsLegalMove(false);
	    }
	}
//	gameView.update(gameModel);
    }

    /**
     * Resets the game to be able to start playing again.
     */
    public abstract void resetGame();

    public RowGamePlayer togglePlayer(RowGamePlayer player){
    	return player == RowGamePlayer.PLAYER_1 ? RowGamePlayer.PLAYER_2 : RowGamePlayer.PLAYER_1 ;
	}

	static class RowGameMessage {
		public static final String GAME_END_NOWINNER = "Game ends in a draw";
		public static final String PLAYER_1_WINS = "Player 1 wins!";
		public static final String PLAYER_2_WINS = "Player 2 wins!";
	}

	/**
	 *
	 * @return TODO : Winner of the game. Will return null if no winner as of current state
	 */
	public RowGamePlayer isWin(){
		RowGamePlayer winner = null;
		String winnerString = "";
		/*
		* Rule #1 : if all columns are the same
		* TODO : Remove 3x3 hardcoding
		* */
		for(int currentRow = 0; currentRow < cols; currentRow++){
			int finalCurrentRow = currentRow;
			List<String> filteredBlockContent = Arrays.stream(gameModel.blocksData)
					.map(row -> row[finalCurrentRow].getContents())
					.filter(blockContent -> !blockContent.equals(""))
					.collect(Collectors.toList());
			if(filteredBlockContent.stream().distinct().count() == 1 && filteredBlockContent.size() == rows) {
				return mapStringtoWinner(filteredBlockContent.stream().distinct().findFirst().get());
			}
		}
		/*
		* Rule #2 : if all rows are the same
		* */
		winnerString = Arrays.stream(gameModel.blocksData)
				.map(row -> {
					List<String> filteredBlockContent = Arrays.stream(row)
							.map(RowBlockModel::getContents)
							.filter(blockContent -> !blockContent.equals(""))
							.collect(Collectors.toList());
					if(filteredBlockContent.stream().distinct().count() == 1 && filteredBlockContent.size() == cols) return filteredBlockContent.stream().distinct().findFirst().get();
					return null;
				})
				.filter(Objects::nonNull)
				.findAny()
				.orElse("");
		/*
		 * Rule #3 : if all diagonals are the same
		 * */
		if(rows == cols) {
			List<String> diagonalElementsRight = IntStream.range(0, rows)
					.mapToObj(i -> gameModel.blocksData[i][i].getContents())
					.filter(blockContent -> !blockContent.equals(""))
					.collect(Collectors.toList());
			if (diagonalElementsRight.stream().distinct().count() == 1 && diagonalElementsRight.size() == rows)
				winnerString = diagonalElementsRight.stream().distinct().findFirst().get();
			List<String> diagonalElementsLeft = IntStream.range(0, rows)
					.mapToObj(i -> gameModel.blocksData[i][rows - 1 - i].getContents())
					.filter(blockContent -> !blockContent.equals(""))
					.collect(Collectors.toList());
			if (diagonalElementsLeft.stream().distinct().count() == 1 && diagonalElementsLeft.size() == rows)
				winnerString = diagonalElementsLeft.stream().distinct().findFirst().get();

		}
		return mapStringtoWinner(winnerString);
	}

	abstract List<RowBlockModel> getLegalBlocks(int currentPosition);

	RowGamePlayer mapStringtoWinner(String winnerString){
		switch (winnerString){
			case "X" : return RowGamePlayer.PLAYER_1;
			case "0" : return RowGamePlayer.PLAYER_2;
		}
		return null;
	}

	public static List<Integer> parsePositionString(String positionString){
		return Arrays.stream(positionString.split(":"))
				.mapToInt(Integer::parseInt)
				.boxed()
				.collect(Collectors.toList());
	}

}
