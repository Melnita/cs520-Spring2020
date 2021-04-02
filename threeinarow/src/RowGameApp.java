import controller.RowGameController;
import controller.ThreeInARowController;
import controller.TicTacToeController;


public class RowGameApp 
{
    /**                                                                             
     * Starts a new game in the GUI.
     */
    public static void main(String[] args) {
	RowGameController game = new ThreeInARowController();
//	RowGameController game = new TicTacToeController();
	game.startUp();
    }
}
