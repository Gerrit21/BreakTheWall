package breakthewall.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventListener;

import breakthewall.model.BreakWallModel;

public class BreakWallController implements KeyListener, ActionListener {
	
	BreakWallModel gameModel;

	public BreakWallController(BreakWallModel gameModel) {
		this.gameModel = gameModel;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// entspricht der linken Pfeiltaste
		if(e.getKeyCode() == 37) {
			// bewege das Paddle nach links
			gameModel.change("left");
		}
		// entspricht der rechten Pfeiltaste
		if(e.getKeyCode() == 39) {
			// bewege das Paddle nach rechts
			gameModel.change("right");								
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		gameModel.change(e.getActionCommand());		
	}

}
