package main;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class Player extends Sprite{


	private boolean dead = false;
	

	public Player(int x, int y, int w, int h, String type, Color color) {
		super(x, y, w, h, type, color);
			
		AnimationTimer timer = new AnimationTimer() {

			public void handle(long now) {
			}
		};

		timer.start();
	}

	public void jump(int jump) {
		setTranslateY(getTranslateY() - jump);

	}
	public int getBottom(){
		return (int)(this.getX()+ this.getHeight());
	}
	public void gravity() {
		setTranslateY(getTranslateY() + 10);
	}


}

