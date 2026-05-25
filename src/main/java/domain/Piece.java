package domain;

public class Piece {

    private final PieceType pieceType;
    private final PieceColor pieceColor;
    private boolean moved = false;

    public Piece(PieceType pieceType, PieceColor pieceColor) {
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
    }

    public boolean hasMoved() { return moved; }
    public void setMoved(boolean moved) { this.moved = moved; }
    public PieceColor getColor() { return this.pieceColor; }
    public PieceType getPieceType() { return this.pieceType; }

    public boolean canMove(Board board, Location from, Location to) {
        if (isOutOfBounds(to)) {
            return false;
        }

        if (this.pieceType == PieceType.PAWN) {
            return isValidPawnMove(board, from, to);
        }

        return false;
    }

    private boolean isOutOfBounds(Location loc) {
        return loc.getRow() < 0 || loc.getRow() > 7 || loc.getCol() < 0 || loc.getCol() > 7;
    }

    private boolean isValidPawnMove(Board board, Location from, Location to) {
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();

        if (isPawnOneForward(rowDiff, colDiff)) {
            return board.getPiece(to) == null;
        }

        if (isPawnTwoForwardInitial(rowDiff, colDiff)) {
            Location intermediate = new Location(from.getRow() + 1, from.getCol());
            return board.getPiece(intermediate) == null && board.getPiece(to) == null;
        }

        if (isPawnDiagonalCapture(rowDiff, colDiff)) {
            Piece target = board.getPiece(to);
            return target != null && target.getColor() != this.getColor();
        }

        return false;
    }

    private boolean isPawnOneForward(int rowDiff, int colDiff) {
        return rowDiff == 1 && colDiff == 0;
    }

    private boolean isPawnTwoForwardInitial(int rowDiff, int colDiff) {
        return rowDiff == 2 && colDiff == 0 && !this.hasMoved();
    }

    private boolean isPawnDiagonalCapture(int rowDiff, int colDiff) {
        return rowDiff == 1 && Math.abs(colDiff) == 1;
    }
}