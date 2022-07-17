package tictactoe;

import java.util.Scanner;

class Grid {
    private int rows;
    private int columns;
    private char[][] tiles;

    Grid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.tiles = new char[rows][columns];
        this.initializeTiles();
    }

    private void initializeTiles() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                this.tiles[i][j] = ' ';
            }
        }
    }

    public char[][] getTiles() {
        return this.tiles;
    }

    public void setTiles(String tileValues) {
        if (tileValues == null) return;

        if (tileValues.length() != this.rows * this.columns) {
            throw new RuntimeException("Grid expects a starting state parameter string with the length of " + this.rows * this.columns);
        }

        for (int i = 0; i < tileValues.length(); i++) {
            this.tiles[i / rows][i % columns] = tileValues.charAt(i);
        }
    }

    public char getTile(int row, int column) {
        return this.tiles[row][column];
    }

    public void setTile(int row, int column, char value) {
        this.tiles[row][column] = value;
    }
}

class TicTacToe {
    private final int DIMENSION = 3;
    private Grid grid;

    private char currentPlayer = 'X';

    TicTacToe() {
        this.grid = new Grid(this.DIMENSION, this.DIMENSION);
    }

    public void run() {
        this.draw();
        while (true) {
            this.makeMove(currentPlayer);
            this.draw();
            if (this.isWinner(currentPlayer)) {
                System.out.println(currentPlayer + " wins");
                break;
            } else if (this.isGameFinished()) {
                System.out.println("Draw");
                break;
            }

            // switch player
            if (currentPlayer == 'X') {
                currentPlayer = 'O';
            } else {
                currentPlayer = 'X';
            }
        }
    }

    public void loadGameState(String gameState) {
        this.grid.setTiles(gameState);
    }

    public void makeMove(char symbol) {
        Scanner scanner = new Scanner(System.in);
        char[][] tiles = this.grid.getTiles();
        while (true) {
            int row;
            if(scanner.hasNextInt()){
                row = scanner.nextInt();
            }else{
                System.out.println("You should enter numbers!");
                continue;
            }

            int column;
            if(scanner.hasNextInt()){
                column = scanner.nextInt();
            }else{
                System.out.println("You should enter numbers!");
                continue;
            }

            if ((row < 1 || row > this.DIMENSION) || (column < 1 || column > this.DIMENSION)) {
                System.out.println("Coordinates should be from 1 to " + this.DIMENSION + "!");
                continue;
            }

            if (tiles[row-1][column-1] == 'X' || tiles[row-1][column-1] == 'O') {
                System.out.println("This cell is occupied! Choose another one!");
                continue;
            }

            this.grid.setTile(row-1,column-1, symbol);
            break;
        }
    }
    public void draw() {
        char[][] tiles = this.grid.getTiles();
        System.out.println("---------");
        for (int i = 0; i < this.DIMENSION; i++) {
            System.out.print("| ");
            for (int j = 0; j < this.DIMENSION; j++) {
                System.out.print(tiles[i][j] + " ");
            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("---------");
    }

    public void printGameResult() {
        if (this.isGameStateValid() == false) {
            System.out.println("Impossible");
        } else if (this.isWinner('X')) {
            System.out.println("X wins");
        } else if (this.isWinner('O')) {
            System.out.println("O wins");
        } else if (this.isGameFinished()) {
            System.out.println("Draw");
        } else {
            System.out.println("Game not finished");
        }
    }

    private boolean isGameFinished() {
        char[][] tiles = this.grid.getTiles();
        for (int i = 0; i < this.DIMENSION; i++) {
            for (int j = 0; j < this.DIMENSION; j++) {
                if (tiles[i][j] == '_' || tiles[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isWinner(char playerSymbol) {
        char[][] tiles = this.grid.getTiles();

        int dl = 0, dr = 0;
        for (int i = 0; i < this.DIMENSION; i++) {
            int r = 0, c = 0;
            for (int j = 0; j < this.DIMENSION; j++) {
                // count in row
                if (tiles[i][j] == playerSymbol) r++;
                // count in column
                if (tiles[j][i] == playerSymbol) c++;
                // count left diagonal
                if (tiles[i][j] == playerSymbol && i == j) dl++;
                // count right diagonal
                if (tiles[i][j] == playerSymbol && (i + j) == (this.DIMENSION - 1)) dr++;
            }
            if (r == this.DIMENSION || c == this.DIMENSION || dl == this.DIMENSION || dr == this.DIMENSION) {
                return true;
            }
        }

        return false;
    }

    private boolean isGameStateValid() {
        // check if both players are winners
        if (this.isWinner('X') && this.isWinner('O')) {
            return false;
        }

        // check if each player has the sam number of moves
        int xCount = 0;
        int yCount = 0;
        char[][] tiles = this.grid.getTiles();
        for (int i = 0; i < this.DIMENSION; i++) {
            for (int j = 0; j < this.DIMENSION; j++) {
                if (tiles[i][j] == 'X') {
                    xCount++;
                } else if (tiles[i][j] == 'O') {
                    yCount++;
                }
            }
        }
        if (Math.abs(xCount - yCount) > 1) {
            return false;
        }

        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.run();
    }
}
