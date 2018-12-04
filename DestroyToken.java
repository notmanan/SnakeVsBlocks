package SnakeVsBlocks;

import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class DestroyToken extends Token implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String pictureLocation = "/SnakeVsBlocks/Assets/burst.gif";
	transient Image picture = new Image(pictureLocation);	
//	int value;
	
	public DestroyToken() {
		super();
//		value = (int)Math.random() * 5  + 1;
		img = new ImageView();
		img.setImage(picture);
		img.setFitHeight(tokenSize);
		img.setFitWidth(tokenSize);
		obj.getChildren().add(img);
		obj.setTranslateX(positionX);
		obj.setTranslateY(positionY);
	}
	
	public void deserialize() {
		img = new ImageView();
		picture = new Image(pictureLocation);
		img.setImage(picture);
		img.setFitHeight(tokenSize);
		img.setFitWidth(tokenSize);
		obj = new StackPane();
		obj.getChildren().add(img);
		obj.setTranslateX(positionX);
		obj.setTranslateY(positionY);
	}
}
