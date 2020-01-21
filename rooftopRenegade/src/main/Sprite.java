package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle{

	private final String type;
	double xSpeed;


	Sprite(int x, int y, int w, int h, String type, ImagePattern imagePattern) {
		super(w, h, imagePattern);
		this.type = type;
		setX(x);
		setY(y);
	}

	Sprite(int x, int y, int w, int h, String type, Color color) {

		super(w, h, color);
		this.type = type;
		setX(x);
		setY(y);

	}

}

