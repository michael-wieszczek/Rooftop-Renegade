package main;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class Player extends Sprite{


	private boolean dead = false;
	


	Player(int x, int y, int w, int h, String type, ImageView image) {
		super(x, y, w, h, type, image);

			
		AnimationTimer timer = new AnimationTimer() {

			public void handle(long now) {
			}
		};

		timer.start();
	}

	public void jump(int jump) {
		setY(getY() - jump);

	}
	public int getBottom(){
		return (int)(this.getY()+ this.getHeight());
	}
	public void gravity() {
		setY(getY() + 10);
	}
	public void antiGravity() {
		setY(getY() - 10);
	}


}

