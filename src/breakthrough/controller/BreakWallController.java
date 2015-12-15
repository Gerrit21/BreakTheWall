package breakthewall.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventListener;

import breakthewall.BreakWallConfig;
import breakthewall.model.BreakWallModel;

public class BreakWallController implements KeyListener, ActionListener {
	
	private BreakWallModel gameModel;
	private String command;

	public BreakWallController(BreakWallModel gameModel) {
		this.gameModel = gameModel;
	}
	
	public void initNewLevel() {		
		gameModel.startNewLevel();		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// entspricht der Leertaste
		if(e.getKeyCode() == 32) {
			if(gameModel.getBallAction() == false) {
				gameModel.setBallAction(true);
			}
		}
		// entspricht der linken Pfeiltaste
		if(e.getKeyCode() == 37) {
			// bewege das Paddle nach links
			gameModel.movePaddleLeft();
		}
		// entspricht der rechten Pfeiltaste
		if(e.getKeyCode() == 39) {
			// bewege das Paddle nach rechts
			gameModel.movePaddleRight();								
		}
		
		if(e.getKeyCode() == 69) {
			// cheating: remove random brick
			gameModel.cheat();								
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
		command = e.getActionCommand();
		if(command.equals("Pause")) {
			gameModel.pauseGame();
		}
		if(command.equals("Play")) {
			gameModel.playGame();
		}
		if(command.equals("Save")) {
			gameModel.saveGame();
		}
		if(command.equals("Exit")) {
			gameModel.exitGame();
		}		
	}

}
