package main;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.ImagePattern;

/**
 * Player.java
 * This class extends the sprite class, and is used to create a player object for the game.
 * This class takes care of player jumping as well as gravity and antigravity.
 * Jan 21, 2020
 * @author leonard
 *
 */
public class Player extends Sprite{


	private boolean dead = false;


	/**
	 * Calls the Superclass constructor and creates an AnimationTimer for the player object.
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
	Player(int x, int y, int w, int h, String type, ImagePattern imagePattern) {
		super(x, y, w, h, type, imagePattern);


		AnimationTimer timer = new AnimationTimer() {

			public void handle(long now) {
			}
		};

		timer.start();
	}

	/**
	 * This method simulates jumping for the player object by taking current y position 
	 * and moving it up by a certain value.
	 * 
	 * @param jump
	 * jump value is used to determine how far the y position will be moved up when jump is called.
	 * The jump value is subtracted from the y position to move the player object up by amount of
	 * the jump value.
	 */
	public void jump(int jump) {
		setY(getY() - jump);
	}
	/**
	 * This method returns the int y value of the bottom edge of the player object.
	 * 
	 * @return
	 * Returns the Bottom boundary of the player object by taking the y position of the object 
	 * and adding the height of the object to get the bottom edge.
	 */
	public int getBottom(){
		return (int)(this.getY()+ this.getHeight());
	}
	/**
	 * This method is used to simulate gravity for the player class. This method is always active
	 * and it constantly adds 10 to the y position making the player object move down or "fall."
	 * This method is needed to insure player object does not "float" during the game and that
	 * the player object falls whenever it is in the air.
	 */
	public void gravity() {
		setY(getY() + 10);
	}
	/**
	 * This method is used to counteract the constant gravity on the player object. This method is 
	 * only active when a player is in contact with a platform. This subtracts 10 form the y position
	 * constantly while active. This method keeps player object from falling through platforms due to
	 * the constant gravity.
	 */
	public void antiGravity() {
		setY(getY() - 10);
	}


}

