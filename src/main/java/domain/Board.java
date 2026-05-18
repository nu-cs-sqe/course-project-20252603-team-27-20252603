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
        for (int row = 0; row < TOTAL_ROWS; row++) {
            for (int col = 0; col < TOTAL_COLS; col++) {
                pieces[row][col] = null;
            }
        }
    }

    public boolean isInsideBoard(Location location) {
        int row = location.getRow();
        int col = location.getCol();
        return row >= 0 && row < TOTAL_ROWS && col >= 0 && col < TOTAL_COLS;
    }

    public void initBoard() {
        clearBoard();
        pieces[6][0] = new Pawn(Color.WHITE);
        pieces[0][0] = new Rook(Color.BLACK);
    }

    public Piece getPiece(Location location) {
        return pieces[location.getRow()][location.getCol()];
    }

    public Piece[][] getSnapshot() {
        Piece[][] snapshot = new Piece[TOTAL_ROWS][TOTAL_COLS];
        for (int row = 0; row < TOTAL_ROWS; row++) {
            for (int col = 0; col < TOTAL_COLS; col++) {
                Piece piece = pieces[row][col];
                snapshot[row][col] = piece == null ? null : piece.makeCopy();
            }
        }
        return snapshot;
    }

    public void movePiece(Location from, Location to) {
        Piece movingPiece = getPiece(from);
        pieces[to.getRow()][to.getCol()] = movingPiece;
        pieces[from.getRow()][from.getCol()] = null;
    }

    public boolean isEmpty(Location location) {
        return getPiece(location) == null;
    }

    public Location findKing(Color color) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public String toPositionString() {

        throw new UnsupportedOperationException("Not implemented yet");
    }
}
