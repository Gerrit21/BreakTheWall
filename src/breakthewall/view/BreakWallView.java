package breakthewall.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import org.w3c.dom.Document;

import breakthewall.BreakWallConfig;
import breakthewall.controller.BreakWallController;
import breakthewall.model.BonusXtraPoints;
import breakthewall.model.BreakWallModel;
import breakthewall.model.GameElement;

/**
 * Class which places game elements on the game field GUI.
 * Also contains methods to move and remove existing elements.
 * Class observes changes in the model (main model class is BreakWallModel).
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, October 2015.
 */
public class BreakWallView extends JFrame implements Observer {
	
	private Observable gameModel = null;
	private BreakWallController controller;
	private static final long serialVersionUID = 1L;
	private JLayeredPane gamePane;
	// Hash Map to store and retrieve elements on the game field
	// every element which should be displayed has to be add to the Hash Map
	// The first parameter is meant to be the "ID" of the element
	Map<String, JComponent> gameElements;
	ArrayList<GameElement> displayableElements;
	ArrayList<GameElement> movableElements;
	private int layerCount = 0;
	private NavigationBarView panelbar;
	private MenuView pauseMenu;
	private HighscoreView showHighscore;
	private UserLoadView showUserLoad;
	private EnterNameView showEnterName;
	private JLabel gameInfo;
	
	
	/*
	 * Constructor call initializes the game field GUI
	 */	
	public BreakWallView(Observable gameModel, BreakWallController controller) {
		this.gameModel = gameModel;
		gameModel.addObserver(this);		
		this.controller = controller;
		this.addKeyListener(controller);
		this.setPreferredSize(new Dimension(BreakWallConfig.gameFieldWidth, BreakWallConfig.gameFieldHeight));
		this.setResizable(false);
		// the JLayeredPane stacks JComponents on top of each other
		// JCoponents can later be retrieved via their layer index
		gamePane = new JLayeredPane();
		gameElements = new HashMap<String, JComponent>();
		this.add(gamePane, BorderLayout.CENTER);
		gamePane.setBounds(0, 0, BreakWallConfig.gameFieldWidth, BreakWallConfig.gameFieldHeight);
		this.setLocationRelativeTo(getParent());
		this.setTitle(BreakWallConfig.title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setFocusable(true);
		this.setVisible(true);
		buildGameLayout();
	}

	/**
	 * Private method to add initial game elements to the GUI
	 */
	private void buildGameLayout() {
		// add background image
		addElementToGameField(BreakWallConfig.bgImagePath, "background", 0, 40, BreakWallConfig.gameFieldWidth, BreakWallConfig.gameFieldHeight);
		panelbar = new NavigationBarView();
		// add ActionListener to navigation buttons
		ArrayList<JButton> navigationButtons = panelbar.getButtonList();
		for(int i = 0; i < navigationButtons.size(); i++) {
			navigationButtons.get(i).addActionListener(controller);
		}		
		addPanelToGameField(panelbar, 0, 0, BreakWallConfig.barWidth, BreakWallConfig.barHeight);
		gameInfo = new JLabel("game hints");
		gameInfo.setBackground(new Color(139, 223, 113,255));
		gameInfo.setBorder(BorderFactory.createLineBorder(new Color(100, 158, 82)));
		addPanelToGameField(gameInfo, 0, (BreakWallConfig.offsetHeight - 42), BreakWallConfig.offsetWidth + 10, 20);
		gameInfo.setOpaque(true);
	}

	/**
	 * Private method to add the main menu panel to the GUI.
	 * Overlaps the game GUI.
	 */
	private void buildMenuLayout() {		
		pauseMenu = new MenuView();
		// add ActionListener to navigation buttons
		ArrayList<JButton> navigationMenuButtons = pauseMenu.getButtonList();
		for(int i = 0; i < navigationMenuButtons.size(); i++) {
			navigationMenuButtons.get(i).addActionListener(controller);
		}
		gameElements.put("mainMenu", pauseMenu);
		addPanelToGameField(pauseMenu, 0, 0, BreakWallConfig.gameFieldWidth, BreakWallConfig.gameFieldHeight);		
	}
	
	/**
	 * Private method to add the highscore list panel to the GUI.
	 * Overlaps the game and main menu GUI.
	 * @param highscoreDocument contains player highscore info.
	 */
	private void buildHighscoreLayout(Document highscoreDocument) {		
		showHighscore = new HighscoreView(highscoreDocument);
		
		// add ActionListener to navigation buttons
		showHighscore.getButton().addActionListener(controller);
				
		
		gameElements.put("mainHighscore", showHighscore);
		addPanelToGameField(showHighscore, 0, 0, BreakWallConfig.gameFieldWidth, BreakWallConfig.gameFieldHeight);		
	}
	
	public void buildUserSelectLayout(Document highscoreDocument) {		
		showUserLoad = new UserLoadView(highscoreDocument);	
		// add ActionListener to navigation buttons
		ArrayList<JButton> navigationButtonsLoad = showUserLoad.getButtonList();
		for(int i = 0; i < navigationButtonsLoad.size(); i++) {
			navigationButtonsLoad.get(i).addActionListener(controller);
		}
		showUserLoad.getDropdownMenu().addItemListener(controller);
		gameElements.put("mainHighscore", showUserLoad);
		addPanelToGameField(showUserLoad, 0, 0, BreakWallConfig.gameFieldWidth, BreakWallConfig.gameFieldHeight);		
	}
	
	/**
	 * Private method to add the menu to enter the user name.
	 * Overlaps the main menu GUI.
	 */
	private void buildEnterNameLayout() {		
		showEnterName = new EnterNameView();
		
		// add ActionListener to navigation buttons
		ArrayList<JButton> navigationButtonsSave = showEnterName.getButtonList();
		for(int i = 0; i < navigationButtonsSave.size(); i++) {
			navigationButtonsSave.get(i).addActionListener(controller);
		}
		showEnterName.getEingabeFeld().getDocument().addDocumentListener(controller);		
		
		gameElements.put("mainEnterName", showEnterName);
		addPanelToGameField(showEnterName, 0, 0, BreakWallConfig.gameFieldWidth, BreakWallConfig.gameFieldHeight);		
	}
	
	/**
	 * Sets the text to the game info bar on the bottom of the game field GUI.
	 * @param newText Text that should be shown inside the bar
	 */
	private void editGameInfo(String newText) {
		gameInfo.setText(newText);
	}
	
	/**
	 * Private method to remove all game elements from the game field
	 * and from the hash map that stores the game elements.
	 */
	private void clearGameField() {
		gamePane.removeAll();
		gameElements.clear();
		layerCount = 0;
	}
	
	/**
	 * Adds a JComponent to the game field, respectively to the JLayeredPane
	 * 
	 * @param newComp	JComponent that should be added
	 * @param xCoord	component's future x-position on the game field
	 * @param yCoord	component's future y-position on the game field
	 * @param width		width of the component
	 * @param height	height of the component
	 */
	private void addPanelToGameField(JComponent newComp, int xCoord, int yCoord, int width, int height) {
		newComp.setBounds(xCoord, yCoord, width, height);
		newComp.setOpaque(false);
		// layerCount: layer index of the new element
		// background element has layer index 0
		gamePane.add(newComp, new Integer(layerCount), 0);
		layerCount++;
		gamePane.validate();
	
	}
	
	/**
	 * Private methods takes an image reference,
	 * sets image as background to a JPanels and stacks the Panel
	 * on top of the game field (the layered pane).
	 * Element references are stored in a hash map together with the new panel.
	 * References may be used as IDs to retrieve the element on the game field.
	 * 
	 * @param imgPath image path of the new game element
	 * @param xCoord future x-position of the new game element
	 * @param yCoord future y-position of the new game element
	 * @param width width of the new element
	 * @param height height of the new element
	 */
	private void addElementToGameField(String imgPath, String id, int xCoord, int yCoord, int width, int height) {
		JPanel newPanel = new JPanel();
		newPanel.setLayout(null);
		ImgPanel newImage = new ImgPanel(imgPath);
		gameElements.put(id, newPanel);
		newPanel.add(newImage);
		newPanel.setBounds(xCoord, yCoord, width, height);
		newPanel.setOpaque(false);
		// layerCount: layer index of the new element
		// background element has layer index 0
		gamePane.add(newPanel, new Integer(layerCount), 0);
		layerCount++;
		gamePane.validate();
	}
	
	/**
	 * Private method removes an element from the game field (the layered pane)
	 * Retrieves the layer index of the element via hash map
	 * @param elementId	the ID of the element to be removed
	 */
	private void removeElementFromGameField(String elementId) {
		int removeIndex = gamePane.getIndexOf(gameElements.get(elementId));
		gamePane.remove(removeIndex);
		gamePane.revalidate();
		gamePane.repaint();
	}
	
 	/**
	 * Changes the position of an existing game field element
	 * Retrieves the element via its id
	 * 
	 * @param elementId ID of element to be moved
	 * @param xCoord	new x-position of the element
	 * @param yCoord	new y-position of the element
	 */
	private void relocateElement(String elementId, int xCoord, int yCoord) {
		JComponent redrawnElement = gameElements.get(elementId);
		redrawnElement.setLayout(null);
		redrawnElement.setLocation(xCoord, yCoord);
	}
	
	/**
	 * Changes the position of an existing game field element
	 * and changes the width and height of the element.
	 * Retrieves the element via its id
	 * @param elementId ID of element to be moved
	 * @param xCoord	new x-position of the element
	 * @param yCoord	new y-position of the element
	 * @param width		new width of the element
	 * @param height	new height of the element
	 */
	private void redrawElement(String elementId, int xCoord, int yCoord, int width, int height) {
		JComponent redrawnElement = gameElements.get(elementId);
		redrawnElement.setLayout(null);
		redrawnElement.setBounds(xCoord, yCoord, width, height);
	}
	
	/**
	 * Observer method which is called when the game model has changed
	 */
	@Override
	public void update(Observable gameModel, Object gameObject) {
		if(gameModel != null) {		
			if(gameObject.equals("updateGameElements")) {
				BreakWallModel currentModel = (BreakWallModel) gameModel;
				displayableElements = currentModel.getElementList();
				for(int i = 0; i < displayableElements.size(); i++) {			
					if(gameElements.get(displayableElements.get(i).getId()) != null) {
						if(displayableElements.get(i).getDestroyedState() == false) {
							redrawElement(displayableElements.get(i).getId(), displayableElements.get(i).getXCoord(), displayableElements.get(i).getYCoord(), displayableElements.get(i).getWidth(), displayableElements.get(i).getHeight());
						} else {
							removeElementFromGameField(displayableElements.get(i).getId());
						}
					} else {
						addElementToGameField(displayableElements.get(i).getImage(), displayableElements.get(i).getId(), displayableElements.get(i).getXCoord(), displayableElements.get(i).getYCoord(), displayableElements.get(i).getWidth(), displayableElements.get(i).getHeight());
					}
	
				}
				
				movableElements = currentModel.getMovableElementsList();
				for(int i = 0; i < movableElements.size(); i++) {				
					if(gameElements.get(movableElements.get(i).getId()) != null) {
						if(movableElements.get(i).getDestroyedState() == false) {
							redrawElement(movableElements.get(i).getId(), movableElements.get(i).getXCoord(), movableElements.get(i).getYCoord(), movableElements.get(i).getWidth(), movableElements.get(i).getHeight());
						} else {
							removeElementFromGameField(movableElements.get(i).getId());
						}
					} else { 
						BonusXtraPoints activeBonus = (BonusXtraPoints) movableElements.get(i);
						JLabel pointsLabel = new JLabel("+ " + Integer.toString(activeBonus.getBonusPoints()));
						pointsLabel.setOpaque(false);
						pointsLabel.setOpaque(true);
						pointsLabel.setBackground(new Color(0,0,0,0));
						addPanelToGameField(pointsLabel, activeBonus.getXCoord(), activeBonus.getYCoord(), 30, 10);
						gameElements.put(activeBonus.getId(), pointsLabel);
					}
	
				}
				editGameInfo(currentModel.getInfoText());
				
				panelbar.updateScoreView(currentModel.getScore());
				panelbar.updateLevelView(currentModel.getLevel());
				panelbar.updateLifeView(currentModel.getLives());
				//pauseMenu.updateScoreMenuView(currentModel.getScore());
			
			}
			if(gameObject.equals("focusGameElements")) {
				panelbar.setPlayPauseButton("Pause");
				this.requestFocus();
			}
			if(gameObject.equals("stopGame")) {
				panelbar.setPlayPauseButton("Play");
			}	
			if(gameObject.equals("playGame")) {
				panelbar.setPlayPauseButton("Pause");
			}		
			if(gameObject.equals("initLevel")) {
				clearGameField();
				controller.initNewLevel();
			}
			if(gameObject.equals("updateLevel")) {
				buildGameLayout();
			}
			if(gameObject.equals("loadLevel")) {
				clearGameField();
				controller.initExistingLevel();
				buildGameLayout();
			}		
			if(gameObject.equals("showMenu")) {
				buildMenuLayout();	
			}
			if(gameObject.equals("quitMenu")) {		
				removeElementFromGameField("mainMenu");
			}	
			
			if(gameObject.equals("showHighscore")) {
				Document highscoreDocument = ((BreakWallModel) gameModel).getHighscoreDocument();
				buildHighscoreLayout(highscoreDocument);			
			}	
			
			if(gameObject.equals("showUserSelect")) {
				Document highscoreDocument = ((BreakWallModel) gameModel).getHighscoreDocument();
				buildUserSelectLayout(highscoreDocument);			
			}
			
			if(gameObject.equals("loadUserGame")) {
				removeElementFromGameField("mainHighscore");
				removeElementFromGameField("mainMenu");
				
			}
			
			if(gameObject.equals("showEnterName")) {
				buildEnterNameLayout();			
			}	
			
			if(gameObject.equals("quitHighscore")) {		
				removeElementFromGameField("mainHighscore");
				System.out.println("Highscore remove");
			}	
			
			if(gameObject.equals("quitEnterName")) {		
				removeElementFromGameField("mainEnterName");
				System.out.println("EnterName remove");
			}	
			
			if(gameObject.equals("startNewGame")) {			
				clearGameField();
				buildGameLayout();
				System.out.println("New Game!");
				
			}
			
			if(gameObject.equals("showPlayButton")) {			
				panelbar.setMusicButton(true);
				this.requestFocus();
			}
			if(gameObject.equals("showMuteButton")) {			
				panelbar.setMusicButton(false);
				this.requestFocus();
			}
		}
	}

	/**
	 * Public static method to create an image from a given URL.
	 * Returns an exception when no image is found under the given url. 
	 * @param e instance of the class that want to create an image
	 * @param ref string reference of the image path
	 * @return newImg new instance of the image
	 */
	public static Image getImageByURL(Object e, String ref) {
		Image newImg = null;
		try {
			newImg = new ImageIcon(e.getClass().getResource(ref)).getImage();
		} catch(NullPointerException e1) {
			System.out.println("Image could not be found using the path: " + ref);
			e1.printStackTrace();
		}
		return newImg;
	}
	
	/**
	 * Private class to create an image object of type ImgPanel 
	 * @author Mareike Röncke, Gerrit Schulte
	 *
	 */
	private class ImgPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		private Image newImg;
		private int x;
		private int y;
		
		/*
		 *  Konstruktoren creates an object of type image
		 */
		  public ImgPanel(String imgRef) {
			try {
			    newImg = new ImageIcon(getImageByURL(this, imgRef)).getImage();
			    this.x = 0;
			    this.y = 0;
			    Dimension size = new Dimension(newImg.getWidth(null), newImg.getHeight(null));
			    setPreferredSize(size);
			    setMinimumSize(size);
			    setMaximumSize(size);
			    setSize(size);
			    setLayout(null);
			} catch(NullPointerException e) {
				System.out.println("Could not find image for image path: " + imgRef);
				System.out.println("Check if path is correct.");
				e.printStackTrace();
			}
		  }	    

		  public void paintComponent(Graphics g) {
		    g.drawImage(newImg, x, y, null);	    
		  }		
	}

}
