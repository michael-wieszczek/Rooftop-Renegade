package main;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class Platform extends Sprite{

	double xSpeed;
	int top;
	boolean moving;

	public Platform(int x, int y, int w, int h, String type, Color color) {
		super(x, y, w, h, type, color);

		this.xSpeed = 1;
		
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

	public int getTop(){
		return (int)getY();

	}


	private void moveLeft() {
		setX(getX() - 7);

	}

}

