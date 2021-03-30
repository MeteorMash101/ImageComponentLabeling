public class Position {
    // data members
    private int row; // row number of the position
    private int col; // column number of the position

    // constructor
    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }
    // getters & setters
    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        if (row >= 0) {
            this.row = row;
        }
    }

    public void setCol(int col) {
        if (col >= 0) {
            this.col = col;
        }
    }

    // convert to string suitable for output
    @Override
    public String toString() {
        String description = "(" + row + "," + col + ")";
        return description;
    }
}