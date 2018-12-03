package SnakeVsBlocks;

import java.io.Serializable;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//Rectangle wall = new Rectangle(i*100 + 100,yAxis+100,3,(i+1)*100);wall.setFill(Color.BLUE);wall.setArcHeight(15.0d);wall.setArcWidth(15.0d);

public class Wall implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double positionX;
	double positionY;
	double wallSize;
	boolean wallAlive;
	transient Rectangle wall;
	transient Color wallColor = Color.RED;

	public Wall() {
		Random rand = new Random();
		wallAlive = true;
		int i = rand.nextInt(5);
		System.out.println("wall position : " + i);
		int size = rand.nextInt(3) + 1;

		double yAxis = -300; // TODO remove later, temporary to see where the wall lands

		wall = new Rectangle(i * 100 + 100, yAxis, 3, size * 100);

		wallColor = Color.RED;

		wall.setFill(wallColor);
		wall.setArcHeight(15.0d);
		wall.setArcWidth(15.0d);

		positionX = wall.getX();
		positionY = wall.getY();
		wallSize = wall.getHeight();
	}

	public boolean checkAlive() {
		if (positionY > 903) {
			wallAlive = false;
		} else {
			wallAlive = true;
		}
		return wallAlive;
	}

	public double getPositionY() {
		return positionY;
	}

	public void setPositionY(double positionY) {
		this.positionY = positionY;
		wall.setY(positionY);
	}

	public double getPositionX() {
		return positionX;
	}

	public void setPositionX(double positionX) {
		this.positionX = positionX;
		wall.setX(positionX);
	}

	public void moveForward(double gameScreenSpeed) {
		setPositionY(gameScreenSpeed + getPositionY());
	}

	public void deserialize() {
		wall = new Rectangle(getPositionX(), getPositionY(), 3, wallSize);
		wallColor = Color.RED;
		wall.setFill(wallColor);
		wall.setArcHeight(15.0d);
		wall.setArcWidth(15.0d);

	}
}
