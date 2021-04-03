import controller.RowGameController;
import controller.ThreeInARowController;
import controller.TicTacToeController;
import view.RowGameGUI;


public class RowGameApp 
{
    /**                                                                             
     * Starts a new game in the GUI.
     */
    public static void main(String[] args) {
	RowGameController game = new ThreeInARowController(4, 4);
//	RowGameController game = new TicTacToeController();
	RowGameGUI gui = new RowGameGUI(game);
	game.resetGame();
    }
}
