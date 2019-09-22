package mazegame;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Coin extends Sprite {
    /**
     * Constructor for coin object
     * x, y coordinates randomly generated
     *
     * @param mazeSideLength Side length of the maze
     */
    public Coin(int mazeSideLength) {
        int xCoordinate = ThreadLocalRandom.current().nextInt(1, mazeSideLength);
        int yCoordinate = ThreadLocalRandom.current().nextInt(1, mazeSideLength);
        setCoordinates(xCoordinate, yCoordinate);
    }

    /**
     * removes coin from list of coins if player x y coordinates equals coin x y coordinates
     *
     * @param currentCoins Array list of the current coins
     * @param player       Player object  to get the location
     * @return Updated Array List of coins
     */
    public ArrayList<Coin> removeCoin(ArrayList<Coin> currentCoins, Player player) {
        for (Coin c : currentCoins) {
            if (c.getXPos() == player.getXPos() && c.getYPos() == player.getYPos()) {
                currentCoins.remove(c);
            }
        }
        return currentCoins;
    }
}