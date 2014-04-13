package SolarSystemMiner;

import java.awt.*;

public class ShipExplosion {
	int x, y; // positional coordinates
	int height, width; // size
	int lifeLeft;

	ShipExplosion(double x, double y, int height, int width) {
		this.x = (int) x;
		this.y = (int) y;
		this.height = height;
		this.width = width;
		lifeLeft = 30;
	}

	public void draw(Graphics g) {
		g.setColor(Color.RED);
		g.fillOval(x, y, height, width);
	}

	public void expand() {
		height++;
		width++;
		lifeLeft--;
	}

	public int getLifeLeft() {
		return lifeLeft;
	}
}
