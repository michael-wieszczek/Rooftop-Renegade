package main;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

public class Coins extends Sprite{

	public Coins(int x, int y, int w, int h, String type, ImagePattern imagePattern) {
		super(x, y, w, h, type, imagePattern);
		
		AnimationTimer timer = new AnimationTimer() {

			public void handle(long now) {
				moveLeft();

			}
		};

		timer.start();
	}

	void moveLeft() {
		setX(getX() - 6);

	}

}
