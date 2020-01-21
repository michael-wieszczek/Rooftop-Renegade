package main;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.ImagePattern;

/**
 * Coins.java
 * This class extends the sprite class, and is used to create coin objects for the game.
 * This class makes sure that all coin objects are constantly moving from the right side of the screen
 * to the left.<br><br>
 * Jan 21, 2020
 * @author leonard
 *
 */
public class Coins extends Sprite{

	/**
	 * Calls the Superclass constructor and creates an AnimationTimer for the Coin object.
	 * AnimationTimer ensures that the moveLeft() method is constantly active.
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
	 * Sets an imagePattern as the fill for the player object shape.
	 */
	Coins(int x, int y, int w, int h, String type, ImagePattern imagePattern) {
		super(x, y, w, h, type, imagePattern);

		AnimationTimer timer = new AnimationTimer() {

			public void handle(long now) {
				moveLeft();

			}
		};

		timer.start();
	}

	/**
	 * This method simulates movement of the coin object across the screen and is always active.
	 * This method takes the x value of the coin object, and subtracts a set value every time the
	 * method is called, making the object move towards the left side. This method makes it so 
	 * after the coin object is spawned at the right edge of the screen, the coin object will 
	 * constantly move left. 
	 */
	void moveLeft() {
		setX(getX() - 6);

	}

}
