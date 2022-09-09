package logic_classes;

import java.awt.Color;
import java.awt.Graphics;

public class Pocket extends Entity {	//Extend abstract class Entity

	private int sizeX;	//Define instance variable
	private int sizeY;
	
	private int posX;
	private int posY;
	
	private Color color;
	
	public Pocket(int sizeX, int sizeY, Color color) {
		super();
		this.sizeX = sizeX;
		this.sizeY = sizeY;		//Set instance variables to variables parsed in
		this.color = color;
	}
	
	public int getSizeX() {		//Define getters and setters
		return sizeX;
	}
	public int getSizeY() {
		return sizeY;
	}
	public Color getColor() {
		return color;
	}

	public int getPosX() {
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

	@Override
	public void render(Graphics g) {
		g.setColor(color);	//Set graphics color
		g.fillOval(posX, posY, sizeX, sizeY); //Render pocket
		
	}
}
