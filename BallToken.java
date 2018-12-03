 package SnakeVsBlocks;

import javafx.scene.image.Image;

//import java.awt.Image;
 

public class BallToken extends Token{
	private int frequency = 4;
	private Image picture;
	public int getFrequency() {
		return frequency;
	}
	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}
	public Image getPicture() {
		return picture;
	}
	public void setPicture(Image picture) {
		this.picture = picture;
	}
}
