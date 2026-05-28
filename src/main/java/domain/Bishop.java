package domain;

public class Bishop extends Piece{
	public Bishop(Color color) {
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
