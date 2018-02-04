package dm550.tictactoe;


import java.util.Arrays;

/** represents a tic tac toe board of a given size */
public class TTTBoard {

    /**
     * A method to keep track of the latest position taken in a turn
     * */
    private XYCoordinate newMove;

    /**
     * A getter for the last moved position variable
     * */
    public XYCoordinate getMove(){
        return newMove;
    }


    /**
     * and a setter for the same variable
     * */
    public XYCoordinate setMove(XYCoordinate c){
        return newMove = c;
    }

    /** 2-dimensional array representing the board
     * coordinates are counted from top-left (0,0) to bottom-right (size-1, size-1)
     * board[x][y] == 0   signifies free at position (x,y)
     * board[x][y] == i   for i > 0 signifies that Player i made a move on (x,y)
     */
    private int[][] board;
    
    /** 
     * size of the sides of the (quadratic) board
     * */
    private int size;
    
    /** A variable that keeps check of the amount of moves taken in total
     * used to specify if the game is done
     * */
    private int turns = 0;

    /** constructor for creating a copy of the board
     * not needed in Part 1 - can be viewed as an example 
     * - ok
     */
    public TTTBoard(TTTBoard original) {
        this.size = original.size;
        for (int y = 0; y < this.size; y++) {
            for (int x = 0; x < this.size; x++) {
                this.board[y][x] = original.board[y][x];
            }
        }
    }
    
    /**
     * constructor for creating an empty board for a given number of players
     * */
    public TTTBoard(int numPlayers) {
        this.size = numPlayers+1;
        this.board = new int[this.getSize()][this.getSize()];
    }
    
