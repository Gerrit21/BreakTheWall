package breakthrough;


import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;




public class NavigationBar extends JPanel implements Observer, MVCIView  {
	
	
	private MVCIModel model = null;
	private MVCData data = null;
	private JPanel p;
	private JButton buttonPlay;
	private JButton buttonPause;
	private JButton buttonSave;
	private JButton buttonExit;
	private JLabel labuttonPlay;
	private JLabel labuttonPause;
	private JLabel labuttonSave;
	
	private TextField scorecount;
	private TextField levelcount;
	private TextField lifecount; 
	private int counter = 0;
	
	
	public NavigationBar(MVCIModel o) {

		model = o;
		((Observable) model).addObserver(this);
		
		
		p = new JPanel();
		p.setBackground(Color.GRAY);
        p.setPreferredSize(new Dimension(590, BreakWallData.barHeight));
        p.setVisible(true);
        p.setOpaque(false);	
		
		labuttonPlay = new JLabel ("Score");
		labuttonPause = new JLabel ("Level");
		labuttonSave = new JLabel ("Life");
		buttonPlay= new JButton ("Play");
		buttonPause= new JButton ("Pause");
		buttonSave= new JButton ("Save");
		buttonExit= new JButton ("Exit");
		scorecount = new TextField(4);
		scorecount.setEditable(false);
		scorecount.setFocusable(false);
		levelcount = new TextField(" 1 ");
		levelcount.setFocusable(false);
		levelcount.setEditable(false);
		lifecount = new TextField(" 2 ");
		lifecount.setFocusable(false);
		lifecount.setEditable(false);
		
		buttonPause.setEnabled(false);
		buttonPlay.addActionListener(new MVCControllerBreakWallScore(model, this));
		buttonPause.addActionListener(new MVCControllerBreakWallScore(model, this));
		buttonExit.addActionListener(new MVCControllerBreakWallScore(model, this));
		
		
		p.add(labuttonPlay);
		p.add(scorecount);
		p.add(labuttonPause);
		p.add(levelcount);
		p.add(labuttonSave);
		p.add(lifecount);
		p.add(buttonPlay);
		p.add(buttonPause);
		p.add(buttonSave);
		p.add(buttonExit);
				
		add(p,BorderLayout.NORTH);	
		
		setScorecount(getCurrentCounter());
		
		
	
	}

	
	public int setCurrentCounter(int zahl) {
		counter = zahl;
		return counter;
	}
	
	public int getCurrentCounter() {
		return counter;
	}
	
	public void update(Observable o, Object arg) {
		// Bei dieser Signatur benoetigt man kein getData.		
		System.out.println("TICK");
		setCurrentCounter(getCurrentCounter() - 1);
		setScorecount(getCurrentCounter());		
	} // update
	
	public void setScorecount(int currentTime) {
		String s = new String();
		s = new String();
		s += currentTime;	
		scorecount.setText(s);
	}
	
	/**
	 * Reagieren auf MVCController
	 * @param s
	 */
	public void change(String s) {
		if (s.equals("Pause")) {
			System.out.println("+++ PauseUhrMVC: Pause");
			buttonPause.setEnabled(false);
			buttonPlay.setEnabled(true);
		} // if
		if (s.equals("Play")) {
			System.out.println("+++ PauseUhrMVC: Play");
			buttonPause.setEnabled(true);
			buttonPlay.setEnabled(false);
		} // if
		if (s.equals("Reset")) {
			System.out.println("+++ PauseUhrMVC: RESET");
			setCurrentCounter(0);
			setScorecount(getCurrentCounter());
			buttonPause.setEnabled(false);
			buttonPlay.setEnabled(true);
		} // if		
		if (s.equals("Exit")) {
			System.out.println("+++ Exit");
			setCurrentCounter(0);
			setScorecount(getCurrentCounter());
			System.exit(0);
			
		} // if		
		
		
		
	} // change
	
}
