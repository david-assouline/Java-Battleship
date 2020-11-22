//--------------------------------------------------
// Assignment # 4
// Written by: David-Raphael Assouline 40082595
// For COMP 248 Section EC – Fall 2020
//--------------------------------------------------
// This program will run a game of battleship on an 8x8 grid between
// the user and a computer. The user will place his ships and attack
// coordinates based on his input while the computer will select
// all coordinates at random.

import java.util.Random;
import java.util.Scanner;

public class Main {
    // whenever a ship is hit, the respective condition will += 1 until it reaches 6, indicating the game is over.
    static int playerWinCondition = 0;
    static int computerWinCondition = 0;
    // these booleans will be activated whenever a grenade is hit, skipping 1 turn as per the rules
    static boolean playerHitGrenade = false;
    static boolean computerHitGrenade = false;

    public static void main(String[] args) {
        // main method creates both a player grid and computer grid, then executes the main loop.
        System.out.println("Hi, let's play Battleship!\n");
        Grid mainGrid = new Grid();
        Grid frontEndGrid = new Grid();
        playerSetUp(mainGrid.grid);
        computerSetUp(mainGrid.grid);
        // game loop will continue running until a win condition is achieved
        while (true) {

            if (!playerHitGrenade) {
                playerTurn(mainGrid.grid, frontEndGrid.grid);
                frontEndGrid.display();
            } else {
                playerHitGrenade = false;
                System.out.println("**************************************");
                System.out.println("Skipping player turn. Grenade was hit.");
                System.out.println("**************************************");
            }
            if (playerWinCondition == 6) {
                break;
            }
            if (!computerHitGrenade) {
                computerTurn(mainGrid.grid, frontEndGrid.grid);
                frontEndGrid.display();
            } else {
                computerHitGrenade = false;
                System.out.println("****************************************");
                System.out.println("Skipping computer turn. Grenade was hit.");
                System.out.println("****************************************");
            }
            if (computerWinCondition == 6) {
                break;
            }
        }

    }

    public static void playerSetUp(char[][] grid) {
        Scanner userInput = new Scanner(System.in);
        int counter = 0;
        int xCoordinate = -1;
        int yCoordinate = -1;
        do {
            System.out.print("Enter the coordinates of your ship #" + (counter + 1) + ": ");
            String coordinates = userInput.next();
            xCoordinate = letterToNumber(coordinates.charAt(0));
            yCoordinate = Character.getNumericValue(coordinates.charAt(1)) - 1;
            if (xCoordinate == -1 || xCoordinate > 7 || yCoordinate < 0 || yCoordinate > 7) {
                System.out.println("sorry, coordinates outside the grid. try again.");
            } else if (grid[xCoordinate][yCoordinate] == '_') {
                grid[xCoordinate][yCoordinate] = 's';
                counter += 1;
            } else {
                System.out.println("sorry, coordinates already used. try again.");
            }

        } while (counter != 6);
        counter = 0;
        do {
            System.out.print("Enter the coordinates of your grenade #" + (counter + 1) + ": ");
            String coordinates = userInput.next();
            xCoordinate = letterToNumber(coordinates.charAt(0));
            yCoordinate = Character.getNumericValue(coordinates.charAt(1)) - 1;
            if (xCoordinate == -1 || xCoordinate > 7 || yCoordinate < 0 || yCoordinate > 7) {
                System.out.println("sorry, coordinates outside the grid. try again.");
            } else if (grid[xCoordinate][yCoordinate] == '_') {
                grid[xCoordinate][yCoordinate] = 'g';
                counter += 1;
            } else {
                System.out.println("sorry, coordinates already used. try again.");
            }

        } while (counter != 4);
    }

    public static void computerSetUp(char[][] grid) {
        int counter = 0;
        do {
            Random x = new Random();
            Random y = new Random();
            int x1 = x.nextInt(8);
            int y1 = y.nextInt(8);
            if (grid[x1][y1] == '_') {
                grid[x1][y1] = 'S';
                counter += 1;
            }

        } while (counter != 6);
        counter = 0;

        do {
            Random x = new Random();
            Random y = new Random();
            int x1 = x.nextInt(8);
            int y1 = y.nextInt(8);
            if (grid[x1][y1] == '_') {
                grid[x1][y1] = 'G';
                counter += 1;
            }
        } while (counter != 4);

        System.out.println("\nOK, the computer placed its ships and grenades at random, Let's play.");
    }

