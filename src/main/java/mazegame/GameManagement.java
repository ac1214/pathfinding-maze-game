package mazegame;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class GameManagement {
    /**
     * This method will display the lines of the maze
     *
     * @param gc               GraphicsContext to draw the lines
     * @param mazeInfo         all of the properties of the maze
     * @param screenResolution Resolution of the maze window
     * @param numOfGrids       side length of the maze
     */
    public static void displayMaze(GraphicsContext gc, GridData[][] mazeInfo, double screenResolution, int numOfGrids) {
        double lineWidth = 2;
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(lineWidth);

        drawBorders(gc, screenResolution);

        for (int i = 0; i < mazeInfo.length; i++) {
            for (int j = 0; j < mazeInfo[i].length; j++) {
                drawGrid(gc, i, j, mazeInfo, screenResolution, numOfGrids);
            }
        }
    }

    /**
     * This method will draw borders on the edges of the screen
     *
     * @param gc               GraphicsContext to draw the lines
     * @param screenResolution Resolution of the maze window
     */
    public static void drawBorders(GraphicsContext gc, double screenResolution) {
        double PADDING = 1;
        gc.strokeLine(PADDING, PADDING, screenResolution - PADDING, PADDING);
        gc.strokeLine(PADDING, PADDING, PADDING, screenResolution - PADDING);
        gc.strokeLine(screenResolution - PADDING, PADDING, screenResolution - PADDING, screenResolution - PADDING);
        gc.strokeLine(PADDING, screenResolution - PADDING, screenResolution - PADDING, screenResolution - PADDING);
    }

    /**
     * This method will draw each individual grids taking into account which sides should have walls
     *
     * @param gc               GraphicsContext to draw the lines
     * @param row              Row of the grid
     * @param column           Column of the grid
     * @param mazeInfo         Property of the whole maze
     * @param screenResolution side resolution of the window
     * @param numOfGrids       side length of the maze
     */
    private static void drawGrid(GraphicsContext gc, int row, int column, GridData[][] mazeInfo, double screenResolution, int numOfGrids) {
        double x1 = column * (screenResolution / numOfGrids);
        double y1 = row * (screenResolution / numOfGrids);

        double x2 = (column + 1) * (screenResolution / numOfGrids);
        double y2 = (row + 1) * (screenResolution / numOfGrids);

        if (mazeInfo[row][column].getRightWall() == true) {
            gc.strokeLine(x2, y1, x2, y2);
        }
        if (mazeInfo[row][column].getBottomWall() == true) {
            gc.strokeLine(x1, y2, x2, y2);
        }
    }

    /**
     * This method will display the coins in the maze
     *
     * @param gc               GraphicsContext to draw the lines
     * @param currentCoins     Array that contain the property of the coins(location)
     * @param screenResolution side resolution of the window
     * @param sideLength       side length of the maze
     * @param cheeseImage      Image for the coins
     */
    public static void displayCoins(GraphicsContext gc, ArrayList<Coin> currentCoins, double screenResolution, int sideLength, Image cheeseImage) {
        for (int i = 0; i < currentCoins.size(); i++) {
            currentCoins.get(i).displaySprite(gc, screenResolution, sideLength, cheeseImage, 1.1);
        }
    }

    /**
     * This method will check the player location and if they are in the same location as the coin the coin will be removed
     *
     * @param player       Player object to get coordinates
     * @param currentCoins ArrayList of coins that contain their location
     */
    public static void removeCoin(Player player, ArrayList<Coin> currentCoins) {
        for (Coin coin : currentCoins) {
            if (player.equalPosition(coin)) {
                currentCoins.remove(coin);
                break;
            }
        }
    }

    /**
     * This method will check if the player has been caught by the enemy
     *
     * @param player Player object that contains the coordinates
     * @param enemy  Enemy object that contains the coordinates
     * @return boolean value of if the player has been caught
     */
    public static boolean playerCaught(Player player, Enemy enemy) {
        if (player.equalPosition(enemy)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method will generate a specified number of coins randomly in the maze
     *
     * @param numberOfCoins  number of coins to be generated
     * @param mazeSideLength Side length of the maze
     * @return ArrayList of generated coins
     */
    public static ArrayList<Coin> createCoins(int numberOfCoins, int mazeSideLength) {
        ArrayList<Coin> coins = new ArrayList<>();

        for (int i = 0; i < numberOfCoins; i++) {
            coins.add(new Coin(mazeSideLength));
        }

        return coins;
    }

    /**
     * This method will display the player and enemy sprites on the maze
     *
     * @param gc               GraphicsContext to draw the lines
     * @param screenResolution side resolution of the window
     * @param mazeSideLength   side length of the maze(in grids)
     * @param playerImage      image to display for the player sprite
     * @param enemyImage       image to display for the enemy sprite
     * @param player           Player object to get coordinates
     * @param enemy            Enemy object to get coordinates
     */
    public static void displaySprites(GraphicsContext gc, double screenResolution, int mazeSideLength, Image playerImage, Image enemyImage, Player player, Enemy enemy) {
        player.displaySprite(gc, screenResolution, mazeSideLength, playerImage, 1);
        enemy.displaySprite(gc, screenResolution, mazeSideLength, enemyImage, 1);
    }
}
