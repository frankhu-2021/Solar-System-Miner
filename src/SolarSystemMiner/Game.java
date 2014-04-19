package SolarSystemMiner;

import java.applet.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.swing.JPanel;
import javax.swing.Timer;

/*
 <applet code="SampleApplet.class" CodeBase="" width=500 height=500></applet>
 */

@SuppressWarnings("serial")
public class Game extends Applet implements Runnable, KeyListener {

	// timing variables
	Thread thread;
	long startTime, endTime, framePeriod;
	int ballSize = 0;
	boolean gameOver = false;
	// graphics variables
	Image img;
	Image jupiter, mars, neptune, pluto, saturn, uranus;
	Dimension dim;
	int width, height;
	Graphics g;
	double counter;
	// text items
	int level, lives, score;
	ArrayList<Integer> mineralType = new ArrayList<Integer>();

	SpaceShip ship;
	// Ship obtain minerals
	boolean shipCollision, shipExplode, shipObtainMinerals;

	// ArrayList to hold asteroids
	ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();

	// Added minerals
	ArrayList<Minerals> minerals = new ArrayList<Minerals>();
	int numOfAsteroids = 5;

	// ArrayList to hold the lasers
	ArrayList<Laser> lasers = new ArrayList<Laser>();
	final double rateOfFire = 10; // limits rate of fire
	double rateOfFireRemaining; // decrements rate of fire

	// ArrayList to hold explosion particles
	ArrayList<AsteroidExplosion> explodingLines = new ArrayList<AsteroidExplosion>();

	// ArrayList to hold ship explosions
	ArrayList<ShipExplosion> shipExplosion = new ArrayList<ShipExplosion>();

	public void start() {
		thread = new Thread(this);
		thread.start();

	}

