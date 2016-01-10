package breakthewall.view;


import javax.swing.*;

import breakthewall.controller.BreakWallController;

import java.awt.*;
import java.util.ArrayList;




public class MenuView extends JPanel  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel menup;
	private JButton bu1;
	private JButton bu2;
	private JButton bu2b;
	private JButton bu3;
	private JButton bu4;
	private ArrayList<JButton> navigationButtonsMenu;
	private JLabel lab1;
	
	
	private TextField scorecount;

	
	
	public MenuView() {
		
		navigationButtonsMenu = new ArrayList<JButton>();
		menup = new JPanel();
		
        menup.setPreferredSize(new Dimension(120, 500));
        menup.setVisible(true);
        menup.setOpaque(false);	
		
		lab1 = new JLabel ("Current Score");
	
		bu1 = new JButton ("Highscores");		
		
		bu2 = new JButton ("Save");
		
		bu2b = new JButton ("Load");
		
		bu3 = new JButton ("Back");
		
		bu4 = new JButton ("Exit");
		
		scorecount = new TextField(" 1234 ");
		scorecount.setEditable(false);
		scorecount.setFocusable(false);
		
		navigationButtonsMenu.add(bu1);
		navigationButtonsMenu.add(bu2);
		navigationButtonsMenu.add(bu2b);
		navigationButtonsMenu.add(bu3);
		navigationButtonsMenu.add(bu4);
		
		menup.add(lab1);
		menup.add(scorecount);

		menup.add(bu1);
		menup.add(bu2);
		menup.add(bu2b);
		menup.add(bu3);
		menup.add(bu4);
				
		add(menup,BorderLayout.CENTER);	
	
	}
	
	public ArrayList<JButton> getButtonList() {
		return navigationButtonsMenu;
	}

	public void updateScoreView(int score) {
		scorecount.setText(Integer.toString(score));
	}

	
	
	
}
