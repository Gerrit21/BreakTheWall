package breakthewall.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
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

	/**
	 * Constructor takes the game model as parameter
	 * @param gameModel	instance of game model
	 **/
	public BreakWallController(BreakWallModel gameModel) {
		this.gameModel = gameModel;
	}
	
	/**
	 * Public method is called when the user starts a new game via the main menu
	**/
	public void initNewLevel() {		
		gameModel.startNewLevel();		
	}

	/**
	 * Public method is called when the user loads an existing game
	**/
	public void initExistingLevel() {		
		gameModel.startExistingLevel();		
	}
	
	// ***************************************************************//
	// KeyListener Methods
	// ***************************************************************//
	
	@Override
	public void keyPressed(KeyEvent e) {
		// key code for space key
		// starts the ball movement
		if(e.getKeyCode() == 32) {
			if(gameModel.getBallAction() == false) {
				gameModel.setBallAction(true);
			}
		}
		
		// key code for left arrow key
		if(e.getKeyCode() == 37) {
			gameModel.movePaddleLeft();
		}
		
		// key code for right arrow key
		if(e.getKeyCode() == 39) {
			gameModel.movePaddleRight();								
		}
		
		// key code for 'e' key
		if(e.getKeyCode() == 69) {
			// cheating key: remove random brick
			gameModel.cheat();								
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	// ***************************************************************//
	// ActionListener Methods
	// ***************************************************************//
	
	@Override
	// events for when a button has been clicked in the GUI
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
			try {
				gameModel.saveGame(newName);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			gameModel.backMenuAfterSave();
		}
		
		if(command.equals("New Game")) {
			System.out.println("start new button");
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
			// stores the name update from the "save name"-input-field
			// when text is inserted
			newName = textInputEvent.getDocument().getText(0, textInputEvent.getDocument().getLength()); 
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent textInputEvent) {
		try {
			// stores the name update from the "save name"-input-field
			// when text is removed
			newName = textInputEvent.getDocument().getText(0, textInputEvent.getDocument().getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
	
	// ***************************************************************//
	// ItemListener Methods
	// ***************************************************************//

	@Override
	public void itemStateChanged(ItemEvent changedItemEvent) {
		// stores the selected name from the load-game dropdown field in the load menu
		loadedName = (String) changedItemEvent.getItem();
	}

}
