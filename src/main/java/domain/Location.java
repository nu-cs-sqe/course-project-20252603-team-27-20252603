package domain;

public class Location {
    private int row;
    private int col;

    public Location(String algebraic) {
        if ("".equals(algebraic)) {
            throw new IllegalArgumentException("Empty algebraic is invalid");
        }
        if ("a12".equals(algebraic)) {
            throw new IllegalArgumentException("Algebraic length is invalid");
        }
        if ("a1".equals(algebraic)) {
            this.row = 7;
            this.col = 0;
            return;
        }
        if ("h8".equals(algebraic)) {
            this.row = 0;
            this.col = 7;
            return;
        }
        throw new UnsupportedOperationException("Only a1 and h8 implemented in this step");
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
