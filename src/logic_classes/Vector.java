package logic_classes;

public class Vector {

	private double xAcceleration;
	private double yAcceleration;		//Define instance variables
	private double speed;
	private double velocityLoss;

	public Vector() {
		xAcceleration = 0;	//Default constructor sets values to zero
		xAcceleration = 0;
		velocityLoss = 0;
	}

	public Vector(double velocityLoss, double setSpeed) {
		xAcceleration = 0;
		xAcceleration = 0;
		this.velocityLoss = velocityLoss;	//Set instance variables to variables parsed in
		speed = setSpeed;
	}

	public int[] calculateNewPositions(int[] pos) {
		int[] newPos = new int[2];
		newPos[0] = (int) (pos[0] + Math.round((speed * xAcceleration)));	//Calculate positions by adding previous positions plus the speed factor multiplied by x and y acceleration
		newPos[1] = (int) (pos[1] + Math.round((speed * yAcceleration)));

		return newPos;
	}

	public void updateSpeedReduction() {
		speed -= speed/velocityLoss;	//Divide speed factor by velocity loss and take away from speed factor
		if(speed < 1) {	//If the speed is below one:
			speed = getSpeed();	//Reset speed factor
			xAcceleration = 0; //And set x and y acceleration to zero, i.e. ball has stopped
			yAcceleration = 0;
		}
	}

	public double getxAcceleration() {	//Getters and setters defined below
		return xAcceleration;
	}

	public void setxAcceleration(double xAcceleration) {
		this.xAcceleration = xAcceleration;
	}

	public double getyAcceleration() {
		return yAcceleration;
	}

	public void setyAcceleration(double yAcceleration) {
		this.yAcceleration = yAcceleration;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
}