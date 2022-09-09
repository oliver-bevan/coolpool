package logic_classes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import gui.GamePanel;

public class Cue extends Entity {	//Extend abstract class Entity

	private int sizeX;
	private int sizeY;

	private int tipSizeX;
	
	private Color mainColor;	//Define instance variables
	private Color tipColor;

	private int posX;
	private int posY;
	private int rotation;
	
	
	
	public Cue(int sizeX, int sizeY, int tipSizeX, Color mainColor, Color highlightColor, Color tipColor,
			Color outlineColor) {
		super();
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.tipSizeX = tipSizeX;
		this.mainColor = mainColor;		//Set instance variables based on parsed in values
		this.tipColor = tipColor;
	}

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;	//Use a Graphics 2D to draw at an angle
		
		g2d.translate(posX, posY + sizeY/2); //Move to the position set by instance variable
		g2d.rotate(Math.toRadians(rotation));	//Rotate to rotation
		g2d.setColor(mainColor);
		g2d.fillRect(0, 0, sizeX, sizeY);		//Set the colour and draw the main rectangle
		g2d.setColor(tipColor);
		g2d.fillRect(0, 0, tipSizeX, sizeY);	//Draw the tip rectangle 
		g2d.rotate(-Math.toRadians(rotation));	//Return the Graphics 2D to original position.
		g2d.translate(-posX, -posY + sizeY/2);
	}

	public int getPosX() {		//Getters and setters
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	public boolean fire(ArrayList<Ball> balls, Table table, ArrayList<Integer> collisions) {		//Fire class
		for (int i = 0; i < balls.size(); i++) {	
			if (GamePanel.checkColision(posX, posY, balls.get(i).getPosX(), balls.get(i).getPosY(),	//Use the checkColision class to check if cue is near ball 
					sizeX / 4, balls.get(i).getSizeX()/4)) {
				double tempXAcceleration = (Math.cos(Math.toRadians(rotation))*-1)*1.5;		//If it is then use sin and cos on radian value of cue position negated and multiplied by 1.5 to calculate vector acceleration
				double tempYAcceleration = (Math.sin(Math.toRadians(rotation))*-1)*1.5;
				
				collisions = new ArrayList<Integer>();
				if(balls.get(i).isCue() == false) {
					return true;	//If the ball is not the cue ball then return true to flag disqualification
				}
				balls.get(i).setMove(tempXAcceleration, tempYAcceleration, balls, table); //Set the ball move
			}
		}
		return false;
		
	}
	
	
}
