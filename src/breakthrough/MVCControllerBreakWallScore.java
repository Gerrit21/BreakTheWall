package breakthrough;

/**
 * ControllerMVC fuer Play-/Stop-Button
 */

/**
 * @author clecon
 * @version 1.0, 09/2011
 *
 */
import java.awt.event.*;

public class MVCControllerBreakWallScore extends MVCController {

	MVCIModel model;
	MVCIView view;
	
	// public ControllerUhrMVC(KonkreteUhrMVC model) {
	public MVCControllerBreakWallScore(MVCIModel model, MVCIView view) {
		this.model = model;
		this.view = view;
	} // Konstruktor
	
	public void actionPerformed(ActionEvent e) {
		// Play-Button:
		if ("Play".equals(e.getActionCommand())) {
			System.out.println("Play!!!!!!!");
			view.change(new String("Play"));
			model.change(new String("Play"));
		} // if (Play)

		// Pause-Button:
		if ("Pause".equals(e.getActionCommand())) {
			System.out.println("Pause!!!!!!!");
			view.change(new String("Pause"));
			model.change(new String("Pause"));
		} // if (Pause)

		// Reset-Button:
		if ("Reset".equals(e.getActionCommand())) {
			System.out.println("Reset!!!!!!!");
			view.change(new String("Reset"));
			model.change(new String("Pause"));
		} // if 
		
		// Reset-Button:
		if ("Exit".equals(e.getActionCommand())) {
			System.out.println("Exit!!!!!!!");
			view.change(new String("Exit"));
			model.change(new String("Pause"));
		} // if 

		

	} // actionPerformed

} // class ControllerUhrMVC