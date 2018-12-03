// figure out how the animations move
// figure out their interactions
// figure out appropriate bounds

// game can either be paused or lost
// if the game is lost --> setPaused to false, save score to LeaderBoard. [add
// functionality so that only top 10 highscores are added to leaderboard
// if the game is paused -> setPaused to true, serialize all the data.

package SnakeVsBlocks;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameState implements Serializable {
	private static final long serialVersionUID = 1L;
	transient Group g, blockGroup, wallGroup, tokenGroup, buttonGroup;
	transient public Scene gameScene;
	private Snake s;
//	private LinkedList<Wall> wallList;
	private LinkedList<Block> blockList;
//	private LinkedList<Token> tokenList;
	Game parentGame;
	double prevFrameTime = 0;
	double fps = 0;
	private int score;
	private boolean gamePaused;
	double framesElapsed = -3;
	double moveSnakeSpeed = 10;
	double gameScreenSpeed = 3;
	int blockCount = 0;
	int windowCount = 0;

	public GameState(Game parentGame) {
		this.parentGame = parentGame;
		s = new Snake(this);
//		wallList = new LinkedList<>();
		blockList = new LinkedList<>();
//		tokenList = new LinkedList<>();
		score = 0;
		gamePaused = false;
	}
	
	public void deserialize() {
//		for(Wall w: wallList) {
//			// TODO w.deserialize();
//		}
		for(Block b : blockList) {
			b.deserialize();
		}
//		for(Token t: tokenList) {
//			//TODO t.deserialize();
//		}
		s.deserialize();
	}

	public Snake getS() {
		return s;
	}

	public void setS(Snake s) {
		this.s = s;
	}

	public void addBlock(Block b) {
		blockList.add(b);
	}

	public void removeBlock(Block b) {
		try {
			blockList.remove(b);
		} catch (Exception e) {
		}

	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void importSerializedBlocks() {
		for(Block b : blockList) {
			blockGroup.getChildren().add(b.bt);
		}		
	}

	public void begin(Stage primaryStage) {
		System.out.println("New Game begun");
		g = new Group();
		blockGroup = new Group();
		
		importSerializedBlocks();

		g.getChildren().add(blockGroup);
		AnimationTimer timer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				fps = getFramesPerSecond(now);
				framesElapsed += gameScreenSpeed;
				if (spawnWindow()) {
					System.out.println("Window Found : " + framesElapsed);
					boolean allowBlock = blockSpawnWindow();
					if (allowBlock) {
						spawnBlocks(6);
					}
					if (!allowBlock) {
						System.out.println("Token Spawn Window: " + framesElapsed);
//						spawnBlocks(1, Color.RED);
					}

				}

				updateOnScreenElements();

				s.updateNodes();
			}
		};
		timer.start();

		g.getChildren().add(s.st);
		g.getChildren().addAll(s.getSnakeNodes());

		gameScene = new Scene(g, 603, 903);

		gameScene.setOnKeyPressed(e -> {
			double keyBoardX = s.getSnakeNodes().get(0).getCenterX();
			// TODO change snake movement to speed model instead
			if (e.getCode() == KeyCode.LEFT) {
				keyBoardX = s.getSnakeNodes().get(0).getCenterX() - moveSnakeSpeed;
			} else if (e.getCode() == KeyCode.RIGHT) {
				keyBoardX = s.getSnakeNodes().get(0).getCenterX() + moveSnakeSpeed;
			} else if (e.getCode() == KeyCode.UP) {
				System.out.println("UP");
				changeGameSpeed(gameScreenSpeed + 1);
			} else if (e.getCode() == KeyCode.DOWN) {
				System.out.println("DOWN");
				changeGameSpeed(gameScreenSpeed - 1);
			} else if(e.getCode() == KeyCode.S){
				System.out.println("attempting serialization");
				parentGame.serialize();
			} else if(e.getCode() == KeyCode.D){
				System.out.println("attempting deserialization");
				parentGame.deserialize();
			}
			s.setPositionX(keyBoardX);
		});

		gameScene.setOnMouseMoved(e -> {
			double mouseX = e.getX();
			s.setPositionX(mouseX);
		});

		primaryStage.setScene(gameScene);
	}

	protected void updateOnScreenElements() {
		// TODO Auto-generated method stub
		updateBlockLocation();
		deleteDeadBlocks();

//		updateTokenLocation();
//		deleteDeadTokens();

//		updateWallLocation();
//		deleteDealWalls();

	}

	private void changeGameSpeed(double d) {
		if (d <= 0) {
			gameScreenSpeed = 0;
		} else {
			gameScreenSpeed = d;
			System.out.println();
			System.out.println("frames initial: " + framesElapsed);
			System.out.println("frames offset: " + (gameScreenSpeed - framesElapsed % gameScreenSpeed));
			framesElapsed = framesElapsed + (gameScreenSpeed - framesElapsed % gameScreenSpeed);
			System.out.println("frames final: " + framesElapsed);
			System.out.println("current gameSpeed: " + gameScreenSpeed);
			System.out.println();
		}
	}

	protected void deleteDeadBlocks() {
		for (int i = 0; i < blockList.size(); i++) {
			Block temp = blockList.get(i);
			if (!temp.checkAlive()) {
				blockGroup.getChildren().remove(temp.bt);
				blockList.remove(temp);
				i--;
			}
		}
	}

	protected void updateBlockLocation() {
		for (int i = 0; i < blockList.size(); i++) {
			Block temp = blockList.get(i);
			temp.moveForward((gameScreenSpeed));
		}
	}
	
	protected void spawnBlocks(int maxBlocks) {
		Random rand = new Random();
		int n = rand.nextInt(20);
		int blockSpawnCount = 0;
		if (n <= 20) {
			blockSpawnCount = (n / 3) > maxBlocks ? maxBlocks : (n / 3);
			System.out.println("blocks: " + blockSpawnCount);
		}
		LinkedList<Integer> blockLoc = new LinkedList<>();

		while (blockSpawnCount != 0) {
			int nnn = rand.nextInt(6) + 0;
			if (!blockLoc.contains(nnn)) {
				blockLoc.add(nnn);
				blockSpawnCount--;
			}
		}

		for (int i : blockLoc) {
			Block newBlock = new Block(i);
			blockList.add(newBlock);
			blockGroup.getChildren().add(newBlock.bt);
		}
	}

	public boolean spawnWindow() {
		if (framesElapsed > windowCount * 100) {
			windowCount++;
			return true;
		} else {
			return false;
		}
	}

	public boolean blockSpawnWindow() {
		if (framesElapsed > blockCount * 200) {
			blockCount++;
			System.out.println("Block Spawn Window: " + framesElapsed);
			return true;
		} else {
			return false;
		}
	}

	private double getFramesPerSecond(double now) {
		double currFrameTime = now;
		double timeDiff = (currFrameTime - prevFrameTime) * Math.pow(10, -9);
		double fps = 1 / timeDiff;
		prevFrameTime = currFrameTime;
		return fps;
	}

	public void pause() {
		System.out.println("Game Paused");
	}

	public void resume() {
		System.out.println("Game Resumed");
	}

	public boolean getGamePaused() {
		return gamePaused;
	}

	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;
	}
}