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
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
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

	Scene sceneMainMenu, sceneSettings, scene;
	Stage stage;

	private static Pane root = new Pane();

	ImagePattern playerRun = null;
	ImagePattern playerJump = null;
	ImagePattern coin = null;

	static int counter = 0;

	static ImagePattern [] backgroundImages  = new ImagePattern [3];
	Node icon;
	private Player player = null;



	//Starting Platforms


	//Making Platforms and Coins
	ArrayList<GamePlatform> platforms = new ArrayList<GamePlatform>();
	static ArrayList<GameBackground> backgrounds = new ArrayList<GameBackground>();
	ArrayList<Coins> coins = new ArrayList<Coins>();
	GamePlatform p;
	Coins c;
	int numCoins = 0;
	private boolean canJump = true;
	private boolean doubleJump = true;
	private int jump = 26;//Changes the jump height
	private int score = 0;
	public static int thicc = 7;

	boolean isDead = false;
	KeyCode jumpButton;

	GamePlatform s = new GamePlatform(0, 340, 800, 5,  "platform", Color.BLACK);
	GamePlatform s2 = new GamePlatform(400, 240, 500, 5, "platform", Color.BLACK);

	public Parent initGame() {

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

				//For kurtis to not die, and actually get to the red part :)
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
				try {
					if (backgrounds.get(backgrounds.size()-1).getX() <= -2000 ) {
						background();
					}
				}catch(Exception e) {

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
		//Leaderboards and score here
		numCoins = 0;
		canJump = true;
		doubleJump = true;
		jump = 26;//Changes the jump height
		score = 0;
		isDead = false;
		counter = 0;
		backgrounds.clear();
		stage.setScene(sceneMainMenu);
	}

	private void platform() {
		if((int)(Math.random() * 1000) <= 20) {
			p = new GamePlatform(800, (int)(Math.random() * 8 + 7) * 30, (int)(Math.random() * 500) + 100, 7, "platform", Color.DARKORANGE);
			platforms.add(p);
			root.getChildren().add(p);
		}
		if(platforms.isEmpty()) {
		}
		else if(platforms.get(platforms.size() - 1).getX() <= 300) {
			p = new GamePlatform(800, 400, 200, 7, "platform", Color.AQUA);
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
			GameBackground b = new GameBackground(1723, 0, 3724, 608, "background", backgroundImages[0]);
			b.setFill(backgroundImages[0]);
			backgrounds.add(b);
			root.getChildren().add(0, b);
		}
		if(counter == 2) {
			GameBackground b = new GameBackground(1723, 0, 3724, 608, "background", backgroundImages[1]);
			b.setFill(backgroundImages[1]);
			backgrounds.add(b);
			root.getChildren().add(0, b);
		}
		else{
			GameBackground b = new GameBackground(1710, 0, 3724, 608, "background", backgroundImages[2]);
			b.setFill(backgroundImages[2]);
			backgrounds.add(b);
			root.getChildren().add(0, b);
		}
	}

	public void start (Stage mainWindow) throws Exception {
		stage = mainWindow;

		Button game = new Button();	
		game.setStyle("-fx-background-image: url('main/startaButton.png')");
		game.setMinSize(190,49);

		Button settings = new Button();	
		settings.setStyle("-fx-background-image: url('main/settingsButton.png')");
		settings.setMinSize(190,49);

		Button exit = new Button();	
		exit.setStyle("-fx-background-image: url('main/exitButton.png')");
		exit.setMinSize(190,49);


		//CREATES BUTTON MENU
		//Creates new gridpane and adds the buttons to the pane
		GridPane menu = new GridPane();
		menu.add(game, 0, 0);
		menu.add(settings, 0, 1);
		menu.add(exit, 0, 2);
		//Sets each buttons alignment on the screen
		GridPane.setHalignment(game,HPos.RIGHT);
		GridPane.setValignment(game,VPos.BOTTOM);
		GridPane.setHalignment(settings,HPos.RIGHT);
		GridPane.setValignment(settings,VPos.CENTER);
		GridPane.setHalignment(exit,HPos.RIGHT);
		GridPane.setValignment(exit,VPos.TOP);
		//Creates columns to put the buttons in
		ColumnConstraints column1 = new ColumnConstraints();
		ColumnConstraints column2 = new ColumnConstraints();
		//Sets the space that each button occupy
		column1.setPercentWidth(95);
		column1.setHgrow(Priority.ALWAYS);
		column2.setPercentWidth(5);
		column2.setHgrow(Priority.ALWAYS);
		menu.getColumnConstraints().addAll(column1,column2);
		RowConstraints row1 = new RowConstraints(), row2 = new RowConstraints(), row3 = new RowConstraints();
		row1.setPercentHeight(20);
		row2.setPercentHeight(15);
		row3.setPercentHeight(70);
		row1.setVgrow(Priority.ALWAYS);
		row2.setVgrow(Priority.ALWAYS);
		row3.setVgrow(Priority.ALWAYS);
		menu.getRowConstraints().addAll(row1,row2,row3);

		//MENU BACKGROUND
		Image image1 = null;
		try {
			image1 = new Image (new FileInputStream ("Resources/RRB.png"));
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		//Sets size of background area
		BackgroundSize backSize = new BackgroundSize(800, 500, false, false, false, false);
		//Creates the background image
		BackgroundImage backImage= new BackgroundImage(image1, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT,
				backSize);
		Background background = new Background(backImage);

		//SETTINGS BACKGROUND
		Image image2 = null;
		try {
			image2 = new Image (new FileInputStream ("Resources/Settings Background(1).png"));
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		//Sets size of background area
		BackgroundSize backSize2 = new BackgroundSize(800, 500, false, false, false, false);
		//Creates the background image
		BackgroundImage backImage2= new BackgroundImage(image2, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT,
				backSize2);
		Background background2 = new Background(backImage2);

		//CREATES MENU
		BorderPane pane = new BorderPane();
		pane.setCenter(menu);
		pane.setBackground(background);

		//CREATES SETTINGS
		BorderPane pane2 = new BorderPane();
		pane2.setBackground(background2);

		//CREATE GAME SCREEN
		BorderPane pane3 = new BorderPane();

		sceneMainMenu = new Scene(pane,800,500);
		sceneSettings = new Scene(pane2,800,500);
		scene = new Scene(root,800,500);

		//Changes scene on button cick
		game.setOnAction(e -> {
			try {
				GamePlatform s = new GamePlatform(0, 340, 800, 5,  "platform", Color.BLACK);
				GamePlatform s2 = new GamePlatform(400, 240, 500, 5, "platform", Color.BLACK);
				initGame();
				GameBackground background3 = new GameBackground(0, 0, 3724, 608, "background", backgroundImages[0]);
				root.getChildren().add(background3);
				platforms.add(s);
				platforms.add(s2);
				root.getChildren().addAll(s, s2);
				try {
					playerRun = new ImagePattern(new Image (new FileInputStream ("Resources/Walking15.gif")));
					playerJump = new ImagePattern(new Image (new FileInputStream ("Resources/JumpAnimation (1).gif")));
					backgroundImages[0] = new ImagePattern(new Image (new FileInputStream ("Resources/BachgroundGoodPiece.png")));
					backgroundImages[1] = new ImagePattern(new Image (new FileInputStream ("Resources/Transition.png")));
					backgroundImages[2] = new ImagePattern(new Image (new FileInputStream ("Resources/BadBackgroundGood.png")));
				}catch (FileNotFoundException f) {

				}
				jumpButton = KeyCode.ALT;



				scene.setOnKeyPressed(f -> {
					if(f.getCode() == KeyCode.W || f.getCode() == KeyCode.UP || f.getCode() == KeyCode.SPACE) {
						jumpButton = KeyCode.SPACE;
					}
				});
				background3.setFill(backgroundImages[0]);
				backgrounds.add(background3);
				player = new Player(300, 280, 40, 60, "player", playerRun);
				root.getChildren().add(player);
				mainWindow.setScene(scene);
				mainWindow.show();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		settings.setOnAction(e -> mainWindow.setScene(sceneSettings));
		exit.setOnAction(e ->Platform.exit());

		mainWindow.setTitle("Rooftop Renegade");
		mainWindow.setResizable(false);	

		mainWindow.setScene(sceneMainMenu);
		mainWindow.show();
	}





	//DRAWING
	//		Group root = new Group();
	//		Canvas canvas = new Canvas(mainWindow.getWidth(),200);
	//		final GraphicsContext gc = canvas.getGraphicsContext2D();
	//		
	//		draw(gc);
	//		
	//		root.getChildren().add(canvas);

	//CREATES BUTTONS 

	public static void main(String[] args) {
		launch(args);

	}
}