    /**
     * checks whether the board is free at the given position
     * */
    public boolean isFree(XYCoordinate c) {
        /*
        If the value at the given coordinate is 0, the position is free
        */
        if (this.board[c.getY()][c.getX()] == 0){
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Returns the players that made a move on (x,y) or 0 if the positon is free
     * */
    public int getPlayer(XYCoordinate c) {
        // I assume the position is marked with a number representing which player placed something here
        return this.board[c.getY()][c.getX()];
    }
    

    /** 
     * Adds a move, and keeps track of the amount of turns taken, along with the last taken position
     * */
    public void addMove(XYCoordinate c, int player) {
        /*
        First checks if player making a move is a vlid player
        Then checks if the given position is within the board
        and then checks if the position isn't taken already
        */
        
        // Player Check
        if (player < this.getSize()){
        
            // Out of Bounds check
            if (c.checkBoundaries(this.getSize(), this.getSize())){
                
                // Valid move check
                if (this.isFree(c)){
                    this.board[c.getY()][c.getX()] = player;
                    this.setMove(c);
                    this.turns++;
                } else {
                    throw new IllegalArgumentException("The specified position has already been taken, by another player\n");
                }
            } else {
                throw new IllegalArgumentException("The specified position is not within the boar\n");
            }
        } else {
            throw new IllegalArgumentException("A non-valid player has issued a move\n");
        }    
    }   


    /**
     * Returns true once every position has been taken on the board
     * */
    public boolean checkFull() {
        /*
        This is done by calculating the area, and comparing it to toal turns taken this game
        */
        
        int totalFields = this.getSize() * this.getSize();
        if (totalFields < this.turns + 1){
            return true;
        } else{
            return false;
        }
    }


    /** 
     * Checks if a player has won, after making a move
     * Returns the player of the potential winner
     * Otherwise it returns 0
     * */
    public int checkWinning() {
       /*
       For this method a helper method is used.
       This helper method takes a direction and a starting position
       From there it finds if a new move will warrant a winner or not
       And the returns the player numbker of the winner.
       */

        // Variable used to check if somebody won
        int anyWinner = 0;

        // Direction of the wincondition
        int dx;
        int dy;
        
        // Strartposition of the tracker
        int x;
        int y;

        
        // With the calls here, i will need change the tracker, and it's direction
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -



        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        // Horizontal check, moving tracker down each loop
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        x = this.getMove().getX() - 2;
        y = this.getMove().getY();
        dx = 1;
        dy = 0;

        anyWinner = checker(x, y, dx, dy);
        if (anyWinner > 0){
            return anyWinner;
        } else {
            //nothing
        }


        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        // Vertical check, moving tracker to the right, and setting the tracker in the bottom left corner
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        x = this.getMove().getX();
        y = this.getMove().getY() - 2;
        dx = 0;
        dy = 1;
        anyWinner = checker(x, y, dx, dy);
        if (anyWinner > 0){
            return anyWinner;
        } else {
            //nothing
        }


        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        // Diagonal left to right, going down starting from bottom left
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        x = this.getMove().getX() - 2;
        y = this.getMove().getY() - 2;

        // the runner moves 1 unit in each direction when diagonal
        dx = 1;
        dy = 1;


        anyWinner = checker(x, y, dx, dy);
        if (anyWinner > 0){
            return anyWinner;
        } else {
            //nothing
        }
    
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        // Diagonal right to left, going downwards starting from top left
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        x = this.getMove().getX() + 2;
        y = this.getMove().getY() - 2;

        // the runner moves 1 unit in each direction when diagonal
        dx = -1;
        dy = 1;
        
        anyWinner = checker(x, y, dx, dy);
        if (anyWinner > 0){
            return anyWinner;
        } else {
            //nothing
        }

        // Returns 0, after having been through each part.
        return 0;
    }


    /**
     * The helper method for checkWinning()
     * This has its own helper method as well.
     * It has a loop that loops 3 times
     * each loop uses the helper function to create a new array with 3 indices
     * Returning the created sequence, should it contain three of the same numbers in a row
     */
    private int checker(int x, int y, int dx, int dy) {
        
        // Variable to return the wincondition
        int winCheck = 0;

        // The tracker for the position from which the method is checking for three in a row
        XYCoordinate tracker = new XYCoordinate(x, y);
        
        
        // A for loop marked tracker, so that it can be returned to, in case of being out of bounds
        tracker: for (int d = 0; d < 3; d++) {

            // Checker for out of bounds
            if (tracker.checkBoundaries(this.getSize(), this.getSize()) == false) {
                tracker = tracker.shift(dx, dy);

                // Continue statement, so that a sequence can be created from the next position
                continue tracker;
            }

            // A tracker that tracks the position that is to be add to the new 3 indice array
            XYCoordinate point = tracker;

            // The array with three indices that holds values that could never be assigned to players, and are not 3 of the same numbers
            int[] seq3 = {-1, -2, -3};

            // The for loop that creates the 3 indice array
             for (int i = 0; i < 3; i++) {

                // Out of bounds checker, for when the point reaches the end of the board
                if (point.checkBoundaries(this.getSize(), this.getSize()) == false) {
                    
                    // Shifts the tracker, and starts anew
                    tracker = tracker.shift(dx, dy);
                    continue tracker;
                
                // The section that uses the helper function to create the 3 indice array, and checks for the winning condition
                } else {
                    seq3 = createRows(seq3, point);

                    winCheck = checkWin(seq3);

                    // shifts the point, and checks for wincondition
                    point = point.shift(dx, dy);
                    if (winCheck > 0) {
                        return winCheck;
                    }
                }
            }
            // Shifts the tracker, if it has not been out of bounds yet, and returns 0, should it go out of bounds.
            tracker = tracker.shift(dx, dy);
            if (tracker.checkBoundaries(this.getSize(), this.getSize()) == false) {
                return 0;
            }
        }
        // A return statement should the board be huge, and no 3 in a row has been found.
        return 0;
    }


    /**
     * Checks a given array for the wincondition, and returns the numbker of the winnning player
     * */
    private int checkWin(int[]seq){
        if ((seq[0] == seq[1]) && (seq[1] == seq[2])) {
            return seq[0];
        } else {
            return 0;
        }
    }

    /**
     * Helper funciton that create a new array containing the last two indices of the given array
     * Assumes an array of three indices is given, and that the wincondittion is of three in a row.
     */
    private int[] createRows(int[] inpRow, XYCoordinate point) {
        /*
        Create a new array, which takes the last two of the given array
        Then adds the next field that needs to be checked, should it not be out of bounds.
        If it cannot create an array of 3 indices it returns the array with a 0 at the end.
        */
        
        
        int[] rows3 = new int[3];

        
        rows3[0] = inpRow[1];
        rows3[1] = inpRow[2];

        
        try {
            rows3[2] = this.board[point.getY()][point.getX()];
            return rows3;

        } catch (ArrayIndexOutOfBoundsException e) {
            rows3[2] = 0;
            return rows3;
        }
    }





    /**
     * getter for size of the board
     * */
    public int getSize() {
        return this.size;
    }

    
    /** 
     * Pretty printing of the board
     * usefule for debugging purposes
     * */
    public String toString() {
        String result = "";
        for (int y = 0; y < this.getSize(); y++) {
            for (int x = 0; x < this.getSize(); x++) {
                result += this.board[y][x]+" ";
            }
            result += "\n";
        }
        return result;
    }

}
