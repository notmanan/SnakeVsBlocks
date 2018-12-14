package SnakeVsBlocks;

import java.io.Serializable;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class magnetSphere implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double positionX;
	double positionY;
	double radius;
	
	transient Circle sphere;
	
	Snake tempSnake;
	boolean alive;
	int magnetTime;

	public magnetSphere(Snake s) {
		magnetTime = 600;
		tempSnake = s;
		positionX = tempSnake.getPositionX();
		positionY = tempSnake.getPositionY();
		radius = 300;
		alive = true;
		sphere = new Circle(positionX, positionY, radius);
		sphere.setOpacity(0.2);
		sphere.setFill(Color.BLUE);
	}

	public void animate() {
		magnetTime--;
		if(magnetTime < 0) {
			alive = false;
		}
		setX(tempSnake.getPositionX());
		setY(tempSnake.getPositionY());
	}

	public void setY(double y) {
		positionY = y;
		sphere.setCenterY(positionY);
	}
	
	public void setX(double x) {
		positionX = x;
		sphere.setCenterX(positionX);
	}
	
	public void deserialize() {
//		TODO
		sphere = new Circle(positionX, positionY, radius);
		sphere.setOpacity(0.2);
		sphere.setFill(Color.BLUE);
	}
}
