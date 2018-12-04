 package SnakeVsBlocks;

import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class BallToken extends Token implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String pictureLoc = "/SnakeVsBlocks/Assets/ball.gif";
	transient Image picture = new Image(pictureLoc);	
	int value;
	transient StackPane bt;
	transient Text t;
	
	public BallToken() {
		super();
		
		value = (int)(Math.random() * 5 )+ 1;
		t = new Text("" + (value));
		t.setFill(Color.WHITE);
		
		img = new ImageView();
		img.setImage(picture);
		img.setFitHeight(tokenSize);
		img.setFitWidth(tokenSize);
		
		obj.getChildren().addAll(img, t);
		obj.setTranslateX(positionX);
		obj.setTranslateY(positionY);
	}
	
	
	public void deserialize() {
		t = new Text("" + (value));
		t.setFill(Color.WHITE);
		
		img = new ImageView();
		picture = new Image(pictureLoc);
		img.setImage(picture);
		img.setFitHeight(tokenSize);
		img.setFitWidth(tokenSize);
		
		obj = new StackPane();
		obj.getChildren().addAll(img,t);
		obj.setTranslateX(positionX);
		obj.setTranslateY(positionY);
	}
	
	public void activateToken(Snake s) {
		System.out.println("ball token activation");
		tokenAlive = false;
		s.increaseLength(value);
	}
}



