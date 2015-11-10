package breakthewall.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;

import breakthewall.model.BreakWallModel;

public class BreakWallController implements KeyListener {
	
	BreakWallModel gameModel;
	
	public void removeGameElement(int index) {
		gameModel.remove(index);
	}
	
	public BreakWallController(BreakWallModel gameData) {
		this.gameModel = gameData;
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

}
