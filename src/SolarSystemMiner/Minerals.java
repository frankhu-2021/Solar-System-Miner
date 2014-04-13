package SolarSystemMiner;
import java.awt.geom.Point2D;

public class Minerals {

	private int mineralType;
	/*
	 * Mineral Types corresponding numbers Iron =1 , nickel = 2, titanium = 3,
	 * water =4, oxygen = 5
	 */

	private double x, y;
	private double size;
	private double xVelocity;
	private double yVelocity;
	private boolean active = true;

	public Minerals(int size, double x, double y, double angle,
			double rotationalSpeed, double xVelocity, double yVelocity) {
		this.size = size;
		this.x = x;
		this.y = y;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		active = true;

		// used size variable to determine how big the asteroid polygon should
		// be

	}

	public void move(int sWidth, int sHeight) {
		if (active) {
			x += xVelocity;
			y += yVelocity;
		}

		if (x < (0))
			x += sWidth;
		else if (x > (sWidth))
			x -= sWidth;

		if (y < (0)) {
			y += sHeight;
		} else if (y > (sHeight))
			y -= sHeight;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Point2D getCenter() {
		return new Point2D.Double(x, y);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
