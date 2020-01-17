package main;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Platform extends Sprite{
	private double speed = 7;
	int top;

	public Platform(int x, int y, int w, int h, String type, Color color) {
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
				moveLeft();
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

