package main;

import javafx.scene.paint.Color;

public class Platform extends Sprite{

	double xSpeed;
	int top;
	
	Platform(int x, int y, int w, int h, int top, String type, Color color) {
		super(x, y, w, h, type, color);
		
		this.top = top;
		this.xSpeed = 1;

	}
	
	public int getTop(){
		return this.top;
		
	}
	
	void moveLeft() {
		setTranslateX(getTranslateX() - 1);
	}

	
}
