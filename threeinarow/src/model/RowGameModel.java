package model;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class RowGameModel
{
    public static final String GAME_END_NOWINNER = "Game ends in a draw";

    public RowBlockModel[][] blocksData = new RowBlockModel[3][3];

    /**
     * The current player taking their turn
     */
    public RowGamePlayer player = RowGamePlayer.PLAYER_1;
    public int movesLeft = 9;

    private String finalResult = null;

    private final PropertyChangeSupport support;


    public RowGameModel() {
	super();

	for (int row = 0; row < 3; row++) {
	    for (int col = 0; col < 3; col++) {
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

}
