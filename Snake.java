package SnakeVsBlocks;

import java.util.LinkedList;

import javafx.scene.control.Label;
//import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
//import javafx.scene.text.Text;

public class Snake {
	private double positionX; // assign center screen horizontally
	private double positionY; // assign apprporiate value here
	private int snakelength = 25;
	transient private LinkedList<Circle> snakeNodes;
	private double snakeRadius = 15;
	private GameState gs;
	transient Label t;
	transient StackPane st;

	public Snake(GameState gs2) {
		st = new StackPane();
		t = new Label(""+snakelength);
		t.setTextFill(Color.BLACK);	
		//		t. change text size
		t.prefWidth(10);
		gs = gs2;
		positionX = 301;
		positionY = 400;
		setSnakeNodes(new LinkedList<>());
		for (int i = 0; i < snakelength; i++) {
			snakeNodes.add(new Circle(positionX, positionY + (i * (snakeRadius + snakeRadius)), snakeRadius));
		}
		snakeNodes.getFirst().setFill(Color.RED);
		st.getChildren().addAll(snakeNodes.getFirst() , t);	
	}
	
	public void deserialize() {
		st = new StackPane();
		t = new Label(""+snakelength);
		t.setTextFill(Color.BLACK);
		t.prefWidth(10);
		setSnakeNodes(new LinkedList<>());
		for (int i = 0; i < snakelength; i++) {
			snakeNodes.add(new Circle(positionX, positionY + (i * (snakeRadius + snakeRadius)), snakeRadius));
		}
		snakeNodes.getFirst().setFill(Color.RED);
		st.getChildren().addAll(snakeNodes.getFirst() , t);	
	}

	public double getPositionX() {
		return positionX;
	}

	public void setPositionX(double xxx) {
		this.positionX = findProposedX(xxx);
		snakeNodes.getFirst().setCenterX(this.positionX);
		t.setTranslateX(positionX-5);
		t.setTranslateY(positionY - 40);
	}
	
	public void setPositionY(double yyy) {
		this.positionY = yyy;
		snakeNodes.getFirst().setCenterX(this.positionY);
	}

	public double findProposedX(double propx) {
		// TODO if propX works:
		return propx;
		// if it doesn't work, find appropriate propX;
		// return newX
	}

	public double getPositionY() {
		return positionY;
	}


	public int getSnakeLength() throws NoLengthException {
		return snakelength;
	}

	public void setSnakeLength(int length) {
		this.snakelength = length;
		t.setText(""+length);
	}
	
	public void display() {
	}

	public LinkedList<Circle> getSnakeNodes() {
		return snakeNodes;
	}

	public void setSnakeNodes(LinkedList<Circle> snakeNodes) {
		this.snakeNodes = snakeNodes;
	}

	public Circle returnHead() throws NoLengthException {
		return snakeNodes.getFirst();
	}

	public void updateNodes() {
		for (int i = 1; i < snakeNodes.size(); i++) {
			Circle topCircle = snakeNodes.get(i - 1);
			Circle currCircle = snakeNodes.get(i);
			currCircle.setCenterY(currCircle.getCenterY() + currCircle.getRadius());
			double ax = currCircle.getCenterX();
			double ay = currCircle.getCenterY();
			double bx = topCircle.getCenterX();
			double by = topCircle.getCenterY();
			if (!(ax == bx || ay == by)) {
				double d = Math.sqrt(Math.pow(ax - bx, 2) + Math.pow(ay - by, 2));
				double dt = snakeNodes.get(i).getRadius() * 2;
				double t = dt / d;
				double xt = (1 - t) * bx + t * ax;
				double yt = (1 - t) * by + t * ay;
				currCircle.setCenterX(xt);
				currCircle.setCenterY(yt);
			} else {
				currCircle.setCenterX(topCircle.getCenterX());
				currCircle.setCenterY(topCircle.getCenterY() + currCircle.getRadius() * 2);
			}
		}
	}
}
