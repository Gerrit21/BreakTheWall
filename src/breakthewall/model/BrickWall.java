﻿package breakthewall.model;

import java.util.ArrayList;

import breakthewall.BreakWallConfig;

/**
 * Klasse zur Erstellung einer Brick-Wand, bestehend aus verschiedenen Brick-Typen.
 * 
 * @author Mareike R�ncke, Gerrit Schulte
 * @version 1.0, Oktober 2015.
 */
public class BrickWall {
	
	// private BreakWallView gameField;
	private GameElement randomBrick;
	private ArrayList<GameElement> brickList;
	private int wallWidth = BreakWallConfig.wallWidth;
	private int wallHeight = BreakWallConfig.wallHeight;
	private int brickX = 30;
	private int brickY = 30 + BreakWallConfig.barHeight;
	
	/*
	 * Konstruktor ruft die Methode zur Erzeugung der Brick-Wand auf.
	 * Diese wird in das �bergebene Spielfeld gezeichnet.
	 */
	
	public BrickWall() {		
		brickList = new ArrayList<GameElement>();
		buildWall();
	}
	
	public BrickWall(ArrayList<GameElement> brickList) {		
		this.brickList = brickList;
	}
	
	/**
	 * Ruft die Methode getRandomBrick() zur Erzeugung eines Bricks auf und 
	 * zeichnet diesen Brick in das Spielfeld.
	 * Die Begrenzungen f�r die Brick-Wand sind in der Klasse BreakWallData definiert.
	 */
	
	public void buildWall() {
		// setzt erzeugte Bricks mit einem Abstand von 10 Pixeln neben- und untereinander
		while(getBrickY() < wallHeight) {			
			while(getBrickX() < wallWidth) {
				randomBrick = getRandomBrick();
				randomBrick.setXCoord(getBrickX());
				randomBrick.setYCoord(getBrickY());				
				randomBrick.setId(Integer.toString(getBrickX()) + Integer.toString(getBrickY()));
				brickList.add(randomBrick);
				setBrickX(getBrickX() + randomBrick.getWidth() + 10);
			}
			setBrickY(getBrickY() + randomBrick.getHeight() + 10);
			// versetzt die Brick-Zeilen in zuf�lligem Abstand vom Rand
			setBrickX(BreakWallModel.randomFromRange(1, 3) * 15);
		}
	}
	
	/**
	 * �ber eine Random-Funktion wird zuf�llig einer von 3 Brick-Typen erzeugt.
	 * Die Wahrscheinlichkeit f�r einen Typen ist in der Klasse BreakWallData definiert.
	 */
	
	private void setRandomBrick() {
		int randomInt = BreakWallModel.randomFromRange(1, 100);
		if(randomInt <= BreakWallConfig.normalPossible) {
			randomBrick = new BrickNormal(BreakWallConfig.stabilityNormal);
		} else if(randomInt > BreakWallConfig.normalPossible) {
			if(randomInt < (BreakWallConfig.bonusPossible + BreakWallConfig.normalPossible)) {
				randomBrick = new BrickBonus();				
			} else if(randomInt >= (BreakWallConfig.bonusPossible + BreakWallConfig.normalPossible)) {
				randomBrick = new BrickNormal(BreakWallConfig.stabilityHard);
			}
		}
	}
	
	private GameElement getRandomBrick() {
		setRandomBrick();
		return this.randomBrick;
	}
	
	public ArrayList<GameElement> getBrickList() {
		return this.brickList;
	}
	
	public void setBrickList(ArrayList<GameElement> brickList) {
		this.brickList = brickList;
	}
	
	public void removeFromBrickList(GameElement removeBrick) {
		this.brickList.remove(removeBrick);
	}
	
	public void setBrickX(int x) {
		this.brickX = x;
	}
	
	public int getBrickX() {
		return this.brickX;
	}
	
	public void setBrickY(int y) {
		this.brickY = y;
	}
	
	public int getBrickY() {
		return this.brickY;
	}
}