	public void init() {

		resize(900, 700); // set size of the applet
		dim = getSize(); // get dimension of the applet
		width = dim.width;
		height = dim.height;
		framePeriod = 25; // set refresh rate

		addKeyListener(this); // to get commands from keyboard
		setFocusable(true);

		ship = new SpaceShip(width / 2, height / 2, 0, .15, .5, .15, .98); // add
																			// ship
		InputStream in;
		Toolkit toolkit;
		byte buffer[];// to
		try { // game
			in = getClass().getResourceAsStream("mars.jpg");
			buffer = new byte[in.available()];
			for (int i = 0, n = in.available(); i < n; i++)
				buffer[i] = (byte) in.read();
			// in.read(buffer);
			// this doesn't read all when reading from JAR
			toolkit = Toolkit.getDefaultToolkit();
			mars = toolkit.createImage(buffer);
		} catch (Exception e) {

		}
		try {
			in = getClass().getResourceAsStream("Jupiter.gif");
			buffer = new byte[in.available()];
			for (int i = 0, n = in.available(); i < n; i++)
				buffer[i] = (byte) in.read();
			// in.read(buffer);
			// this doesn't read all when reading from JAR
			toolkit = Toolkit.getDefaultToolkit();
			jupiter = toolkit.createImage(buffer);
		} catch (Exception e) {

		}
		try {
			in = getClass().getResourceAsStream("neptune.jpg");
			buffer = new byte[in.available()];
			for (int i = 0, n = in.available(); i < n; i++)
				buffer[i] = (byte) in.read();
			// in.read(buffer);
			// this doesn't read all when reading from JAR
			toolkit = Toolkit.getDefaultToolkit();
			neptune = toolkit.createImage(buffer);
		} catch (Exception e) {

		}
		try {
			in = getClass().getResourceAsStream("pluto.jpg");
			buffer = new byte[in.available()];
			for (int i = 0, n = in.available(); i < n; i++)
				buffer[i] = (byte) in.read();
			// in.read(buffer);
			// this doesn't read all when reading from JAR
			toolkit = Toolkit.getDefaultToolkit();
			pluto = toolkit.createImage(buffer);
		} catch (Exception e) {

		}
		try {
			in = getClass().getResourceAsStream("uranus.jpg");
			buffer = new byte[in.available()];
			for (int i = 0, n = in.available(); i < n; i++)
				buffer[i] = (byte) in.read();
			// in.read(buffer);
			// this doesn't read all when reading from JAR
			toolkit = Toolkit.getDefaultToolkit();
			uranus = toolkit.createImage(buffer);
		} catch (Exception e) {

		}
		shipCollision = false;
		shipExplode = false;
		level = numOfAsteroids;
		lives = 3;
		addAsteroids();

		img = createImage(width, height); // create an off-screen image for
											// double-buffering
		g = img.getGraphics(); // assign the off-screen image

		int delay = 200;
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (shipCollision) {

					lives--;

					shipCollision = false;
				}

				counter += 2;

			}
		};

		new Timer(delay, taskPerformer).start();

		int delay2 = 1000000;
		ActionListener taskPerformer2 = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (numOfAsteroids < 15) {
					numOfAsteroids += 1;
					addAsteroids();

				}
			}
		};

		new Timer(delay2, taskPerformer2).start();

		int delay3 = 10000;
		ActionListener taskPerformer3 = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ship.decreaseFuel();
			}
		};

		new Timer(delay3, taskPerformer3).start();

	}

	public void paint(Graphics gfx) {

		Graphics2D g2d = (Graphics2D) g;
		if (lives == 0 || ship.getWater() == 0 || ship.getOxygen() == 0) {
			gameOver = true;
		}
	
		if (iron >= 10) {
			iron = 0;
			lives++;
			// increase the buffer(life) of the asteroid
		}
		if (nickel >= 10) {
			nickel = 0;
			lives++;
		}
		if (titanium >= 10) {
			titanium = 0;
			lives++;
		}

		// give the graphics smooth edges
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
		g2d.setRenderingHints(rh);
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, width, height); // add a black background

		if (counter == 360) {
			ballSize = 0;
		} else if (counter == 70) {
			ballSize = 0;
		} else if (counter == 1300) {
			ballSize = 0;
		} else if (counter == 1600) {
			ballSize = 0;
		} else if (counter == 4500) {
			ballSize = 0;
		}

		if (counter >= 100 && counter <= 360) {

			g2d.drawImage(mars, 80, 100, ballSize, ballSize, null);

			ballSize += 5;
		} else if (counter >= 500 && counter <= 700) {
			g2d.drawImage(jupiter, 80, 100, ballSize, ballSize, null);
			ballSize += 5;
		} else if (counter >= 1000 && counter <= 1300) {
			g2d.drawImage(saturn, 80, 100, ballSize, ballSize, null);
			ballSize += 5;
		} else if (counter >= 1100 && counter <= 1600) {
			g2d.drawImage(uranus, 80, 100, ballSize, ballSize, null);
			ballSize += 5;
		} else if (counter >= 4100 && counter <= 4500) {
			g2d.drawImage(neptune, 80, 100, ballSize, ballSize, null);
			ballSize += 5;
		} else if (counter >= 4300 && counter <= 4600) {
			g2d.drawImage(pluto, 80, 100, ballSize, ballSize, null);
			ballSize += 5;
		}
		// add text for lives, score, and level
		g2d.setColor(Color.WHITE);
		g2d.drawString("Level : " + level, 10, 690);
		g2d.drawString("Hull Life : " + lives, 110, 690);
		g2d.drawString("Million Kilometers Traveled: " + counter, 10, 630);
		g2d.setColor(Color.gray);

		g2d.drawString("Hull Life : Nickel and Iron", 300, 670);
		g2d.drawString("Nickel", 280, 690);
		g2d.drawRect(320, 680, 40, 10);
		g2d.fillRect(320, 680, 4 * ship.getNickel(), 10);
		g2d.setColor(Color.red);
		g2d.drawString("Iron", 380, 690);
		g2d.drawRect(408, 680, 40, 10);
		g2d.fillRect(408, 680, 4 * ship.getIron(), 10);
		g2d.setColor(Color.green);
		g2d.drawString("Fuel : Oxygen and Water", 530, 670);
		g2d.drawString("Oxygen", 465, 690);
		g2d.drawRect(518, 680, 40, 10);
		g2d.fillRect(518, 680, 4 * ship.getOxygen(), 10);
		g2d.setColor(Color.blue);
		g2d.drawString("Water", 580, 690);
		g2d.drawRect(620, 680, 40, 10);
		g2d.fillRect(620, 680, 4 * ship.getWater(), 10);

		g2d.setColor(Color.magenta);

		g2d.drawString("Hull Life: Titanium", 700, 670);
		g2d.drawString("Titanium", 680, 690);
		g2d.drawRect(740, 680, 40, 10);
		g2d.fillRect(740, 680, 4 * ship.getTitanium(), 10);
		// draws bars for each of the minerals

		for (Asteroid a : asteroids) { // draw asteroids
			a.draw(g2d);
		}

		for (Laser l : lasers) { // draw lasers
			l.draw(g2d);
		}

		for (AsteroidExplosion e : explodingLines) {
			e.draw(g2d);
		}

		for (ShipExplosion ex : shipExplosion)
			ex.draw(g2d);

		ship.draw(g2d, shipCollision); // draw ship
		if (shipCollision) {

			shipExplosion.add(new ShipExplosion(ship.getX(), ship.getY(), 10,
					10));
			ship.setX(width / 2);
			ship.setY(height / 2);
		}

		if (shipObtainMinerals) {
			shipObtainMinerals = false;

		}
		if (gameOver == true) {

			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, 0, 1000, 1000);
			g2d.setColor(Color.WHITE);
			g2d.drawString(
					"Game Over... Would you like to continue? Press space if so. Also watch out for asteroids as soon as you spawn",
					100, 500);

		}
		gfx.drawImage(img, 0, 0, this); // draw the off-screen image
										// (double-buffering) onto the applet

	}

	public void update(Graphics gfx) {
		paint(gfx); // gets rid of white flickering
	}

	public void run() {
		for (;;) {
			startTime = System.currentTimeMillis(); // timestamp
			ship.move(width, height); // ship movement
			for (Asteroid a : asteroids) { // asteroid movement
				a.move(width, height);
			}
			for (Laser l : lasers) { // laser movement
				l.move(width, height);
			}
			for (int i = 0; i < lasers.size(); i++) { // laser removal
				if (!lasers.get(i).getActive())
					lasers.remove(i);
			}
			for (AsteroidExplosion e : explodingLines) { // asteroid explosion
															// floating lines
															// movement
				e.move();
			}
			for (int i = 0; i < explodingLines.size(); i++) { // asteroid
																// explosion
																// floating
																// lines removal
				if (explodingLines.get(i).getLifeLeft() <= 0)
					explodingLines.remove(i);
			}
			for (ShipExplosion ex : shipExplosion) { // ship explosion expansion
				ex.expand();
			}
			for (int i = 0; i < shipExplosion.size(); i++) {
				if (shipExplosion.get(i).getLifeLeft() <= 0)
					shipExplosion.remove(i);
			}
			rateOfFireRemaining--;
			collisionCheck();
			if (asteroids.size() == 0) {
				addAsteroids();
				level = numOfAsteroids;
			}
			repaint();

			try {
				endTime = System.currentTimeMillis(); // new timestamp
				if (framePeriod - (endTime - startTime) > 0) // if there is time
																// left over
																// after
																// repaint, then
																// sleep
					Thread.sleep(framePeriod - (endTime - startTime)); // for
																		// whatever
																		// is
																		// remaining
																		// in
																		// framePeriod
			} catch (InterruptedException e) {
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		// fires laser
		if (key == KeyEvent.VK_SPACE) {
			if (rateOfFireRemaining <= 0) {
				lasers.add(ship.fire());
				rateOfFireRemaining = rateOfFire;
			}
		}
		if (key == KeyEvent.VK_UP)
			ship.setAccelerating(true);
		if (key == KeyEvent.VK_RIGHT)
			ship.setTurningRight(true);
		if (key == KeyEvent.VK_LEFT)
			ship.setTurningLeft(true);
		if (key == KeyEvent.VK_DOWN)
			ship.setDecelerating(true);
		if (key == KeyEvent.VK_SPACE) {
			if (gameOver) {
				init();
				gameOver = false;
			}

		}
	}

	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP)
			ship.setAccelerating(false);
		if (key == KeyEvent.VK_RIGHT)
			ship.setTurningRight(false);
		if (key == KeyEvent.VK_LEFT)
			ship.setTurningLeft(false);
		if (key == KeyEvent.VK_DOWN)
			ship.setDecelerating(false);
	}

	public void keyTyped(KeyEvent e) {

	}

	// add Minerals
	public void addMinerals() {

	}

	public void addAsteroids() {
		int numAsteroidsLeft = numOfAsteroids;
		int size;

		for (int i = 0; i < numOfAsteroids; i++) { // add asteroids to game
			// randomize starting position
			int asteroidX = (int) (Math.random() * width) + 1;
			int asteroidY = (int) (Math.random() * height) + 1;

			// randomize speed and direction
			double xVelocity = Math.random() + 1; // horizontal velocity
			double yVelocity = Math.random() + 1; // vertical velocity
			// used starting direction
			int xDirection = (int) (Math.random() * 2);
			int yDirection = (int) (Math.random() * 2);
			// randomize horizontal direction
			if (xDirection == 1)
				xVelocity *= (-1);
			// randomize vertical direction
			if (yDirection == 1)
				yVelocity *= (-1);

			// if there are more then four asteroids, any new ones are MEGA
			// asteroids
			if (numAsteroidsLeft > 4) {
				size = 2;
			} else {
				size = 1;
			}

			asteroids.add(new Asteroid(size, asteroidX, asteroidY, 0, .1,
					xVelocity, yVelocity));
			minerals.add(new Minerals(5, asteroidX, asteroidY, 0, .1,
					xVelocity, yVelocity));
			numAsteroidsLeft--;

			// Make sure that no asteroids can appear right on top of the ship
			// get center of recently created asteroid and ship and check the
			// distance between them
			Point2D asteroidCenter = asteroids.get(i).getCenter();
			Point2D shipCenter = ship.getCenter();
			double distanceBetween = asteroidCenter.distance(shipCenter);

			// if asteroid center is within 80 pixels of ship's center, remove
			// it from the ArrayList and rebuild it
			if (distanceBetween <= 80) {
				asteroids.remove(i);
				i--;
				numAsteroidsLeft++;
			}

		}
	}

	public void collisionCheck() {
		// cycle through active asteroids checking for collisions
		for (int i = 0; i < asteroids.size(); i++) {
			Asteroid a = asteroids.get(i);
			Point2D aCenter = a.getCenter();

			// check for collisions between lasers and asteroids
			for (int j = 0; j < lasers.size(); j++) {
				Laser l = lasers.get(j);
				Point2D lCenter = l.getCenter();

				double distanceBetween = aCenter.distance(lCenter);
				if (distanceBetween <= (a.getRadius() + l.getRadius())) {

					// split larger asteroids into smaller ones, remove smaller
					// asteroids from screen
					if (a.getRadius() >= 60) {
						for (int k = 0; k < 3; k++)
							explodingLines.add(a.explode());
						split(i);

					} else if (a.getRadius() >= 30) {
						for (int k = 0; k < 3; k++)
							explodingLines.add(a.explode());
						split(i);
						score += 100;
					} else {
						for (int k = 0; k < 3; k++)
							explodingLines.add(a.explode());
						asteroids.remove(i);
						score += 50;
					}

					lasers.remove(j); // remove laser from screen
				}
			}

			// check for collisions between ship and asteroid
			Point2D sCenter = ship.getCenter();
			double distanceBetween = aCenter.distance(sCenter);
			if (distanceBetween <= (a.getRadius() + ship.getRadius())) {
				shipCollision = true;
				shipExplode = true;
			}

		}

		// added
		for (int k = 0; k < explodingLines.size(); k++) {

			AsteroidExplosion e = explodingLines.get(k);
			Point2D eCenter = e.getCenter();

			Point2D sCenter = ship.getCenter();
			double distanceBetween = eCenter.distance(sCenter);
			if (distanceBetween <= (ship.getRadius() + ship.getRadius())) {
				shipObtainMinerals = true;
				if (explodingLines.get(k).getMineralType() == 1) {
					ship.increaseNickel();
				}
				if (explodingLines.get(k).getMineralType() == 0) {
					ship.increaseIron();
				}
				if (explodingLines.get(k).getMineralType() == 2) {
					ship.increaseOxygen();
				}
				if (explodingLines.get(k).getMineralType() == 3) {
					ship.increaseWater();
				}
				if (explodingLines.get(k).getMineralType() == 4) {
					ship.increaseTitanium();
				}

				mineralType.add(k);
				explodingLines.remove(k);
				ship.setSpaceship();
			}
		}

	}

	public void split(int i) {
		Asteroid a = asteroids.get(i);
		double bigAsteroidX = a.getX();
		double bigAsteroidY = a.getY();
		int size = (a.getSize() / 2);
		asteroids.remove(i);
		for (int j = 0; j < 2; j++) {
			// randomize speed and direction
			double xVelocity = Math.random() + 1; // horizontal velocity
			double yVelocity = Math.random() + 1; // vertical velocity
			// used randomize starting direction
			int xDirection = (int) (Math.random() * 2);
			int yDirection = (int) (Math.random() * 2);
			// randomize horizontal direction
			if (xDirection == 1)
				xVelocity *= (-1);
			// randomize vertical direction
			if (yDirection == 1)
				yVelocity *= (-1);
			asteroids.add(new Asteroid(size, bigAsteroidX, bigAsteroidY, 0, .1,
					xVelocity, yVelocity));
		}

	}

}
