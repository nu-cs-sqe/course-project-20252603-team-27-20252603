package domain;

import java.util.Objects;

public class Piece {
	private final PieceType pieceType;
	private final PieceColor pieceColor;

	public Piece(PieceType pieceType, PieceColor pieceColor) {
		this.pieceType = Objects.requireNonNull(pieceType,
				"pieceType must not be null");
		this.pieceColor = Objects.requireNonNull(pieceColor,
				"pieceColor must not be null");
	}

	public PieceType getPieceType() {
		return pieceType;
	}

	public PieceColor getPieceColor() {
		return pieceColor;
	}

	public PieceColor getColor() {
		return pieceColor;
	}

	public String getType() {
		return pieceType.getDisplayName();
	}

	public Piece makeCopy() {
		return new Piece(pieceType, pieceColor);
	}
}
