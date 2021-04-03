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
        Arrays.stream(gameModel.blocksData)
                .flatMap(Arrays::stream)
                .forEach(RowBlockModel::reset);
        Arrays.stream(gameModel.blocksData[gameModel.blocksData.length - 1])
                .forEach(block -> block.setIsLegalMove(true));
        gameModel.player = RowGamePlayer.PLAYER_1;
        gameModel.movesLeft = rows * cols;
        gameModel.setFinalResult(null);
//        gameView.update(gameModel);
    }

    /**
     * get the legal block for Three in a Row game type, which is the block above the last selected block
     * @param currentPosition : current integer position of the last selected block in a flat list
     * @return list of legal blocks.
     */
    @Override
    List<RowBlockModel> getLegalBlocks(int currentPosition) {
        List<RowBlockModel> flatBlocks = Arrays.stream(this.gameModel.blocksData)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
        return Collections.singletonList(flatBlocks.get(currentPosition - gameModel.getRows() >= 0 ? currentPosition - gameModel.getRows() : currentPosition));
    }

    @Override
    public String getGameName() {
        return "Three in a Row";
    }
}
