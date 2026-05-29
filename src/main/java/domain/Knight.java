package domain;

public class Knight extends Piece{
	public Knight(Color color) {
		super(color);
	}

	@Override
	public PieceType getType() {

		return PieceType.KNIGHT;
	}

	@Override
	public Piece makeCopy() {
		return new Rook(getColor());
	}
}
