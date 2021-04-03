package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

import model.RowGameModel;
import controller.RowGameController;


public class RowGameGUI implements RowGameView
{
    JFrame gui = new JFrame("");
    public RowGameBoardView gameBoardView;
    JButton reset = new JButton(RowGameMessage.ROWGAME_RESET);
    public RowGameStatusView gameStatusView; // TODO : Make private and create getters / setters
    
    RowGameController gameController;


    /**
     * Creates a new game initializing the GUI.
     */
    public RowGameGUI(RowGameController gameController) {
	this.gameController = gameController;
	
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(new Dimension(350/3 * gameController.gameModel.rows, 300/3 * gameController.gameModel.cols));
        gui.setResizable(true);
        gui.setTitle(gameController.getGameName());
	gameBoardView = new RowGameBoardView(this.gameController);
        JPanel gamePanel = gameBoardView.gamePanel;

        JPanel options = new JPanel(new FlowLayout());
        options.add(reset);

	gameStatusView = new RowGameStatusView(this.gameController);
        JPanel messages = gameStatusView.messages;

        gui.add(gamePanel, BorderLayout.NORTH);
        gui.add(options, BorderLayout.CENTER);
        gui.add(messages, BorderLayout.SOUTH);

        reset.addActionListener(e -> gameController.resetGame());
        gui.setVisible(true);
    }

    /**
     * Updates the game view after the game model
     * changes state.
     *
     * @param gameModel The current game model
     */
    public void update(RowGameModel gameModel) {
	gameBoardView.update(gameModel);

	gameStatusView.update(gameModel);
    }

    public void makeVisible(){
        this.gui.setVisible(true);
    }

    public static class RowGameMessage{
        public static final String ROWGAME_NAME = "Three in a Row";
        public static final String ROWGAME_RESET = "Reset";
    }
}
