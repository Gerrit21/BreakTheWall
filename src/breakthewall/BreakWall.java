package breakthewall;

import breakthewall.controller.BreakWallController;
import breakthewall.model.BreakWallModel;
import breakthewall.view.BreakWallView;

/**
 * Main Break Wall class
 * Returns an instance of the game (Singleton).
 * Invokes an MVC-architecture by connecting
 * model, view and controller
 * 
 * @author Mareike Röncke, Gerrit Schulte
 * @version 1.0, October 2015.
 */
public class BreakWall {
	
	private static volatile BreakWall gameInstance;
	
	/**
	 * Public constructor ensures that only one instance 
	 * of the class is instatiated (Singleton pattern).
	 * 
	 * @param initialLevel sets the initial level to start the game
	 */
	public static BreakWall getInstance() {
		if (gameInstance == null) {
			BreakWallConfigXML.setGeneralConfigurations();
			gameInstance = new BreakWall();
		}
		return gameInstance;
	}

	/**
	 * Private constructor call initiates the game
	 * 
	 * @param initialLevel sets the initial level to start the game
	 */
	private BreakWall() {		
		BreakWallModel newModel = new BreakWallModel();
		BreakWallController newController = new BreakWallController(newModel);
		new BreakWallView(newModel, newController);
		newModel.playGame();
	}

}
