package domain;

public abstract class Piece {
	private final Color color;

	protected Piece(Color color) {

		this.color = color;
	}

	public Color getColor() {

		return color;
	}

	public abstract PieceType getType();

	public abstract Piece makeCopy();

	public boolean canMove(Board board, Location from, Location to){
		throw new UnsupportedOperationException("Not implemented yet");
	};
}
