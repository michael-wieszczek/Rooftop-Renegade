package main;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle{


	private ImageView image;
	private final String type;
//	private Color color;

	Sprite(int x, int y, int w, int h, String type, ImageView image) {
		super(w, h);

		this.type = type;
		setX(x);
		setY(y);
		
		this.image = image;
	}
	
	Sprite(int x, int y, int w, int h, String type, Color color) {
		super(w, h, color);

		this.type = type;
		setTranslateX(x);
		setTranslateY(y);

	}

}

