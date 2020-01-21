package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * MainApplication.java <br>
 * This class extends application, and is what runs the game. It calls launch which starts
 * the game. On start, the user is presented with a menu with 3 interactive buttons,
 * these buttons can be used to start the game, check the settings, or exit. Upon pressing
 * one of these buttons the corresponding piece of code will play. <br>
 * 
 * Game: <br>
 * which starts the game, starts spawning randomly generated platforms using the Platform class which the
 * player has to jump on to survive and accumulate points, during this time the player can
 * collect coins spawned in from the Coins class to receive extra points. When the player
 * reaches the lower bounds of the screen, he will be dead. The players score is then checked
 * ,and if within the top 5 scores, will be prompted to enter a name and be shown on the 
 * leaderboard screen, from this, the player can either play the game again or go back to 
 * the main menu. <br> <br>
 * 
 * Settings: <br>
 * This shows the settings scene, which provides the controls for the game, as well as 
 * the option to mute the music, if the back button is clicked, the player will go back
 * to the main menu <br><br>
 * 
 * Exit: <br>
 * This will call system.exit to exit the application <br>
 * @author Michael Wieszczek <br>
 * January 21st, 2020
 */
public class MainApplication extends Application {
	AnimationTimer timer;
	Timeline scorePoints;
	Scanner sc = new Scanner(System.in);

	Scene sceneMainMenu, sceneSettings, scene, sceneLeaderboard;
	Stage stage;
	Text settingText;

	//Group groupSetting = new Group();
	Group group = new Group();
	Group leaderboardGroup = new Group();
	Text scorePrint;
	Text [] highScoreNames = new Text[5];
	Text [] highScores = new Text[5];
	Font font;
	int index = 0;
	BorderPane pane3 = new BorderPane();

	private static Pane root = new Pane();

	ImagePattern playerRun = null;
	ImagePattern playerJump = null;
	ImagePattern coin = null;

	static int counter = 0;

	static ImagePattern [] backgroundImages  = new ImagePattern [3];
	Node icon;

	private Player player = null;
	ArrayList<Integer> leaderboardScore = new ArrayList<Integer>();
	ArrayList <String> leaderboardName = new ArrayList<String>();
	File file = new File("Resources/LeaderBoard.txt");

	File song = new File ("Resources/TimeMachineSong.wav");
	int isMuted = 1;
	MediaPlayer music;


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
	boolean isDead = false;
	KeyCode jumpButton;

