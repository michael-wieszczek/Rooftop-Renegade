/**
 * 
 */
package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
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
		mainWindow.setTitle("Rooftop Renegade");
		
		mainWindow.setResizable(false);
		mainWindow.setMaxWidth(800);
		mainWindow.setMinWidth(800);
		mainWindow.setMaxHeight(600);
		mainWindow.setMinHeight(600);
		
		Canvas canvas = new Canvas(800, 600);
		final GraphicsContext gc = canvas.getGraphicsContext2D();
		canvas.setFocusTraversable(true);
		
		draw(gc);
		

		//Group group = new Group();
		StackPane layout = new StackPane();
		layout.getChildren().add(canvas);

		Scene scene = new Scene(layout, 300, 250);
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
	 * Display basic information from mouse listeners
	 * @param g The graphics context
	 **/
	public void draw (GraphicsContext gc){
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, 800, 600);
		
		gc.setFill(Color.BLACK);
		gc.fillRect(100, 100, 30, 50);


	}

}
