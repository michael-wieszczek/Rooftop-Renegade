package main;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle{


	private final String type;
	private int x, y, w, h;
	private Color color;
		
		Sprite(int x, int y, int w, int h, String type, Color color) {
			super(w, h, color);
			
			this.type = type;
			setTranslateX(x);
			setTranslateY(y);
			
		}
		

}
