/**
 * 
 */
package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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
		Button settings = new Button("Settings");
		Button exit = new Button("Exit");
		game.setOnAction(e -> System.out.println("Placeholder"));
		settings.setOnAction(e -> System.out.println("Placeholder"));
		exit.setOnAction(e -> {
			//Save Leaderboards
			System.exit(0);
		});
		VBox layout = new VBox();
		layout.getChildren().addAll(game, settings, exit);
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(layout);
		Scene scene = new Scene(borderPane, 500, 300);
		mainWindow.setTitle("Rooftop Renegade");
		mainWindow.setScene(scene);
		mainWindow.show();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}

}
