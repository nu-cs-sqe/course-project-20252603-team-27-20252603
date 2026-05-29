package domain;

public final class Move {
    private final Location from;
    private final Location to;
    private final Piece movedPiece;
    private final Piece capturedPiece;
    private final PieceType promotionType;

    public Move(Location from, Location to, Piece movedPiece, Piece capturedPiece, PieceType promotionType) {
        this.from = from;
        this.to = to;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
        this.promotionType = promotionType;
    }

    public Location getFrom() {
        return from;
    }

    public Location getTo() {
        return to;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public Piece getCapturedPiece() {
        return capturedPiece;
    }

    public PieceType getPromotionType() {
        return promotionType;
    }

    public boolean isCastle() {
        return false;
    }

    public boolean isEnPassant() {
        return false;
    }

    public String getNotation() {
        return "";
    }
}
