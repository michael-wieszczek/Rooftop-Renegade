package main;
/**
 * 
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import main.Background;
import main.Coins;
import main.Platform;
import main.Player;


/**
 * @author mimlo
 *
 */
public class MainApplication extends Application {
	AnimationTimer timer;
	Timeline scorePoints;

	private static Pane root = new Pane();

	ImagePattern playerRun = null;
	ImagePattern playerJump = null;
	ImagePattern coin = null;

	static int counter = 0;

	static ImagePattern [] backgroundImages  = new ImagePattern [3];
	Node icon;
	private Player player = null;


	//Starting Platforms
	Platform s = new Platform(0, 340, 800, 5,  "platform", Color.BLACK);
	Platform s2 = new Platform(400, 240, 500, 5, "platform", Color.BLACK);

	Background background = new Background(0, 0, 3724, 608, "background", backgroundImages[0]);

	//Making Platforms and Coins
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	static ArrayList<Background> backgrounds = new ArrayList<Background>();
	ArrayList<Coins> coins = new ArrayList<Coins>();
	Platform p;
	Coins c;
	int numCoins = 0;
	//private Point2D playerVelocity = new Point2D(0, 0);
	private boolean canJump = true;
	private boolean doubleJump = true;
	private int jump = 26;//Changes the jump height
	private int score = 0;
	public int speed = 7;
	public static int thicc = 8;
	boolean isDead = false;
	KeyCode jumpButton;

	public Parent initGame() {
		root.setPrefSize(800, 600);

		scorePoints = new Timeline(
				new KeyFrame(Duration.millis(400), e -> {
					score++;
					System.out.println(score); //temp
				})
				);
		scorePoints.setCycleCount(Timeline.INDEFINITE);
		scorePoints.play();


		timer = new AnimationTimer() {

			public void handle(long now) {
				coins();
				platform();
				//player.antiGravity();
				//See's if player is on a platform, and takes gravity into account
				for(int i = 0; i < platforms.size();i++) {

					if(player.getBoundsInParent().intersects(platforms.get(i).getBoundsInParent())) {
						if(	player.getBottom() < platforms.get(i).getTop()+10) {
							player.setY(platforms.get(i).getTop() - player.getHeight());
							canJump = true;
							doubleJump = true;
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
						score += 10;
					}
				}
				player.gravity();
				if(canJump == true) {
					if(jumpButton == KeyCode.SPACE) {
						if(jump >= 0) {
							player.jump(jump);
							jump--;
							player.setFill(playerJump);
						}
						else {
							canJump = false;
							jump = 26;
							jumpButton = KeyCode.ALT;
						}
					}
				}
				else if(doubleJump == true) {
					if(jumpButton == KeyCode.SPACE) {
						if(jump >= 0) {
							player.jump(jump);
							jump--;
						}
						else {
							doubleJump = false;
							jump = 26;
							jumpButton = KeyCode.ALT;
						}
					}
				}
				if(player.getY() >= 600) {
					System.out.println("dead");
					dead(timer);
				}

				if (backgrounds.get(backgrounds.size()-1).getX() <= -2000 ) {
					background();
				}

			}

		};
		timer.start();

		platform();

		return root;
	}

	public void dead(AnimationTimer timer) {
		timer.stop();
		scorePoints.stop();
	}

	private void platform() {
		if((int)(Math.random() * 1000) <= 20) {
			p = new Platform(800, (int)(Math.random() * 8 + 7) * 30, (int)(Math.random() * 500) + 100, thicc, "platform", Color.DARKORANGE);
			platforms.add(p);
			root.getChildren().add(p);
		}
		if(platforms.isEmpty()) {
		}
		else if(platforms.get(platforms.size() - 1).getX() <= 300) {
			p = new Platform(800, 400, 200, thicc, "platform", Color.AQUA);
			platforms.add(p);
			root.getChildren().add(p);
		}


	}

	private void coins() {
		try {
			coin = new ImagePattern(new Image (new FileInputStream ("Resources/Coin 10FPS.gif")));
		}catch (FileNotFoundException e) {

		}

		if((int)(Math.random() * 1000) <= 12) {
			c = new Coins(800, (int)(Math.random() * 10 + 9) * 20, 40, 40, "coin", coin);
			coins.add(c);
			root.getChildren().add(c);
		}

	}

	public static void background() {
		counter++;
		if(counter == 1) {
			Background b = new Background(1723, 0, 3724, 608, "background", backgroundImages[0]);
			b.setFill(backgroundImages[0]);
			backgrounds.add(b);
			root.getChildren().add(0, b);
		}
		if(counter == 2) {
			Background b = new Background(1723, 0, 3724, 608, "background", backgroundImages[1]);
			b.setFill(backgroundImages[1]);
			backgrounds.add(b);
			root.getChildren().add(0, b);
		}
		else{
			Background b = new Background(1710, 0, 3724, 608, "background", backgroundImages[2]);
			b.setFill(backgroundImages[2]);
			backgrounds.add(b);
			root.getChildren().add(0, b);
		}
	}

	public void start (Stage stage) throws Exception {
		Scene scene = new Scene(initGame());
		root.getChildren().add(background);
		platforms.add(s);
		platforms.add(s2);
		root.getChildren().addAll(s, s2);
		try {
			playerRun = new ImagePattern(new Image (new FileInputStream ("Resources/Walking15.gif")));
			playerJump = new ImagePattern(new Image (new FileInputStream ("Resources/JumpAnimation (1).gif")));
			backgroundImages[0] = new ImagePattern(new Image (new FileInputStream ("Resources/BachgroundGoodPiece.png")));
			backgroundImages[1] = new ImagePattern(new Image (new FileInputStream ("Resources/Transition.png")));
			backgroundImages[2] = new ImagePattern(new Image (new FileInputStream ("Resources/BadBackgroundGood.png")));
		}catch (FileNotFoundException e) {

		}
		jumpButton = KeyCode.ALT;



		scene.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP || e.getCode() == KeyCode.SPACE) {
				jumpButton = KeyCode.SPACE;
			}
		});
		background.setFill(backgroundImages[0]);
		backgrounds.add(background);
		player = new Player(300, 280, 40, 60, "player", playerRun);
		root.getChildren().add(player);
		stage.setScene(scene);
		stage.show();


	}


	public static void main(String[] args) {
		launch(args);

	}
}

