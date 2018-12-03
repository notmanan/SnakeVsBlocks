package SnakeVsBlocks;

import java.io.Serializable;
import java.util.Random;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//Rectangle wall = new Rectangle(i*100 + 100,yAxis+100,3,(i+1)*100);wall.setFill(Color.BLUE);wall.setArcHeight(15.0d);wall.setArcWidth(15.0d);

public class Wall implements Serializable{
	double positionX; 
	double positionY;
	transient Rectangle wall;
	Color wallColor;
	
	public Wall() {
		Random rand = new Random();
		int i = rand.nextInt(5);
		double yAxis = 200; // TODO remove later, temporary to see where the wall lands 
		wall = new Rectangle(i*100 + 100,yAxis+100,3,(i+1)*100);wall.setFill(Color.BLUE);wall.setArcHeight(15.0d);wall.setArcWidth(15.0d);		
		wallColor = (Color) wall.getFill();
		positionX = wall.getX();
		positionY = wall.getY();
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
}
