package domain;

public class Rook extends Piece {
	public Rook(Color color) {
		super(color);
	}

	@Override
	public PieceType getType() {

		return PieceType.ROOK;
	}

	@Override
	public Piece makeCopy() {
		return new Rook(getColor());
	}
}
