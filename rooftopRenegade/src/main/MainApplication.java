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
	Sprite s = new Sprite(0, 340, 800, 5, "platform", Color.BLACK);
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
		
		Sprite s = new Sprite(0, 340, 800, 5, "platform", Color.BLACK);
		
		root.getChildren().add(s);
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
				if(player.getBoundsInParent().intersects(s.getBoundsInParent())) {
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
	
	
	private static class Sprite extends Rectangle {
		boolean dead = false;
		final String type;
		
		Sprite(int x, int y, int w, int h, String type, Color color) {
			super(w, h, color);
			
			this.type = type;
			setTranslateX(x);
			setTranslateY(y);
			
		}
		
		void jump() {
			setTranslateY(getTranslateY() - 5);
		}
		
		void tempGravity() {
			setTranslateY(getTranslateY() + 5);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}