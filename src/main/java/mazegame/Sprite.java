package mazegame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

abstract class Sprite {
    private int xCoordinate = 0;
    private int yCoordinate = 0;

    /**
     * This method sets the coordinates of the Sprite.
     * @param newX - value of x coordinate.
     * @param newY - value of y coordinate.
     */
    public void setCoordinates(int newX, int newY) {
        this.xCoordinate = newX;
        this.yCoordinate = newY;
    }

    /**
     * This method moves the x coordinate of the Sprite one unit left.
     */
    public void moveLeft() {
        xCoordinate--;
    }

    /**
     * This method moves the x coordinate of the Sprite one unit right.
     */
    public void moveRight() {
        xCoordinate++;
    }

    /**
     * This method moves the y coordinate of the Sprite one unit up.
     */
    public void moveUp() {
        yCoordinate--;
    }

    /**
     * This method moves the y coordinate of the Sprite one unit down.
     */
    public void moveDown() {
        yCoordinate++;
    }

    /**
     * This method returns an integer array of the current sprite's coordinates.
     * @return - returns an integer array of the coordinates of the Sprite.
     */
    public int[] getCoordinates() {
        int[] coordinates = new int[2];
        coordinates[0] = xCoordinate;
        coordinates[1] = yCoordinate;
        return coordinates;
    }

    /**
     * This method returns the y coordinate.
     * @return - y coordinate integer of Sprite.
     */
    public int getYCoord() {
        return this.yCoordinate;
    }

    /**
     * This method returns the x coordinate.
     * @return - x coordinate integer of Sprite.
     */
    public int getXCoord() {
        return this.xCoordinate;
    }

    /**
     * This method displays the sprite on the graphics context and scales it according to the screen dimensions.
     * @param gc - graphics context for displaying the player and enemy.
     * @param screenResolution - the side length of the window which is displayed.
     * @param sideLength - number of grids on the side.
     * @param image - image file that will be displayed.
     * @param scale - scaling for the image.
     */
    public void displaySprite(GraphicsContext gc,  double screenResolution, int sideLength, Image image, double scale) {
        double scaling = screenResolution/sideLength;
        double radius = (screenResolution/sideLength)/scale;
        double xPadding = (screenResolution/sideLength - radius)/2;
        double yPadding = (screenResolution/sideLength - radius)/2;

        gc.drawImage(image, scaling * getXCoord() + xPadding, scaling * getYCoord() + yPadding, radius, radius);
    }
}
