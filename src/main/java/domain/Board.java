package domain;

public class Board {
    private Piece[][] pieces;
    public static final int TOTAL_ROWS = 8;
    public static final int TOTAL_COLS = 8;

    public Board() {
        this.pieces = new Piece[TOTAL_ROWS][TOTAL_COLS];
    }

    // Test-friendly constructor to inject a pre-populated pieces array (e.g. mocks)
    public Board(Piece[][] pieces) {
        if (pieces == null) {
            this.pieces = new Piece[TOTAL_ROWS][TOTAL_COLS];
        } else {
            this.pieces = pieces;
        }
    }

    public void clearBoard() {

        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean isInsideBoard(Location location) {
        return (location.getRow() == 0 && location.getCol() == 0)
                || (location.getRow() == 7 && location.getCol() == 7);
    }

    public void initBoard() {
        pieces[6][0] = new Pawn(Color.WHITE);
        pieces[0][0] = new Rook(Color.BLACK);
    }

    public Piece getPiece(Location location) {
        return pieces[location.getRow()][location.getCol()];
    }

    public Piece[][] getSnapshot() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void movePiece(Location from, Location to) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public boolean isEmpty(Location location) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Location findKing(Color color) {

        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String toPositionString() {

        throw new UnsupportedOperationException("Not implemented yet");
    }
}
