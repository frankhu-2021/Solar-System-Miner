package SolarSystemMiner;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

public class AsteroidExplosion {

	double lifeLeft = 400;

	double x, y; // positional coordinates
	double xVelocity, yVelocity, angle, rotationalSpeed; // movement

	final double startingXPts[] = { -5, 5, 5 };
	final double startingYPts[] = { -5, 5, 5 };
	int[] xPts, yPts;
	// added
	private int mineralType;

	public AsteroidExplosion(double x, double y, double xVelocity,
			double yVelocity, double angle, double rotationalSpeed, int mineralType) {
		this.x = x;
		this.y = y;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.angle = angle;
		this.rotationalSpeed = rotationalSpeed;
		// added
		this.mineralType = mineralType;
		xPts = new int[3];
		yPts = new int[3];
	}

	public void draw(Graphics g) {
		for (int i = 0; i < 3; i++) {
			xPts[i] = (int) (startingXPts[i] * Math.cos(angle)
					- startingYPts[i] * Math.sin(angle) + x + 0.5);
			yPts[i] = (int) (startingXPts[i] * Math.sin(angle)
					+ startingYPts[i] * Math.cos(angle) + y + 0.5);
		}
		if (mineralType == 1)
			g.setColor(Color.GRAY);
		else if (mineralType == 0) {
			g.setColor(Color.RED);

		} else if (mineralType == 2) {
			g.setColor(Color.green);
		} else if (mineralType == 3) {
			g.setColor(Color.BLUE);
		} else if (mineralType == 4) {
			g.setColor(Color.MAGENTA);
		}
		g.drawPolygon(xPts, yPts, 3);

		g.fillPolygon(xPts, yPts, 3);
	}

	public void move() {
		angle += rotationalSpeed;
		x += xVelocity;
		y += yVelocity;
		lifeLeft--;
	}

	// getters and setters
	public double getLifeLeft() {
		return lifeLeft;
	}

	public Point2D getCenter() {
		return new Point2D.Double(x, y);
	}

	public int getMineralType() {
		return mineralType;
	}
}