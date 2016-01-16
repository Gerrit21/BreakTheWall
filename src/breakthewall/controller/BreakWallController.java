package breakthewall.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import breakthewall.BreakWallConfig;
import breakthewall.model.BreakWallModel;

public class BreakWallController implements KeyListener, ActionListener, DocumentListener{
	
	private BreakWallModel gameModel;
	private String command;
	private String name;

	public BreakWallController(BreakWallModel gameModel) {
		this.gameModel = gameModel;
	}
	
	public void initNewLevel() {		
		gameModel.startNewLevel();		
	}
	
	public void initExistingLevel() {		
		gameModel.startExistingLevel();		
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
			gameModel.setPlayPause(true);
			gameModel.pauseGame();			
		}
		
		if(command.equals("Play")) {
			gameModel.setPlayPause(false);
			gameModel.playGame();			
		}		

		if(command.equals("Save")) {
			gameModel.enterName();
		}
		if(command.equals("Exit")) {
			gameModel.exitGame();
		}
		if(command.equals("MuteMusic")) {
			gameModel.setMusicPlaying(false);
		}
		if(command.equals("PlayMusic")) {
			gameModel.setMusicPlaying(true);
		}		
		if(command.equals("Menu")) {
			gameModel.menuGame();
		}	
		if(command.equals("Back")) {
			gameModel.backGame();
		}	
		if(command.equals("Highscores")) {
			gameModel.scoreGame();
		}	
		if(command.equals("Load")) {
			gameModel.selectUser();
		}	
		if(command.equals("Load Game")) {
			gameModel.loadUser("Helmut");
		}
		if(command.equals("BackMenu")) {
			gameModel.backMenu();
		}
		if(command.equals("BackSaveMenu")) {
			gameModel.backMenuAfterSave();
		}
		if(command.equals("Enter and Save")) {
			gameModel.saveGame(name);
			gameModel.backMenuAfterSave();
		}
		
		
		
	}

	@Override
	public void changedUpdate(DocumentEvent arg0) {
	}

	@Override
	public void insertUpdate(DocumentEvent arg0) {
		System.out.println("Änderung:");
		try {
			name = arg0.getDocument().getText(0, arg0.getDocument().getLength()); 
			System.out.println(arg0.getDocument().getText(0, arg0.getDocument().getLength()));
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent arg0) {
		System.out.println("Änderung rückgängig:");
		try {
			name = arg0.getDocument().getText(0, arg0.getDocument().getLength());
			System.out.println(arg0.getDocument().getText(0, arg0.getDocument().getLength()));
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
