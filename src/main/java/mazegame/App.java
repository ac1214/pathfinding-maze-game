package mazegame;

import javafx.stage.Stage;
import javafx.event.EventHandler;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

public class App extends Application {
    private final double SCREENRESOLUTION = 700;

    private int level = 0;

    private int mazeSideLength;
    private final int INITIALMAZESIDELENGTH = 10;
    private final int MAZESIDEINCREMENT = 5;

    private int numOfLoops;
    private final int INITIALLOOPCOUNT = 15;
    private final int LOOPINCREMENT = 10;

    private MazeData mazeData;
    private GridData[][] mazeInfo;

    private int numOfCoins = 3;
    private ArrayList<Coin> currentCoins;

    private final Image PLAYERIMAGERIGHT = new Image(getClass().getResourceAsStream("/playerRight.png"));
    private final Image PLAYERIMAGEUP = new Image(getClass().getResourceAsStream("/playerUp.png"));
    private final Image PLAYERIMAGELEFT = new Image(getClass().getResourceAsStream("/playerLeft.png"));
    private final Image PLAYERIMAGEDOWN = new Image(getClass().getResourceAsStream("/playerDown.png"));

    private Image ENEMYIMAGE = new Image(getClass().getResourceAsStream("/enemy.png"));
    private final Image COINIMAGE = new Image(getClass().getResourceAsStream("/coin.png"));

    private final ImageView BACKGROUNDIMAGE = new ImageView(new Image(getClass().getResourceAsStream("/grass.png")));

    private Image currentPlayerImage = PLAYERIMAGERIGHT;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        setMazeAttributes();
        Group root = new Group();
        Scene scene = new Scene(root, SCREENRESOLUTION, SCREENRESOLUTION);

        stage.setTitle("Maze");
        stage.setScene(scene);
        stage.show();

        Player player = new Player(0, 0);
        Enemy enemy = new Enemy(mazeSideLength, mazeInfo);

        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 28_000_0000) {
                    Group root = new Group();
                    Scene scene = new Scene(root, SCREENRESOLUTION, SCREENRESOLUTION);
                    stage.setScene(scene);
                    scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
                        @Override
                        public void handle(KeyEvent key) {
                            final Canvas canvas = new Canvas(SCREENRESOLUTION, SCREENRESOLUTION);
                            GraphicsContext gc = canvas.getGraphicsContext2D();

                            if (key.getCode() == KeyCode.W) {
                                player.movePlayer("W", mazeInfo);
                                currentPlayerImage = PLAYERIMAGEUP;
                            }
                            if (key.getCode() == KeyCode.A) {
                                player.movePlayer("A", mazeInfo);
                                currentPlayerImage = PLAYERIMAGELEFT;
                            }
                            if (key.getCode() == KeyCode.S) {
                                player.movePlayer("S", mazeInfo);
                                currentPlayerImage = PLAYERIMAGEDOWN;
                            }
                            if (key.getCode() == KeyCode.D) {
                                player.movePlayer("D", mazeInfo);
                                currentPlayerImage = PLAYERIMAGERIGHT;
                            }

                            GameManagement.displaySprites(gc, SCREENRESOLUTION, mazeSideLength, currentPlayerImage, ENEMYIMAGE, player, enemy);

                            root.getChildren().clear();

                            GameManagement.displayMaze(gc, mazeInfo, SCREENRESOLUTION, mazeSideLength);
                            GameManagement.removeCoin(player, currentCoins);
                            GameManagement.displayCoins(gc, currentCoins, SCREENRESOLUTION, mazeSideLength, COINIMAGE);
                            root.getChildren().add(BACKGROUNDIMAGE);
                            root.getChildren().add(canvas);

                            checkCoins(player, enemy);
                        }

                    });

                    final Canvas canvas = new Canvas(SCREENRESOLUTION, SCREENRESOLUTION);
                    GraphicsContext gc = canvas.getGraphicsContext2D();

                    enemy.pathfinding(player.getCoordinates());
                    enemy.moveEnemy();

                    GameManagement.displayMaze(gc, mazeInfo, SCREENRESOLUTION, mazeSideLength);
                    GameManagement.removeCoin(player, currentCoins);
                    GameManagement.displayCoins(gc, currentCoins, SCREENRESOLUTION, mazeSideLength, COINIMAGE);
                    GameManagement.displaySprites(gc, SCREENRESOLUTION, mazeSideLength, currentPlayerImage, ENEMYIMAGE, player, enemy);

                    root.getChildren().add(BACKGROUNDIMAGE);
                    root.getChildren().add(canvas);

                    lastUpdate = now;
                }
                checkIfCaught(player, enemy);
            }
        };

        gameLoop.start();
    }

    /**
     * This method checks if the player is caught and resets the game if the player has been caught
     *
     * @param player Player object to check coordinates
     * @param enemy  Enemy object to check coordinates
     */
    public void checkIfCaught(Player player, Enemy enemy) {
        if (GameManagement.playerCaught(player, enemy)) {
            resetGame();
            currentCoins = GameManagement.createCoins(numOfCoins, mazeSideLength);
            player.setCoordinates(0, 0);
            enemy.setNewLevelData(mazeInfo, mazeSideLength);
        }
    }

    /**
     * Checks if there are still coins on the board if there are none the game will level up
     *
     * @param player Player object to check coordinates
     * @param enemy  Enemy object to set new coordinates
     */
    public void checkCoins(Player player, Enemy enemy) {
        if (currentCoins.size() == 0) {
            currentCoins = GameManagement.createCoins(numOfCoins, mazeSideLength);
            increaseDifficulty();
            setMazeAttributes();
            player.setCoordinates(0, 0);
            enemy.setNewLevelData(mazeInfo, mazeSideLength);
        }
    }

    public void resetGame() {
        level = 0;
        setMazeAttributes();
    }

    /**
     * This method sets the initial maze attributes to the default values
     */
    public void setMazeAttributes() {
        this.mazeSideLength = INITIALMAZESIDELENGTH + level * MAZESIDEINCREMENT;
        this.numOfLoops = INITIALLOOPCOUNT + level * LOOPINCREMENT;

        this.mazeData = new MazeData(mazeSideLength, mazeSideLength);
        this.currentCoins = GameManagement.createCoins(numOfCoins, mazeSideLength);

        mazeData.generateMaze();
        mazeData.addLoops(numOfLoops);
        mazeInfo = mazeData.getMazeInfo();
    }

    public void increaseDifficulty() {
        this.level++;
        this.currentPlayerImage = PLAYERIMAGERIGHT;
    }
}
