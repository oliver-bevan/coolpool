package logic_classes;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Entity {

	int posX;	//Define instance variables and Entity must have
	int posY;
	
	int sizeX;
	int sizeY;
	
	Color mainColor;
	
	public abstract void render(Graphics g);	//Define the abstract class render.=
}
