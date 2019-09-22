package mazegame;


public class GridData {
    private boolean visited = false;

    private boolean leftWall = true;
    private boolean topWall = true;
    private boolean rightWall = true;
    private boolean bottomWall = true;

    private int gridRow;
    private int gridColumn;

    /**
     * This constructor sets the row and column of each grid
     *
     * @param row    row of grid
     * @param column column of grid
     */
    public GridData(int row, int column) {
        this.gridRow = row;
        this.gridColumn = column;
    }

    /**
     * Getter for grid row
     *
     * @return grid's row
     */
    public int getGridRow() {
        return this.gridRow;
    }

    /**
     * Getter for grid column
     *
     * @return grid's column
     */
    public int getGridColumn() {
        return this.gridColumn;
    }

    /**
     * Getter to check if grid is visited by the maze generation algorithm
     *
     * @return boolean value of if visited
     */
    public boolean getVisited() {
        return this.visited;
    }

    /**
     * This method sets the grid as visited
     */
    public void setVisited() {
        this.visited = true;
    }

    /**
     * This method opens the left wall of the grid
     */
    public void openLeft() {
        this.leftWall = false;
    }

    /**
     * This method opens the top wall of the grid
     */
    public void openTop() {
        this.topWall = false;
    }

    /**
     * This method opens the right wall of the grid
     */
    public void openRight() {
        this.rightWall = false;
    }

    /**
     * This method opens the bottom wall of the grid
     */
    public void openBottom() {
        this.bottomWall = false;
    }

    /**
     * Returns a string that shows which walls currently exist
     *
     * @return which walls exist
     */
    public String getWalls() {
        return ("left: " + leftWall + " top: " + topWall + " right: " + rightWall + " bottom: " + bottomWall);
    }

    /**
     * Getter for if left wall exists
     *
     * @return boolean of if left wall exists
     */
    public boolean getLeftWall() {
        return this.leftWall;
    }

    /**
     * Getter for if top wall exists
     *
     * @return boolean of if top wall exists
     */
    public boolean getTopWall() {
        return this.topWall;
    }

    /**
     * Getter for if right wall exists
     *
     * @return boolean of if right wall exists
     */
    public boolean getRightWall() {
        return this.rightWall;
    }

    /**
     * Getter for if bottom wall exists
     *
     * @return boolean of if bottom wall exists
     */
    public boolean getBottomWall() {
        return this.bottomWall;
    }

    /**
     * This method returns current row and column as a string
     */
    public String toString() {
        return "row: " + gridRow + " column: " + gridColumn;
    }
}
