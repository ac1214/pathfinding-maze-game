package mazegame;


public class Player extends Sprite {
    private int collectedCoins = 0;

    /**
     * A constructor for player object.
     * @param x X coordinate of player
     * @param y Y coordinate of player
     */
    public Player(int x, int y){
        setCoordinates(x, y);
    }

    /**
     * Keeps track of the number of coins the player has collected
     */
    public void collectCoin() {
        collectedCoins++;
        System.out.println(collectedCoins);
    }

    /**
     * Sets collectedCoins to 0.
     */
    public void resetCoins() {
        collectedCoins = 0;
    }

    /**
     * Moves the player in the indicated direction by the user
     * @param keyPressed Key that the player has pressed
     * @param mazeInfo Current maze properties
     */
    public void movePlayer(String keyPressed, GridData[][] mazeInfo) {

        if(keyPressed == "W") {
            checkMove("up", mazeInfo);
        }
        else if(keyPressed == "A") {
            checkMove("left", mazeInfo);
        }
        else if(keyPressed == "S") {
            checkMove("down", mazeInfo);
        }
        else if(keyPressed == "D") {
            checkMove("right", mazeInfo);
        }
    }

    /**
     * checks if user input for move player is valid
     * if it is valid changes player coordinate accordingly.
     * @param direction Direction of the player movement
     * @param mazeInfo Current maze properties
     */
    public void checkMove(String direction, GridData[][] mazeInfo) {
        GridData currentTile = mazeInfo[getYCoord()][getXCoord()];

        if(direction == "left") {
            if(currentTile.getLeftWall() == false) {
                moveLeft();
            }
        }
        else if(direction == "up") {
            if(currentTile.getTopWall() == false) {
                moveUp();
            }
        }
        else if(direction == "right") {
            if(currentTile.getRightWall() == false) {
                moveRight();
            }
        }
        else {
            if(currentTile.getBottomWall() == false) {
                moveDown();
            }
        }
    }
}
