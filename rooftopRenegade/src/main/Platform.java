package main;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class Platform extends Sprite{

	double xSpeed;
	int top;
	boolean moving;

	Platform(int x, int y, int w, int h, int top, String type, Color color) {
		super(x, y, w, h, type, color);

		this.top = top;
		this.xSpeed = 1;
		
		AnimationTimer timer = new AnimationTimer() {

			public void handle(long now) {
				moveLeft();
			}
		};

		timer.start();
	}

	public int getTop(){
		return this.top;

	}

	void moveLeft() {
		setTranslateX(getTranslateX() - 1.5);
	}

}