	public Parent initGame() {
		if(isMuted == 1) {
			Media sound=null;
			try {
				sound = new Media (song.toURI().toURL().toString());
			}
			catch (MalformedURLException g) {
				g.printStackTrace();
			}
			music = new MediaPlayer(sound);
			music.setVolume(.1);
			music.setCycleCount(music.INDEFINITE);
			music.play();
		}

		scorePoints = new Timeline(
				new KeyFrame(Duration.millis(400), e -> {
					group.getChildren().remove(scorePrint);
					score++;
					scorePrint = new Text(Integer.toString(score));
					font = new Font("Candara", 38);
					scorePrint.setFont(font);
					scorePrint.setFill(Color.CRIMSON);
					group.getChildren().add(scorePrint);
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
					if(isMuted == 1) {
						music.stop();
					}
					dead(timer);
				}
				try {
					if (backgrounds.get(backgrounds.size()-1).getX() <= -2000 ) {
						background();
					}
				}catch(Exception e) {

				}
				try {
					if (score <10) {
						scorePrint.setX(player.getX()+10);
						scorePrint.setY(player.getY()-20);
					}
					else if (score >= 10 && score < 100) {
						scorePrint.setX(player.getX());
						scorePrint.setY(player.getY()-20);
					}
					else if (score >=100  && score < 1000) {
						scorePrint.setX(player.getX()-10);
						scorePrint.setY(player.getY()-20);
					}
					else{
						scorePrint.setX(player.getX()-20);
						scorePrint.setY(player.getY()-20);
					}

				}catch(Exception e) {
				}
			}


		};
		timer.start();

		platform();

		return root;
	}
	/**
	 * This method runs after the player reaches the lower bound of the screen, this
	 * method checks the players current score, and compares it to the current scores
	 * saved in the leaderboard. If the player score is within the top 5, the player 
	 * will be prompted to enter a 4 character name and their score will be saved on 
	 * the leaderboard, This method will also reset all key variables so that they are
	 * ready to be used again. The user will have 2 buttons in which they can either
	 * choose to restart the game, or go back to the main menu.
	 * @param timer - The timer that keeps track of score
	 */
	public void dead(AnimationTimer timer) {
		for(int i = 0; i < 5; i++) {
			pane3.getChildren().removeAll(highScores[i], highScoreNames[i]);
		}
		int leaderboardPos = linear(leaderboardScore, score);
		timer.stop();
		scorePoints.stop();
		stage.setScene(sceneLeaderboard);
		if(leaderboardPos == -1) {
		}
		else if (leaderboardPos > 4){
		}
		else {
			leaderboardScore.add(leaderboardPos, score);
			System.out.println("New Highscore! Please enter a 4 character name");
			for(int i = 0; i < 1;) {
				String name = sc.nextLine();
				if(name.length() > 4 || name.length() < 4) {
					System.out.println("Please Enter a valid name");
				}
				else {
					i++;
					leaderboardName.add(leaderboardPos, name);
				}
			}
		}
		for(int i = 0; i < 5; i++) {
			highScores [i]= new Text(Integer.toString(leaderboardScore.get(i)));
			highScoreNames [i]= new Text(leaderboardName.get(i));
			if (i==0) {
				highScores [i].setX(550);
				highScores [i].setY(200);
				highScoreNames [i].setX(170);
				highScoreNames [i].setY(200);
			}
			else {
				highScores [i].setX(550);
				highScores [i].setY(200 + 40*i);
				highScoreNames [i].setX(170);
				highScoreNames [i].setY(200 + 40*i);
			}
			font = new Font("Candara", 38);
			highScores [i].setFont(font);
			highScores [i].setFill(Color.BLACK);
			highScoreNames [i].setFont(font);
			highScoreNames [i].setFill(Color.BLACK);
			pane3.getChildren().addAll(highScores [i], highScoreNames[i]);
		}
		//save data from current session into file
		PrintStream fps;
		try {
			fps = new PrintStream(file);
			for(int j = 0; j < 5; j++) {
				fps.println(toString(leaderboardName.get(j),leaderboardScore.get(j)));
			}
			fps.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.err.println("Error Saving To File");
		}

		numCoins = 0;
		canJump = true;
		doubleJump = true;
		jump = 26;
		score = 0;
		isDead = false;
		counter = 0;
		group.getChildren().remove(scorePrint);
		backgrounds.clear();
	}
	/**
	 * Has a 15 in 1000 chance to spawn a randomly generated platform each time this
	 * method is called. The platform will have a random y and width value and will be
	 * added to the root. If the last spawned platform starts reaching the left side of the
	 * screen, a safety platform will be spawned, to ensure that the jump will be possible
	 */
	private void platform() {
		if((int)(Math.random() * 1000) <= 15) {
			p = new GamePlatform(800, (int)(Math.random() * 8 + 7) * 30, (int)(Math.random() * 500) + 100, 7, "platform", Color.DARKORANGE);
			platforms.add(p);
			root.getChildren().add(p);
		}
		if(platforms.isEmpty()) {
		}
		else if(platforms.get(platforms.size() - 1).getX() <= 300) {
			p = new GamePlatform(800, 400, (int)(Math.random() * 300) + 100, 7, "platform", Color.DARKORANGE);
			platforms.add(p);
			root.getChildren().add(p);
		}
	}
	/**
	 * This method will have a 12 in 1000 chance of spawning a coin into the game with
	 * a randomly generated y value.
	 */
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
	/**
	 * This method sets a specific background depending on the value of counter. 
	 */
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
	/**
	 * This method sets up all the scenes and buttons in the application
	 */
	public void start (Stage mainWindow) throws Exception {
		//Sets the stage equal to the mainWindow
		stage = mainWindow;

		//load data from file
		try {
			Scanner fscan = new Scanner(file);
			for(int i = 0; i < 5; i++) {
				String[] arr = fscan.nextLine().split("SPLIT");
				leaderboardName.add(arr[0]);
				leaderboardScore.add(Integer.parseInt(arr[1]));
			}	
			fscan.close();
		}
		catch (FileNotFoundException e) {
			System.err.print("Could not find file");
		}	
		catch (NoSuchElementException e) {
			System.err.print("File is empty");
		}

		//CREATES BUTTONS FOR THE MAIN MENU

		//Start button
		Button game = new Button();	
		game.setStyle("-fx-background-image: url('main/startaButton.png')");
		game.setMinSize(190,49);

		//Settings button
		Button settings = new Button();	
		settings.setStyle("-fx-background-image: url('main/settingsButton.png')");
		settings.setMinSize(190,49);

		//Exit button
		Button exit = new Button();	
		exit.setStyle("-fx-background-image: url('main/exitButton.png')");
		exit.setMinSize(190,49);

		//CREATES BUTTON MENU

		//Creates new grid pane and adds the buttons to the pane
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
		//Sets the space that each button may occupy
		//Creates columns for the buttons
		column1.setPercentWidth(95);
		column1.setHgrow(Priority.ALWAYS);
		column2.setPercentWidth(5);
		column2.setHgrow(Priority.ALWAYS);
		menu.getColumnConstraints().addAll(column1,column2);
		//Creates rows for the buttons
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
			e2.printStackTrace();
		}
		//Sets size of background area
		BackgroundSize backSize = new BackgroundSize(820, 520, false, false, false, false);
		//Creates the background image
		BackgroundImage backImage= new BackgroundImage(image1, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT,
				backSize);
		Background background = new Background(backImage);

