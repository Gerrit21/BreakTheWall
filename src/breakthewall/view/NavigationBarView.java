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
	private JPanel panel;
	public JButton btnMenu;
	private JButton btnMute;
	private JButton btnPause;
	
	private ArrayList<JButton> navigationButtons;
	private JLabel lblLife;
	private JLabel lblLevel;
	private JLabel lblScore;
	private JLabel lblBreakthewall;
	
	private JTextField txtScoreCount;
	private JTextField txtLevelCount;
	private JTextField txtLifeCount;
	
	
	public NavigationBarView() {
		
		
		navigationButtons = new ArrayList<JButton>();
		
		panel = new JPanel();
	
		add(panel);
		
		panel.setLayout(null);
		panel.setPreferredSize(new Dimension(600, 498));
		
		
		lblScore = new JLabel("Score");
		lblScore.setBackground(SystemColor.activeCaptionBorder);
		lblScore.setFont(new Font("Calibri", Font.BOLD, 14));
		lblScore.setBounds(2, 0, 42, 30);
		lblScore.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblScore);
		
		txtScoreCount = new JTextField();
		txtScoreCount.setFont(new Font("Calibri", Font.BOLD, 14));
		txtScoreCount.setHorizontalAlignment(SwingConstants.CENTER);
		txtScoreCount.setBounds(44, 0, 48, 30);
		txtScoreCount.setText("12354");
		panel.add(txtScoreCount);
		txtScoreCount.setColumns(10);
		
		lblLevel = new JLabel("Level");
		lblLevel.setBackground(new Color(245, 245, 245));
		lblLevel.setFont(new Font("Calibri", Font.BOLD, 14));
		lblLevel.setBounds(92, 0, 42, 30);
		lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblLevel.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(lblLevel);
		
		txtLevelCount = new JTextField();
		txtLevelCount.setFont(new Font("Calibri", Font.BOLD, 14));
		txtLevelCount.setHorizontalAlignment(SwingConstants.CENTER);
		txtLevelCount.setBounds(134, 0, 40, 31);
		txtLevelCount.setText("1");
		panel.add(txtLevelCount);
		txtLevelCount.setColumns(10);
		
		lblLife = new JLabel("Life");
		lblLife.setFont(new Font("Calibri", Font.BOLD, 14));
		lblLife.setBounds(173, 0, 40, 30);
		lblLife.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblLife);
		
		txtLifeCount = new JTextField();
		txtLifeCount.setFont(new Font("Calibri", Font.BOLD, 14));
		txtLifeCount.setHorizontalAlignment(SwingConstants.CENTER);
		txtLifeCount.setBounds(211, 0, 40, 31);
		txtLifeCount.setText("3");
		panel.add(txtLifeCount);
		txtLifeCount.setColumns(10);
		
		lblBreakthewall = new JLabel("BREAK THE WALL");
		lblBreakthewall.setHorizontalAlignment(SwingConstants.CENTER);
		lblBreakthewall.setFont(new Font("Calibri", Font.BOLD, 16));
		lblBreakthewall.setBounds(251, 1, 159, 30);
		panel.add(lblBreakthewall);
		
		btnPause = new JButton("Pause");
		btnPause.setBounds(411, 0, 70, 30);
		panel.add(btnPause);
		
		Icon musicIcon = new ImageIcon(this.getClass().getResource(BreakWallConfig.musicIconPlaying));
		btnMute = new JButton(musicIcon);
		btnMute.setActionCommand("MuteMusic");
		btnMute.setBounds(482, 0, 40, 30);
		panel.add(btnMute);
		
		btnMenu = new JButton("Menu");
		btnMenu.setBounds(523, 0, 65, 30);
		panel.add(btnMenu);
		

		navigationButtons.add(btnPause);
		navigationButtons.add(btnMute);
		navigationButtons.add(btnMenu);
	}
	
	public ArrayList<JButton> getButtonList() {
		return navigationButtons;
	}
	
	public void setPlayPauseButton(String buttonText) {
		btnPause.setText(buttonText);		
	}
	
	public void setMusicButton(boolean isPlaying) {
		System.out.println(isPlaying);
		if(isPlaying == true) {
			Icon musicIcon = new ImageIcon(this.getClass().getResource(BreakWallConfig.musicIconPlaying));			
			btnMute.setIcon(musicIcon);
			btnMute.setActionCommand("MuteMusic");			
		} else {
			Icon musicIcon = new ImageIcon(this.getClass().getResource(BreakWallConfig.musicIconPausing));
			btnMute.setIcon(musicIcon);
			btnMute.setActionCommand("PlayMusic");
		}

	}
	
	public void updateScoreView(int score) {
		txtScoreCount.setText(Integer.toString(score));
	}
	
	public void updateLevelView(int level) {
		txtLevelCount.setText(Integer.toString(level));
	}
	
	public void updateLifeView(int lives) {
		txtLifeCount.setText(Integer.toString(lives));
	}
	
}
