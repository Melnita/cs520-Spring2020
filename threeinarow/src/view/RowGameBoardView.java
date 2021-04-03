package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.RowGameController;
import model.RowGameModel;


public class RowGameBoardView implements RowGameView
{
    private JButton[][] blocks;
    private JPanel gamePanel = new JPanel(new FlowLayout());

    
    public RowGameBoardView(RowGameController gameController) {
	super();

        JPanel game = new JPanel(new GridLayout(gameController.getRows(), gameController.getCols()));
        getGamePanel().add(game, BorderLayout.CENTER);
        int rows = gameController.getRows();
        int cols = gameController.getCols();
        setBlocks(new JButton[rows][cols]);
       // Initialize a JButton for each cell of the 3x3 game board.
        for(int row = 0; row<rows; row++) {
            for(int column = 0; column<cols ;column++) {
                getBlocks()[row][column] = new JButton();
                getBlocks()[row][column].setPreferredSize(new Dimension(75,75));
                game.add(getBlocks()[row][column]);
                int finalRow = row;
                int finalColumn = column;
                getBlocks()[row][column].addActionListener(e -> {
                    gameController.move(finalRow, finalColumn);
                });
            }
        }
        gameController.getModel().addPropertyChangeListener(e -> update(gameController.getModel()));
    }

    /**
     * Updates the game view after the game model
     * changes state.
     *
     * @param gameModel The current game model
     */
    public void update(RowGameModel gameModel) {
	for (int row = 0; row < gameModel.getRows(); row++) {
	    for (int column = 0; column < gameModel.getCols(); column++) {
		this.updateBlock(gameModel, row, column);
	    } // end for col
	} // end for row	
    }

    /**
     * Updates the block view at the given row and column 
     * after the game model changes state.
     *
     * @param gameModel The game model
     * @param row The row that contains the block
     * @param col The column that contains the block
     */
    protected void updateBlock(RowGameModel gameModel, int row, int col) {
	getBlocks()[row][col].setText(gameModel.getBlocksData()[row][col].getContents());
	getBlocks()[row][col].setEnabled(gameModel.getBlocksData()[row][col].getIsLegalMove());
    }

    public JPanel getGamePanel() {
        return gamePanel;
    }

    public void setGamePanel(JPanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public JButton[][] getBlocks() {
        return blocks;
    }

    public void setBlocks(JButton[][] blocks) {
        this.blocks = blocks;
    }
}
