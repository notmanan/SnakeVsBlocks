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

	public DestroyToken(GameState gs) {
		super(gs);
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
	
	public void activateToken(Snake s) {
		System.out.println("destroy token activation");
		tokenAlive = false;
		gs.dbs = new destroyBlockSphere(this);
	}
}
