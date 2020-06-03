package checkers;

public class Move {
    public int currRow, currCol, movRow, movCol;
    public boolean jump;

    /**
     * Creates a new move
     * @param currRow the current row of the piece
     * @param currCol the current column of the piece
     * @param movRow the row the piece will be moved to
     * @param movCol the column the piece will be moved to
     */
    public Move(int currRow, int currCol, int movRow, int movCol) {
        this.currRow = currRow;
        this.currCol = currCol;
        this.movRow = movRow;
        this.movCol = movCol;
    }

    
    public boolean isJump() {
        if(Math.abs(this.movCol-this.currCol) == 2) {
            return true;
        }
        return false;
    }

    /**
     * Creates a string representation of a move
     * @return a string version of a move
     */
    public String toString() {
        return "current: (" + currRow + ", " + currCol + ") + next: (" + movRow + "," + movCol + ")" + " " + jump;
    }
}
