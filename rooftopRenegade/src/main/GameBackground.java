package main;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class GameBackground extends Sprite{
	public GameBackground(int x, int y, int w, int h, String type, ImagePattern imagePattern) {
		super(x, y, w, h, type, imagePattern);

		this.xSpeed = 1;
		
		AnimationTimer timer = new AnimationTimer() {

			public void handle(long now) {
				moveLeft();

			}
		};

		timer.start();
	}

	private void moveLeft() {
		setX(getX() - 1);
	}
	

}
