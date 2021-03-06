package controller;

import model.RowBlockModel;
import model.RowGamePlayer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TicTacToeController extends RowGameController{

    /**
     * Creates a new game initializing the GUI.
     *
     * @param rows
     * @param cols
     */
    public TicTacToeController(int rows, int cols) {
        super(rows, cols);
    }

    @Override
    public void resetGame() {
        Arrays.stream(getGameModel().getBlocksData())
                .flatMap(Arrays::stream)
                .forEach(block -> {
                    block.reset();
                    block.setIsLegalMove(true);
                });
        getGameModel().player = RowGamePlayer.PLAYER_1;
        getGameModel().movesLeft = getRows() * getCols();
        getGameModel().setFinalResult(null);
//        gameView.update(gameModel);
    }

    @Override
    List<RowBlockModel> getLegalBlocks(int currentPosition) {
        return Arrays.stream(this.getGameModel().getBlocksData())
                .flatMap(Arrays::stream)
                .filter(block -> block.getContents().equals(""))
                .collect(Collectors.toList());
    }

    @Override
    public String getGameName() {
        return "Tic Tac Toe";
    }
}
