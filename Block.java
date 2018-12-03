package SnakeVsBlocks;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.Serializable;
import java.util.Random;

//Rectangle block = new Rectangle( i*100 + 3, yAxis, height, width);block.setArcHeight(15.0d); block.setArcWidth(15.0d);

public class Block implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int BlockId;
	double positionX;
	double positionY;
	double size = 97;
	transient Color blockColor;
	int blockVal;
	Boolean blockAlive = true;
	Random rand = new Random();
	transient StackPane bt;
	transient Rectangle b;
	transient Text t;

	public Block(int n) {
		// update 50 to accomodate for max size of snake
		blockVal = rand.nextInt(50) + 1;
		t = new Text("" + (blockVal));
		t.setFill(Color.WHITE);
		positionX = n * 100 + 3;
		positionY = -97;
		b = new Rectangle(positionX, positionY, size, size);
		b.setArcHeight(15.0d);
		b.setArcWidth(15.0d);
		b.setFill(findColor(blockVal));
		bt = new StackPane();
		bt.setTranslateX(positionX);
		bt.setTranslateY(positionY);
		bt.getChildren().addAll(b, t);
	}

	public Color findColor(int i) {
		double r = 1 - 0.02 * i;
		Color cc = new Color(r, 0.35, 0.77 - 0.001 * i, 1);
		return cc;
	}

	public boolean checkAlive() {
		if (positionY > 903) {
			blockAlive = false;
		} else {
			blockAlive = true;
		}
		return blockAlive;
	}

	public void setPositionX(double xxx) {
		positionX = xxx;
		bt.setTranslateX(this.positionX);
	}

	public void setPositionY(double yyy) {
		positionY = yyy;
		bt.setTranslateY(positionY);
	}

	public void setBlockColor(Color c) {
		blockColor = c;
		b.setFill(c);
	}

	public void deserialize() {
		t = new Text("" + (blockVal));
		System.out.println(t.getText());
		t.setFill(Color.WHITE);
		System.out.println(t.getFill());
		b = new Rectangle(positionX, positionY, 97, 97);
		b.setArcHeight(15.0d);
		b.setArcWidth(15.0d);
		b.setFill(findColor(blockVal));
		bt = new StackPane();
		bt.setTranslateX(positionX);
		bt.setTranslateY(positionY);
		bt.getChildren().addAll(b, t);
	}

	public void moveForward(double gameSpeed) {
		setPositionY(gameSpeed + positionY);
	}
}