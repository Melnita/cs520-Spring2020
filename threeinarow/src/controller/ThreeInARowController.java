package controller;

import model.RowBlockModel;
import model.RowGamePlayer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ThreeInARowController extends RowGameController {
    /**
     * Creates a new game initializing the GUI.
     *
     * @param rows
     * @param cols
     */
    public ThreeInARowController(int rows, int cols) {
        super(rows, cols);
    }

    @Override
    public void resetGame() {
        Arrays.stream(getGameModel().getBlocksData())
                .flatMap(Arrays::stream)
                .forEach(RowBlockModel::reset);
        Arrays.stream(getGameModel().getBlocksData()[getGameModel().getBlocksData().length - 1])
                .forEach(block -> block.setIsLegalMove(true));
        getGameModel().player = RowGamePlayer.PLAYER_1;
        getGameModel().movesLeft = getRows() * getCols();
        getGameModel().setFinalResult(null);
//        gameView.update(gameModel);
    }

    /**
     * get the legal block for Three in a Row game type, which is the block above the last selected block
     * @param currentPosition : current integer position of the last selected block in a flat list
     * @return list of legal blocks.
     */
    @Override
    List<RowBlockModel> getLegalBlocks(int currentPosition) {
        List<RowBlockModel> flatBlocks = Arrays.stream(this.getGameModel().getBlocksData())
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
        return Collections.singletonList(flatBlocks.get(currentPosition - getGameModel().getCols() >= 0 ? currentPosition - getGameModel().getCols() : currentPosition));
    }

    @Override
    public String getGameName() {
        return "Three in a Row";
    }
}
