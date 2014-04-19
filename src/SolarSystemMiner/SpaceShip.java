package SolarSystemMiner;
import java.awt.*;
import java.awt.geom.Point2D;

public class SpaceShip {

	double x, y; // positional coordinates
	double angle, rotationalSpeed; // used to rotate the ship
	double acceleration, deceleration, xVelocity, yVelocity, drag; // used for
																	// speed and
																	// forward
																	// motion
	double radius = 12; // used to check for collisions
	int iron = 0;
	int nickel = 10;
	int titanium = 0;
	int water = 10;
	int oxygen = 10;
	int hydrogen = 10;
	int buffer = 0;

	// this counter will determine what upgrade the asteroid will receive (whih
	// is up for the queue);

	// flags
	boolean accelerating, turningLeft, turningRight, decelerating;

	// the polygon points for drawing the spaceship
	double[] startingXPts = { -15, -15, -15, 15 };
	double[] startingYPts = { 10, 0, -10, 0 };
	// determine the shape of the spaceships

	final double[] startingMainThrustXPts = { -10, -25, -10 };
	final double[] startingMainThrustYPts = { 5, 0, -5 };
	final double[] startingRearThrustXPts = { 3, 15, 10, 15 };
	final double[] startingRearThrustYPts = { 0, 12, 0, -12 };
	int[] xPts, yPts, mainThrustXPts, mainThrustYPts, rearThrustXPts,
			rearThrustYPts; // need this to hold int values to be passed to
							// fillPolygon()

	// constructor statement
	public SpaceShip(double x, double y, double angle, double rotationalSpeed,
			double acceleration, double deceleration, double drag) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		this.rotationalSpeed = rotationalSpeed;
		this.acceleration = acceleration;
		this.deceleration = deceleration;
		this.drag = drag;
		accelerating = false;
		turningLeft = false;
		turningRight = false;

		// set aside space for the coordinate holder arrays
		xPts = new int[4];
		yPts = new int[4];
		mainThrustXPts = new int[3];
		mainThrustYPts = new int[3];
		rearThrustXPts = new int[4];
		rearThrustYPts = new int[4];
	}

	public void setSpaceship() {
	
		
		if (hydrogen > 20) {
			acceleration += .05;
			deceleration += .05;
			hydrogen = 0;
		}
	}

	public int getIron() {
		return iron;
	}

	public int getNickel() {
		return nickel;
	}

	public int getTitanium() {
		return titanium;
	}

	public int getWater() {
		return water;
	}

	public int getOxygen() {
		return oxygen;
	}

	public int getHydrogen() {
		return hydrogen;
	}

	public void increaseIron() {
		iron++;
	}

	public void increaseNickel() {
		nickel++;
	}

	public void increaseTitanium() {
		titanium++;
	}

	public void increaseWater() {
		water++;
	}

	public void increaseOxygen() {
		oxygen++;
	}

	// these methods are called whenever a line is collided into, and increase
	// their respective variables

	public void draw(Graphics g, boolean isImmortal) {
		if (accelerating) {
			for (int i = 0; i < 3; i++) {
				// formula to rotate around a point is -- x = x*cos(angle) -
				// y*sin(angle) -- y = x*sin(angle) + y*cos(angle)
				// put each point of the main thrusters through this formula
				mainThrustXPts[i] = (int) (startingMainThrustXPts[i]
						* Math.cos(angle) - startingMainThrustYPts[i]
						* Math.sin(angle) + x + 0.5);
				mainThrustYPts[i] = (int) (startingMainThrustXPts[i]
						* Math.sin(angle) + startingMainThrustYPts[i]
						* Math.cos(angle) + y + 0.5);
			}
			g.setColor(Color.ORANGE);
			g.fillPolygon(mainThrustXPts, mainThrustYPts, 3);
		}

		if (decelerating) {
			for (int i = 0; i < 4; i++) {
				// formula to rotate around a point is -- x = x*cos(angle) -
				// y*sin(angle) -- y = x*sin(angle) + y*cos(angle)
				// put each point of the main thrusters through this formula
				rearThrustXPts[i] = (int) (startingRearThrustXPts[i]
						* Math.cos(angle) - startingRearThrustYPts[i]
						* Math.sin(angle) + x + 0.5);
				rearThrustYPts[i] = (int) (startingRearThrustXPts[i]
						* Math.sin(angle) + startingRearThrustYPts[i]
						* Math.cos(angle) + y + 0.5);
			}
			g.setColor(Color.BLUE);
			g.fillPolygon(rearThrustXPts, rearThrustYPts, 4);
		}

		for (int i = 0; i < 4; i++) {
			// formula to rotate around a point is -- x = x*cos(angle) -
			// y*sin(angle) -- y = x*sin(angle) + y*cos(angle)
			// put each point of the ship through this formula
			xPts[i] = (int) (startingXPts[i] * Math.cos(angle)
					- startingYPts[i] * Math.sin(angle) + x + 0.5);
			yPts[i] = (int) (startingXPts[i] * Math.sin(angle)
					+ startingYPts[i] * Math.cos(angle) + y + 0.5);
		}
		// set a different color depending on if the ship is currently immortal
		// or not
		if (isImmortal)
			g.setColor(new Color(115, 74, 18));
		else
			g.setColor(Color.WHITE);
		// draw ship
		g.fillPolygon(xPts, yPts, 4);

	}

	public void move(int sWidth, int sHeight) {
		if (turningRight)
			angle += rotationalSpeed;
		if (turningLeft)
			angle -= rotationalSpeed;

		if (accelerating) {
			xVelocity += acceleration * Math.cos(angle);
			yVelocity += acceleration * Math.sin(angle);
		}

		if (decelerating) {
			xVelocity -= deceleration * Math.cos(angle);
			yVelocity -= deceleration * Math.sin(angle);
		}

		x += xVelocity;
		y += yVelocity;
		xVelocity *= drag;
		yVelocity *= drag;

		if (x < 0)
			x += sWidth;
		else if (x > sWidth)
			x -= sWidth;

		if (y < 0) {
			y += sHeight;
		} else if (y > sHeight)
			y -= sHeight;
	}

	// getters and setters
	public Laser fire() {

		return new Laser(x, y, angle, xVelocity, yVelocity, 50);
	}

	public void setAccelerating(boolean accelerating) {
		this.accelerating = accelerating;
	}

	public void setTurningLeft(boolean turningLeft) {
		this.turningLeft = turningLeft;
	}

	public void setTurningRight(boolean turningRight) {
		this.turningRight = turningRight;
	}

	public void setDecelerating(boolean decelerating) {
		this.decelerating = decelerating;
	}

	public Point2D getCenter() {
		return new Point2D.Double(x, y);
	}

	public double getRadius() {
		return radius;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void decreaseFuel() {
		oxygen--;
		hydrogen--;
		water--;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setVelocity(double v) {
		xVelocity = v;
		yVelocity = v;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

}
