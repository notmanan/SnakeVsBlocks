 package SnakeVsBlocks;

import java.io.Serializable;

import javafx.scene.image.Image;

//import java.awt.Image;
 

public class BallToken extends Token implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int frequency = 4;
	transient Image picture;
	
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
