package domain;

public class Queen extends Piece{
	public Queen(Color color) {
		super(color);
	}

	@Override
	public PieceType getType() {

		return PieceType.QUEEN;
	}

	@Override
	public Piece makeCopy() {
		return new Queen(getColor());
	}
}
