package dm550.tictactoe;


public class XYCoordinate implements Coordinate {

    /** variables specifying horizontal position on the board */
    private int x;

    /** variable specifying vertical positoin on the board */
    private int y;

    /** constructor creating a Coordinate from x and y values */
    public XYCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }


    @Override
    public boolean checkBoundaries(int xSize, int ySize) {
        /*
        Checks if the position of the XYCoordinate is within the boundaries given.
        If the positions of the coordinates, is lower than the given board size, and higher than -1, then it fits within the boardsize.
        */
        if ((-1 < this.x) && (this.x < xSize) && (-1 < this.y) && (this.y < ySize)){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public XYCoordinate shift(int dx, int dy) {
       /*
       returns a mew position of the object the method is called upon, with the direction given
       */
       XYCoordinate tmp = new XYCoordinate(0,0);
       tmp.x = this.x + dx;
       tmp.y = this.y + dy;
       return tmp;
    }

    @Override
    public String toString(){
        String tmp = "x: " + this.x + "\n" + "y: " + this.y + "\n";
        return tmp;
    }

}
