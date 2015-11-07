package breakthrough;

/**
 * Beispiel Observer-Muster
 * Konkretes Subjekt (Uhrwerk).
 */

/**
 * @author clecon
 * @version 1.0, 08/2011
 *
 */
import java.util.*;

public class MVCBreakWallModel extends Observable implements MVCIModel   {
	
	// Daten:
	private Calendar calendar = Calendar.getInstance();
	private int FREQUENZ = 1000; // Frequenz der Uhrzeit-Aktualisierung (in Millisekunden)
	private MVCBreakWallModel singleton;
	
	// Controllerdaten:
	private boolean Pause = true;
	
	public MVCBreakWallModel() {

	} // Konstruktor
	/**
	 * Ueberschreibt die Methode der abstrakten Klasse Observable
	 */
	public void addObserver(Observer o) {
		super.addObserver(o);
	} // addObserver

	public MVCData getData() {
		MVCData data = new MVCData(getHours(), getMinutes(), getSeconds());
		return data;
	} // getData
	
	public int getHours() {
		// return date.getHours(); // deprecated
		return calendar.get(Calendar.HOUR_OF_DAY);
	} // getHours
	
	public int getMinutes() {
		// return date.getMinutes(); // deprecated
		return calendar.get(Calendar.MINUTE);
	} // getMinutes

	public int getSeconds() {
		// return date.getSeconds(); // deprecated
		return calendar.get(Calendar.SECOND);
	} // getSeconds

	/**
	 * Allgemeine change-Methode.
	 * Weiterleitung an interne change-Methoden f�r String oder Date.
	 */
	public void change(Object o) {
		String className = o.getClass().getSimpleName();
		System.out.println(className);
		if (className.equals("String")) {
			change ((String) o);
		} // if
		if (className.equals("Calendar")) {
			change ((Calendar) o);
		} // if
	} // change
	
	/**
	 * Aufruf bei vom ControllerMVC geaenderten Werten.
	 * @param s Aktueller Wert eines Buttons.
	 */
	public void change(String s) {
		// Play-Button:
		if (s.equals("Play")) {
			Pause = false; // Pauseen der Uhrzeit aufheben
		} // if
		
		// Pause-Button:
		if (s.equals("Pause")) {
			Pause = true; // Uhrzeit Pause
		} // if
		
		
	} // change
	
	/**
	 * Interner Aufruf (Methode zeitmessung).
	 * @param date Aktuelles Datum.
	 */
	public void change(Calendar calendar) {
		this.calendar = calendar;
		setChanged();
		notifyObservers(getData());
	} // change
	
	public void zeitmessung() {
		while (true) {
			try {
				Thread.sleep(FREQUENZ);
				System.out.println("TACK");
				if (!Pause) {
					change(Calendar.getInstance());
				} // if
			} catch (InterruptedException e) {
				System.err.println("FEHLER bei der Uhr-Warteschleife.");
			} // catch
		} // while
	} // zeitmessung

} // class MVCBreakWallModel