package SnakeVsBlocks;

import java.io.Serializable;
import java.util.Random;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Token implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	double positionX;
	double positionY;
	transient StackPane obj;
	transient ImageView img;
	int tokenSize = 70;
	boolean tokenAlive = true;

	public Token() {
		Random rand = new Random();
		obj = new StackPane();
		int nnn = rand.nextInt(6) + 0;
		positionX = nnn * 100 + 3 + (100 - tokenSize) / 2;
		positionY = -97 + (100 - tokenSize) / 2;
		tokenAlive = true;
	}

	public boolean checkAlive() {
		if (positionY > 903) {
			tokenAlive = false;
		} else {
			tokenAlive = true;
		}
		return tokenAlive;
	}

	public void setY(double y) {
		positionY = y;
		obj.setTranslateY(positionY);
	}

	public void moveForward(double offset) {
		setY(positionY + offset);
	}
}