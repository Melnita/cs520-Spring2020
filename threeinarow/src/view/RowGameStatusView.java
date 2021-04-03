package view;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.RowGameController;
import model.RowGameModel;
import model.RowGamePlayer;


public class RowGameStatusView implements RowGameView
{
    private JTextArea playerturn = new JTextArea();
    private JPanel messages = new JPanel(new FlowLayout());

    
    public RowGameStatusView(RowGameController gameController) {
	super();

	getMessages().setBackground(Color.white);
	getMessages().add(getPlayerturn());
	gameController.getModel().addPropertyChangeListener(e -> update(gameController.getModel()));
    }

    public void update(RowGameModel gameModel) {
	if (gameModel.getFinalResult() == null) {
	    if (gameModel.player.equals(RowGamePlayer.PLAYER_1)) {
		getPlayerturn().setText("Player 1 to play 'X'");
	    }
	    else {
		getPlayerturn().setText("Player 2 to play 'O'");
	    }
	}
	else {
	    getPlayerturn().setText(gameModel.getFinalResult());
	}	
    }

	public JTextArea getPlayerturn() {
		return playerturn;
	}

	public void setPlayerturn(JTextArea playerturn) {
		this.playerturn = playerturn;
	}

	public JPanel getMessages() {
		return messages;
	}

	public void setMessages(JPanel messages) {
		this.messages = messages;
	}
}
