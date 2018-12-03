package SnakeVsBlocks;

import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MagnetToken extends Token implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	transient Image picture = new Image("/SnakeVsBlocks/Assets/shield.gif");
	
	public MagnetToken() {
		super();
		obj = new ImageView();
		obj.setImage(picture);
		obj.setFitHeight(tokenSize);
		obj.setFitWidth(tokenSize);
		obj.setX(positionX);
		obj.setY(positionY);
	}
	
	public void deserialize() {
		obj = new ImageView();
		picture = new Image("/SnakeVsBlocks/Assets/shield.gif");
		obj.setImage(picture);
		obj.setFitHeight(tokenSize);
		obj.setFitWidth(tokenSize);
		obj.setX(positionX);
		obj.setY(positionY);
	}
}
