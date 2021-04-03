package model;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class RowGameModel
{
    public static final String GAME_END_NOWINNER = "Game ends in a draw";
    private int rows;
    private int cols;
    public RowBlockModel[][] blocksData;

    /**
     * The current player taking their turn
     */
    public RowGamePlayer player = RowGamePlayer.PLAYER_1;
    public int movesLeft = 9;

    private String finalResult = null;

    private final PropertyChangeSupport support;


    public RowGameModel(int rows, int cols) {
        super();
        this.setRows(rows);
        this.cols = cols;
        blocksData = new RowBlockModel[rows][cols];
	for (int row = 0; row < rows; row++) {
	    for (int col = 0; col < cols; col++) {
		blocksData[row][col] = new RowBlockModel(this);
	    } // end for col
	} // end for row
        support = new PropertyChangeSupport(this);
    }

    public String getFinalResult() {
	return this.finalResult;
    }

    public void setFinalResult(String finalResult) {
        this.finalResult = finalResult;
        support.firePropertyChange("update", null, this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    public void moveCompleted(){
        this.movesLeft--;
    }

    public RowBlockModel getBlock(int row, int col){
        return this.blocksData[row][col];
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getCols() {
        return cols;
    }

    public void setCols(int cols) {
        this.cols = cols;
    }
}
