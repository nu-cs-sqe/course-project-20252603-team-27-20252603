package domain;

public class Location {
    private int row;
    private int col;

    public Location(String algebraic) {
        if (!"a1".equals(algebraic)) {
            throw new UnsupportedOperationException("Only a1 implemented in this step");
        }
        this.row = 7;
        this.col = 0;
    }

    public Location(int row, int col) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
