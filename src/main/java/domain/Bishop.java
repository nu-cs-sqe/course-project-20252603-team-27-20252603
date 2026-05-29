package domain;

public class Bishop extends Piece{
	public Bishop(Color color) {
		super(color);
	}

	@Override
	public PieceType getType() {

		return PieceType.BISHOP;
	}

	@Override
	public Piece makeCopy() {
		return new Bishop(getColor());
	}
}
