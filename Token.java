package SnakeVsBlocks;

import java.io.Serializable;
import java.util.Random;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Token implements Serializable {
	/**
	 * 
	 */
	GameState gs;
	private static final long serialVersionUID = 1L;
	double positionX;
	double positionY;
	transient StackPane obj;
	transient ImageView img;
	int tokenSize = 70;
	boolean tokenAlive = true;

	public Token(GameState gs) {
		this.gs = gs;
		Random rand = new Random();
		obj = new StackPane();
		int nnn = rand.nextInt(6) + 0;
		positionX = nnn * 100 + 3 + (100 - tokenSize) / 2;
		positionY = -97 + (100 - tokenSize) / 2;
		tokenAlive = true;
	}

	public boolean checkAlive() {
		if(tokenAlive == false) {
			return false;
		}
		
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
	
	public void setX(double x) {
		positionX = x;
		obj.setTranslateX(positionX);
	}

	public void moveForward(double offset) {
		setY(positionY + offset);
	}
	
	public void activateToken(Snake s) {
		System.out.println("default token activation");
	}

	public void attractToken(magnetSphere ms) {
		// TODO Auto-generated method stub
		double x1 = ms.positionX;
		double y1 = ms.positionY;
		double x2 = positionX;
		double y2 = positionY;
		double dist = Math.sqrt(Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2));
		double attraction = 1000 / Math.pow(dist,2);
		double x = x2 + (x1-x2)*attraction;
		double y = y2 + (y1-y2)*attraction;
		setX(x);
		setY(y);
	}
}