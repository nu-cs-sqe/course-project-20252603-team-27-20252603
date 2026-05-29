package domain;

public class Pawn extends Piece {
	public Pawn(Color color) {
		super(color);
	}

	@Override
	public String getType() {
		return "Pawn";
	}

	@Override
	public Piece makeCopy() {
		return new Pawn(getColor());
	}
}
