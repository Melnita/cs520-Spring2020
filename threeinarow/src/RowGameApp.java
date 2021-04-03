import controller.RowGameController;
import controller.ThreeInARowController;
import controller.TicTacToeController;
import view.RowGameGUI;

import java.util.Arrays;


public class RowGameApp 
{
    /**                                                                             
     * Starts a new game in the GUI.
     */
    public static void main(String[] args) {
        if(args.length < 3){
            throw new IllegalArgumentException("Missing arguments\n" +
                    "Following arguments are needed : GameType Rows Columns\n" +
                    "GameType: ThreeInARow, TicTacToe\n" +
                    "e.g java RowGameApp ThreeInARow 3 3\n" +
                    "e.g java RowGameApp TicTacToe 3 3");
        }
        int rows = Integer.parseInt(args[1]);
        int cols = Integer.parseInt(args[2]);
        RowGameController game;
        String gameType = args[0];
        switch(gameType){
            case "ThreeInARow" : game = new ThreeInARowController(rows, cols);  break;
            case "TicTacToe" : game = new TicTacToeController(rows, cols); break;
            default: game = null;
        }
        if(game != null) {
            RowGameGUI gui = new RowGameGUI(game);
            game.resetGame();
        }
        else{
            throw new IllegalArgumentException("Incorrect Game Type");
        }
    }
}
