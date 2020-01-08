package main;
import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainApplication extends Application {

	private Pane root = new Pane();
	
	private Sprite player = new Sprite(300, 280, 40, 60, "player", Color.BLUEVIOLET);
	Platform s = new Platform(0, 340, 800, 5, "platform", Color.BLACK);
	Platform s2 = new Platform(0, 240, 500, 5, "platform", Color.BLACK);
	private Point2D playerVelocity = new Point2D(0, 0);
	private boolean canJump = true;

	private Parent createContent() {
		root.setPrefSize(800, 600);
		
		root.getChildren().add(player);
		
		AnimationTimer timer = new AnimationTimer() {
			
			public void handle(long now) {
				update();
				System.out.println(System.currentTimeMillis());
			}
		};
		
		timer.start();
		
		Platform();
		
		return root;
	}
	
	private void Platform() {
		

		
		root.getChildren().addAll(s, s2);
	}

	private void update() {
		
	}
	
	public void start (Stage stage) throws Exception {
		Scene scene = new Scene(createContent());
		
		scene.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.W || e.getCode() == KeyCode.UP) {
				player.jump();
			}
			else if(e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN) {
				if(player.getBoundsInParent().intersects(s.getBoundsInParent()) || player.getBoundsInParent().intersects(s2.getBoundsInParent())) {
					boolean canJump = true;
				}
				else {
					player.tempGravity();
				}
				
			}
				
		});
		stage.setScene(scene);
		stage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}