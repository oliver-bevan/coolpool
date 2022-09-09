package logic_classes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Table {

	private String name; // Define instance variables


	private Color outlineColor;
	private Color mainColor;
	private Color turfColor;

	private int mainRectSizeX;
	private int mainRectSizeY;

	private int turfSizeX;
	private int turfSizeY;

	private int paddingSizeX;
	private int paddingSizeY;

	private int roundedCornerSizeX;
	private int roundedCornerSizeY;

	private int outlineThickness;

	private Pocket[] pockets;

	int posX;
	int posY;

	public Table(double velocityLoss, double velocityLossEdge, Color outlineColor, Color mainColor,
			Color turfColor, int mainRectSizeX, int mainRectSizeY, int turfSizeX, int turfSizeY, int roundedCornerSizeX,
			int roundedCornerSizeY, int outlineThickness, Pocket setPocket) {


		this.outlineColor = outlineColor;
		this.mainColor = mainColor; // Set instance variables to variables parsed in
		this.turfColor = turfColor;

		this.mainRectSizeX = mainRectSizeX;
		this.mainRectSizeY = mainRectSizeY;

		this.turfSizeX = turfSizeX;
		this.turfSizeY = turfSizeY;

		this.roundedCornerSizeX = roundedCornerSizeX;
		this.roundedCornerSizeY = roundedCornerSizeY;

		this.outlineThickness = outlineThickness;

		this.posX = 0;
		this.posY = 0;

		this.pockets = new Pocket[6];
		pockets[0] = new Pocket(setPocket.getSizeX(), setPocket.getSizeY(), setPocket.getColor());
		pockets[1] = new Pocket(setPocket.getSizeX(), setPocket.getSizeY(), setPocket.getColor());
		pockets[2] = new Pocket(setPocket.getSizeX(), setPocket.getSizeY(), setPocket.getColor()); // Duplicate pocket
																									// into new class
		pockets[3] = new Pocket(setPocket.getSizeX(), setPocket.getSizeY(), setPocket.getColor());
		pockets[4] = new Pocket(setPocket.getSizeX(), setPocket.getSizeY(), setPocket.getColor());
		pockets[5] = new Pocket(setPocket.getSizeX(), setPocket.getSizeY(), setPocket.getColor());

	}

	public void render(Graphics g, int windowWidth, int windowHeight, int paddingSizeX, int paddingSizeY) {
		this.paddingSizeX = paddingSizeX; // Set padding size instance variables to variables parsed in
		this.paddingSizeY = paddingSizeY;

		// Draw brown table rounded rectangle
		g.setColor(mainColor);
		g.fillRoundRect(paddingSizeX, paddingSizeY, mainRectSizeX, mainRectSizeY, roundedCornerSizeX,
				roundedCornerSizeY);

		// Draw outline on brown rounded rectangle with stroke of 3px
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(outlineColor);
		g2d.setStroke(new BasicStroke(outlineThickness));
		g2d.drawRoundRect(paddingSizeX, paddingSizeY, mainRectSizeX, mainRectSizeY, roundedCornerSizeX,
				roundedCornerSizeY);
		// Draw green turf
		g.setColor(turfColor);
		g.fillRect(paddingSizeX + ((mainRectSizeX - turfSizeX) / 2), paddingSizeY + ((mainRectSizeY - turfSizeY) / 2),
				turfSizeX, turfSizeY);

		// Calculate positions of pockets based on padding, turf, and main rounded
		// rectangle size
		pockets[0].setPosX(paddingSizeX + ((mainRectSizeX - turfSizeX) / 2) - ((pockets[0].getSizeX() - 10) / 2));
		pockets[1].setPosX(
				paddingSizeX + (((mainRectSizeX - turfSizeX) / 2) + (turfSizeX / 2)) - (pockets[1].getSizeX() / 2));
		pockets[2].setPosX(paddingSizeX + ((mainRectSizeX - turfSizeX) / 2) - ((pockets[2].getSizeX() - 10) / 2)
				+ mainRectSizeX - (mainRectSizeX - turfSizeX) - (pockets[2].getSizeX() / 2) / 2);
		pockets[3].setPosX(paddingSizeX + ((mainRectSizeX - turfSizeX) / 2) - ((pockets[3].getSizeX() - 10) / 2));
		pockets[4].setPosX(
				paddingSizeX + (((mainRectSizeX - turfSizeX) / 2) + (turfSizeX / 2)) - (pockets[4].getSizeX() / 2));
		pockets[5].setPosX(paddingSizeX + ((mainRectSizeX - turfSizeX) / 2) - ((pockets[5].getSizeX() - 10) / 2)
				+ mainRectSizeX - (mainRectSizeX - turfSizeX) - (pockets[5].getSizeX() / 2) / 2);

		pockets[0].setPosY((paddingSizeY + ((mainRectSizeY - turfSizeY) / 2) - ((pockets[0].getSizeY() - 10) / 2)));
		pockets[1].setPosY(paddingSizeY + ((mainRectSizeX - turfSizeX) / 2) - ((pockets[1].getSizeX() - 10) / 2));
		pockets[2].setPosY((paddingSizeY + ((mainRectSizeY - turfSizeY) / 2) - ((pockets[2].getSizeY() - 10) / 2)));
		pockets[3].setPosY(paddingSizeY + turfSizeY);
		pockets[4].setPosY(paddingSizeY + turfSizeY);
		pockets[5].setPosY(paddingSizeY + turfSizeY);

		for (int i = 0; i < pockets.length; i++) {
			pockets[i].render(g);
		}
	}

	public boolean[] checkColision(int x1, int y1, int sizeX) { // Check colision with edges of table
		int circleRadius = sizeX / 2;
		if (x1 + circleRadius > paddingSizeX + turfSizeX + ((mainRectSizeX - turfSizeX) / 2)) { // If the position is
																								// greater than the
																								// bounds of the turf
																								// size then return
																								// collision
			return new boolean[] { true, true, true };
		} else if (x1 - circleRadius < paddingSizeX + (mainRectSizeX - turfSizeX) / 2) {
			return new boolean[] { true, true, false };
		} else if (y1 + (circleRadius) * 2.5 > paddingSizeY + turfSizeY + ((mainRectSizeY - turfSizeY) / 2)) {
			return new boolean[] { true, false, true };
		} else if (y1 - circleRadius < paddingSizeY + ((mainRectSizeY - turfSizeY) / 2)) {
			return new boolean[] { true, false, false };
		} else {
			return new boolean[] { false, false };
		}

	}

	public boolean checkPotted(int x1, int y1, int holeNum, int circleRadii1) {
		int xSide = (x1 - (pockets[holeNum].getPosX() + (pockets[holeNum].getSizeX()) / 2)); // Calculate sides of right
																								// angled triangle
		int ySide = (y1 - (pockets[holeNum].getPosY() + (pockets[holeNum].getSizeX()) / 2));
		double hypotenuse = Math.sqrt((int) Math.pow(xSide, 2) + (int) Math.pow(ySide, 2)); // Calculate hypotenuse of
																							// right angled triangle
		if (circleRadii1 > hypotenuse) {
			return true; // If the circle radii is greater than hypotenuse then return colision
		} else {
			return false;
		}
	}

	public String getName() { // Define getters and setters below
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getOutlineColor() {
		return outlineColor;
	}

	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}

	public Color getMainColor() {
		return mainColor;
	}

	public void setMainColor(Color mainColor) {
		this.mainColor = mainColor;
	}

	public Color getTurfColor() {
		return turfColor;
	}

	public void setTurfColor(Color turfColor) {
		this.turfColor = turfColor;
	}

	public int getMainRectSizeX() {
		return mainRectSizeX;
	}

	public void setMainRectSizeX(int mainRectSizeX) {
		this.mainRectSizeX = mainRectSizeX;
	}

	public int getMainRectSizeY() {
		return mainRectSizeY;
	}

	public void setMainRectSizeY(int mainRectSizeY) {
		this.mainRectSizeY = mainRectSizeY;
	}

	public int getTurfSizeX() {
		return turfSizeX;
	}

	public void setTurfSizeX(int turfSizeX) {
		this.turfSizeX = turfSizeX;
	}

	public int getTurfSizeY() {
		return turfSizeY;
	}

	public void setTurfSizeY(int turfSizeY) {
		this.turfSizeY = turfSizeY;
	}

	public int getPaddingSizeX() {
		return paddingSizeX;
	}

	public void setPaddingSizeX(int paddingSizeX) {
		this.paddingSizeX = paddingSizeX;
	}

	public int getPaddingSizeY() {
		return paddingSizeY;
	}

	public void setPaddingSizeY(int paddingSizeY) {
		this.paddingSizeY = paddingSizeY;
	}

	public int getRoundedCornerSizeX() {
		return roundedCornerSizeX;
	}

	public void setRoundedCornerSizeX(int roundedCornerSizeX) {
		this.roundedCornerSizeX = roundedCornerSizeX;
	}

	public int getRoundedCornerSizeY() {
		return roundedCornerSizeY;
	}

	public void setRoundedCornerSizeY(int roundedCornerSizeY) {
		this.roundedCornerSizeY = roundedCornerSizeY;
	}

	public int getOutlineThickness() {
		return outlineThickness;
	}

	public void setOutlineThickness(int outlineThickness) {
		this.outlineThickness = outlineThickness;
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

	public Pocket[] getPockets() {
		return pockets;
	}

	public void setPockets(Pocket[] pockets) {
		this.pockets = pockets;
	}

}
