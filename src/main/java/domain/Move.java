package domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.Objects;

@SuppressFBWarnings({"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public final class Move {
	private final Location from;
	private final Location to;
	private final Piece movedPiece;
	private final Piece capturedPiece;
	private final PieceType promotionType;
	private final boolean isCastle;
	private final boolean isEnPassant;
	private final String notation;

	public Move(
			Location from,
			Location to,
			Piece movedPiece,
			Piece capturedPiece,
			PieceType promotionType) {
		this(from, to, movedPiece, capturedPiece, promotionType, false, false, "");
	}

	public Move(
			Location from,
			Location to,
			Piece movedPiece,
			Piece capturedPiece,
			PieceType promotionType,
			boolean isCastle,
			boolean isEnPassant,
			String notation) {
		if (from == null) {
			throw new IllegalArgumentException("from must not be null");
		}
		if (to == null) {
			throw new IllegalArgumentException("to must not be null");
		}
		if (movedPiece == null) {
			throw new IllegalArgumentException("movedPiece must not be null");
		}
		this.from = from;
		this.to = to;
		this.movedPiece = movedPiece;
		this.capturedPiece = capturedPiece;
		this.promotionType = promotionType;
		this.isCastle = isCastle;
		this.isEnPassant = isEnPassant;
		this.notation = notation != null ? notation : "";
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
		return isCastle;
	}

	public boolean isEnPassant() {
		return isEnPassant;
	}

	public String getNotation() {
		return notation;
	}
	@Override
	public boolean equals(Object obj){
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Move queen = (Move) obj;
		return queen.from==this.from &&
				queen.to==this.to &&
				queen.movedPiece==this.movedPiece;
	}
	@Override
	public int hashCode() {
		return Objects.hash(from, to, movedPiece);
	}
}
