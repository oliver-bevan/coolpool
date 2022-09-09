package logic_classes;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import gui.GamePanel;

public class Ball extends Entity {	//Extend abstract class Entity

	private int sizeX;
	private int sizeY;
	

	private Color mainColor;
	private Color highlightColor;
	private Color outlineColor;	//Define instance variables

	private Vector vector;
	private int velocityLoss;
	private double setSpeed;

	private boolean potted;
	
	Table table;
	
	private int posX;
	private int posY;

	ArrayList<Ball> nearbyBalls;
	private boolean black;
	private boolean cue;

	public Ball( int id, int sizeX, int sizeY, Color mainColor, Color highlightColor, Color outlineColor, int velocityLoss,
			int setSpeed, boolean black, boolean cue) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.mainColor = mainColor;
		this.highlightColor = highlightColor;
		this.outlineColor = outlineColor;	//Set instance variables to parsed inputs
		this.velocityLoss = velocityLoss;
		vector = new Vector(velocityLoss, setSpeed);
		this.setSpeed = setSpeed;
		potted = false;
		this.black = black;
		this.cue = cue;
	}

	public void render(Graphics g) {
		int tempPosX = posX - sizeX/2;
		int tempPosY = posY - sizeY/2;	//Move effective centre of circles from centre back to top left

		g.setColor(outlineColor);	//Set the colour of brush for outline
		g.drawOval(tempPosX, tempPosY, sizeX, sizeY);	//Draw outline

		g.setColor(mainColor);	//Set colour of brush to main oval
		g.fillOval(tempPosX, tempPosY, sizeX, sizeY);	//Fill and draw the main oval
	}

	public void resetSpeed() {
		vector.setSpeed(setSpeed);	//Return speed factor to original value
	}

	public void setMove(double xAcceleration, double yAcceleration, ArrayList<Ball> balls, Table table) {
		vector.setxAcceleration(xAcceleration);
		vector.setyAcceleration(yAcceleration);	//Set the vector to parsed in values
		this.table = table;
		this.nearbyBalls = balls;
	}
	public boolean[] updatePositions(ArrayList<Integer> collisions, ArrayList<Ball> nearbyBalls) {
		this.nearbyBalls = nearbyBalls;
		if (vector.getxAcceleration() == 0.0 && vector.getyAcceleration() == 0.0) {
			return new boolean[] {false, false, false};	//Return ball stopped as speed is 0
		}
		int temp[] = (vector.calculateNewPositions(new int[] { posX, posY }));	//Calculate the new positions from vector and speed factor

		posX = temp[0];	//Set positions
		posY = temp[1];
		
		vector.updateSpeedReduction();	//Update the speed reduction
		
		for (int i = 0; i < nearbyBalls.size(); i++) {
			if (GamePanel.checkColision(posX, posY, nearbyBalls.get(i).getPosX(), nearbyBalls.get(i).getPosY(),
					sizeX / 2, nearbyBalls.get(i).getSizeX() / 2)) {	//Check collisions
				if(this.equals(nearbyBalls.get(i))) {
					continue;	//If collision is with itself, skip
				}
				
				int tempPosX = posX;	//Set the temporary position
				int tempPosY = posY;
				
				double tempXAcceleration = (nearbyBalls.get(i).getPosX()-posX)/23.0; //Calculate the temporary X and Y acceleration for the ball just collided with
				double tempYAcceleration = (nearbyBalls.get(i).getPosY()-posY)/23.0;

				this.setPosX(posX+(tempPosX-posX));	//Calculate the new positions for this ball
				this.setPosY(posY+(tempPosY-posY));
				
				nearbyBalls.get(i).setMove(tempXAcceleration, tempYAcceleration, nearbyBalls, table);	//Move the ball just collided with
				this.setMove(vector.getxAcceleration()-nearbyBalls.get(i).getVector().getxAcceleration(), vector.getyAcceleration()-nearbyBalls.get(i).getVector().getyAcceleration(), nearbyBalls, table);	//Move this ball
				return new boolean[] {true, false, false};	//Return ball collision only
			}
		}
		for (int i = 0; i < table.getPockets().length; i++) {	//Loop through the tables pockets
			if (table.checkPotted(posX, posY, i, sizeX)) {	//Check if the ball has been potted
				potted = true;	//Kill this instance of ball by setting potted flag
				nearbyBalls.remove(this);	//Remove from list of current balls
				if(this.cue) {	//Check if the cue ball has been potted
					return new boolean[] {false, true, false};	//Return white/cue ball potted
				} else if(this.black) {		//Check if black ball has been pottedthis.
					return new boolean[] {false, false, true};	//Return black ball potted
				}
				return new boolean[] {false, false, false};	//Return nothing as a result of move
			}
		}
		if(table.checkColision(posX, posY, sizeX)[0]) {	//Check collision with table edge if its in

			if(table.checkColision(posX, posY, sizeX)[1]) { //if it's in Y axis then reverse acceleration factor		
				this.setMove((vector.getxAcceleration()*-1), vector.getyAcceleration(), nearbyBalls, table);	//Multiply vector by minus 1 to negate
				
			} else {
				this.setMove(vector.getxAcceleration(), (vector.getyAcceleration()*-1), nearbyBalls, table);	//Multiply vector by minus 1 to negate
			}
			return new boolean[] {true, false, false};	//Return ball still moving bounced in table
		}
		
		if (vector.getSpeed() < 1) {	//If the speed factor, which allows ball to slow to a stop, is below 1, then reset speed and stop ball from moving
			vector.setSpeed(setSpeed);
			return new boolean[] {false, false, false};	//Return ball stopped
		}
		return new boolean[] {true, false, false};	//Else return ball still moving
	}

	public void setPos(int[] xy) {
		this.posX = xy[0];	//Set instance variable position from parsed in array
		this.posY = xy[1];
	}
	
	public int getSizeX() {			//Getters and setters for instance variables below
		return sizeX;
	}

	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}

	public int getSizeY() {
		return sizeY;
	}

	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}

	public Color getMainColor() {
		return mainColor;
	}

	public void setMainColor(Color mainColor) {
		this.mainColor = mainColor;
	}

	public Color getHighlightColor() {
		return highlightColor;
	}

	public void setHighlightColor(Color highlightColor) {
		this.highlightColor = highlightColor;
	}

	public Color getOutlineColor() {
		return outlineColor;
	}

	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}

	public Vector getVector() {
		return vector;
	}

	public void setVector(Vector vector) {
		this.vector = vector;
	}

	public int getVelocityLoss() {
		return velocityLoss;
	}

	public void setVelocityLoss(int velocityLoss) {
		this.velocityLoss = velocityLoss;
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

	public boolean isPotted() {
		return potted;
	}

	public void setPotted(boolean potted) {
		this.potted = potted;
	}

	public boolean isBlack() {
		return black;
	}

	public void setBlack(boolean black) {
		this.black = black;
	}

	public boolean isCue() {
		return cue;
	}

	public void setCue(boolean cue) {
		this.cue = cue;
	}
}