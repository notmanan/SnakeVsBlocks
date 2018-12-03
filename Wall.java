package SnakeVsBlocks;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//Rectangle wall = new Rectangle(i*100 + 100,yAxis+100,3,(i+1)*100);wall.setFill(Color.BLUE);wall.setArcHeight(15.0d);wall.setArcWidth(15.0d);

public class Wall {
	private int positionX; 
	private int positionY;
	public int getPositionY() {
		return positionY;
	}
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}
	public int getPositionX() {
		return positionX;
	}
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	} 
	public void display() {
		
	}
	
	public void collision(Object o) {
		
	}
}
