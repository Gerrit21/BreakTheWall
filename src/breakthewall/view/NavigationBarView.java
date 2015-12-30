package breakthewall.view;


import javax.swing.*;

import breakthewall.BreakWallConfig;
import breakthewall.controller.BreakWallController;

import java.awt.*;
import java.util.ArrayList;




public class NavigationBarView extends JPanel  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel p;
	public JButton b1;
	private JButton b2;
	private JButton b3;
	
	private ArrayList<JButton> navigationButtons;
	private JLabel lab1;
	private JLabel lab2;
	private JLabel lab3;
	
	private TextField scorecount;
	private TextField levelcount;
	private TextField lifecount; 
	private int counter = 0;
	
	
	public NavigationBarView() {
		
		navigationButtons = new ArrayList<JButton>();
		p = new JPanel();
		p.setBackground(Color.GRAY);
        p.setPreferredSize(new Dimension(590, 40));
        p.setVisible(true);
        p.setOpaque(false);	
		
		lab1 = new JLabel ("Score");
		lab2 = new JLabel ("Level");
		lab3 = new JLabel ("Life");
		b1 = new JButton ("Pause");
		b1.setPreferredSize(new Dimension(75, 25));
		Icon musicIcon = new ImageIcon(this.getClass().getResource(BreakWallConfig.musicIconPlaying));
		b2 = new JButton(musicIcon);
		b2.setActionCommand("MuteMusic");
		b3 = new JButton ("Menu");
		scorecount = new TextField(" 1234 ");
		scorecount.setEditable(false);
		scorecount.setFocusable(false);
		levelcount = new TextField(" 1 ");
		levelcount.setFocusable(false);
		levelcount.setEditable(false);
		lifecount = new TextField(" 2 ");
		lifecount.setFocusable(false);
		lifecount.setEditable(false);
		
		navigationButtons.add(b1);
		navigationButtons.add(b2);
		navigationButtons.add(b3);
	
		
		p.add(lab1);
		p.add(scorecount);
		p.add(lab2);
		p.add(levelcount);
		p.add(lab3);
		p.add(lifecount);
		p.add(b1);
		p.add(b2);
		p.add(b3);
	
				
		add(p,BorderLayout.NORTH);	
	
	}
	
	public ArrayList<JButton> getButtonList() {
		return navigationButtons;
	}

	
	public int setCurrentCounter(int zahl) {
		counter = zahl;
		return counter;
	}
	
	public int getCurrentCounter() {
		return counter;
	}
	
	public void setPlayPauseButton(String buttonText) {
			b1.setText(buttonText);		
	}
	
	public void setMusicButton(boolean isPlaying) {
		System.out.println(isPlaying);
		if(isPlaying == true) {
			Icon musicIcon = new ImageIcon(this.getClass().getResource(BreakWallConfig.musicIconPlaying));			
			b2.setIcon(musicIcon);
			b2.setActionCommand("MuteMusic");			
		} else {
			Icon musicIcon = new ImageIcon(this.getClass().getResource(BreakWallConfig.musicIconPausing));
			b2.setIcon(musicIcon);
			b2.setActionCommand("PlayMusic");
		}

	}
	
	public void updateScoreView(int score) {
		scorecount.setText(Integer.toString(score));
	}
	
	public void updateLevelView(int level) {
		levelcount.setText(Integer.toString(level));
	}
	
	public void updateLifeView(int lives) {
		lifecount.setText(Integer.toString(lives));
	}
	
}
