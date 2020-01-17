package main;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Platform extends Sprite{
	private double speed = 7;
	int top;

	public Platform(int x, int y, int w, int h, int top, String type, Color color) {
		super(x, y, w, h, type, color);

		this.top = top;
		Timeline speedValue = new Timeline(
				new KeyFrame(Duration.seconds(5), e -> {
//					speed += 7;
//					System.out.println(speed); //temp
				})
		);
		speedValue.setCycleCount(Timeline.INDEFINITE);
		speedValue.play();
		
		AnimationTimer timer = new AnimationTimer() {

			public void handle(long now) {
				moveLeft(speed);
				//When platform goes off screen it would despawn
//				if(getTranslateX() - Platform.this.getWidth() == 0 - Platform.this.getWidth()) {
//					System.out.println("yeet");
//				}
			}
		};

		timer.start();
	}

	public int getTop(){
		return this.top;

	}

	void moveLeft(double speed) {
		setTranslateX(getTranslateX() - speed);
	}

}

