package mazegame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

public class MazeData {
    private int mazeRows = 0;
    private int mazeColumns = 0;
    private GridData[][] mazeInfo;
    private int currentRow = 0;
    private int currentColumn = 0;

    /**
     * This constructor stores the data of the maze.
     *
     * @param rows    row length.
     * @param columns column length.
     */
    public MazeData(int rows, int columns) {
        this.mazeRows = rows;
        this.mazeColumns = columns;
        this.mazeInfo = new GridData[rows][columns];

        for (int i = 0; i < mazeInfo.length; i++) {
            for (int j = 0; j < mazeInfo[i].length; j++) {
                this.mazeInfo[i][j] = new GridData(i, j);
            }
        }
    }

    /**
     * This method gets the rows of the maze.
     *
     * @return row size of maze.
     */
    public int getMazeRows() {
        return this.mazeRows;
    }

    /**
     * This method gets the columns of the maze.
     *
     * @return column size of maze.
     */
    public int getMazeColumns() {
        return this.mazeColumns;
    }

    /**
     * This method gets the Array maze information.
     *
     * @return Array of the maze information.
     */
    public GridData[][] getMazeInfo() {
        return this.mazeInfo;
    }

    /**
     * This method generates a new maze.
     */
    public void generateMaze() {
        Stack<GridData> stack = new Stack<GridData>();

        this.currentRow = 0;
        this.currentColumn = 0;

        mazeInfo[this.currentRow][this.currentColumn].setVisited();
        stack.push(mazeInfo[this.currentRow][this.currentColumn]);

        while (!stack.isEmpty()) {
            //Get a list of all valid moves from the current position
            ArrayList<String> moveList = validMoves(this.currentRow, this.currentColumn);

            if (moveList.size() == 1) {
                String moveString = moveList.get(0);

                makeMove(moveString);
                stack.push(mazeInfo[this.currentRow][this.currentColumn]);
            }

            //means no more possible moves
            else if (moveList.size() == 0) {
                stack.pop();

                if (stack.isEmpty()) {
                    continue;
                }

                GridData previousGrid = (GridData) stack.peek();

                this.currentRow = previousGrid.getGridRow();
                this.currentColumn = previousGrid.getGridColumn();
            } else {
                //Choose a random valid move and make that move
                int randomMoveInt = ThreadLocalRandom.current().nextInt(0, moveList.size());
                String moveString = moveList.get(randomMoveInt);

                makeMove(moveString);
                stack.push(mazeInfo[this.currentRow][this.currentColumn]);
            }
            mazeInfo[this.currentRow][this.currentColumn].setVisited();
        }
    }

    /**
     * This method opens the grid data to a specified direction.
     *
     * @param move the direction to open.
     */
    public void makeMove(String move) {
        if (move == "left") {
            mazeInfo[this.currentRow][this.currentColumn].openLeft();
            this.currentColumn--;
            mazeInfo[this.currentRow][this.currentColumn].openRight();
        } else if (move == "top") {
            mazeInfo[this.currentRow][this.currentColumn].openTop();
            this.currentRow--;
            mazeInfo[this.currentRow][this.currentColumn].openBottom();
        } else if (move == "right") {
            mazeInfo[this.currentRow][this.currentColumn].openRight();
            this.currentColumn++;
            mazeInfo[this.currentRow][this.currentColumn].openLeft();
        } else if (move == "bottom") {
            mazeInfo[this.currentRow][this.currentColumn].openBottom();
            this.currentRow++;
            mazeInfo[this.currentRow][this.currentColumn].openTop();
        }
    }

    /**
     * This method returns a list of valid directions to open.
     *
     * @param rows    row position of current grid.
     * @param columns column position of current grid.
     * @return ArrayList of the possible directions to open.
     */
    public ArrayList<String> validMoves(int row, int column) {
        ArrayList<String> moveList = new ArrayList<String>();
        Collections.addAll(moveList, "left", "top", "right", "bottom");

        moveList = removeEdges(row, column, moveList);
        moveList = removeFlaggedNeighbours(row, column, moveList);

        return moveList;
    }

    /**
     * This method removes already visited grids from possible moves.
     *
     * @param rows        row position of current grid.
     * @param columns     column position of current grid.
     * @param currentList the current set of possible moves.
     * @return ArrayList of moves with visited neighbours removed.
     */
    public ArrayList<String> removeFlaggedNeighbours(int row, int column, ArrayList<String> currentList) {
        int length = currentList.size();

        ArrayList<String> newCurrentList = currentList;
        ArrayList<String> toRemove = new ArrayList<String>();

        for (int i = 0; i < length; i++) {
            if (currentList.get(i) == "left") {
                if (mazeInfo[row][column - 1].getVisited() == true) {
                    toRemove.add("left");
                }
            } else if (currentList.get(i) == "top") {
                if (mazeInfo[row - 1][column].getVisited() == true) {
                    toRemove.add("top");
                }
            } else if (currentList.get(i) == "right") {
                if (mazeInfo[row][column + 1].getVisited() == true) {
                    toRemove.add("right");
                }
            } else if (currentList.get(i) == "bottom") {
                if (mazeInfo[row + 1][column].getVisited() == true) {
                    toRemove.add("bottom");
                }
            }
        }

        for (int j = 0; j < toRemove.size(); j++) {
            newCurrentList = removeMove(toRemove.get(j), newCurrentList);
        }

        return newCurrentList;
    }

