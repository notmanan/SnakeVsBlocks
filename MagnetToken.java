package SnakeVsBlocks;

import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class MagnetToken extends Token implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String pictureLoc = "/SnakeVsBlocks/Assets/magnet.gif";
	transient Image picture = new Image(pictureLoc);
	
	public MagnetToken() {
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
		picture = new Image(pictureLoc);
		img.setImage(picture);
		img.setFitHeight(tokenSize);
		img.setFitWidth(tokenSize);
		obj = new StackPane();
		obj.getChildren().add(img);
		obj.setTranslateX(positionX);
		obj.setTranslateY(positionY);
	}
}
