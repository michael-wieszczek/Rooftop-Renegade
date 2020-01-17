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


/**
 * @author mimlo
 *
 */
public class MainApplication extends Application {
	AnimationTimer timer;
	Timeline scorePoints;
	
	private Pane root = new Pane();

	ImagePattern playerRun = null;
	ImagePattern playerJump = null;
	ImagePattern coin = null;
	ImagePattern backgroundP = null;
	ImagePattern backgroundT = null;
	ImagePattern backgroundB = null;
	Node icon;
	private Player player = null;

	
	//Starting Platforms
	Platform s = new Platform(0, 340, 800, 5,  "platform", Color.BLACK);
	Platform s2 = new Platform(400, 240, 500, 5, "platform", Color.BLACK);
	
	Background background = new Background(0, 0, 3724, 608, "background", backgroundP);
	Background background2 = new Background(3724, 0, 3724, 608, "background", backgroundT);
	Background background3 = new Background(7448, 0, 3724, 608, "background", backgroundB);
	//Making Platforms and Coins
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	ArrayList<Coins> coins = new ArrayList<Coins>();
	Platform p;
	Coins c;
	int numCoins = 0;
	//private Point2D playerVelocity = new Point2D(0, 0);
	private boolean canJump = true;
	private boolean doubleJump = true;
	private int jump = 25;//Changes the jump height
	private int score = 0;
	public int speed = 7;
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
							jump = 25;
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
							jump = 25;
							jumpButton = KeyCode.ALT;
						}
					}
				}
				if(player.getY() >= 600) {
					System.out.println("dead");
					dead(timer);
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
			p = new Platform(800, (int)(Math.random() * 8 + 7) * 30, (int)(Math.random() * 500) + 100, 5, "platform", Color.RED);
			platforms.add(p);
			root.getChildren().add(p);
		}
		if(platforms.isEmpty()) {
		}
		else if(platforms.get(platforms.size() - 1).getX() <= 300) {
				p = new Platform(800, 400, 200, 5, "platform", Color.AQUA);
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
		root.getChildren().addAll(background, background2, background3);
		platforms.add(s);
		platforms.add(s2);
		root.getChildren().addAll(s, s2);
		try {
			playerRun = new ImagePattern(new Image (new FileInputStream ("Resources/Walking15.gif")));
			playerJump = new ImagePattern(new Image (new FileInputStream ("Resources/JumpAnimation (1).gif")));
			backgroundP = new ImagePattern(new Image (new FileInputStream ("Resources/BachgroundGoodPiece.png")));
			backgroundT = new ImagePattern(new Image (new FileInputStream ("Resources/Transition.png")));
			backgroundB = new ImagePattern(new Image (new FileInputStream ("Resources/BadBackgroundGood.png")));
		}catch (FileNotFoundException e) {

		}
		jumpButton = KeyCode.ALT;
		
		

		scene.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP || e.getCode() == KeyCode.SPACE) {
				jumpButton = KeyCode.SPACE;
			}
		});
		
		background.setFill(backgroundP);
		background2.setFill(backgroundT);
		background3.setFill(backgroundB);
		player = new Player(300, 280, 40, 60, "player", playerRun);
		root.getChildren().add(player);
		stage.setScene(scene);
		stage.show();

		
	}


	public static void main(String[] args) {
		launch(args);

	}
}

