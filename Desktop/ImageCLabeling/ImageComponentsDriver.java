
/*
 * Image Component Labeling
 * Project 1
 * < Shelby Sakamoto >
 * < February 18, 2019 >
 *
 * DFS, BFS, image labeling.
 *   . . .
 *   . . .
 *
 */

import java.util.*;

public class ImageComponentsDriver {
    private static final int DEFAULT_DIMENSION = 15;
    private static final float DEFAULT_DENSITY = 0.33f;
    private static int dimension;
    private static float density;

    private static Pixel[][] gridA;
    private static Pixel[][] gridB;
    private static ArrayStack pathA;
    private static ArrayQueue pathB;

    private static Pixel zeroPixel = new Pixel(0, 0);
    private static Pixel onePixel = new Pixel(1, 0);

    public static void main(String[] args) {
        welcomeMessage();
        generateGrids();
        System.out.println("\nGrid Before Processing");
        printGrid(gridA);
        labelComponentsDfs(gridA, pathA);
        labelComponentsBfs(gridB, pathB);
        System.out.println("\nGrid After Processing (DFS)");
        printGrid(gridA);
        System.out.println("\nGrid After Processing (BFS)");
        printGrid(gridB);
    }

    private static void welcomeMessage() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter image dimension...(Enter -1 for default value: [15])");
        dimension = scan.nextInt();
        if (dimension <= 0) {
            dimension = DEFAULT_DIMENSION;
        }
        System.out.println(
                "Enter image density...(Enter -1 for default value: [0.33]) // (~ex. .25 = 25% 1's, .75 = 75% 1's, etc.)");
        density = scan.nextFloat();
        if (density <= 0) {
            density = DEFAULT_DENSITY;
        }
    }

    private static void generateGrids() {
        gridA = new Pixel[dimension + 2][dimension + 2]; // grids are 2d array of pixel objects
        gridB = new Pixel[dimension + 2][dimension + 2];
        Random generator = new Random();
        for (int row = 1; row < gridA.length; row++) {
            for (int col = 1; col < gridA.length; col++) {
                float randomR = 0 + (1 - 0) * generator.nextFloat();
                if (randomR < density) {
                    gridA[row][col] = new Pixel(1, 0);
                    gridB[row][col] = new Pixel(1, 0);
                } else {
                    gridA[row][col] = new Pixel(0, 0);
                    gridB[row][col] = new Pixel(0, 0);
                }
            }
        }
        // initialize wall of 0's around the grid
        for (int i = 0; i <= dimension + 1; i++) {
            gridA[0][i] = gridA[dimension + 1][i] = zeroPixel; // bottom and top
            gridA[i][0] = gridA[i][dimension + 1] = zeroPixel; // left and right
            gridB[0][i] = gridB[dimension + 1][i] = zeroPixel; // bottom and top
            gridB[i][0] = gridB[i][dimension + 1] = zeroPixel; // left and right
        }
    }

    private static void printGrid(Pixel[][] grid) {
        // printing out the whole grid
        for (Pixel[] row : grid) {
            printRowHelper(row);
        }
    }

    private static void printRowHelper(Pixel[] row) {
        for (Pixel i : row) {
            System.out.print(i);
            System.out.print("\t");
        }
        System.out.println();
    }

    /** label the components using DFS */
    private static void labelComponentsDfs(Pixel[][] grid, ArrayStack path) {
        // initialize offsets
        Position[] offset = new Position[4];
        offset[0] = new Position(0, 1); // right
        offset[1] = new Position(1, 0); // down
        offset[2] = new Position(0, -1); // left
        offset[3] = new Position(-1, 0); // up

        int option = 0; // next move
        int numOfNbrs = 4; // neighbors of a grid position
        path = new ArrayStack(); // stack of Positions
        Position here; // checkpoint Position

        int idLabel = 2; // component id (Starting w/ 2)
        int orderNum = 1; // component id
        int newRow = 0, newCol = 0; // new rows/columns

        // scan all grids labeling components
        for (int row = 1; row <= dimension; row++) { // row
            for (int col = 1; col <= dimension; col++) { // column
                if (grid[row][col].equals(onePixel)) { // new Image Component
                    here = new Position(row, col); // create checkpoint
                    grid[row][col].setLabel(idLabel);
                    grid[row][col].setOrder(orderNum);
                    orderNum++;
                    while (option < numOfNbrs) {
                        newRow = here.getRow() + offset[option].getRow();
                        newCol = here.getCol() + offset[option].getCol();
                        if (grid[newRow][newCol].equals(onePixel)) { // if a neighbor is 1, label again.
                            path.push(here); // push checkpoint
                            here = new Position(newRow, newCol);
                            grid[newRow][newCol].setLabel(idLabel);
                            grid[newRow][newCol].setOrder(orderNum);
                            orderNum++;
                            option = 0;
                            // break;
                        } else { // found 0 instead of 1.
                            option++;
                            if (option == numOfNbrs) { // if checked all neighbors.
                                if (!path.empty()) {
                                    here = (Position) path.pop(); // pop stack of previous checkpoints (Positions)
                                    option = 0;
                                } else { // if stack is empty...
                                    idLabel++;
                                    orderNum = 1;
                                }
                            }
                        }
                    } // breaks while-loop if option = 4
                    option = 0; // reset option
                }
            } // inner for loop
        } // outer for loop
    }

    /** label the components using BFS */
    private static void labelComponentsBfs(Pixel[][] grid, ArrayQueue path) {
        // initialize offsets
        Position[] offset = new Position[4];
        offset[0] = new Position(0, 1); // right
        offset[1] = new Position(1, 0); // down
        offset[2] = new Position(0, -1); // left
        offset[3] = new Position(-1, 0); // up

        int option = 0; // next move
        int numOfNbrs = 4; // neighbors of a grid position
        path = new ArrayQueue(); // queue of Positions
        Position here; // checkpoint Position
        Position neighbor; // neighbor Position

        int idLabel = 2; // component id (Starting w/ 2)
        int orderNum = 1; // component id
        int newRow = 0, newCol = 0; // new rows/columns
        // scan all grids labeling components
        for (int row = 1; row <= dimension; row++) { // row
            for (int col = 1; col <= dimension; col++) { // column
                if (grid[row][col].equals(onePixel)) { // new Image Component
                    here = new Position(row, col); // checkpoint
                    grid[row][col].setLabel(idLabel);
                    grid[row][col].setOrder(orderNum);
                    orderNum++;
                    while (option < numOfNbrs) {
                        newRow = here.getRow() + offset[option].getRow();
                        newCol = here.getCol() + offset[option].getCol();
                        if (grid[newRow][newCol].equals(onePixel)) { // if neighbor one
                            neighbor = new Position(newRow, newCol);
                            path.put(neighbor); // push checkpoint
                            grid[newRow][newCol].setLabel(idLabel);
                            grid[newRow][newCol].setOrder(orderNum);
                            orderNum++;
                            option++;
                        } else {
                            option++;
                        }
                        if (option == numOfNbrs) {
                            if (!path.isEmpty()) {
                                here = (Position) path.remove(); // start labeling from the first queue
                                option = 0;
                            } else { // if queue is empty...
                                idLabel++;
                                orderNum = 1;
                            }
                        }
                    } // end of while loop
                    option = 0; // reset option
                }
            } // inner for loop
        } // outer for loop
    }
}