package main;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

/**
 * GamePlatform.java<br>
 * This class extends the sprite class, and is used to create platform objects for the game.
 * This class takes care of the platforms in the game, making sure they are always moving to the left,
 * as well as returning the top edge of the object to be used for walking and jumping in the game.<br><br>
 * Jan 21, 2020
 * @author leonard
 *
 */
public class GamePlatform extends Sprite{
	private double speed = 7;
	int top;

	/**
	 * Calls the Superclass constructor and creates an AnimationTimer for the platform object.
	 * AnimationTimer is used to constantly call the moveLeft() method.
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
	 * Sets a color as the initial fill of the platform object.
	 */
	public GamePlatform(int x, int y, int w, int h, String type, Color color) {
		super(x, y, w, h, type, color);

		AnimationTimer timer = new AnimationTimer() {

			public void handle(long now) {
				moveLeft();
			}
		};

		timer.start();
	}

	/**
	 * This method returns the y value of the Top edge of the platform object.
	 * 
	 * @return
	 * Returns the top edge of the platform object by taking the initial y position of the object
	 */
	public int getTop(){
		return (int)getY();

	}
	/**
	 * This method simulates movement of the platform object across the screen and is always active.
	 * This method takes the x value of the platform object, and subtracts a set value every time the
	 * method is called, making the object move towards the left side. This method makes it so 
	 * after the platform object is spawned at the right edge of the screen, the platform object will 
	 * constantly move left. 
	 */
	private void moveLeft() {
		setX(getX() - 6);
	}

}