package main;

import javafx.animation.AnimationTimer;
import javafx.scene.paint.ImagePattern;

/**
 * GameBackground.java<br>
 * This class extends the Sprite class, and is used for creating a background for the game. While
 * the game is running, the background will constantly be moving across the screen to simulate, a
 * city that is moving behind the player object as it moves.<br><br>
 * Jan 21, 2020
 * @author leonard
 *
 */
public class GameBackground extends Sprite{
	
	/**
	 * Calls the Superclass constructor and creates an AnimationTimer for the GameBackground object.
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
	 * Sets an imagePattern as the fill for the GameBackground object shape.
	 */
	public GameBackground(int x, int y, int w, int h, String type, ImagePattern imagePattern) {
		super(x, y, w, h, type, imagePattern);


		AnimationTimer timer = new AnimationTimer() {

			public void handle(long now) {
				moveLeft();

			}
		};

		timer.start();
	}

	/**
	 * This method simulates movement of the GameBackground while the the game is running. This method is
	 * constantly active and it makes the background of the game move to the left while the game is playing.
	 * This method takes the x position and subtracts a constant amount of pixels every time moveLeft()
	 * is called, so that the object moves left every time. This object is used as a background for the 
	 * game, and it cannot be interacted with.
	 */
	private void moveLeft() {
		setX(getX() - 1);
	}


}
