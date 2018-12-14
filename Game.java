package SnakeVsBlocks;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Game extends Application implements Serializable {
	private static final long serialVersionUID = 1L;
	GameState gs;
	LinkedList<RankingPosition> Leaderboard;

	transient public Scene mainScene, leaderBoardScene;
	transient Stage primaryStage;
	transient public Button playbtn, resumeBtn, leaderboardBtn;
	
	String filename = "myGameinfo.txt";
	static String[] args;

	public Game() {
		gs = new GameState(this);

		Leaderboard = new LinkedList<>();
		addToLeaderBoard(new RankingPosition("Dummies", 10));
		addToLeaderBoard(new RankingPosition("Dummies", 20));
		addToLeaderBoard(new RankingPosition("Dummies", 30));
		addToLeaderBoard(new RankingPosition("Dummies", 40));
		addToLeaderBoard(new RankingPosition("Dummies", 50));
		addToLeaderBoard(new RankingPosition("Dummies", 60));
		addToLeaderBoard(new RankingPosition("Dummies", 70));
		addToLeaderBoard(new RankingPosition("Dummies", 80));
		addToLeaderBoard(new RankingPosition("Dummies", 90));
		addToLeaderBoard(new RankingPosition("Dummies", 0));
	}

	public GameState getGs() {
		return gs;
	}

	public void setGs(GameState gs) {
		this.gs = gs;
	}

	public LinkedList<RankingPosition> getLeaderboard() {
		return Leaderboard;
	}

	public void setLeaderboard(LinkedList<RankingPosition> leaderboard) {
		Leaderboard = leaderboard;
	}

	public void addToLeaderBoard(RankingPosition rp) {
		Leaderboard.add(rp);
		Collections.sort(Leaderboard);
	}

	public void leaderBoardPage(Stage primaryStage) {
		// TODO works in terms of functionality, fix looks, implement better ways of
		// doing the same thing
		Label leaderLabel = new Label();
		leaderLabel.setText("Leaderboard");

		Button backBtn = new Button();
		backBtn.setText("Back");
		backBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("Hello Back!");
				mainPage(primaryStage);
			}
		});
		backBtn.setTranslateY(800);

		Pane p = new Pane();
		p.getChildren().addAll(leaderLabel, backBtn);
		leaderBoardScene = new Scene(p, 603, 903);
		primaryStage.setScene(leaderBoardScene);

		centerOnX(leaderBoardScene, leaderLabel);
		centerOnX(leaderBoardScene, backBtn);

		for (int i = -1; i < getLeaderboard().size() && i < 10; i++) {
			Label l1, l2, l3;

			String stylePreset;
			if (i == -1) {
				l1 = new Label("Name");
				l2 = new Label("Score");
				l3 = new Label("Date");
				stylePreset = "-fx-background-color: transparent; -fx-text-fill: blue; -fx-font-size: 20px; -fx-font-family: \"Impact\";-fx-alignment: center ;";
			} else {
				RankingPosition rank = getLeaderboard().get(i);
				l1 = new Label((i + 1) + ". " + rank.name);
				l2 = new Label("" + rank.score);
				l3 = new Label(rank.dateString);
				stylePreset = "-fx-background-color: transparent; -fx-text-fill: red; -fx-font-size: 20px; -fx-font-family: \"Impact\";-fx-alignment: center ;";
			}

			l1.setTranslateX(20);
			l2.setTranslateX(230);
			l3.setTranslateX(330);

			l1.setStyle(stylePreset);
			l2.setStyle(stylePreset);
			l3.setStyle(stylePreset);

			int yLine = 40;
			int yFirst = 200;
			l1.setTranslateY(yFirst + (i * yLine));
			l2.setTranslateY(yFirst + (i * yLine));
			l3.setTranslateY(yFirst + (i * yLine));
			p.getChildren().addAll(l1, l2, l3);
		}

	}

	public static void main(String[] argss) {
		args = argss;
		launch(args);
	}

	public void start(Stage primaryStage) {
		deserialize();
		this.primaryStage = primaryStage;
		Pane p = new Pane();
		mainScene = new Scene(p, 603, 903);
		primaryStage.setScene(mainScene);
		playbtn = new Button("Play Game");
		playbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("Hello Play!");
				gs.begin(primaryStage);
			}
		});

		resumeBtn = new Button("Resume Game");
		resumeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Hello Resume!");
//				TODO gs.resume();
			}
		});
		resumeBtn.setTranslateY(100);

		leaderboardBtn = new Button("Show Leaderboard");
		leaderboardBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.out.println("Hello Leader!");
				leaderBoardPage(primaryStage);
			}
		});
		leaderboardBtn.setTranslateY(200);

		p.getChildren().addAll(playbtn, resumeBtn, leaderboardBtn);
		System.out.println("Running");
		primaryStage.setResizable(false);
		primaryStage.show();

		centerOnX(mainScene, playbtn);
		centerOnX(mainScene, resumeBtn);
		centerOnX(mainScene, leaderboardBtn);
		mainPage(primaryStage);
		resumeBtn.setVisible(!gs.gameOver);
	}

	public void centerOnX(Scene s, Button b) {
		b.setTranslateX(s.getWidth() / 2 - b.getWidth() / 2);
	}

	public void centerOnX(Scene s, Label l) {
		l.setTranslateX(s.getWidth() / 2 - l.getWidth() / 2);
	}

	public void mainPage(Stage primaryStage) {
		// TODO Display Main Page : Start and Leaderboard. Optional Resume game if the
		// game
		// was paused the last time.
		// Options : sound, themes, colors, CONTROLS
		primaryStage.setScene(mainScene);
		resumeBtn.setVisible(!gs.gameOver);
	}

	public void serialize() {
		// TODO Auto-generated method stub
		try {
			// Saving of object in a file
			FileOutputStream file = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(file);

			// Method for serialization of object
			out.writeObject(this);
			out.close();
			file.close();

			System.out.println("Object has been serialized");

		} catch (Exception e) {
			e.printStackTrace();
		}
//
//		catch (IOException ex) {
//			System.out.println("IOException is caught");
//		}
	}

	public void deserialize() {
		try {
			// Reading the object from a file
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);

			// Method for deserialization of object
			Game deserialized = (Game) in.readObject();
			completeDeserialization(deserialized);
			in.close();
			file.close();

			if(gs.ms!=null) {
				gs.ms.deserialize();
			}
			if(gs.dbs!=null) {
				gs.dbs.deserialize();
			}
			
			System.out.println("Object has been deserialized ");
		}

		catch (IOException ex) {
			System.out.println("IOException is caught");
		}

		catch (ClassNotFoundException ex) {
			System.out.println("ClassNotFoundException is caught");
		}
	}

	private void completeDeserialization(Game deserialized) {
		// TODO Auto-generated method stub
		this.gs = deserialized.gs;
		Leaderboard = deserialized.Leaderboard;
		filename = deserialized.filename;
		gs.deserialize();
//		transient public Scene mainScene, leaderBoardScene;
//		transient public Button playbtn, resumeBtn, leaderboardBtn;
	}

}
