package main;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class Player extends Sprite{


	private boolean dead = false;

	Player(int x, int y, int w, int h, String type, Color color) {
		super(x, y, w, h, type, color);
			
		AnimationTimer timer = new AnimationTimer() {

			public void handle(long now) {
			}
		};

		timer.start();
	}

	void jump(int jump) {
		setTranslateY(getTranslateY() - jump);


	}

	void gravity() {
		setTranslateY(getTranslateY() + 10);
	}
	void antiGravity() {
		setTranslateY(getTranslateY() - 10);
	}


}

