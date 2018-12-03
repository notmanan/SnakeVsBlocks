 package SnakeVsBlocks;

import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//import java.awt.Image;
 

public class BallToken extends Token implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	transient Image picture = new Image("/SnakeVsBlocks/Assets/ball.gif");	
	int value;
	
	public BallToken() {
		super();
		value = (int)Math.random() * 5  + 1;
		obj = new ImageView();
		obj.setImage(picture);
		obj.setFitHeight(tokenSize);
		obj.setFitWidth(tokenSize);
		obj.setX(positionX);
		obj.setY(positionY);
	}
	
	public void deserialize() {
		obj = new ImageView();
		picture = new Image("/SnakeVsBlocks/Assets/ball.gif");
		obj.setImage(picture);
		obj.setFitHeight(tokenSize);
		obj.setFitWidth(tokenSize);
		obj.setX(positionX);
		obj.setY(positionY);
	}
}
