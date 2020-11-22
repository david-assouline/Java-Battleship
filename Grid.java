// grid class can be called upon to create an 8x8, 2 dimensional array
// each position in the array will initially be filled with a '_' character

import java.util.Arrays;

// initialize the 2-D array for the Grid Class
public class Grid {
    char[][] grid = new char[8][8];

// assign '_' to every position in the array
    public Grid() {
        for (char[] row : grid) {
            Arrays.fill(row, '_');
        }
    }
// this method can be called to display the current state of the grid array
    public void display() {
        for (int i = 0; i < 8; i += 1) {
            for (int j = 0; j < 8; j += 1) {
                System.out.print(this.grid[i][j] + "   ");
            }
            System.out.println();
        }
    }
}