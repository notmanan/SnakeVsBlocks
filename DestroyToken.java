package SnakeVsBlocks;

import javafx.scene.image.Image;

public class DestroyToken extends Token{
	private int frequency = 1;
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
