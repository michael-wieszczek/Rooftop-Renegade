package main;
/**
 * 
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;


/**
 * @author mimlo
 *
 */
public class MainApplication extends Application {

	private Pane root = new Pane();

	ImagePattern playerRun = null;
	ImagePattern playerJump = null;
	ImagePattern coin = null;
	Node icon;
	private Player player = null;

	//Starting Platforms
	Platform s = new Platform(0, 340, 800, 5,  "platform", Color.BLACK);
	Platform s2 = new Platform(400, 240, 500, 5, "platform", Color.BLACK);


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


		AnimationTimer timer = new AnimationTimer() {

			public void handle(long now) {
				coins();
				platform();

				//See's if player is on a platform, and takes gravity into account
				for(int i = 0; i < platforms.size();i++) {

					if(player.getBoundsInParent().intersects(platforms.get(i).getBoundsInParent())) {
						if(	player.getBottom() < platforms.get(i).getTop()+10) {
							player.setY(platforms.get(i).getTop() - player.getHeight());
							canJump = true;
							player.antiGravity();
							player.setFill(playerRun);
						}							

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
					if(jumpButton == KeyCode.SPACE)
						if(jump >= 0) {
							player.jump(jump);
							jump--;
							player.setFill(playerJump);
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
		if((int)(Math.random() * 1000) <= 45) {
			p = new Platform(800, (int)(Math.random() * 8 + 7) * 30, (int)(Math.random() * 500) + 100, 5, "platform", Color.RED);
			platforms.add(p);
			root.getChildren().add(p);
		}

	}


	private void coins() {
		try {
			coin = new ImagePattern(new Image (new FileInputStream ("Resources/Coin 10FPS.gif")));
		}catch (FileNotFoundException e) {

		}
		if((int)(Math.random() * 1000) <= 8) {
			c = new Coins(800, (int)(Math.random() * 10 + 9) * 20, 40, 40, "coin", coin);

			coins.add(c);
			root.getChildren().add(c);
		}

	}


	public void start (Stage stage) throws Exception {
		Scene scene = new Scene(initGame());
		platforms.add(s);
		platforms.add(s2);
		root.getChildren().addAll(s, s2);
		try {
			playerRun = new ImagePattern(new Image (new FileInputStream ("Resources/Walking15.gif")));
			playerJump = new ImagePattern(new Image (new FileInputStream ("Resources/JumpAnimation (1).gif")));
		}catch (FileNotFoundException e) {

		}
		jumpButton = KeyCode.ALT;

		scene.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP || e.getCode() == KeyCode.SPACE) {
				jumpButton = KeyCode.SPACE;
			}
		});

		player = new Player(300, 280, 40, 60, "player", playerRun);
		root.getChildren().add(player);
		stage.setScene(scene);
		stage.show();

		
	}


	public static void main(String[] args) {
		launch(args);

	}
}

