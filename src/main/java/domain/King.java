package domain;

public class King extends Piece{
	public King(Color color) {
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
