package SnakeVsBlocks;

import java.io.Serializable;
import java.util.Random;

import javafx.scene.image.ImageView;

public class Token implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double positionX; 
	double positionY;
	boolean tokenAlive;
	transient ImageView obj;
	int tokenSize = 70;

	public Token(){
		Random rand = new Random();
		int nnn = rand.nextInt(6) + 0;
		positionX = nnn * 100 + 3 + (100-tokenSize)/2;
		positionY = -97+ (100-tokenSize)/2;
		tokenAlive = true;
	}
	
	public void setY(double y) {
		positionY = y;
		obj.setY(positionY);
	}
	
	public void moveForward(double offset) {
		setY(positionY + offset);
	}
}