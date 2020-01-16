package main;
/**
 * 
 */

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


/**
 * @author mimlo
 *
 */
public class MainApplication extends Application {

	private Pane root = new Pane();

	//Making the Player
	private Player player = new Player(300, 280, 40, 60, "player", Color.BLUEVIOLET);

	//Starting Platforms
	Platform s = new Platform(0, 340, 800, 5, 340, "platform", Color.BLACK);
	Platform s2 = new Platform(400, 240, 500, 5, 240, "platform", Color.BLACK);

	//Making Platforms and Coins
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	ArrayList<Coins> coins = new ArrayList<Coins>();
	Platform p;
	Coins c;
	int numCoins = 0;
		
	//Jumping 
	private boolean canJump = true;
	private int jump = 25;
	KeyCode jumpButton;
	
	private Parent initGame() {
		root.setPrefSize(800, 600);

		root.getChildren().add(player);

		AnimationTimer timer = new AnimationTimer() {

			public void handle(long now) {
				platform();
				coins();
				//See's if player is on a platform, and takes gravity into account
				for(int i = 0; i < platforms.size();i++) {

					if(player.getBoundsInParent().intersects(platforms.get(i).getBoundsInParent())) {
						canJump = true;
						player.antiGravity();
					}
				}

				for(int i = 0; i < coins.size(); i++) {
					if(player.getBoundsInParent().intersects(coins.get(i).getBoundsInParent())) {
						root.getChildren().remove(coins.get(i));
						coins.remove(i);
						numCoins++;
						System.out.println(numCoins);
					}
				}
				player.gravity();


				if(canJump == true) {
					//Need to make it when up or space is inputted this will work.
					if(jumpButton == KeyCode.SPACE)
						if(jump >= 0) {
							player.jump(jump);
							jump--;
						}
						else {
							canJump = false;
							jump = 25;
							jumpButton = KeyCode.ALT;
						}
				}


			}
		};

		timer.start();

		platform();

		return root;
	}

	private void platform() {
		//How and where platforms spawn, and how commonly they appear
		if((int)(Math.random() * 1000) <= 50) {
			p = new Platform(800, (int)(Math.random() * 100) + 300, (int)(Math.random() * 500) + 100, 5, 0, "platform", Color.RED);
			platforms.add(p);
			root.getChildren().add(p);
		}

	}

	private void coins() {
		if((int)(Math.random() * 1000) <= 15) {
			c = new Coins(800, (int)(Math.random() * 500) - 300, 40, 40, "coin", Color.YELLOW);
			coins.add(c);
			root.getChildren().add(c);
		}

	}


	public void start (Stage stage) throws Exception {
		Scene scene = new Scene(initGame());
		platforms.add(s);
		platforms.add(s2);
		root.getChildren().addAll(s, s2);

		jumpButton = KeyCode.ALT;

		scene.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP || e.getCode() == KeyCode.SPACE) {
				jumpButton = KeyCode.SPACE;
			}
		});

		stage.setScene(scene);
		stage.show();

	}


	public static void main(String[] args) {
		launch(args);

	}
}

