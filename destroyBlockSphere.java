package SnakeVsBlocks;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class destroyBlockSphere {
	double positionX;
	double positionY;
	double radius;
	double increase;
	int frames;
	transient Circle sphere;
	boolean alive;

	public destroyBlockSphere(DestroyToken D) {
		positionX = D.positionX + D.tokenSize/2;
		positionY = D.positionY+ D.tokenSize/2;
		frames = 30;
		radius = 5;
		increase = 20;
		alive = true;
		sphere = new Circle(positionX, positionY, radius);
		sphere.setOpacity(0.2);
		sphere.setFill(Color.WHITE);
	}

	public void animate(double offset) {
		if (frames > 0) {
			frames--;
			radius += increase;
			sphere.setRadius(radius);
			double amt = 0.2/30;
			sphere.setOpacity(sphere.getOpacity() - amt);
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
}
