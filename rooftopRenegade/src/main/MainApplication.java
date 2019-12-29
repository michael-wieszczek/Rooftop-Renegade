/**
 * 
 */
package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author mimlo
 *
 */
public class MainApplication extends Application {
	
	/* (non-Javadoc)
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 */
	@Override
	public void start(Stage mainWindow) throws Exception {

		Button game = new Button("Start");
		game.setLayoutX(380);
		game.setLayoutY(250);
		
		Button settings = new Button("Settings");
		settings.setLayoutX(380);
		settings.setLayoutY(275);
		
		Button exit = new Button("Exit");
		exit.setLayoutX(380);
		exit.setLayoutY(300);
		
		game.setOnAction(e -> {
			//Placeholder Code
		});
		settings.setOnAction(e -> {
			//Placeholder Code
		});
		exit.setOnAction(e -> {
			//Save Leaderboards
			Platform.exit();
		});
		
		AnchorPane layout = new AnchorPane();
		layout.getChildren().addAll(game, settings, exit);
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(layout);
		Scene scene = new Scene(borderPane, 800, 500);
		
		mainWindow.setTitle("Rooftop Renegade");
		mainWindow.setResizable(false);
		mainWindow.setMaxWidth(800);
		mainWindow.setMinWidth(800);
		mainWindow.setMaxHeight(500);
		mainWindow.setMinHeight(500);
		
		mainWindow.setScene(scene);
		mainWindow.show();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Displays the basic game information
	 * @param g The graphics context
	 **/
	public void draw (GraphicsContext gc){


	}

}