    public static int letterToNumber(char letter) {
        return switch (Character.toUpperCase(letter)) {
            case 'A' -> 0;
            case 'B' -> 1;
            case 'C' -> 2;
            case 'D' -> 3;
            case 'E' -> 4;
            case 'F' -> 5;
            case 'G' -> 6;
            case 'H' -> 7;
            default -> -1;
        };
    }

    public static void playerTurn(char[][] backEndGrid, char[][] frontEndGrid) {

        Scanner userInput = new Scanner(System.in);

        while (true) {

            System.out.print("Position of your rocket: ");
            String attackCoordinate = userInput.next();
            int xCoordinate = letterToNumber(attackCoordinate.charAt(0));
            int yCoordinate = Character.getNumericValue(attackCoordinate.charAt(1)) - 1;

            if (xCoordinate == -1 || xCoordinate > 7 || yCoordinate < 0 || yCoordinate > 7) {
                System.out.println("sorry, coordinates outside the grid. try again.");
            } else if (frontEndGrid[xCoordinate][yCoordinate] != '_') {
                System.out.println("position already called.");
            } else if (backEndGrid[xCoordinate][yCoordinate] == '_') {
                frontEndGrid[xCoordinate][yCoordinate] = '*';
                System.out.println("nothing.");
                break;
            } else if (backEndGrid[xCoordinate][yCoordinate] == 's') {
                frontEndGrid[xCoordinate][yCoordinate] = 's';
                computerWinCondition += 1;
                if (computerWinCondition == 6) {
                    System.out.println("player ship hit. Computer wins.");
                } else {
                    System.out.println("player ship hit.");
                }
                break;
            } else if (backEndGrid[xCoordinate][yCoordinate] == 'S') {
                frontEndGrid[xCoordinate][yCoordinate] = 'S';
                playerWinCondition += 1;
                if (playerWinCondition == 6) {
                    System.out.println("enemy ship hit. Player wins!");
                } else {
                    System.out.println("enemy ship hit.");
                }
                break;
            } else if (backEndGrid[xCoordinate][yCoordinate] == 'g') {
                frontEndGrid[xCoordinate][yCoordinate] = 'g';
                System.out.println("friendly grenade hit.");
                playerHitGrenade = true;
                break;
            } else if (backEndGrid[xCoordinate][yCoordinate] == 'G') {
                frontEndGrid[xCoordinate][yCoordinate] = 'G';
                System.out.println("boom! grenade hit.");
                playerHitGrenade = true;
                break;
            } else {
                System.out.println("sorry, coordinates already used. try again.");
            }

        }
    }

    public static void computerTurn(char[][] backEndGrid, char[][] frontEndGrid) {

        Random x = new Random();
        Random y = new Random();
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',};
        int winCondition = 0;

        while (true) {

            int x1 = x.nextInt(8);
            int y1 = y.nextInt(8);

            if (frontEndGrid[x1][y1] == '_') {
                System.out.println("Position of my rocket: " + letters[x1] + y1);
                if (backEndGrid[x1][y1] == '_') {
                    frontEndGrid[x1][y1] = '*';
                    System.out.println("nothing.");
                    break;
                } else if (backEndGrid[x1][y1] == 's') {
                    frontEndGrid[x1][y1] = 's';
                    computerWinCondition += 1;
                    if (computerWinCondition == 6) {
                        System.out.println("player ship hit. Computer wins.");
                    } else {
                        System.out.println("player ship hit.");
                    }
                    break;
                } else if (backEndGrid[x1][y1] == 'S') {
                    frontEndGrid[x1][y1] = 'S';
                    playerWinCondition += 1;
                    if (playerWinCondition == 6) {
                        System.out.println("computer ship hit. Player wins!");
                    } else {
                        System.out.println("computer ship hit.");
                    }
                    break;
                } else if (backEndGrid[x1][y1] == 'g') {
                    frontEndGrid[x1][y1] = 'g';
                    System.out.println("boom! player grenade hit.");
                    computerHitGrenade = true;
                    break;
                } else if (backEndGrid[x1][y1] == 'G') {
                    frontEndGrid[x1][y1] = 'G';
                    System.out.println("boom! computer grenade hit.");
                    computerHitGrenade = true;
                    break;
                }
            }
        }
    }
}
