package domain;

public class Piece {

    // Piece Constructor
    public Piece(domain.PieceType pieceType, domain.PieceColor pieceColor) {
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
    }

    private PieceType pieceType;
    private PieceColor pieceColor;

    private boolean moved = false;

    public boolean hasMoved() { return moved; }
    public void setMoved(boolean moved) { this.moved = moved; }

    public boolean canMove(Board board, Location from, Location to) {
        int rowDiff = to.getRow() - from.getRow();
        int colDiff = to.getCol() - from.getCol();

        if (this.pieceType == PieceType.PAWN) {
            if (rowDiff == 1 && colDiff == 0) {
                return board.getPiece(to) == null;
            }
        }
        return false;
    }

}
