package breakthewall.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.EventListener;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.xml.parsers.ParserConfigurationException;

import breakthewall.BreakWallConfig;
import breakthewall.model.BreakWallModel;

/**
 * Controller class to handle events occurring on the game field.
 * Implements Listener interfaces to react to key taps, button clicks
 * and entered text.
 * Calls model methods to update the model's data.
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, October 2015.
 */
public class BreakWallController implements KeyListener, ActionListener, DocumentListener, ItemListener {
	
	private BreakWallModel gameModel;
	private String command;
	private String newName;
	private String loadedName;

	public BreakWallController(BreakWallModel gameModel) {
		this.gameModel = gameModel;
	}
	
	public void initNewLevel() {		
		gameModel.startNewLevel();		
	}
	
	public void initExistingLevel() {		
		gameModel.startExistingLevel();		
	}
	
	// ***************************************************************//
	// KeyListener Methods
	// ***************************************************************//
	
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

	// ***************************************************************//
	// ActionListener Methods
	// ***************************************************************//
	
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
			gameModel.loadUser(loadedName);
		}
		if(command.equals("BackMenu")) {
			gameModel.backMenu();
		}
		if(command.equals("BackSaveMenu")) {
			gameModel.backMenuAfterSave();
		}
		if(command.equals("Enter and Save")) {
			gameModel.saveGame(newName);
			gameModel.backMenuAfterSave();
		}
		
		if(command.equals("New Game")) {
			gameModel.restartNewGame();
		}
		
		
		
	}
	
	// ***************************************************************//
	// DocumentListener Methods
	// ***************************************************************//

	@Override
	public void changedUpdate(DocumentEvent arg0) {
	}

	@Override
	public void insertUpdate(DocumentEvent textInputEvent) {
		try {
			newName = textInputEvent.getDocument().getText(0, textInputEvent.getDocument().getLength()); 
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent textInputEvent) {
		try {
			newName = textInputEvent.getDocument().getText(0, textInputEvent.getDocument().getLength());
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// ***************************************************************//
	// ItemListener Methods
	// ***************************************************************//

	@Override
	public void itemStateChanged(ItemEvent changedItemEvent) {
		loadedName = (String) changedItemEvent.getItem();
	}

}