    /**
     * This method removes the edges from the possible moves.
     *
     * @param row         row position of current grid.
     * @param column      column position of current grid.
     * @param currentList current set of possible moves.
     * @return ArrayList of potential moves.
     */
    public ArrayList<String> removeEdges(int row, int column, ArrayList<String> currentList) {
        ArrayList<String> newCurrentList = currentList;
        if (row == 0) {
            currentList = removeMove("top", currentList);
        }
        if (row == (mazeRows - 1)) {
            currentList = removeMove("bottom", currentList);
        }
        if (column == 0) {
            currentList = removeMove("left", currentList);
        }
        if (column == (mazeColumns - 1)) {
            currentList = removeMove("right", currentList);
        }

        return newCurrentList;
    }

    /**
     * This method removes the string from the array that it is passed as a parameter.
     *
     * @param remove      The element to remove.
     * @param currentList List of current moves.
     * @return new list with element removed.
     */
    public ArrayList<String> removeMove(String removeThis, ArrayList<String> currentList) {
        for (int i = 0; i < currentList.size(); i++) {
            if (currentList.get(i) == removeThis) {
                currentList.remove(i);
                break;
            }
        }

        return currentList;
    }

    /**
     * This method prints the grid properties of the maze.
     */
    public void printMazeData() {
        for (int i = 0; i < mazeInfo.length; i++) {
            for (int j = 0; j < mazeInfo[i].length; j++) {
                System.out.println("Row: " + i + " " + "Column: " + j);
                System.out.println(mazeInfo[i][j].getWalls());
                System.out.println();
            }
        }
    }

    public void createABorder() {
        this.mazeInfo[0][0].openRight();
        this.mazeInfo[0][0].openBottom();

        for (int i = 1; i < mazeInfo.length - 1; i++) {
            this.mazeInfo[0][i].openLeft();
            this.mazeInfo[0][i].openRight();
        }
        this.mazeInfo[0][mazeInfo.length - 1].openLeft();

        for (int i = 1; i < mazeInfo.length - 1; i++) {
            this.mazeInfo[i][0].openTop();
            this.mazeInfo[i][0].openBottom();

            this.mazeInfo[i][mazeInfo.length - 1].openTop();
            this.mazeInfo[i][mazeInfo.length - 1].openBottom();
        }

        this.mazeInfo[mazeInfo.length - 1][0].openRight();
        for (int i = 1; i < mazeInfo.length - 1; i++) {
            this.mazeInfo[mazeInfo.length - 1][i].openLeft();
            this.mazeInfo[mazeInfo.length - 1][i].openRight();
        }
        this.mazeInfo[mazeInfo.length - 1][mazeInfo.length - 1].openLeft();
    }

    /**
     * This method adds loop to the maze.
     *
     * @param numberOfLoops amount of loops to be added.
     */
    public void addLoops(int numberOfLoops) {

        int NUMBEROFLOOPS = numberOfLoops;

        for (int i = 0; i < NUMBEROFLOOPS; i++) {
            int randomRow = ThreadLocalRandom.current().nextInt(0, this.mazeRows);
            int randomColumn = ThreadLocalRandom.current().nextInt(0, this.mazeColumns);

            ArrayList<String> moveList = new ArrayList<String>();
            Collections.addAll(moveList, "left", "top", "right", "bottom");
            moveList = removeEdges(randomRow, randomColumn, moveList);
            String move;

            if (moveList.size() == 1) {
                move = moveList.get(0);
                makeLoop(move, randomRow, randomColumn);
            } else if (moveList.size() >= 1) {
                int randomMove = ThreadLocalRandom.current().nextInt(0, moveList.size());
                move = moveList.get(randomMove);
                makeLoop(move, randomRow, randomColumn);
            }
        }
    }

    /**
     * This method creates a loop in the specified position.
     *
     * @param move   direction of loop.
     * @param row    row position of loop.
     * @param column column position of loop.
     */
    public void makeLoop(String move, int row, int column) {
        if (move == "left") {
            mazeInfo[row][column].openLeft();
            mazeInfo[row][column - 1].openRight();
        } else if (move == "top") {
            mazeInfo[row][column].openTop();
            mazeInfo[row - 1][column].openBottom();
        } else if (move == "right") {
            mazeInfo[row][column].openRight();
            mazeInfo[row][column + 1].openLeft();
        } else if (move == "bottom") {
            mazeInfo[row][column].openBottom();
            mazeInfo[row + 1][column].openTop();
        }
    }
}
