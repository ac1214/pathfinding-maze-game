package mazegame;


public class MazeDisplay {
    private GridData[][] mazeData;

    /**
     * A constructor which sets the mazeData value.
     *
     * @param maze - Array of grid properties.
     */
    public MazeDisplay(GridData[][] maze) {
        this.mazeData = maze;
    }

    /**
     * This method prints the text version of the maze in the console.
     */
    public void printMaze() {
        int row = 0;

        for (int i = 0; i < mazeData.length * 2; i++) {
            if (i % 2 == 0) {
                for (int j = 0; j < mazeData[row].length; j++) {
                    System.out.print("+");
                    if (mazeData[row][j].getTopWall() == true) {
                        System.out.print("--");
                    } else {
                        System.out.print("  ");
                    }
                }

                System.out.print("+");
                System.out.println();
            } else {
                System.out.print("|");

                for (int l = 0; l < mazeData[row].length; l++) {
                    if (mazeData[row][l].getRightWall() == true) {
                        System.out.print("  |");
                    } else {
                        System.out.print("   ");
                    }
                }

                row++;
                System.out.println();
            }
        }

        for (int k = 0; k < (mazeData.length * 2); k++) {
            if (k % (2) == 0) {
                System.out.print("+");
            } else {
                System.out.print("--");
            }
        }

        System.out.print("+");
        System.out.println();
    }
}
