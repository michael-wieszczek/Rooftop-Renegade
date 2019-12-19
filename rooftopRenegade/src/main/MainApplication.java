/**
 * 
 */
package main;

import javafx.application.Application;
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
		mainWindow.show();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);

	}

}
