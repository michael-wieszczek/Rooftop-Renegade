package main;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class Coins extends Sprite{

	Coins(int x, int y, int w, int h, String type, Color color) {
		super(x, y, w, h, type, color);
		
		AnimationTimer timer = new AnimationTimer() {

			public void handle(long now) {
				moveLeft();
				//When platform goes off screen it would despawn
//				if(getTranslateX() - Platform.this.getWidth() == 0 - Platform.this.getWidth()) {
//					System.out.println("yeet");
//				}
			}
		};

		timer.start();
	}

	void moveLeft() {
		setTranslateX(getTranslateX() - 7);
	}

}
