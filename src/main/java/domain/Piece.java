package domain;

public class Piece {
	private final PieceType type;
	private final PieceColor color;
	private boolean moved;

	public Piece(PieceType type, PieceColor color) {
		this.type = type;
		this.color = color;
	}

	public boolean canMove(Board board, Location from, Location to) {
		return false;
	}

	public PieceType getType() {
		return type;
	}

	public PieceColor getColor() {
		return color;
	}

	public boolean hasMoved() {
		return moved;
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}
}
