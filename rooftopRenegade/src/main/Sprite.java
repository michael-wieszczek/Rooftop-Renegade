package main;

import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
/**
 * Sprite.java
 * This class extends the Rectangle Class. This class is used for a generic moving object. 
 * This class takes care of the x and y location (should be the top left corner of the object),
 * the fill of the object shape, as well as the Type of sprite. Although the Type of sprite is not
 * being used currently in the game, it will be useful if the game is further developed in the
 * future.<br><br>
 * 
 * Jan 21, 2020
 * 
 * @author Leonard
 * 
 */
public class Sprite extends Rectangle{

	private final String type;
	double xSpeed;

	/**
	 * Calls the Superclass constructor. The Superclass constructor sets the width, height and sets an 
	 * imagePattern as the fill of the object shape. Sets initial x, and y of the object, as well as 
	 * the type. This constructor is used for all subclasses that have an imagePattern as a fill
	 * for it's shape instead of a color. ImagePattern is used to play an animation for the object
	 * while the game is running.
	 * 
	 * @param x
	 * Initial x position
	 * @param y
	 * Initial y position
	 * @param w
	 * Initial width size
	 * @param h
	 * Initial height size
	 * @param type
	 * Initial type of sprite, used for super class. Currently not being used in program, but useful 
	 * for further development of the game.
	 * @param imagePattern
	 * Sets an imagePattern as the fill for the object shape.
	 */
	Sprite(int x, int y, int w, int h, String type, ImagePattern imagePattern) {
		super(w, h, imagePattern);
		this.type = type;
		setX(x);
		setY(y);
	}

	/**
	 * Calls the Superclass constructor. The Superclass constructor sets the width, height and the 
	 * color of the object shape. Sets initial x, and y of the object, as well as the type. This 
	 * constructor is used for all subclasses that only have a color fill for its shape, instead
	 * of an imagePattern.
	 * 
	 * @param x
	 * Initial x position
	 * @param y
	 * Initial y position
	 * @param w
	 * Initial width size
	 * @param h
	 * Initial height size
	 * @param type
	 * Initial type of sprite, used for super class. Currently not being used in program, but useful 
	 * for further development of the game.
	 * @param color
	 * Sets a color as the initial fill of the object.
	 */
	Sprite(int x, int y, int w, int h, String type, Color color) {

		super(w, h, color);
		this.type = type;
		setX(x);
		setY(y);

	}

}

