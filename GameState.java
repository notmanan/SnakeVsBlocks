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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameState implements Serializable {
	transient Group g, blockGroup, wallGroup, tokenGroup, buttonGroup;
	transient public Scene gameScene;

	private static final long serialVersionUID = 1L;
	Snake s;
	LinkedList<Block> blockList;
	LinkedList<Wall> wallList;
	LinkedList<Token> tokenList;

	Game parentGame;
	double prevFrameTime = 0;
	double fps = 0;

	int score = 0;
	transient Text scoreCard;
	destroyBlockSphere dbs;
	magnetSphere ms;

	boolean arrestGame, gameOver;

	double framesElapsed = -3;
	double moveSnakeSpeed = 10;
	double gameScreenSpeed = 3;
	int blockCount = 0;
	int windowCount = 0;

	public double returnEffectiveGameSpeed() {
		return gameScreenSpeed * (arrestGame ? 0 : 1);
	}

	public GameState(Game parentGame) {
		gameOver = false;
		this.parentGame = parentGame;
		s = new Snake(this);
		wallList = new LinkedList<>();
		blockList = new LinkedList<>();
		tokenList = new LinkedList<>();
		score = 0;
		arrestGame = false;
	}

	public void deserialize() {
		for (Wall w : wallList) {
			w.deserialize();
		}
		for (Block b : blockList) {
			b.deserialize();
		}

		for (Token t : tokenList) {
			if (t instanceof BallToken) {
				((BallToken) t).deserialize();
			} else if (t instanceof DestroyToken) {
				((DestroyToken) t).deserialize();
			} else if (t instanceof ShieldToken) {
				((ShieldToken) t).deserialize();
			} else if (t instanceof MagnetToken) {
				((MagnetToken) t).deserialize();
			} else {
				System.out.println("wat 2000");
			}
		}
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

	private void importSerializedBlocks() {
		for (Block b : blockList) {
			blockGroup.getChildren().add(b.bt);
		}
	}

	private void importSerializedWalls() {
		for (Wall w : wallList) {
			wallGroup.getChildren().add(w.wall);
		}
	}

	private void importSerializedTokens() {
		for (Token t : tokenList) {
			blockGroup.getChildren().add(t.obj);
		}
	}

	public void begin(Stage primaryStage) {
		System.out.println("New Game begun");
		scoreCard = new Text(20, 60, "" + score);
		scoreCard.setFill(Color.RED);
		String scoreCardCss = ".modal-dialog {\r\n" + "    -fx-padding: 20;\r\n" + "    -fx-spacing: 10;\r\n"
				+ "    -fx-alignment: center;\r\n" + "    -fx-font-size: 60;\r\n" + "}";
		scoreCard.setStyle(scoreCardCss);
		g = new Group();
		blockGroup = new Group();
		wallGroup = new Group();
		tokenGroup = new Group();

		// TODO
		importSerializedBlocks();
		importSerializedWalls();
		importSerializedTokens();

		g.getChildren().addAll(blockGroup, wallGroup, tokenGroup, scoreCard);

		AnimationTimer timer = new AnimationTimer() {
			@Override
			public void handle(long now) {
				fps = getFramesPerSecond(now);
				framesElapsed += returnEffectiveGameSpeed();
				if (spawnWindow()) {
					Random rand = new Random();
					int ch = rand.nextInt(80);
					if (ch > 60) {
						spawnWall();
					}
					boolean allowBlock = blockSpawnWindow();
					if (allowBlock) {
						spawnBlocks(6);
					}
					if (!allowBlock) {
						spawnToken();
					}
				}
				updateOnScreenElements();

				if (checkBlockCollisions() || checkWallCollisions()) {
					arrestMovement();
				} else {
					unarrestMovement();
				}

				collectTokens();
				handleDBS();
				handleMS();

				s.updateNodes();
			}
		};
		timer.start();

		g.getChildren().add(s.st);
		g.getChildren().addAll(s.getSnakeNodes());

		gameScene = new Scene(g, 603, 903);
		gameScene.setFill(Color.BLACK);

		gameScene.setOnKeyPressed(e -> {
			double keyBoardX = s.getSnakeNodes().get(0).getCenterX();
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
			} else if (e.getCode() == KeyCode.S) {
				System.out.println("attempting serialization");
				parentGame.serialize();
			} else if (e.getCode() == KeyCode.I) {
				s.increaseLength(5);
			}
			s.setPositionX(keyBoardX);
		});

		gameScene.setOnMouseMoved(e -> {
			double mouseX = e.getX();
			s.setPositionX(mouseX);
		});

		primaryStage.setScene(gameScene);
	}

	protected void handleMS() {
		// TODO Auto-generated method stub
		if (ms == null) {
			// do nothing
		} else {
			if (!g.getChildren().contains(ms.sphere)) {
				g.getChildren().add(ms.sphere);
			}

			for (Token t : tokenList) {
				if (t instanceof BallToken && isColliding(ms.sphere, t.obj)) {
					t.attractToken(ms);
				}
			}

			ms.animate();
			if (!ms.alive) {
				g.getChildren().remove(ms.sphere);
				ms = null;
			}
		}
	}

	protected void handleDBS() {
		if (dbs == null) {
			// do nothing
		} else {
			if (!g.getChildren().contains(dbs.sphere)) {
				g.getChildren().add(dbs.sphere);
			}

			for (Block b : blockList) {
				if (isColliding(dbs.sphere, b.bt)) {
					updateScore(b.blockVal);
					b.burst();
				}

			}

			dbs.animate(returnEffectiveGameSpeed());
			if (!dbs.alive) {
				g.getChildren().remove(dbs.sphere);
				dbs = null;
			}
		}
	}

	protected void collectTokens() {
		// TODO Auto-generated method stub
		for (Token t : tokenList) {
			if (isColliding(s.returnHead(), t.obj) && t.tokenAlive) {
				t.activateToken(s);
			}
		}
	}

	protected boolean checkBlockCollisions() {
		boolean collisionFound = false;
		for (Block b : blockList) {
			if (isColliding(s.returnHead(), b.bt)) {
				collisionFound = collisionFound || true;
				b.handleCollision(s);
			}
		}
		return collisionFound;
	}

	protected boolean checkWallCollisions() {
		boolean collisionFound = false;
		for (Wall w : wallList) {
			if (isColliding(s.returnHead(), w.wall)) {
				System.out.println("collision with wall");
				collisionFound = collisionFound || true;
			}
		}
		return collisionFound;
	}

	private void unarrestMovement() {
		arrestGame = false;
	}

	private void arrestMovement() {
		arrestGame = true;
	}

	protected void spawnToken() {
		Random rand = new Random();
		int tokenSpawnner = rand.nextInt(1000);
		if (tokenSpawnner < 400) {
		} else if (tokenSpawnner < 700) {
			spawnBallToken();
		} else if (tokenSpawnner < 800) {
			spawnDestroyBlockToken();
		} else if (tokenSpawnner < 900) {
			spawnMagnetToken();
		} else {
			spawnShieldToken();
		}
	}

	private void spawnShieldToken() {
		ShieldToken temp = new ShieldToken(this);
		tokenList.add(temp);
		tokenGroup.getChildren().add(tokenList.getLast().obj);
	}

	private void spawnMagnetToken() {
		MagnetToken temp = new MagnetToken(this);
		tokenList.add(temp);
		tokenGroup.getChildren().add(tokenList.getLast().obj);
	}

	private void spawnDestroyBlockToken() {
		DestroyToken temp = new DestroyToken(this);
		tokenList.add(temp);
		tokenGroup.getChildren().add(tokenList.getLast().obj);
	}

	private void spawnBallToken() {
		BallToken temp = new BallToken(this);
		tokenList.add(temp);
		tokenGroup.getChildren().add(tokenList.getLast().obj);
	}

	protected void updateOnScreenElements() {
		updateBlockLocation();
		updateTokenLocation();
		updateWallLocation();

		deleteDeadBlocks();
		deleteDeadTokens();
		deleteDeadWalls();
	}

	private void updateTokenLocation() {
		for (Token t : tokenList) {
			t.moveForward(returnEffectiveGameSpeed());
		}
	}

	protected void updateBlockLocation() {
		for (int i = 0; i < blockList.size(); i++) {
			blockList.get(i).moveForward((returnEffectiveGameSpeed()));
		}
	}

	private void updateWallLocation() {
		for (Wall w : wallList) {
			w.moveForward(returnEffectiveGameSpeed());
		}
	}

	private void deleteDeadBlocks() {
		for (int i = 0; i < blockList.size(); i++) {
			Block temp = blockList.get(i);
			if (!temp.checkAlive()) {
				blockGroup.getChildren().remove(temp.bt);
				blockList.remove(temp);
				i--;
			}
		}
	}

	private void deleteDeadWalls() {
		for (int i = 0; i < wallList.size(); i++) {
			Wall temp = wallList.get(i);
			if (!temp.checkAlive()) {
				wallGroup.getChildren().remove(temp.wall);
				wallList.remove(temp);
				i--;
			}
		}
	}

	private void deleteDeadTokens() {
		for (int i = 0; i < tokenList.size(); i++) {
			Token temp = tokenList.get(i);
			if (!temp.checkAlive()) {
				tokenGroup.getChildren().remove(temp.obj);
				tokenList.remove(temp);
				i--;
			}
		}
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

	protected void spawnBlocks(int maxBlocks) {
		Random rand = new Random();
		int n = rand.nextInt(20);
		int blockSpawnCount = 0;
		if (n <= 20) {
			blockSpawnCount = (n / 3) > maxBlocks ? maxBlocks : (n / 3);
//			System.out.println("blocks: " + blockSpawnCount);
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

	public void spawnWall() {
		Wall w = new Wall();
		wallList.add(w);
		wallGroup.getChildren().add(w.wall);
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

	public boolean isColliding(Circle imageview, StackPane bt) {
		return imageview.getBoundsInParent().intersects(bt.getBoundsInParent());
	}

	boolean isColliding(Circle imageview, Rectangle wall) {
		return imageview.getBoundsInParent().intersects(wall.getBoundsInParent());
	}

	public void updateScore(int blockVal) {
		// TODO Auto-generated method stub
		score += blockVal;
		scoreCard.setText("" + score);
	}
}