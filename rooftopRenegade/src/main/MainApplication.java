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
		gc.setFill(Color.BLACK);
		gc.fillRect(100, 250, 30, 50);


	}

}
