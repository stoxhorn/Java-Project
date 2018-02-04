package dm550.tictactoe;

import dm550.tictactoe.Coordinate;
import dm550.tictactoe.Game;
import dm550.tictactoe.CFBoard;
import dm550.tictactoe.UserInterface;

/** main class creating a board and the GUI
 * defines the game play
 */
public class CFGame implements Game {

    /** currently active player */
    private int currentPlayer;

    /** total number of players */
    private int numPlayers;
    
    /** the board we play on */
    private CFBoard board;
    
    /** the gui for board games */
    private UserInterface ui;
    
    /** constructor that gets the number of players */
    public CFGame(int numPlayers) {
        this.currentPlayer = 1;
        this.numPlayers = numPlayers;
        this.board = new CFBoard(numPlayers);
    }

    @Override
    public String getTitle() {
        return this.numPlayers+"-man Connect Four";
    }

    @Override
    public void addMove(XYCoordinate pos) {
        /*
        Calls the method addMove from the class board
        And keeps track of who's turn it is
        */
        this.board.addMove(pos, this.currentPlayer);
        if (this.currentPlayer == this.numPlayers) {
            this.currentPlayer = 1;
        } else {
            this.currentPlayer++;
        }
    }

    @Override
    public String getContent(XYCoordinate pos) {
        /*
        Returns the player of the given position, and 0, if no player found
        */
        String result = "";
        int player = this.board.getPlayer(pos);
        if (player > 0) {
            result += player;
        }
        return result;
    }

    @Override
    public int getHorizontalSize() {
        return this.board.getSize() * 2;
    }

    @Override
    public int getVerticalSize() {
        return this.board.getSize() + 2;
    }


    public int getSize() {
        return this.board.getSize();
    }



    @Override
    public void checkResult() {
        /*
        Calls the board method "checkWinning", which checks for a win condition, whenever a tur nis made
        If a winner is found, the proper UI method are called
        */     
        int winner = this.board.checkWinning();
        if (winner > 0) {
            this.ui.showResult("Player "+winner+" wins!");
        } else if (this.board.checkFull()) {
            this.ui.showResult("This is a DRAW!");
        }
    }

    @Override
    public boolean isFree(XYCoordinate pos) {
        return this.board.isFree(pos);
    }

    @Override
    public void setUserInterface(UserInterface ui) {
        this.ui = ui;
        
    }
    
    public String toString() {
        return "Board before Player "+this.currentPlayer+" of "+this.numPlayers+"'s turn:\n"+this.board.toString();
    }

}
