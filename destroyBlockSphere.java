package SnakeVsBlocks;

import java.io.Serializable;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class destroyBlockSphere implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double positionX;
	double positionY;
	double radius;
	double increase;
	int frames;
	double currentTransparency;

	transient Circle sphere;

	boolean alive;

	public destroyBlockSphere(DestroyToken D) {
		positionX = D.positionX + D.tokenSize / 2;
		positionY = D.positionY + D.tokenSize / 2;
		frames = 30;
		radius = 5;
		increase = 20;
		alive = true;
		sphere = new Circle(positionX, positionY, radius);
		currentTransparency = 0.2;
		sphere.setOpacity(currentTransparency);
		sphere.setFill(Color.WHITE);
	}

	public void animate(double offset) {
		if (frames > 0) {
			frames--;
			radius += increase;
			sphere.setRadius(radius);
			double amt = 0.2 / 30;
			currentTransparency = sphere.getOpacity() - amt;
			sphere.setOpacity(currentTransparency);
		} else {
			alive = false;
		}
		moveForward(offset);
	}

	public void setY(double y) {
		positionY = y;
		sphere.setCenterY(positionY);
	}

	public void moveForward(double offset) {
		setY(positionY + offset);
	}

	public void deserialize() {
		sphere = new Circle(positionX, positionY, radius);
		sphere.setOpacity(currentTransparency);
		sphere.setFill(Color.WHITE);
	}
}