		//CREATES MENU

		BorderPane pane = new BorderPane();
		pane.setCenter(menu);
		pane.setBackground(background);

		//SETTINGS BACKGROUND

		Image image2 = null;
		try {
			image2 = new Image (new FileInputStream ("Resources/SettingsBack1.png"));
		} catch (FileNotFoundException e2) {
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

		//CREATES SETTINGS BUTTONS

		//Back button
		Button back = new Button();	
		back.setStyle("-fx-background-image: url('main/backButton.png')");
		back.setMinSize(190,49);

		//Mute button
		Button mute = new Button();	
		mute.setStyle("-fx-background-image: url('main/muteButton.png')");
		mute.setMinSize(190,49);

		//CREATES SETTINGS 

		//Creates grid pane to put buttons on
		GridPane settingGrid = new GridPane();
		settingGrid.add(back,1,0);
		settingGrid.add(mute,2,0);

		//Aligns the buttons
		GridPane.setHalignment(back,HPos.CENTER);
		GridPane.setValignment(back,VPos.BOTTOM);
		GridPane.setHalignment(mute,HPos.CENTER);
		GridPane.setValignment(mute,VPos.BOTTOM);
		//Creates 4 columns for the buttons to go into
		ColumnConstraints columnSettings = new ColumnConstraints();
		ColumnConstraints columnSettings2 = new ColumnConstraints();
		ColumnConstraints columnSettings3 = new ColumnConstraints();
		ColumnConstraints columnSettings4 = new ColumnConstraints();
		columnSettings.setPercentWidth(10);
		columnSettings.setHgrow(Priority.ALWAYS);
		columnSettings2.setPercentWidth(40);
		columnSettings2.setHgrow(Priority.ALWAYS);
		columnSettings3.setPercentWidth(40);
		columnSettings3.setHgrow(Priority.ALWAYS);
		columnSettings4.setPercentWidth(10);
		columnSettings4.setHgrow(Priority.ALWAYS);
		settingGrid.getColumnConstraints().addAll(columnSettings,columnSettings2,columnSettings3,columnSettings4);
		//Creates a row for the buttons
		RowConstraints rowSettings = new RowConstraints();
		rowSettings.setPercentHeight(95);
		settingGrid.getRowConstraints().addAll(rowSettings);

		//Adds grid pane to boarder pane

		//LEADERBOARD BACKGROUND
		Image image3 = null;
		try {
			image3 = new Image (new FileInputStream ("Resources/LeaderboardBackground.png"));
		} catch (FileNotFoundException e3) {
			e3.printStackTrace();
		}
		//Sets size of background area
		BackgroundSize backSize3 = new BackgroundSize(800, 600, false, false, false, false);
		//Creates the background image
		BackgroundImage backImage3= new BackgroundImage(image3, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.DEFAULT,
				backSize3);
		Background background4 = new Background(backImage3);

		//CREATES SETTINGS
		BorderPane pane2 = new BorderPane();
		pane2.setBackground(background2);
		pane2.setCenter(settingGrid);

		//CREATE GAME SCREEN


		pane2.setBackground(background2);

		group.getChildren().add(0, root);

		//LEADERBOARD BUTTONS
		//Back button
		GridPane leaderboardGrid = new GridPane();
		Button back2 = new Button();	
		back2.setStyle("-fx-background-image: url('main/backButton.png')");
		back2.setMinSize(190,49);
		leaderboardGrid.add(back2,0,0);


		GridPane.setHalignment(back2,HPos.CENTER);
		GridPane.setValignment(back2,VPos.BOTTOM);
		ColumnConstraints columnLeaderboard = new ColumnConstraints();
		columnLeaderboard.setPercentWidth(100);
		columnLeaderboard.setHgrow(Priority.ALWAYS);
		leaderboardGrid.getColumnConstraints().addAll(columnLeaderboard);

		RowConstraints rowLeaderboard = new RowConstraints();
		rowLeaderboard.setPercentHeight(100);
		leaderboardGrid.getRowConstraints().addAll(rowLeaderboard);

		//LEADERBOARD MENU
		pane3.setBackground(background4);

		pane3.setCenter(leaderboardGrid);
		sceneMainMenu = new Scene(pane,800,500);
		sceneSettings = new Scene(pane2,800,500);
		scene = new Scene(group,800,500);
		sceneLeaderboard = new Scene(pane3,800,500);

		//BUTTON CLICK COMMANDS

		//Changes scene on button click to the game scene
		game.setOnAction(e -> {
			try {
				//The initial setpiece when starting the game, two platforms designed for the user to
				//prepare, and be able to test out the basic moment of the game before they reach
				//the randomly generated part
				GamePlatform initialPlat1 = new GamePlatform(0, 340, 1000, 7,  "platform", Color.DARKORANGE);
				GamePlatform initialPlat2 = new GamePlatform(700, 240, 500, 7, "platform", Color.DARKORANGE);

				//Starts game
				initGame();
				GameBackground background3 = new GameBackground(0, 0, 3724, 608, "background", backgroundImages[0]);
				root.getChildren().add(background3);
				platforms.add(initialPlat1);
				platforms.add(initialPlat2);
				root.getChildren().addAll(initialPlat1, initialPlat2);
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

				sceneLeaderboard.setOnKeyPressed(f -> {
					if(f.getCode() == KeyCode.W || f.getCode() == KeyCode.UP || f.getCode() == KeyCode.SPACE) {
						mainWindow.setScene(sceneMainMenu);
					}
				});
				background3.setFill(backgroundImages[0]);
				backgrounds.add(background3);
				player = new Player(300, 280, 40, 60, "player", playerRun);
				root.getChildren().add(player);
				mainWindow.setScene(scene);
				mainWindow.show();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		});
		//Changes scene on button click to the settings window

		settings.setOnAction(e -> mainWindow.setScene(sceneSettings));

		//Changes scene on button click (exits game)
		exit.setOnAction(e ->Platform.exit());

		//Changes scene on button click (does back to main menu
		back.setOnAction(e -> mainWindow.setScene(sceneMainMenu));
		mute.setOnAction(e -> isMuted *= -1);
		back2.setOnAction(e -> mainWindow.setScene(sceneMainMenu));

		mainWindow.setTitle("Rooftop Renegade");
		mainWindow.setResizable(false);	
		mainWindow.setScene(sceneMainMenu);
		mainWindow.show();
	}
	/**
	 * This method searches through an array list to find the spot where the target score
	 * would be placed into, if target is smaller than everything in the array, -1 is returned
	 * @param arr - The leaderboard array list containing scores.
	 * @param target - The players current score
	 * @return
	 */
	public static int linear(ArrayList<Integer> arr, int target) {
		for(int i = 0; i < arr.size(); i++) {
			if(target > arr.get(i)) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * This method takes the given name of a leaderboard player, and a given score of that
	 * player, and converts it into a single string, separated by "SPLIT"
	 * @param name - The players name
	 * @param score - The players score
	 * @return scoreSave - The singular string containing the name and score seperated by SPLIT
	 */
	private String toString(String name, int score) {
		String scoreSave = name + "SPLIT" + score;
		return scoreSave;
	}
	/**
	 * Launches the application
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}
}
