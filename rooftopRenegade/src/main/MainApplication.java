package main;
/**
 * 
 */

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;


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
		
		Rectangle r = new Rectangle();
		r.setFill(Color.LIGHTBLUE);
		//r.setX(0);
		//r.setY(0);
		r.setWidth(600);
		r.setHeight(400);
		//r.setArcWidth(20);
		//r.setArcHeight(20);	
	      		
		Button game = new Button("Start");		
		Button settings = new Button("Settings");		
		Button exit = new Button("Exit");
		
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
		
		// Buttom Menu
		GridPane menu = new GridPane();
		menu.add(game, 0, 0);
		menu.add(settings, 0, 1);
		menu.add(exit, 0, 2);
		GridPane.setHalignment(game,HPos.CENTER);
		GridPane.setValignment(game,VPos.BOTTOM);
		GridPane.setHalignment(settings,HPos.CENTER);
		GridPane.setHalignment(exit,HPos.CENTER);
		GridPane.setValignment(exit,VPos.TOP);
		ColumnConstraints column1 = new ColumnConstraints();
	    column1.setPercentWidth(100);
	    column1.setHgrow(Priority.ALWAYS);
	    menu.getColumnConstraints().addAll(column1);
		RowConstraints row1 = new RowConstraints(), row2 = new RowConstraints(), row3 = new RowConstraints();
	    row1.setPercentHeight(50);
	    row3.setPercentHeight(50);
	    row1.setVgrow(Priority.ALWAYS);
	    row3.setVgrow(Priority.ALWAYS);
	    menu.getRowConstraints().addAll(row1,row2,row3);
	    		
		BorderPane pane = new BorderPane();
		pane.setCenter(menu);
				
		Scene scene = new Scene(pane,800,500);
		
		mainWindow.setTitle("Rooftop Renegade");
		mainWindow.setResizable(false);		
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
