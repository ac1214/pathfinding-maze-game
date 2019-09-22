package mazegame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class Enemy extends Sprite {
    private GridData[][] mazeInfo;
    private Boolean[][] visitedGrid;
    private ArrayList<int[]> path;

    /**
     * This is a constructor that creates an enemy with starting coordinates in the bottom right
     * corner and saves the current level data.
     * @param mazeSideLength current side length of the maze
     * @param newMazeInfo new maze info that contains the properties of the maze
     */
    public Enemy(int mazeSideLength, GridData[][] newMazeInfo){
        //Randomly generate 2 coordinates in the bottom right quadrant
        int xCoordinate = ThreadLocalRandom.current().nextInt(mazeSideLength/2 + 1, mazeSideLength);
        int yCoordinate = ThreadLocalRandom.current().nextInt(mazeSideLength/2 + 1, mazeSideLength);

        setCoordinates(xCoordinate, yCoordinate);
        mazeInfo = newMazeInfo;
    }

    /**
     * This method will set new level data for the enemy when the player proceeds to a new level.
     * @param newMazeInfo new maze info that contains the properties of the maze
     * @param mazeSideLength new side length of the maze when leveled up
     */
    public void setNewLevelData(GridData[][] newMazeInfo, int mazeSideLength) {
        //Randomly generate 2 coordinates in the bottom right quadrant
        int xCoordinate = ThreadLocalRandom.current().nextInt(mazeSideLength/2 + 1, mazeSideLength);
        int yCoordinate = ThreadLocalRandom.current().nextInt(mazeSideLength/2 + 1, mazeSideLength);

        setCoordinates(xCoordinate, yCoordinate);
        mazeInfo = newMazeInfo;
    }

    /**
     * This method builds a grid that will keep track of where the path finding has already visited.
     */
    private void buildVisitedGrid() {
        this.visitedGrid = new Boolean[mazeInfo.length][mazeInfo[0].length];

        for(int i = 0; i < visitedGrid.length; i++) {
            for(int j = 0; j < visitedGrid[i].length; j++) {
                this.visitedGrid[i][j] = false;
            }
        }
    }

    /**
     * This method will return the path that the enemy has found to the player
     * @return array list of coordinates that is a path
     */
    public ArrayList<int[]> getPath() {
        ArrayList<int[]> pathCopy = new ArrayList<int[]>(this.path);

        return pathCopy;
    }

    /**
     * This method will generate the shortest path using breadth first search
     * to the goal coordinates
     * @param goal coordinates of where the path should be found to (player position)
     */
    public void Pathfinding(int[] goal) {
        buildVisitedGrid(); //builds a grid to keep track of which grids have been visited

        Queue<ArrayList<int[]>> queue = new LinkedList<ArrayList<int[]>>();

        int[] pathFindingCoords = getCoordinates();

        ArrayList<int[]> currentPath = new ArrayList<int[]>();

        currentPath.add(pathFindingCoords);

        queue.add(currentPath); //adds the initial position of the enemy

        while(!queue.isEmpty()) {
            //elements in the queue are the paths traveled
            currentPath = queue.remove();

            int[] currentGrid = currentPath.get(currentPath.size()-1);

            setVisited(currentGrid);

            if(Arrays.equals(currentGrid, goal)) {
                break;
            }

            ArrayList<int[]> paths = buildPaths(currentGrid);

            //This loop adds all the possible paths to the queue
            for(int i = 0; i < paths.size(); i++) {
                ArrayList<int[]> pathCopy = new ArrayList<int[]>(currentPath);
                pathCopy.add(paths.get(i));
                queue.add(pathCopy);
            }
        }

        this.path = currentPath;
    }

    /**
     * This sets a grid in the visited array as visited. The grid that is visited is passed as a
     * parameter.
     * @param currentGrid the coordinates of the grid that is being visited
     */
    private void setVisited(int[] currentGrid) {
        int x = currentGrid[0];
        int y = currentGrid[1];

        this.visitedGrid[y][x] = true;
    }

    /**
     * This method will get all possible next paths by checking each side of the current coordinates
     * this will then return all the possible paths as an ArrayList
     * @param currentCoordinates coordinates of the current position
     * @return all of the potential next moves
     */
    private ArrayList<int[]> buildPaths(int[] currentCoordinates) {
        ArrayList<int[]> paths = new ArrayList<int[]>();

        int x = currentCoordinates[0];
        int y = currentCoordinates[1];

        GridData currentGrid = mazeInfo[y][x]; //get the grid attributes

        if(currentGrid.getLeftWall() == false) {
            if(visitedGrid[y][x-1] == false) {
                int[] newMove = new int[] {currentCoordinates[0] - 1, currentCoordinates[1]};
                paths.add(newMove);
            }
        }
        if(currentGrid.getRightWall() == false) {
            if(visitedGrid[y][x+1] == false) {
                int[] newMove = new int[] {currentCoordinates[0] + 1, currentCoordinates[1]};
                paths.add(newMove);
            }
        }
        if(currentGrid.getTopWall() == false) {
            if(visitedGrid[y-1][x] == false) {
                int[] newMove = new int[] {currentCoordinates[0], currentCoordinates[1] - 1};
                paths.add(newMove);
            }
        }
        if(currentGrid.getBottomWall() == false) {
            if(visitedGrid[y+1][x] == false) {
                int[] newMove = new int[] {currentCoordinates[0], currentCoordinates[1] + 1};
                paths.add(newMove);
            }
        }

        return paths;
    }

    /**
     * This method will move the enemy along the path that has been found.
     */
    public void moveEnemy() {
        try {
            int[] nextGrid = path.remove(1);
            int nextX = nextGrid[0];
            int nextY = nextGrid[1];

            setCoordinates(nextX, nextY);
        }
        catch(IndexOutOfBoundsException e) {
            System.out.println("Game Over");
        }
    }
}