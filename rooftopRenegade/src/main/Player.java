package main;

import javafx.scene.paint.Color;

public class Player extends Sprite{


	private boolean dead = false;

	Player(int x, int y, int w, int h, String type, Color color) {
		super(x, y, w, h, type, color);

	}

	void jump() {
		setTranslateY(getTranslateY() - 5);


	}

	void tempGravity() {
		setTranslateY(getTranslateY() + 5);
	}


}
