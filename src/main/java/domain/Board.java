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
        pieces[7][4] = new King(Color.WHITE);
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
        for (int row = 0; row < TOTAL_ROWS; row++) {
            for (int col = 0; col < TOTAL_COLS; col++) {
                Piece piece = pieces[row][col];
                if (piece instanceof King && piece.getColor() == color) {
                    return new Location(row, col);
                }
            }
        }
        throw new IllegalStateException("King not found for color: " + color);
    }

    public String toPositionString() {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < TOTAL_ROWS; row++) {
            for (int col = 0; col < TOTAL_COLS; col++) {
                Piece piece = pieces[row][col];
                if (piece == null) {
                    builder.append('.');
                } else {
                    char symbol = piece.getType().charAt(0);
                    if (piece.getColor() == Color.BLACK) {
                        symbol = Character.toLowerCase(symbol);
                    }
                    builder.append(symbol);
                }
            }
            if (row < TOTAL_ROWS - 1) {
                builder.append(System.lineSeparator());
            }
        }
        return builder.toString();
    }
}
